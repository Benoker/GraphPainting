package graph.items;

import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;

/**
 * Describes an item that can be connected with another item
 * @author Benjamin Sigg
 */
public interface ConnectionableCapability {
	/**
	 * Tells whether a connection can be added to this item if the mouse hovers over <code>x/y</code>. The result
	 * of this method is a <code>float</code>:
	 * <ul>
	 * 	<li>A value of <code>0f</code> or below means that the mouse is too far away from the item to have any effect.</li>
	 *  <li>A value between <code>0f</code> (excl.) to <code>1f</code> (excl.) means that the mouse is close, but not inside the item.</li>
	 *  <li>A value of <code>1f</code> means that the mouse is exactly on the border of the item.</li>
	 *  <li>A value higher than <code>1f</code> means the mouse is inside the item.</li> 
	 * </ul>
	 * When creating a connection all {@link ConnectionableCapability}s are asked for {@link #containsConnectionable(int, int)}, and
	 * the one object with the highest result wins. 
	 * @param x x-coordinate
	 * @param y x-coordinate
	 * @return a value indicating how good this item is hit by the mouse
	 */
	public float containsConnectionable( int x, int y );
	
	/**
	 * Tells whether this item can be the source of a connection of type <code>flavor</code>.
	 * @param flavor the kind of connection that is about to be created
	 * @return whether this item can be the source of a new connection
	 */
	public boolean isSource( ConnectionFlavor flavor );
	
	/**
	 * Tells whether this item can be the target of a connection of type <code>flavor</code>.
	 * @param flavor the kind of connection that is about to be created
	 * @return whether this item can be the target of a new connection
	 */
	public boolean isTarget( ConnectionFlavor flavor );
	
	/**
	 * Tells whether this item can be connected to <code>item</code>, when <code>this</code> is the
	 * target of the connection. 
	 * @param item the source of the connection
	 * @param flavor the kind of connection, must already have passed {@link #isSource(ConnectionFlavor)} and 
	 * {@link #isTarget(ConnectionFlavor)} on <code>this</code> and <code>item</code>
	 * @return whether the connection is possible
	 */
	public boolean allowSource( ConnectionableCapability item, ConnectionFlavor flavor  );
	
	/**
	 * Tells whether this item can be connected to <code>item</code>, assuming <code>this</code> is 
	 * the source of the connection.
	 * @param item the target of the connection
	 * @param flavor the kind of connection, must already have passed {@link #isSource(ConnectionFlavor)} and 
	 * {@link #isTarget(ConnectionFlavor)} on <code>this</code> and <code>item</code>
	 * @return whether the connection is possible
	 */
	public boolean allowTarget( ConnectionableCapability item, ConnectionFlavor flavor );

	/**
	 * Gets the {@link ConnectionArray} to which a new {@link EndPoint} is added during construction of a new
	 * connection. This method will be called two times: when the mouse is pressed and construction begins,
	 * and after construction ends to set the final resting place of the new {@link EndPoint}.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param flavor the kind of connection that is created
	 * @param constructing whether the connection is still under construction, or <code>false</code> if
	 * the final resting point of the connection is searched
	 * @return the array to which the connection will be connected, must not be <code>null</code>
	 */
	public ConnectionArray getSourceArray( int x, int y, ConnectionFlavor flavor, boolean constructing );
	
	/**
	 * Gets the {@link ConnectionArray} to which a new {@link EndPoint} is added.
	 * @param flavor the flavor of the endpoint
	 * @return the array, not <code>null</code>
	 */
	public ConnectionArray getSourceArray( ConnectionFlavor flavor );
	
	/**
	 * Gets the {@link ConnectionArray} to which a new {@link EndPoint} is added during construction of a new
	 * connection. This method will be called whenever the mouse is moved, and one time afterwards to find
	 * the final resting point of the new connection.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param flavor the kind of connection that is created
	 * @param constructing whether the connection is still under construction, or <code>false</code> if
	 * the final resting point of the connection is searched
	 * @return the array to which the connection will be connected, must not be <code>null</code>
	 */
	public ConnectionArray getTargetArray( int x, int y, ConnectionFlavor flavor, boolean constructing );
	
	/**
	 * Gets the {@link ConnectionArray} to which a new {@link EndPoint} is added.
	 * @param flavor the flavor of the endpoint
	 * @return the array, not <code>null</code>
	 */
	public ConnectionArray getTargetArray( ConnectionFlavor flavor );
}
