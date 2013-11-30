package graph.items;

import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.Rectangular;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ColoredRectangle extends MoveableRectangularGraphItem implements GraphItem, GraphPaintable, Rectangular{
	private Color color;
	
	public ColoredRectangle( Color color ){
		this.color = color;
	}

	@Override
	protected void addTo( GraphSite site ) {
		site.add( this );
		super.addTo( site );
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		site.remove( this );
		super.removeFrom( site );
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}
	
	@Override
	public void paint( Graphics2D g ) {
		Rectangle bounds = getBoundaries();
		
		g.setColor( color );
		g.fillRect( bounds.x, bounds.y, bounds.width, bounds.height );
	}
}
