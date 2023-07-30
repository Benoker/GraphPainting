package graph.uml.event;

import graph.uml.Item;
import graph.uml.UmlDiagram;

/**
 * A listener that is added to an {@link UmlDiagram} and which will be invoked whenever {@link Item}s are
 * added or removed from the diagram.
 * @author Benjamin Sigg
 */
public interface UmlDiagramListener {
	/**
	 * Called after <code>item</code> was added to <code>diagram</code>
	 * @param diagram the source of the event
	 * @param item the new item on <code>diagram</code>
	 */
	public void itemAdded( UmlDiagram diagram, Item item );
	
	/**
	 * Called after <code>item</code> was removed from <code>diagram</code>
	 * @param diagram the source of the event
	 * @param item the item that was removed
	 */
	public void itemRemoved( UmlDiagram diagram, Item item );
}
