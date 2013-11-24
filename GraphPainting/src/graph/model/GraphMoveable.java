package graph.model;

/**
 * An item that can be grabbed and moved around.
 * @author Benjamin Sigg
 */
public interface GraphMoveable {
	public boolean contains( int x, int y );
	
	public void startMoveAt( int x, int y );
	
	public void moveTo( int x, int y );
	
	public void endMoveAt( int x, int y );
}
