package graph.items;

import graph.model.GraphItem;
import graph.model.MoveableItem;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.model.connection.Rectangular;

import java.awt.Rectangle;

public abstract class MoveableRectangularGraphItem extends RectangularComposite implements GraphItem, MoveableItem, Rectangular{
	private boolean moveableIfSelected = true;
	
	public MoveableRectangularGraphItem(){
		setCapability( CapabilityName.MOVEABLE, new MoveableItemCapability( this ) );
	}
	
	/**
	 * Sets whether being selected implies being moveable. This parameter has an effect on
	 * {@link #isMovingEnabledAt(int, int)}, but it also requires that a {@link SelectableCapability} is added to
	 * this item. If no {@link SelectableCapability} is present, then this parameter is ignored.
	 * @param moveableIfSelected whether selection implies movement
	 */
	public void setMoveableIfSelected( boolean moveableIfSelected ) {
		this.moveableIfSelected = moveableIfSelected;
	}
	
	@Override
	public boolean isMovingEnabledAt( int x, int y ) {
		if( moveableIfSelected ){
			SelectableCapability selectable = getCapability( CapabilityName.SELECTABLE );
			if( selectable != null ){
				return selectable.getSelected().isSelected();
			} else {
				return false;
			}
		} else {
			return getBoundaries().contains( x, y );
		}
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
