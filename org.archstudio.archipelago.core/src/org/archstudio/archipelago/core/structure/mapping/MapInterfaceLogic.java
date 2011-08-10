package org.archstudio.archipelago.core.structure.mapping;

import java.util.List;

import org.archstudio.archipelago.core.ArchipelagoUtils;
import org.archstudio.bna.constants.StickyMode;
import org.archstudio.bna.facets.IHasAnchorPoint;
import org.archstudio.bna.facets.IHasFlow;
import org.archstudio.bna.facets.IHasLineWidth;
import org.archstudio.bna.facets.IHasMutableText;
import org.archstudio.bna.facets.IHasText;
import org.archstudio.bna.facets.IHasToolTip;
import org.archstudio.bna.facets.IRelativeMovable;
import org.archstudio.bna.logics.coordinating.ReorientToThingIDLogic;
import org.archstudio.bna.logics.coordinating.ReparentToThingIDLogic;
import org.archstudio.bna.logics.coordinating.StickPointLogic;
import org.archstudio.bna.things.glass.EndpointGlassThing;
import org.archstudio.bna.utils.Assemblies;
import org.archstudio.bna.utils.BNAPath;
import org.archstudio.bna.utils.UserEditableUtils;
import org.archstudio.swtutils.constants.Flow;
import org.archstudio.xadl3.domain_3_0.DomainType;
import org.archstudio.xadl3.domain_3_0.Domain_3_0Package;
import org.archstudio.xadl3.structure_3_0.Direction;
import org.archstudio.xadlbna.logics.mapping.AbstractXADLToBNAPathLogic;
import org.archstudio.xadlbna.logics.mapping.SynchronizeObjRefAndThingIDLogic;
import org.archstudio.xadlbna.things.IHasXArchID;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.xarchadt.ObjRef;
import org.eclipse.draw2d.geometry.Point;

public class MapInterfaceLogic extends AbstractXADLToBNAPathLogic<EndpointGlassThing> {

	private static final IXADLToBNATranslator<Direction, Flow> DIRECTION_TO_FLOW = new IXADLToBNATranslator<Direction, Flow>() {

		@Override
		public Flow toBNAValue(Direction xadlValue) {
			return Flow.valueOf(xadlValue.getName().toUpperCase());
		}

		@Override
		public Direction toXadlValue(Flow value) {
			return Direction.valueOf(value.name().toLowerCase());
		}
	};

	private static final IXADLToBNATranslator<String, Integer> DOMAIN_TO_EDGE_WIDTH = new IXADLToBNATranslator<String, Integer>() {

		@Override
		public Integer toBNAValue(String xadlValue) {
			return DomainType.TOP.equals(xadlValue) ? 2 : 1;
		};

		@Override
		public String toXadlValue(Integer value) {
			throw new UnsupportedOperationException();
		};
	};

	SynchronizeObjRefAndThingIDLogic syncLogic = null;
	ReparentToThingIDLogic reparentLogic = null;
	ReorientToThingIDLogic reorientLogic = null;
	StickPointLogic stickLogic = null;

	public MapInterfaceLogic(IXArchADT xarch, ObjRef rootObjRef, String objRefPath) {
		super(xarch, rootObjRef, objRefPath);
	}

	@Override
	public void init() {
		super.init();

		syncLogic = addThingLogic(SynchronizeObjRefAndThingIDLogic.class);
		reparentLogic = addThingLogic(ReparentToThingIDLogic.class);
		reorientLogic = addThingLogic(ReorientToThingIDLogic.class);
		stickLogic = addThingLogic(StickPointLogic.class);

		syncAttribute("direction", DIRECTION_TO_FLOW, Flow.NONE, BNAPath.create(Assemblies.LABEL_KEY),
				IHasFlow.FLOW_KEY, true);
		syncAttribute("id", null, null, BNAPath.create(), IHasXArchID.XARCH_ID_KEY, true);
		syncAttribute("name", null, "[no name]", BNAPath.create(Assemblies.TEXT_KEY), IHasText.TEXT_KEY, true);
		syncAttribute("name", null, "[no name]", BNAPath.create(), IHasToolTip.TOOL_TIP_KEY, true);
		syncXAttribute(
				"ext[*[namespace-uri()='" + Domain_3_0Package.eNS_URI + "']]/domain/@type",
				//"ext[*[namespace-uri()='http://www.archstudio.org/xadl3/schemas/domain-3.0.xsd')]]/Domain/type",
				//"ext[*[starts-with(namespace-uri(), 'http://www.archstudio.org/xadl3/schemas/domain-')]]/Domain/type",
				DOMAIN_TO_EDGE_WIDTH, null, BNAPath.create(Assemblies.BACKGROUND_KEY), IHasLineWidth.LINE_WIDTH_KEY,
				false);
	}

	@Override
	protected EndpointGlassThing addThing(List<ObjRef> relativeAncestorRefs, ObjRef objRef) {

		EndpointGlassThing thing = Assemblies.createEndpoint(getBNAWorld(), null, null);
		Point newPointSpot = ArchipelagoUtils.findOpenSpotForNewThing(getBNAWorld().getBNAModel());
		thing.setAnchorPoint(newPointSpot);

		UserEditableUtils.addEditableQualities(thing, IRelativeMovable.USER_MAY_MOVE);
		UserEditableUtils.addEditableQualities(Assemblies.TEXT_KEY.get(thing, getBNAModel()),
				IHasMutableText.USER_MAY_EDIT_TEXT);

		Assemblies.BACKGROUND_KEY.get(thing, getBNAModel())
				.set(syncLogic.syncObjRefKeyToThingIDKey(reparentLogic.getReparentToThingKey()),
						relativeAncestorRefs.get(1));

		Assemblies.LABEL_KEY.get(thing, getBNAModel())
				.set(syncLogic.syncObjRefKeyToThingIDKey(reorientLogic.getReorientToThingKey()),
						relativeAncestorRefs.get(1));

		stickLogic.setStickyMode(thing, IHasAnchorPoint.ANCHOR_POINT_KEY, StickyMode.EDGE);
		thing.set(syncLogic.syncObjRefKeyToThingIDKey(stickLogic.getThingRefKey(IHasAnchorPoint.ANCHOR_POINT_KEY)),
				relativeAncestorRefs.get(1));

		return thing;
	}
}
