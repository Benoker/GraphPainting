package graph.items;

import java.awt.Rectangle;

import graph.model.GraphItem;
import graph.model.GraphMoveableItem;
import graph.model.GraphSite;
import graph.model.connection.Rectangular;

public abstract class MoveableRectangularGraphItem extends RectangularComposite implements GraphItem, GraphMoveableItem, Rectangular{
	private GraphMoveableAdapter moveableAdapter;
	
	public MoveableRectangularGraphItem(){
		moveableAdapter = new GraphMoveableAdapter( this );
	}
	
	@Override
	public void set( GraphSite site ) {
		super.set( site );
		moveableAdapter.set( site );
	}
	
	@Override
	public boolean contains( int x, int y ) {
		return getBoundaries().contains( x, y );
	}

	@Override
	public int getX() {
		return getBoundaries().x;
	}

	@Override
	public int getY() {
		return getBoundaries().y;
	}

	@Override
	public void moveTo( int x, int y ) {
		Rectangle bounds = getBoundaries();
		bounds.x = x;
		bounds.y = y;
		setBoundaries( bounds );
	}
}
