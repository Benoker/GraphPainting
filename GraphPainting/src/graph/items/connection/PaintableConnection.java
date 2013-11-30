package graph.items.connection;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public abstract class PaintableConnection extends AbstractConnection implements GraphPaintable{
	private Color color = Color.BLACK;
	private Stroke stroke;
	
	public PaintableConnection( ConnectionArray source, ConnectionArray target ){
		super( source, target );
	}
	
	@Override
	protected void addTo( GraphSite site ) {
		site.add( (GraphPaintable)this );
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		site.remove( (GraphPaintable)this );
	}
	
	public void setColor( Color color ) {
		this.color = color;
		regraph();
	}
	
	public void setStroke( Stroke stroke ) {
		this.stroke = stroke;
		regraph();
	}
	
	@Override
	public final void paint( Graphics2D g ){
		g.setColor( color );
		if( stroke != null ){
			g.setStroke( stroke );
		}
		paintConnection( g );
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}
	
	protected abstract void paintConnection( Graphics2D g );
}
