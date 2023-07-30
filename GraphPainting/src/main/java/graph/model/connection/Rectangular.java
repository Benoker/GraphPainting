package graph.model.connection;

import graph.items.ShapedGraphItem;
import graph.model.GraphItem;

import java.awt.Rectangle;

public interface Rectangular extends GraphItem, ShapedGraphItem {
	public void setBoundaries( Rectangle boundaries );
	
	public Rectangle getBoundaries();
}
