package graph.model;

public interface GraphMoveableItem {
	public boolean contains( int x, int y );

	public int getX();
	
	public int getY();

	public void moveTo( int x, int y );
}
