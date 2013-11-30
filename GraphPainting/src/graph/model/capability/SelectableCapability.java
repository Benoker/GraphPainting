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
	 * Tells whether this item contains the point <code>x/y</code>.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return whether x/y is part of this selectable item
	 */
	public boolean contains( int x, int y );

}
