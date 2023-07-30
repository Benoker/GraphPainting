package graph.items.capability;

import graph.items.ConnectionFlavor;
import graph.model.GraphItemParent;
import graph.model.connection.GraphConnection;

/**
 * An {@link OpenEndedConnectionStrategy} is part of a {@link ConnectionableCapabilityHandler} and tells
 * the handler what to do if it has a connection with no target.
 * @author Benjamin Sigg
 */
public interface OpenEndedConnectionStrategy {
	/** Strategy that does nothing */
	public static final OpenEndedConnectionStrategy NULL = new OpenEndedConnectionStrategy(){

		@Override
		public void setParent( GraphItemParent parent ) {
			// ignore
		}

		@Override
		public void beginConnecting( GraphConnection connection, int x, int y, ConnectionFlavor flavor ) {
			// ignore			
		}

		@Override
		public void handleOpenEndedAt( int x, int y ) {
			// ignore
		}

		@Override
		public void handleNotOpenEnded( int x, int y ) {
			// ignore
		}

		@Override
		public void endConnecting() {
			// ignore	
		}
	};
	
	/**
	 * Allows this strategy to add items to the graph by
	 * invoking {@link GraphItemParent#addItem(graph.model.GraphItem)}. The
	 * parent is set to a value that is not <code>null</code> before calling
	 * any of the other methods of this interface.
	 * @param parent the parent, can be <code>null</code>
	 */
	public void setParent( GraphItemParent parent );
	
	/**
	 * Called once the user started connecting two items.
	 * @param connection the connection that is created
	 * @param x the x-coordinate of the mouse
	 * @param y the y-coordinate of the mouse
	 * @param flavor the kind of connection that is connected
	 */
	public void beginConnecting( GraphConnection connection, int x, int y, ConnectionFlavor flavor );
	
	/**
	 * Called if the mouse stops at a spot where there is no target for <code>connection</code>.
	 * @param x the x-coordinate of the mouse
	 * @param y the y-coordinate of the mouse
	 */
	public void handleOpenEndedAt( int x, int y );
	
	/**
	 * Called if the mouse stops at a spot where there is a target for <code>connection</code>.
	 * @param x the x-coordinate of the mouse
	 * @param y the y-coordinate of the mouse
	 */
	public void handleNotOpenEnded( int x, int y );
	
	/**
	 * Called once the connection has been created or has been canceled. May be called more
	 * than once.
	 */
	public void endConnecting();
}
