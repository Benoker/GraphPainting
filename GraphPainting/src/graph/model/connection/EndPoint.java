package graph.model.connection;

import graph.items.connection.EndPointAttachementSite;
import graph.model.GraphItem;

import java.awt.Point;
import java.awt.Rectangle;

public interface EndPoint extends GraphItem{
	public EndPointPosition getEndPointPosition();
	
	public EndPoint getOtherEndPoint();
	
	public void setConnection( GraphConnection connection );
	
	public void setArray( ConnectionArray array );

	public Point getArrayCenter();
	
	public Rectangle getArrayBoundaries();
	
	public EndPointAttachement getAttachement();
	
	public EndPointAttachement getAttachement( EndPointAttachementSite site );
	
	public Point getLanding();
	
	public Point getLanding( EndPointAttachementSite site );
}
