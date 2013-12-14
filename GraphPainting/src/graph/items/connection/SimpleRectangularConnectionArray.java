package graph.items.connection;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import graph.model.GraphSite;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;
import graph.model.connection.Rectangular;
import graph.util.Geom;

public class SimpleRectangularConnectionArray implements ConnectionArray, Rectangular{
	private List<EndPoint> endPoints = new ArrayList<>();
	
	private Rectangle boundaries = new Rectangle();
	
	private int landingOffset = 30;
	
	@Override
	public void setBoundaries( Rectangle boundaries ){
		this.boundaries.setBounds( boundaries );
	}
	
	@Override
	public Rectangle getBoundaries() {
		return new Rectangle( boundaries );
	}
	
	@Override
	public float contains( int x, int y ) {
		if( boundaries.contains( x, y ) ){
			return 1.f;
		}
		else{
			return 0.f;
		}
	}
	
	@Override
	public void set( GraphSite site ) {
		// ignore	
	}
	
	@Override
	public <T> T getCapability( CapabilityName<T> name ) {
		return null;
	}
	
	public void setLandingOffset( int landingOffset ) {
		this.landingOffset = landingOffset;
	}
	
	@Override
	public void add( EndPoint endPoint ) {
		endPoints.add( endPoint );
		endPoint.setArray( this );
	}

	@Override
	public void remove( EndPoint endPoint ) {
		endPoints.remove( endPoint );
		endPoint.setArray( null );
	}
	
	@Override
	public Point getCenter() {
		int x = (int)boundaries.getCenterX();
		int y = (int)boundaries.getCenterY();
		return new Point( x, y );
	}

	@Override
	public EndPointAttachement getAttachement( EndPoint endPoint ) {
		return endPoint.getAttachement( endPointAttachementSite( endPoint ));
	}
	
	@Override
	public Point getLanding( EndPoint endPoint ) {
		return endPoint.getLanding( endPointAttachementSite( endPoint ));
	}
	
	private EndPointAttachementSite endPointAttachementSite( final EndPoint endPoint ){
		return new EndPointAttachementSite() {
			private Point hit;
			
			private Point getHit(){
				if( hit == null ){
					hit = Geom.intersection( boundaries, getCenter(), endPoint.getOtherEndPoint().getArrayCenter() );
				}
				return hit;
			}
			
			@Override
			public Point getPointFarthestAwayFromLanding() {
				Point hit = getHit();
				Point approach = new Point( hit );
				
				if( hit.x == boundaries.x ){
					approach.x -= landingOffset;
				}
				else if( hit.x == boundaries.x + boundaries.width ){
					approach.x += landingOffset;
				}
				else if( hit.y == boundaries.y ){
					approach.y -= landingOffset;
				}
				else{
					approach.y += landingOffset;
				}
				return approach;
			}
			
			@Override
			public Point getOtherLanding() {
				return endPoint.getOtherEndPoint().getLanding();
			}
			
			@Override
			public Point getLanding() {
				return getHit();
			}
		};
	}
}
