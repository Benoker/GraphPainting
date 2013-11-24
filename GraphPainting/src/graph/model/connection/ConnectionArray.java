package graph.model.connection;

import java.awt.Point;
import java.awt.Rectangle;

public interface ConnectionArray {
	public void add( EndPoint endPoint );
	
	public void remove( EndPoint endPoint );
	
	public Point getCenter();
	
	public Rectangle getBoundaries();
	
	public EndPointAttachement getAttachement( EndPoint endPoint );
}
