package graph.util;

import graph.model.connection.EndPointPosition;

import java.awt.Point;
import java.awt.Rectangle;

public class Geom {
	public static Point intersection( Rectangle rectangle, Point startingPoint, Point endingPoint ){
		if( !rectangle.contains( startingPoint )){
			throw new IllegalArgumentException( "startingPoint must be within the rectangle" );
		}
		
		if( startingPoint.equals( endingPoint )){
			return rectangle.getLocation();
		}
		
		int sx = startingPoint.x;
		int sy = startingPoint.y;
		
		int tx = endingPoint.x;
		int ty = endingPoint.y;
		
		int rx = rectangle.x;
		int ry = rectangle.y;
		int rw = rectangle.width;
		int rh = rectangle.height;
		
		if( sx == tx ){
			if( sy < ty ){
				return new Point( sx, ry + rh );
			}
			else{
				return new Point( sx, ry );
			}
		}
		
		double m = (double)(sy - ty) / (double)(sx - tx);
		double q = sy - m * sx;
		
		double wallhitX;
		double wallhitY;
		
		if( sx < tx ){
			wallhitY = m * (rx + rw) + q;
			wallhitX = rx + rw;
		}
		else{
			wallhitY = m * rx + q;
			wallhitX = rx;
		}
		
		if( wallhitY < ry ){
			double x = (ry - q) / m;
			return new Point( round(x), ry );
		}
		else if( wallhitY > ry + rh ){
			double x = (ry + rh - q) / m;
			return new Point( round(x), ry+rh );
		}
		else{
			return new Point( round(wallhitX), round(wallhitY));
		}
	}
	
	public enum Side{
		NORTH, SOUTH, EAST, WEST;
	}
	
	public static Side toSide( EndPointPosition position ){
		switch( position ){
			case LEFT: return Side.WEST;
			case RIGHT: return Side.EAST;
			case TOP: return Side.NORTH;
			case BOTTOM: return Side.SOUTH;
			default: throw new IllegalArgumentException();
		}
	}
	
	public static double comparableAngleOnScreen( Point source, Point target, Side nullSide ){
		int sx = source.x;
		int sy = source.y;
		int tx = target.x;
		int ty = target.y;
		
		if( sx == tx && sy == ty ){
			return 0;
		}
		
		double angle = Math.atan2( tx-sx, sy-ty );
		switch( nullSide ){
			case NORTH: return angle;
			case SOUTH: return -rotate( angle, Math.PI );
			case EAST: return rotate( angle, -Math.PI/2 );
			case WEST: return -rotate( angle, Math.PI/2 );
			default: throw new IllegalStateException();
		}
	}
	
	private static double rotate( double angle, double delta ){
		angle += delta;
		if( angle > Math.PI ){
			angle -= 2*Math.PI;
		}
		else if( angle < -Math.PI ){
			angle += 2*Math.PI; 
		}
		return angle;
	}
	
	public static Side nearestSide( Rectangle source, Rectangle target ){ 
		double smx = source.getCenterX();
		double smy = source.getCenterY();
		
		int tx = target.x;
		int ty = target.y;
		int tw = target.width;
		int th = target.height;
		
		int sx = source.x;
		int sy = source.y;
		int sw = source.width;
		int sh = source.height;
		
		double distNW = dist2( smx, smy, tx, ty );
		double distNE = dist2( smx, smy, tx+tw, ty );
		double distSW = dist2( smx, smy, tx, ty+th );
		double distSE = dist2( smx, smy, tx+tw, ty+th );
		
		double min = Math.min( Math.min( distNW, distNE ), Math.min( distSW, distSE ) );
		
		if( distNW == min ){
			if( targetOverLineOnScreen( smx, smy, sx, sy, tx, ty )){
				return Side.WEST;
			}
			else{
				return Side.NORTH;
			}
		}
		else if( distNE == min ){
			if( targetOverLineOnScreen( smx, smy, sx+sw, sy, tx+tw, ty )){
				return Side.EAST;
			}
			else{
				return Side.NORTH;
			}
		}
		else if( distSW == min ){
			if( targetOverLineOnScreen( smx, smy, sx, sy+sh, tx, ty+th )){
				return Side.SOUTH;
			}
			else{
				return Side.WEST;
			}
		}
		else{
			if( targetOverLineOnScreen( smx, smy, sx+sw, sy+sh, tx+tw, ty+th )){
				return Side.SOUTH;
			}
			else{
				return Side.EAST;
			}
		}
	}
	
	private static boolean targetOverLineOnScreen( double sx, double sy, int ex, int ey, int tx, int ty ){
		if( sx == ex && sy == ey ){
			return true;
		}
		if( sx == ex ){
			return tx > sx;
		}
		
		double m = (sy - ey) / (double)(sx - ex);
		m = limitM( m );
		double q = sy - m * sx;
		
		return m * tx + q > ty;
	}
	
	private static double limitM( double m ){
		if( m < 0 ){
			return -limitM( -m );
		}
		if( m < 0.5 ){
			return 0.5;
		}
		else if( m > 1.5 ){
			return 1.5;
		}
		else{
			return m;
		}
	}
	
	private static int round( double d ){
		return (int)(d+0.5);
	}
	
	public static double dist2( double x1, double y1, double x2, double y2 ){
		double dx = x1 - x2;
		double dy = y1 - y2;
		return dx*dx + dy*dy;
	}

	public static Point pointAt( Point start, Point dir, double dist ) {
		if( dir.x == 0 && dir.y == 0 ){
			return start;
		}
		
		double dirDist = Math.sqrt( dist2( 0, 0, dir.x, dir.y ));
		double factor = dist / dirDist;
		
		return new Point( round( start.x + factor * dir.x ), round( start.y + factor * dir.y ) );
	}
}
