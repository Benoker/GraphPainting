package graph.uml;

/**
 * An item is some kind of graphical effect that is part of an UML diagram.
 * @author Benjamin Sigg
 */
public interface Item {
	/**
	 * Removes this item (and all items that depend on this item) from the graph.
	 */
	public void dispose();
	
	/**
	 * Adds <code>listener</code> to this item, the observer will receive an event
	 * if the user right clicks onto this item.<br>
	 * It is possible to add more than one {@link ItemContextListener} to an {@link Item},
	 * and it is the responsibility of the client to open only one popup menu
	 * @param listener a new listener, not <code>null</code>
	 */
	public void addItemContextListener( ItemContextListener listener );
	
	/**
	 * Removes <code>listener</code> from this item.
	 * @param listener the listener to remove
	 */
	public void removeItemContextListener( ItemContextListener listener );
}
