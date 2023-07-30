package graph.uml;

import graph.uml.event.ItemContextListener;
import graph.uml.event.ItemSelectionListener;

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
	 * Gets a unique identifier that is tied to this <code>Item</code>. The key
	 * is neither <code>null</code>, nor does it change. Keys remain unique even if
	 * the Item is {@link #dispose() disposed}.
	 * @return a unique identifier for this item
	 */
	public ItemKey<? extends Item> getKey();
	
	/**
	 * Tells whether this {@link Item} is selected. Selection implies either {@link #isPrimarySelected()}
	 * or {@link #isSecondarySelected()}.
	 * @return whether this item is selected
	 */
	public boolean isSelected();
	
	/**
	 * Tells whether this {@link Item} is the primary selected {@link Item}. At any point in time there can
	 * be only one primary selected {@link Item} in an {@link UmlDiagram}.
	 * @return whether this item is the primary selected item
	 */
	public boolean isPrimarySelected();
	
	/**
	 * Tells whether this {@link Item} is a secondary selected {@link Item}. At any point in time there can
	 * be many secondary selected {@link Item}s in an {@link UmlDiagram}.
	 * @return whether this item is a secondary selected item
	 */
	public boolean isSecondarySelected();

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

	/**
	 * Adds <code>listener</code> to this item. The observer will receive an event
	 * if the user selects or deselects this item.
	 * @param listener
	 */
	public void addItemSelectionListener( ItemSelectionListener listener );

	/**
	 * Removes <code>listener</code> from this item.
	 * @param listener the listener to remove
	 */
	public void removeItemSelectionListener( ItemSelectionListener listener );
}
