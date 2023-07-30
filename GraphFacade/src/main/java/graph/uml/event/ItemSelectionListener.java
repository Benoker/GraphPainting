package graph.uml.event;

import graph.uml.Item;
import graph.uml.UmlDiagram;

/**
 * This listener can be added to an {@link Item}, or an {@link UmlDiagram} and receives events
 * if the user selects or deselects an item.
 * @author Benjamin Sigg
 */
public interface ItemSelectionListener {
	/**
	 * Called if an item was selected or deselected
	 * @param event further description of the event
	 */
	public void itemSelectionChanged( ItemSelectionEvent event );
}
