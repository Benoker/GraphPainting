package graph.model.capability;

/**
 * Capability of an item to have a context menu.
 * @author Benjamin Sigg
 */
public interface ContextCapability {
	/**
	 * Informs this item that the context-menu should be shown.
	 * @param site a description of the position of the mouse, and some helpful 
	 * methods to open a context menu 
	 */
	public void context( ContextSite site );
}
