package graph.uml;

/**
 * An item is some kind of graphical effect that is part of an UML diagram.
 * @author Benjamin Sigg
 */
public interface Item {
	/**
	 * Tells whether this item is visible or not.
	 * @return the visibility
	 */
	public boolean isVisible();
	
	/**
	 * Sets the visibility of this item
	 * @param visible whether the user can see this item or not
	 */
	public void setVisible( boolean visible );
}
