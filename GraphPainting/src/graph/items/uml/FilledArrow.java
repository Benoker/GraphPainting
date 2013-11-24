package graph.items.uml;

import java.awt.Color;
import java.awt.Graphics2D;

import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;

public class FilledArrow extends EndPointPaintable{
	private int size = 20;
	
	public FilledArrow( EndPoint endPoint ) {
		super( endPoint );
	}

	@Override
	protected void paintPointingDownwards( Graphics2D g ) {
		int[] xs = {0, size/2, -size/2};
		int[] ys = {0, size, size};
		
		g.setColor( Color.WHITE );
		g.fillPolygon( xs, ys, 3 );
		
		g.setColor( Color.BLACK );
		g.drawPolygon( xs, ys, 3 );
	}
}
