package org.archstudio.graphlayout.core;

import org.eclipse.jface.preference.IPreferenceStore;

import org.archstudio.graphlayout.GraphLayout;
import org.archstudio.graphlayout.GraphLayoutException;
import org.archstudio.graphlayout.GraphLayoutParameters;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.xarchadt.ObjRef;

public interface ILayoutEngine {
	public String getID();

	public String getDescription();

	public GraphLayout layoutGraph(IXArchADT xarch, IPreferenceStore prefs, ObjRef rootRef, GraphLayoutParameters params) throws GraphLayoutException;
}
