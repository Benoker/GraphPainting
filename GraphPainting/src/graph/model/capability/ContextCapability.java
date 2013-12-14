package graph.model.capability;

/**
 * Capability of an item to have a context menu.
 * @author Benjamin Sigg
 */
public interface ContextCapability {
	/**
	 * Tells whether the context menu should be shown for this item if the mouse is at <code>x/y</code>
	 * @param x x-coordinate of the mouse
	 * @param y y-coordinate of the mouse
	 * @return whether the context menu should be shown, a value below <code>0.f</code> means no, and a value
	 * of <code>1.f</code> or higher means yes
	 */
	public float isContextMenuEnabledAt( int x, int y );
	
	/**
	 * Informs this item that the context-menu should be shown.
	 * @param site a description of the position of the mouse, and some helpful 
	 * methods to open a context menu 
	 */
	public void context( ContextSite site );
}
