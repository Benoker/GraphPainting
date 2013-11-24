package graph.items.uml;

import graph.model.GraphSite;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;

import java.awt.Color;
import java.awt.Graphics2D;

public class Diamond extends EndPointPaintable {
	private Color color;
	private int size = 20;
	private GraphSite site;
	
	public Diamond( EndPoint endPoint, Color color ){
		super( endPoint );
		this.color = color;
	}
	
	@Override
	public void set( GraphSite site ) {
		this.site = site;
		super.set( site );
	}
	
	public void setColor( Color color ) {
		this.color = color;
		if( site != null ){
			site.regraph();
		}
	}
	
	public void setSize( int size ) {
		this.size = size;
		if( site != null ){
			site.regraph();
		}
	}
	
	@Override
	protected void paintPointingDownwards( Graphics2D g ) {
		int[] xs = { 0, size/3, 0, -size/3 };
		int[] ys = { 0, size/2, size, size/2 };
		
		g.setColor( color );
		g.fillPolygon( xs, ys, 4 );
		
		g.setColor( Color.BLACK );
		g.drawPolygon( xs, ys, 4 );
	}
}
