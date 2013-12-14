package graph.uml;

import java.util.List;

/**
 * Contains all the methods required to build and modify an UML diagram.
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
	 * Gets access to some tools that can be used to modify the graph.
	 * @return all the tools
	 */
	public UmlDiagramTools getTools();
	
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
