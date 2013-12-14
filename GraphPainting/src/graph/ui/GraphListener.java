package graph.ui;

import graph.model.GraphItem;

/**
 * An observer that is added to a {@link Graph} and that receives events if new {@link GraphItem}s
 * are added to the graph.
 * @author Benjamin Sigg
 */
public interface GraphListener {
	/**
	 * Called after <code>item</code> has been added to <code>source</code>. This method is
	 * only called for root items.
	 * @param source the source of the event
	 * @param item the new item
	 */
	public void itemAdded( Graph source, GraphItem item );
	
	/**
	 * Called after <code>item</code> has been removed from <code>source</code>. This method
	 * is only called for root items.
	 * @param source the source of the event
	 * @param item the item that was removed
	 */
	public void itemRemoved( Graph source, GraphItem item );
}
