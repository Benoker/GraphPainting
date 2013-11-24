package graph.model;

import graph.ui.GraphPanel;

/**
 * Represents an element that may be painted or interacts with the user on
 * a {@link GraphPanel}. 
 * @author Benjamin Sigg
 */
public interface GraphItem {
	/**
	 * Connects this box to a {@link GraphPanel} that is represented by <code>site</code>.
	 * A value of <code>null</code> means that this box has to unregister itself from
	 * the last known <code>site</code>.
	 * @param site the panel painting this box, or <code>null</code>
	 */
	public void set( GraphSite site );
}
