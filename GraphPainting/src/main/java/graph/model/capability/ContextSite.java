package graph.model.capability;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * Information required by a {@link ContextCapability} to open a context menu.
 * @author Benjamin Sigg
 */
public interface ContextSite {
	/**
	 * Gets the position of the mouse.
	 * @return x-coordinate of the mouse
	 */
	public int getX();

	/**
	 * Gets the position of the mouse.
	 * @return y-coordinate of the mouse
	 */
	public int getY();
	
	/**
	 * Gets a {@link Component} which can be used for calling {@link JPopupMenu#show(Component, int, int)}.
	 * @return the invoking component, not <code>null</code>
	 */
	public Component getInvoker();
	
	/**
	 * Gets a popup menu. The first time this method is called the menu is empty. 
	 * @return the menu, not <code>null</code>
	 */
	public JPopupMenu getMenu();
	
	/**
	 * Opens <code>menu</code> at <code>x/y</code>. The menu is closed if another popup menu is to be opened.
	 * @param menu the menu to show, can be {@link #getMenu()}
	 */
	public void show( JPopupMenu menu );
	
	/**
	 * Adds all components of <code>menuEntries</code> to the {@link #getMenu() menu} and then shows
	 * the menu at <code>x/y</code>. 
	 * @param menuEntries the menu to show
	 */
	public void showMenu( Iterable<JComponent> menuEntries );
}
