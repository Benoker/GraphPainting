package graph.items.connection;

import graph.model.GraphSite;
import graph.model.connection.EndPointAttachement;

import java.awt.Point;

public class OrthogonalEndPoint extends AbstractEndPoint{
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
		Point normal = site.getPointFarthestAwayFromLanding();
		
		Point direction = new Point( normal.x - landing.x, normal.y - landing.y );
		
		return new EndPointAttachement( landing, normal, direction );
	}
}
