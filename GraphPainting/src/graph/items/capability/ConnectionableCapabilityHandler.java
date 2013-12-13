package graph.items.capability;

import graph.items.ConnectionFlavor;
import graph.items.ConnectionableCapability;
import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityHandlerSite;
import graph.model.connection.ConnectionArray;
import graph.model.connection.GraphConnection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Handler allowing to connect two {@link ConnectionableCapability}s. The handler is associated with
 * a {@link ConnectionFactory}, the handler grabs all mouse input if a factory is set, otherwise
 * it is inactive.
 * @author Benjamin Sigg
 */
public class ConnectionableCapabilityHandler implements CapabilityHandler<ConnectionableCapability>{
	private CapabilityHandlerSite<ConnectionableCapability> site;
	private ConnectionFactory factory;
	private OpenEndedConnectionStrategy openEnded = OpenEndedConnectionStrategy.NULL;
	
	/** the start of the connection that is currently manufactured */
	private ConnectionableCapability sourceItem;
	private ConnectionArray sourceArray;
	private int sourceX;
	private int sourceY;
	
	/** the end of the connection that is currently manufactured */
	private ConnectionableCapability targetItem;
	private ConnectionArray targetArray;
	
	/** the connection that is currently manufactured */
	private GraphConnection connection;
	
	/**
	 * Sets the factory which is responsible for actually creating new connections. This handler
	 * will consume all mouse input as a factory is set. If no factory is set, then this handler
	 * becomes inactive.
	 * @param factory the factory creating new connections, can be <code>null</code>
	 */
	public void setFactory( ConnectionFactory factory ) {
		this.factory = factory;
		
		if( site != null ){
			if( factory == null ){
				site.listening();
			}
			else{
				site.triggered();
			}
		}
	}
	
	/**
	 * Gets the factory that is currently used to build new connections.
	 * @return the factory, can be <code>null</code>
	 */
	public ConnectionFactory getFactory() {
		return factory;
	}
	
	/**
	 * Sets the strategy responsible for handling cases where there is target for
	 * new connections
	 * @param openEnded the new strategy, may be <code>null</code>
	 */
	public void setOpenEnded( OpenEndedConnectionStrategy openEnded ) {
		if( openEnded == null ){
			openEnded = OpenEndedConnectionStrategy.NULL;
		}
		
		this.openEnded.endConnecting();
		this.openEnded.setParent( null );
		
		this.openEnded = openEnded;
		this.openEnded.setParent( site );
	}
	
	@Override
	public void init( CapabilityHandlerSite<ConnectionableCapability> site ) {
		this.site = site;
		site.addMouseListener( mouseListener() );
		site.addMouseMotionListener( mouseMotionListener() );
		site.addKeyListener( keyListener() );
		
		if( factory != null ){
			site.triggered();
		}
		openEnded.setParent( site );
	}

	@Override
	public void dispose() {
		cancelConnecting();
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		if( !enabled ){
			cancelConnecting();
		}
	}
	
	private void startConnectingAt( int x, int y ){
		if( connection != null ){
			return;
		}
		sourceItem = getSourceCapability( x, y );
		if( sourceItem == null ){
			return;
		}
		sourceX = x;
		sourceY = y;
		startConnectingAt( true );
		openEnded.beginConnecting( connection, x, y, factory.getFlavor() );
		continueConnectingAt( x, y );
	}
	
	private void startConnectingAt( boolean constructing ){
		connection = factory.createConnection();
		ConnectionFlavor flavor = factory.getFlavor();
		ConnectionArray array = sourceItem.getSourceArray( sourceX, sourceY, flavor, constructing );
		
		if( array != sourceArray ){
			if( targetArray != null ){
				if( sourceArray != null ){
					sourceArray.remove( connection.getSourceEndPoint() );
				}
				array.add( connection.getSourceEndPoint() );
			}
			sourceArray = array;
		}
	}
	
	private void finishConnectingAt( int x, int y ){
		if( connection == null ){
			return;
		}
		startConnectingAt( false );
		connectAt( x, y, false );
		
		if( targetArray == null ){
			cancelConnecting();
		}
		else{
			connected( connection, sourceItem, targetItem );
			
			connection = null;
			targetArray = null;
			sourceArray = null;
			sourceItem = null;
		}
		openEnded.endConnecting();
	}
	
	/**
	 * Called if a connection was made. Subclasses may modify the connection at this point in time.
	 * @param connection the connection that was made
	 * @param sourceItem the source of the connection
	 * @param targetItem the target of the connection
	 */
	protected void connected( GraphConnection connection, ConnectionableCapability sourceItem, ConnectionableCapability targetItem ){
		// nothing
	}
	
