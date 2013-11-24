package graph.model.connection;

import graph.model.GraphItem;

import java.awt.Rectangle;

public interface Rectangular extends GraphItem {
	public void setBoundaries( Rectangle boundaries );
	
	public Rectangle getBoundaries();
}
