package graph.model;

import graph.ui.Graph;

/**
 * Some kind of object that contains a list of {@link GraphItem}s. Usually
 * a {@link GraphItemParent} is connected to a {@link Graph}, and adding
 * items to it will make them appear on the graph.
 * @author Benjamin Sigg
 */
public interface GraphItemParent {
	/**
	 * Adds <code>item</code> to the list of children of this parent.
	 * @param item the item to add
	 */
	public void addItem( GraphItem item );
	
	/**
	 * Removes <code>item</code> from the list of children of this parent.
	 * @param item the item to remove
	 */
	public void removeItem( GraphItem item );
}
