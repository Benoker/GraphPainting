package graph.uml;

import java.util.List;

/**
 * Contains all the methods required to build and modify an UML diagram.<br>
 * 
 * @author Benjamin Sigg
 */
public interface UmlDiagram {
	/**
	 * Creates a new {@link TypeBox}, the box has neither content, nor location, nor connections, not is
	 * it visible.
	 * @return the new, empty, invisible typebox
	 */
	public TypeBox createType();
	
	/**
	 * Gets the current selection in an unmutable list. Changes in the selection may or may not be
	 * reflected by this list.
	 * @return the list of selected items
	 */
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
}
