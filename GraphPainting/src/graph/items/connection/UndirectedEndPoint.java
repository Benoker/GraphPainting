package graph.items.connection;

import graph.model.GraphSite;
import graph.model.connection.EndPointAttachement;

import java.awt.Point;

public class UndirectedEndPoint extends AbstractEndPoint{
	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore	
	}
	
	@Override
	public EndPointAttachement getAttachement( EndPointAttachementSite site ) {
		Point landing = site.getLanding();
		Point otherLanding = site.getOtherLanding();
		
		Point direction = new Point( otherLanding.x - landing.x, otherLanding.y - landing.y );
		
		return new EndPointAttachement( landing, landing, direction );
	}
}
