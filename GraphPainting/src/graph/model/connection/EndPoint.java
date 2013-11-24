package graph.model.connection;

import java.awt.Point;
import java.awt.Rectangle;

import graph.model.GraphItem;

public interface EndPoint extends GraphItem{
	public EndPointPosition getEndPointPosition();
	
	public EndPoint getOtherEndPoint();
	
	public void setConnection( Connection connection );
	
	public void setArray( ConnectionArray array );

	public Point getArrayCenter();
	
	public Rectangle getArrayBoundaries();
	
	public EndPointAttachement getAttachement();
}
