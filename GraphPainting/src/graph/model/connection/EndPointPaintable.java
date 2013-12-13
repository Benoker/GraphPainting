package graph.model.connection;

import graph.items.AbstractGraphItem;
import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class EndPointPaintable extends AbstractGraphItem implements GraphItem, GraphPaintable{
	private EndPoint endPoint;
	
	public EndPointPaintable( EndPoint endPoint ){
		this.endPoint = endPoint;
	}

	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}
	
	@Override
	public void paint( Graphics2D g ) {
		EndPointAttachement attachement = endPoint.getAttachement();
		
		Point zero = attachement.getLanding();
		Point direction = attachement.getDirection();
		
		double angle = Math.atan2( -direction.x, direction.y );
		
		g.translate( zero.x, zero.y );
		g.rotate( angle );
		
		paintPointingDownwards( g );
	}
	
	protected abstract void paintPointingDownwards( Graphics2D g );

	@Override
	protected void addTo( GraphSite site ) {
		site.addPaintable( this );
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		site.removePaintable( this );
	}
}
