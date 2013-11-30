package graph.uml;

import java.awt.Color;

/**
 * A box is a rectangular item that shows some text.
 * @author Benjamin Sigg
 */
public interface Box extends Item{
	/**
	 * Sets the position of this box.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public void setLocation( int x, int y );
	
	/**
	 * Sets the content of this box.
	 * @param text the text to show, can be html
	 */
	public void setText( String text );
	
	/**
	 * Sets the background color of this box.
	 * @param background the background color
	 */
	public void setBackground( Color background );
	
	/**
	 * Sets the background color of this box used if it is selected
	 * @param selectedPrimary the background color
	 */
	public void setSelectedPrimary( Color selectedPrimary );
	
	/**
	 * Sets the background color of this box used if it is selected
	 * @param selectedSecondary the background color
	 */
	public void setSelectedSecondary( Color selectedSecondary );
}
