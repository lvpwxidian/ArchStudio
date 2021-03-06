package org.archstudio.bna.utils;

import java.util.List;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.eclipse.swt.events.MouseEvent;

public interface IBNAMouseClickListener {

	public abstract void mouseClick(IBNAView view, MouseEvent evt, List<IThing> things, ICoordinate location);

	public abstract void mouseDoubleClick(IBNAView view, MouseEvent evt, List<IThing> things, ICoordinate location);

}