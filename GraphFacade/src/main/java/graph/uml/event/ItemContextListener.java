package graph.uml.event;

import graph.uml.Item;

/**
 * A listener that can be added to an {@link Item} and receives an event if the user performs
 * a "context action" - an action that should open a popup menu.
 * @author Benjamin Sigg
 */
public interface ItemContextListener {
	/**
	 * Called if the user triggered the popup action on an {@link Item}.
	 * @param event describes the current selection and the event
	 */
	public void contextAction( ItemContextEvent event );
}
