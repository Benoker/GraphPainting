package graph.items;

import graph.model.GraphItem;

/**
 * A {@link GraphItem} that has some kind of shape and hence allows interaction with the mouse.
 * @author Benjamin Sigg
 */
public interface ShapedGraphItem extends GraphItem{
	/**
	 * Tells whether <code>this</code> contains the point <code>x/y</code>
	 * @param x x-coordinate of the mouse
	 * @param y x-coordinate of the mouse
	 * @return whether <code>this</code> contains <code>x/y</code>
	 */
	public boolean contains( int x, int y );
}
