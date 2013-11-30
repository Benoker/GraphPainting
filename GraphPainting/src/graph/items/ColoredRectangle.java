package graph.items;

import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.Rectangular;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ColoredRectangle extends MoveableRectangularGraphItem implements GraphItem, GraphPaintable, Rectangular{
	private GraphSite site;

	private Color color;
	
	public ColoredRectangle( Color color ){
		this.color = color;
	}
	
	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( this );
		}
		this.site = site;
		super.set( site );
		if( site != null ){
			site.add( this );
		}
	}
	
	@Override
	public void paint( Graphics2D g ) {
		Rectangle bounds = getBoundaries();
		
		g.setColor( color );
		g.fillRect( bounds.x, bounds.y, bounds.width, bounds.height );
	}
}
