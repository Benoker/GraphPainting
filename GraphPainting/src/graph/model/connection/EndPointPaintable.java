package graph.model.connection;

import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class EndPointPaintable implements GraphItem, GraphPaintable{
	private EndPoint endPoint;
	private GraphSite site;
	
	public EndPointPaintable( EndPoint endPoint ){
		this.endPoint = endPoint;
	}

	@Override
	public void paint( Graphics2D g ) {
		
		EndPointAttachement attachement = endPoint.getAttachement();
		Point zero = attachement.getLanding();
		Point approach = attachement.getApproach();
		
		double angle = Math.atan2( zero.x - approach.x, approach.y - zero.y );
		
		g.translate( zero.x, zero.y );
		g.rotate( angle );
		
		paintPointingUpwards( g );
	}
	
	protected abstract void paintPointingUpwards( Graphics2D g );

	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( this );
		}
		this.site = site;
		if( this.site != null ){
			this.site.add( this );
		}
	}
}
