package graph.model.capability;

/**
 * Ability of an item to be moved around.
 * @author Benjamin Sigg
 */
public interface MoveableCapability {
	/**
	 * Tells whether the item should be moved if the mouse is at position <code>x/y</code>
	 * @param x x-coordinate of the mouse
	 * @param y y-coordinate of the mouse
	 * @return whether the item should be moved
	 */
	public boolean isMovingEnabledAt( int x, int y );
	
	/**
	 * Informs the item that it now will be moved around, and that the mouse is at position <code>x/y</code>.
	 * @param x x-coordinate of the mouse
	 * @param y y-coordinate of the mouse
	 */
	public void startMoveAt( int x, int y );
	
	/**
	 * Informs the item that the mouse has moved to <code>x/y</code>. It is up to the item how to act on this information.
	 * @param x x-coordinate of the mouse
	 * @param y y-coordinate of the mouse
	 */
	public void moveTo( int x, int y );
	
	/**
	 * Informs the item that the mouse was released at <code>x/y</code>. It is up to the item how to act on this information.
	 * @param x x-coordinate of the mouse
	 * @param y y-coordinate of the mouse
	 */
	public void endMoveAt( int x, int y );

}