	private void continueConnectingAt( int x, int y ){
		if( connection == null ){
			return;
		}
		connectAt( x, y, true );
	}
	
	private void connectAt( int x, int y, boolean constructing ){
		ConnectionableCapability nextTargetItem = getTargetCapability( x, y );
		ConnectionArray nextTargetArray = null;
		if( nextTargetItem != null ){
			ConnectionFlavor flavor = factory.getFlavor();
			nextTargetArray = nextTargetItem.getTargetArray( x, y, flavor, constructing );
		}
		if( nextTargetArray != targetArray ){
			if( targetArray != null ){
				targetArray.remove( connection.getTargetEndPoint() );
			}
			if( nextTargetArray != null ){
				openEnded.handleNotOpenEnded( x, y );
				nextTargetArray.add( connection.getTargetEndPoint() );
			}
			
			if( targetArray == null && nextTargetArray != null ){
				sourceArray.add( connection.getSourceEndPoint() );
				site.addItem( connection );
			}
			if( targetArray != null && nextTargetArray == null ){
				sourceArray.remove( connection.getSourceEndPoint() );
				site.removeItem( connection );
			}
		}
		if( nextTargetArray == null ){
			openEnded.handleOpenEndedAt( x, y );
		}
		
		targetItem = nextTargetItem;
		targetArray = nextTargetArray;		
	}
	
	private void cancelConnecting(){
		if( connection != null ){
			site.removeItem( connection );
			if( sourceArray != null ){
				sourceArray.remove( connection.getSourceEndPoint() );
			}
			if( targetArray != null ){
				targetArray.remove( connection.getTargetEndPoint() );
			}
			
			connection = null;
		}
		
		sourceArray = null;
		targetArray = null;
		sourceItem = null;
		openEnded.endConnecting();
	}
	
	private ConnectionableCapability getSourceCapability( int x, int y ){
		float bestScore = 1.0f;
		ConnectionableCapability result = null;
		ConnectionFlavor flavor = factory.getFlavor();
		
		for( ConnectionableCapability capability : site.getCapabilities() ){
			if( capability.isSource( flavor )){
				float score = capability.containsConnectionable( x, y );
				if( score >= bestScore ){
					bestScore = score;
					result = capability;
				}
			}
		}
		
		return result;
	}
	
	private ConnectionableCapability getTargetCapability( int x, int y ){
		float bestScore = 0.0f;
		ConnectionableCapability result = null;
		ConnectionFlavor flavor = factory.getFlavor();
		
		for( ConnectionableCapability capability : site.getCapabilities() ){
			if( capability.isTarget( flavor )){
				if( capability.allowSource( sourceItem, flavor ) && sourceItem.allowTarget( capability, flavor )){
					float score = capability.containsConnectionable( x, y );
					if( score > bestScore ){
						bestScore = score;
						result = capability;
					}
				}
			}
		}
		
		return result;
	}
	
	private KeyListener keyListener(){
		return new KeyAdapter() {
			@Override
			public void keyPressed( KeyEvent e ) {
				if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
					cancelConnecting();
				}
			}
		};
	}
	
	private MouseListener mouseListener(){
		return new MouseAdapter() {
			@Override
			public void mousePressed( MouseEvent e ) {
				if( factory != null ){
					if( onlyButtonOne( e )){
						startConnectingAt( e.getX(), e.getY() );
					}
				}
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( e.getButton() == MouseEvent.BUTTON1 ){
					finishConnectingAt( e.getX(), e.getY() );
				}
			}
		};
	}
	
	private MouseMotionListener mouseMotionListener(){
		return new MouseAdapter() {
			@Override
			public void mouseDragged( MouseEvent e ) {
				continueConnectingAt( e.getX(), e.getY() );
			}
		};
	}
	
	private boolean onlyButtonOne( MouseEvent e ){
		if( e.getButton() == MouseEvent.BUTTON1 ){
			int modifiers = e.getModifiersEx();
			int mask = MouseEvent.BUTTON2_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK | 
					MouseEvent.SHIFT_DOWN_MASK | MouseEvent.CTRL_DOWN_MASK | MouseEvent.ALT_DOWN_MASK | 
					MouseEvent.ALT_GRAPH_DOWN_MASK | MouseEvent.META_DOWN_MASK;
			
			return (modifiers & mask) == 0;
		}
		else{
			return false;
		}
	}
}
