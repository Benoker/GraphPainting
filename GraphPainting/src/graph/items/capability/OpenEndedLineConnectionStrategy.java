package graph.items.capability;

import java.awt.Color;

import graph.items.ConnectionFlavor;
import graph.items.Line;
import graph.model.GraphItemParent;
import graph.model.connection.GraphConnection;

/**
 * This strategy paints a simple line if there is no target for a connection.
 * @author Benjamin Sigg
 */
public class OpenEndedLineConnectionStrategy implements OpenEndedConnectionStrategy{
	private GraphItemParent parent;
	
	private Line line;
	private boolean itemShown = false;
	
	/**
	 * Creates a new strategy.
	 */
	public OpenEndedLineConnectionStrategy(){
		this( new Line() );
	}
	
	/**
	 * Creates a new strategy.
	 * @param line the line that should be painted
	 */
	public OpenEndedLineConnectionStrategy( Line line ){
		this.line = line;
		line.setColor( new Color( 100, 0, 0 ) );
	}
	
	@Override
	public void setParent( GraphItemParent parent ) {
		this.parent = parent;
	}

	@Override
	public void beginConnecting( GraphConnection connection, int x, int y, ConnectionFlavor flavor ) {
		line.setPoint1( x, y );
	}

	@Override
	public void handleOpenEndedAt( int x, int y ) {
		if( !itemShown ){
			itemShown = true;
			parent.addItem( line );
		}
		
		line.setPoint2( x, y );
	}

	@Override
	public void handleNotOpenEnded( int x, int y ) {
		if( itemShown ){
			parent.removeItem( line );
		}
		itemShown = false;
	}

	@Override
	public void endConnecting() {
		if( itemShown ){
			parent.removeItem( line );
		}
		itemShown = false;
	}
}
