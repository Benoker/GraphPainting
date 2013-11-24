package graph.items.connection;

import java.awt.Point;

import graph.model.connection.EndPointAttachement;

public class OrthogonalEndPoint extends AbstractEndPoint{

	@Override
	public EndPointAttachement getAttachement( EndPointAttachementSite site ) {
		Point landing = site.getLanding();
		Point normal = site.getPointFarthestAwayFromLanding();
		
		Point direction = new Point( normal.x - landing.x, normal.y - landing.y );
		
		return new EndPointAttachement( landing, normal, direction );
	}
}
