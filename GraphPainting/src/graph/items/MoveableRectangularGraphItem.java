package graph.items;

import graph.model.GraphItem;
import graph.model.MoveableItem;
import graph.model.capability.CapabilityName;
import graph.model.connection.Rectangular;

import java.awt.Rectangle;

public abstract class MoveableRectangularGraphItem extends RectangularComposite implements GraphItem, MoveableItem, Rectangular{
	public MoveableRectangularGraphItem(){
		setCapability( CapabilityName.MOVEABLE, new MoveableItemCapability( this ) );
	}
	
	@Override
	public boolean isMovingEnabledAt( int x, int y ) {
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
