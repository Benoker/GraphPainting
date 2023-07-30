package graph.model.capability;

import graph.model.Selection;

/**
 * Describes an item that can be selected by the user.
 * @author Benjamin Sigg
 */
public interface SelectableCapability {
	/**
	 * Tells this item whether it is selected or not. A selected item may paint some special border.
	 * @param selection more information about the selection
	 */
	public void setSelected( Selection selection );
	
	/**
	 * Tells whether this item is selected.
	 * @return the selection state
	 */
	public Selection getSelected();
	
	/**
	 * Tells whether this item contains the point <code>x/y</code>.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return whether x/y is part of this item. A value that is <code>0.f</code> or lower means no,
	 * a value that is between <code>0.f</code> and <code>1.f</code> means "close", and a value of
	 * <code>1.f</code> or higher means yes
	 */
	public float contains( int x, int y );

}
