package graph.items.connection;

import java.awt.Color;
import java.awt.Graphics2D;

import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;

public class PointConnector extends EndPointPaintable{
	private int size = 10;
	
	public PointConnector( EndPoint endPoint ) {
		super( endPoint );
	}
	
	public void setSize( int size ) {
		this.size = size;
	}

	@Override
	protected void paintPointingDownwards( Graphics2D g ) {
		g.setColor( Color.BLACK );
		g.fillOval( -size/2, 0, size, size );
	}
}
