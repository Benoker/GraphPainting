package graph.items;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.ui.Graph;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A very simple line painted on the {@link Graph}
 * @author Benjamin Sigg
 */
public class Line extends AbstractGraphItem implements GraphPaintable{
	private Color color = Color.BLACK;
	private int x1, y1;
	private int x2, y2;
	
	public void setColor( Color color ) {
		this.color = color;
		regraph();
	}
	
	public void setPoint1( int x1, int y1 ){
		this.x1 = x1;
		this.y1 = y1;
		regraph();
	}
	
	public void setPoint2( int x2, int y2 ){
		this.x2 = x2;
		this.y2 = y2;
		regraph();
	}
	
	@Override
	public void paint( Graphics2D g ) {
		g.setColor( color );
		g.drawLine( x1, y1, x2, y2 );
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		site.removePaintable( this );
	}

	@Override
	protected void addTo( GraphSite site ) {
		site.addPaintable( this );
	}	
}
