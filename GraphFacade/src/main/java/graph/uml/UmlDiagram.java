package graph.uml;

import graph.uml.event.ItemContextListener;
import graph.uml.event.ItemSelectionListener;
import graph.uml.event.UmlDiagramListener;

import java.util.List;

/**
 * Contains all the methods required to build and modify an UML diagram.<br>
 * 
 * @author Benjamin Sigg
 */
public interface UmlDiagram {
	/**
	 * Creates a new {@link TypeBox}, the box has neither content, nor location, nor connections.
	 * @return the new, empty, invisible typebox
	 */
	public TypeBox createType();

	/**
	 * Gets the current selection in an unmutable list. Changes in the selection may or may not be
	 * reflected by this list.
	 * @return the list of selected items
	 * @deprecated please use {@link #getSelectedItems()} instead
	 */
	@Deprecated
	public List<Item> getSelection();

	/**
	 * Gets all the items that are shown on this diagram. Adding or removing items to the list is not possible,
	 * changes on the diagram may or may not be reflected by this list.
	 * @return all the items in an unmodifiable list
	 */
	public List<Item> getItems();

	/**
	 * Searches for the one {@link Item} whose {@link Item#getKey()} equals <code>key</code>.
	 * @param key the identifier of the item to search
	 * @return the item or <code>null</code> if not found
	 */
	public <T extends Item> T getItem( ItemKey<T> key );

	/**
	 * Gets all the {@link Item}s that are currently selected. The first entry of the list
	 * is equal to {@link #getSelectedItem()}.
	 * @return all the currently selected items, may be empty
	 */
	public List<Item> getSelectedItems();
	
	/**
	 * Sets all the currently selected {@link Item}s. The result of {@link #getSelectedItem()} will be the 
	 * first entry of <code>items</code>.
	 * @param items the selected items, can be empty or <code>null</code>
	 */
	public void setSelectedItems( Iterable<Item> items );
	
	/**
	 * Gets the currently selected {@link Item}.
	 * @return the selected item or <code>null</code>
	 * @see #getSelectedItem()
	 */
	public Item getSelectedItem();
	
	/**
	 * Sets the {@link Item} that should be selected.
	 * @param item the item to select, can be <code>null</code>
	 */
	public void setSelectedItem( Item item );
	
	/**
	 * Adds an observer that will be informed when {@link Item}s are added or removed from this diagram.
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addUmlDiagramListener( UmlDiagramListener listener );

	/**
	 * Removes <code>listener</code> from this diagram.
	 * @param listener the observer to be removed
	 */
	public void removeUmlDiagramListener( UmlDiagramListener listener );

	/**
	 * Adds an observer that will be informed whenever the user clicks onto an item.
	 * @param listener the new observer
	 */
	public void addItemContextListener( ItemContextListener listener );

	/**
	 * Removes the observer <code>listener</code>.
	 * @param listener the observer to remove
	 */
	public void removeItemContextListener( ItemContextListener listener );

	/**
	 * Adds an observer that will be informed if any {@link Item} gets selected or deselected.
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addItemSelectionListener( ItemSelectionListener listener );

	/**
	 * Removes the observer <code>listener</code>.
	 * @param listener listener the observer to remove
	 */
	public void removeItemSelectionListener( ItemSelectionListener listener );
}
