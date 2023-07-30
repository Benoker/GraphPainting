package graph.items.uml;

import java.awt.Color;
import java.awt.Graphics2D;

import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;

public class OpenArrow extends EndPointPaintable{
	private int size = 20;
	
	public OpenArrow( EndPoint endPoint ){
		super( endPoint );
	}
	
	@Override
	protected void paintPointingDownwards( Graphics2D g ) {
		g.setColor( Color.BLACK );
		
		g.drawLine( 0, 0, size/2, size );
		g.drawLine( 0, 0, -size/2, size );
	}
}
