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
	private GraphSite site;
	
	public PaintableConnection( ConnectionArray source, ConnectionArray target ){
		super( source, target );
	}
	
	@Override
	public void set( GraphSite site ) {
		super.set( site );
		
		if( this.site != null ){
			this.site.remove( (GraphPaintable)this );
		}
		this.site = site;
		if( site != null ){
			site.add( (GraphPaintable)this );
		}
	}
	
	public void setColor( Color color ) {
		this.color = color;
		if( site != null ){
			site.regraph();
		}
	}
	
	public void setStroke( Stroke stroke ) {
		this.stroke = stroke;
		if( site != null ){
			site.regraph();
		}
	}
	
	@Override
	public final void paint( Graphics2D g ){
		g.setColor( color );
		if( stroke != null ){
			g.setStroke( stroke );
		}
		paintConnection( g );
	}
	
	protected abstract void paintConnection( Graphics2D g );
}
