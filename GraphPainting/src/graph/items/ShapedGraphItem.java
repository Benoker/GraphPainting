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
	 * @return whether <code>this</code> contains <code>x/y</code>. A value of <code>0.f</code> or lower means no,
	 * a value of <code>1.f</code> or higher means yes, a value between <code>0.f</code> and <code>1.f</code> means
	 * "close"
	 */
	public float contains( int x, int y );
}
