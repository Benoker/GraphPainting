package graph.model.capability;

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
}
