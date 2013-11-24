package graph.items.connection;

import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;
import graph.model.connection.EndPointPosition;
import graph.model.connection.Rectangular;
import graph.util.Geom;
import graph.util.Geom.Side;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentRectangularConnectionArray implements ConnectionArray, Rectangular, Regraphable {
	private List<EndPoint> endPoints = new ArrayList<>();
	private Rectangle boundaries = new Rectangle();
	private GraphSite site;
	private CachedLocations locations;
	private int landingOffset = 30;
	
	public void setLandingOffset( int landingOffset ) {
		this.landingOffset = landingOffset;
	}
	
	@Override
	public void setBoundaries( Rectangle boundaries ) {
		this.boundaries.setBounds( boundaries );	
	}
	
	@Override
	public Rectangle getBoundaries() {
		return new Rectangle( boundaries );
	}

	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( this );
		}
		this.site = site;
		if( site != null ){
			site.add( this );
		}
	}

	@Override
	public void regraph() {
		locations = null;	
	}
	
	@Override
	public void add( EndPoint endPoint ) {
		endPoints.add( endPoint );
		endPoint.setArray( this );
		locations = null;
		if( site != null ){
			site.regraph();
		}
	}

	@Override
	public void remove( EndPoint endPoint ) {
		endPoints.remove( endPoint );
		endPoint.setArray( null );
		if( site != null ){
			site.regraph();
		}
	}

	@Override
	public Point getCenter() {
		int x = (int)boundaries.getCenterX();
		int y = (int)boundaries.getCenterY();
		return new Point( x, y );
	}

	@Override
	public EndPointAttachement getAttachement( EndPoint endPoint ) {
		if( locations == null ){
			locations = new CachedLocations();
		}
		return locations.getAttachement( endPoint );
	}
	
	@Override
	public Point getLanding( EndPoint endPoint ) {
		if( locations == null ){
			locations = new CachedLocations();
		}
		return locations.getLanding( endPoint );
	}
	
	private Geom.Side calcPosition( EndPoint endPoint ){
		if( endPoint.getEndPointPosition() != EndPointPosition.AUTOMATIC ){
			return Geom.toSide( endPoint.getEndPointPosition() );
		}
		return Geom.nearestSide( endPoint.getOtherEndPoint().getArrayBoundaries(), getBoundaries() );
	}

	private void sort( List<EndPoint> endPoints, final Side side ){
		final Map<EndPoint, Double> scores = new HashMap<>();
		for( EndPoint point : endPoints ){
			scores.put( point, score( point, side ) );
		}
		Collections.sort( endPoints, new Comparator<EndPoint>() {
			@Override
			public int compare( EndPoint a, EndPoint b ) {
				double sa = scores.get( a );
				double sb = scores.get( b );
				
				if( sa < sb ){
					return -1;
				}
				else if( sa > sb ){
					return 1;
				}
				else{
					return 0;
				}
			}
		} );
	}
	
	private double score( EndPoint point, Side side ){
		return Geom.comparableAngleOnScreen( getCenter(), point.getOtherEndPoint().getArrayCenter(), side );
	}
	
	private int startX( Side side ){
		if( side == Side.EAST ){
			return boundaries.x + boundaries.width;
		}
		else{
			return boundaries.x;
		}
	}
	
	private int startY( Side side ){
		if( side == Side.SOUTH ){
			return boundaries.y + boundaries.height;
		}
		else{
			return boundaries.y;
		}
	}
	
	private int endX( Side side ){
		if( side == Side.WEST ){
			return boundaries.x;
		}
		else{
			return boundaries.x + boundaries.width;
		}
	}
	
	private int endY( Side side ){
		if( side == Side.NORTH ){
			return boundaries.y;
		}
		else{
			return boundaries.y + boundaries.height;
		}
	}
	
	private static class Landing{
		private Point point;
		private Side side;
		
		public Landing( Point point, Side side ){
			this.point = point;
			this.side = side;
		}
	}
	
	private class CachedLocations{
		private Map<EndPoint, Landing> landings = new HashMap<>();
		private Map<EndPoint, EndPointAttachement> attachements = new HashMap<>();
		
		public CachedLocations(){
			List<EndPoint> north = new ArrayList<>();
			List<EndPoint> south = new ArrayList<>();
			List<EndPoint> east = new ArrayList<>();
			List<EndPoint> west = new ArrayList<>();
			
			for( EndPoint endPoint : endPoints ){
				switch( calcPosition( endPoint )){
					case WEST:
						west.add( endPoint );
						break;
					case EAST:
						east.add( endPoint );
						break;
					case NORTH:
						north.add( endPoint );
						break;
					case SOUTH:
						south.add( endPoint );
						break;
				}
			}
			
			createAttachements( west, Side.WEST );
			createAttachements( east, Side.EAST );
			createAttachements( south, Side.SOUTH );
			createAttachements( north, Side.NORTH );
		}
		
		private void createAttachements( List<EndPoint> endPoints, Side side ){
			sort( endPoints, side );
			
			int sx = startX( side );
			int sy = startY( side );
			int tx = endX( side );
			int ty = endY( side );
			
			int dx = (tx - sx) / (endPoints.size()+1);
			int dy = (ty - sy) / (endPoints.size()+1);
			
			int d = 1;
			for( EndPoint point : endPoints ){
				Point landing = new Point( sx + d*dx, sy + d*dy );
				landings.put( point, new Landing( landing, side ) );
				d++;
			}
		}
		
		private EndPointAttachementSite endPointAttachementSite( final EndPoint endPoint, final Point landing, final Side side ){
			return new EndPointAttachementSite() {
				@Override
				public Point getPointFarthestAwayFromLanding() {
					return approach( landing, side );
				}
				
				@Override
				public Point getLanding() {
					return landing;
				}
				
				@Override
				public Point getOtherLanding() {
					return endPoint.getOtherEndPoint().getLanding();
				}
			};
		}
		
		private Point approach( Point landing, Side side ){
			Point approach = new Point( landing );
			switch( side ){
				case EAST:
					approach.x += landingOffset;
					break;
				case WEST:
					approach.x -= landingOffset;
					break;
				case NORTH:
					approach.y -= landingOffset;
					break;
				case SOUTH:
					approach.y += landingOffset;
					break;
			}
			return approach;
		}
		
		public Point getLanding( EndPoint endPoint ){
			return landings.get( endPoint ).point;
		}
		
		public EndPointAttachement getAttachement( EndPoint endPoint ){
			EndPointAttachement result = attachements.get( endPoint );
			if( result == null ){
				Landing landing = landings.get( endPoint );
				result = endPoint.getAttachement( endPointAttachementSite( endPoint, landing.point, landing.side ) );
				attachements.put( endPoint, result );
			}
			return result;
		}
	}
}
