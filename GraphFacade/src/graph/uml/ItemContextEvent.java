package graph.uml;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * Describes an event that is triggering the popup menu.
 * @author Benjamin Sigg
 */
public interface ItemContextEvent {
	/**
	 * Sets the {@link #isConsumed() consumed} flag, notifying other listeners that this
	 * event is already "used up".
	 */
	public void consume();
	
	/**
	 * Tells whether this event is already used up.
	 * @return whether the event has already be {@link #consume() consumed}
	 */
	public boolean isConsumed();
	
	/**
	 * Gets the x-coordinate of the mouse.
	 * @return the mouse position
	 */
	public int getX();
	
	/**
	 * Gets the y-coordinate of the mouse.
	 * @return the mouse positions
	 */
	public int getY();
	
	/**
	 * Gets the {@link Item} that has triggered this event, it may or may not be
	 * selected.
	 * @return the source of the event, not <code>null</code>
	 */
	public Item getSource();
	
	/**
	 * Gets an immutable list containing all the items that are currently selected. This
	 * list may or may not contain {@link #getSource() the source}.
	 * @return the current selection, may be empty
	 */
	public List<Item> getSelection();
	
	/**
	 * Gets the {@link Component} on which a mouse event was registered.
	 * @return the component onto which the user clicked, not <code>null</code>
	 */
	public Component getComponent();
	
	/**
	 * Shows the popup menu <code>menu</code> at the position of the mouse.
	 * @param menu the menu to show
	 */
	public void show( JPopupMenu menu );
	
	/**
	 * Shows all {@link JComponent}s of <code>menuEntries</code> in a popup menu.
	 * @param menuEntries the items to show
	 */
	public void show( Iterable<JComponent> menuEntries );
}
