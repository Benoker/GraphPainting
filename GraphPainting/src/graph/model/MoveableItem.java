package graph.model;

public interface MoveableItem {
	public boolean isMovingEnabledAt( int x, int y );

	public int getX();
	
	public int getY();

	public void moveTo( int x, int y );
}
