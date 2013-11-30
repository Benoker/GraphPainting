package graph.items;

import graph.model.MoveableItem;
import graph.model.capability.MoveableCapability;

/**
 * An adapter encapsulating a {@link MoveableItem} adding the {@link MoveableCapability}, 
 * this adapter handles the offset between the position of the item and the position of 
 * the mouse.
 * @author Benjamin Sigg
 */
public class MoveableItemCapability implements MoveableCapability{
	private MoveableItem item;
	
	private int offsetX;
	private int offsetY;
	
	public MoveableItemCapability( MoveableItem item ){
		this.item = item;
	}

	@Override
	public boolean isMovingEnabledAt( int x, int y ) {
		return item.isMovingEnabledAt( x, y );
	}

	@Override
	public void startMoveAt( int x, int y ) {
		offsetX = item.getX() - x;
		offsetY = item.getY() - y;
	}
	
	@Override
	public void moveTo( int x, int y ) {
		item.moveTo( x + offsetX, y + offsetY );
	}

	@Override
	public void endMoveAt( int x, int y ) {
		moveTo( x, y );
	}
}
