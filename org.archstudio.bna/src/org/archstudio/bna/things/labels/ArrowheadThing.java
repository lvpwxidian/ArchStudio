package org.archstudio.bna.things.labels;

import org.archstudio.bna.IThingListener;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.constants.ArrowheadShape;
import org.archstudio.bna.facets.IHasBoundingBox;
import org.archstudio.bna.facets.IHasLineStyle;
import org.archstudio.bna.facets.IHasMutableArrowhead;
import org.archstudio.bna.facets.IHasMutableColor;
import org.archstudio.bna.facets.IHasMutableEdgeColor;
import org.archstudio.bna.facets.IHasMutableLineData;
import org.archstudio.bna.facets.IHasMutableSecondaryAnchorPoint;
import org.archstudio.bna.facets.IHasMutableSecondaryColor;
import org.archstudio.bna.things.AbstractMutableAnchorPointThing;
import org.archstudio.bna.utils.ArrowheadUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

public class ArrowheadThing extends AbstractMutableAnchorPointThing implements IHasMutableColor,
		IHasMutableSecondaryColor, IHasMutableEdgeColor, IHasMutableArrowhead, IHasMutableSecondaryAnchorPoint,
		IHasMutableLineData, IHasBoundingBox {

	public ArrowheadThing(Object id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setArrowheadShape(ArrowheadShape.TRIANGLE);
		setArrowheadSize(20);
		addThingListener(new IThingListener() {
			@Override
			public void thingChanged(ThingEvent thingEvent) {
				if (!IHasBoundingBox.BOUNDING_BOX_KEY.equals(thingEvent.getPropertyName())) {
					Point lp1 = getAnchorPoint();
					Point lp2 = getSecondaryAnchorPoint();
					int arrowheadSize = getArrowheadSize();
					int[] xyPoints = ArrowheadUtils.calculateTriangularArrowhead(lp2.x, lp2.y, lp1.x, lp1.y,
							arrowheadSize);
					if (xyPoints != null) {
						Rectangle r = new Rectangle(lp1.x, lp1.y, 0, 0);
						for (int i = 0; i < 6; i += 2) {
							r.add(new Rectangle(xyPoints[i], xyPoints[i + 1], 0, 0));
						}
						set(IHasBoundingBox.BOUNDING_BOX_KEY, r);
					}
				}
			}
		});
		setSecondaryAnchorPoint(new Point(0, 0));
		setColor(new RGB(128, 128, 128));
		setSecondaryColor(new RGB(0, 0, 0));
		setEdgeColor(new RGB(0, 0, 0));
		setLineStyle(IHasLineStyle.LINE_STYLE_SOLID);
		setLineWidth(1);
	}

	@Override
	public int getArrowheadSize() {
		return get(ARROWHEAD_SIZE_KEY, 20);
	}

	@Override
	public void setArrowheadSize(int arrowheadSize) {
		set(ARROWHEAD_SIZE_KEY, arrowheadSize);
	}

	@Override
	public void setColor(@Nullable RGB c) {
		set(COLOR_KEY, c);
	}

	@Override
	public @Nullable
	RGB getColor() {
		return get(COLOR_KEY);
	}

	@Override
	public void setSecondaryColor(@Nullable RGB c) {
		set(SECONDARY_COLOR_KEY, c);
	}

	@Override
	public @Nullable
	RGB getSecondaryColor() {
		return get(SECONDARY_COLOR_KEY);
	}

	@Override
	public void setEdgeColor(@Nullable RGB c) {
		set(EDGE_COLOR_KEY, c);
	}

	@Override
	public @Nullable
	RGB getEdgeColor() {
		return get(EDGE_COLOR_KEY);
	}

	@Override
	public ArrowheadShape getArrowheadShape() {
		return get(ARROWHEAD_SHAPE_KEY, ArrowheadShape.TRIANGLE);
	}

	@Override
	public void setArrowheadShape(ArrowheadShape arrowheadShape) {
		set(ARROWHEAD_SHAPE_KEY, arrowheadShape);
	}

	@Override
	public Point getSecondaryAnchorPoint() {
		return get(SECONDARY_ANCHOR_POINT_KEY, new Point(0, 0));
	}

	@Override
	public void setSecondaryAnchorPoint(Point secondaryAnchorPoint) {
		set(SECONDARY_ANCHOR_POINT_KEY, secondaryAnchorPoint);
	}

	@Override
	public int getLineStyle() {
		return get(LINE_STYLE_KEY);
	}

	@Override
	public void setLineStyle(int lineStyle) {
		set(LINE_STYLE_KEY, lineStyle);
	}

	@Override
	public int getLineWidth() {
		return get(LINE_WIDTH_KEY);
	}

	@Override
	public void setLineWidth(int lineWidth) {
		set(LINE_WIDTH_KEY, lineWidth);
	}

	@Override
	public Rectangle getBoundingBox() {
		return get(BOUNDING_BOX_KEY);
	}
}
