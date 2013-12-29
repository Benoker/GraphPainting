package graph.util;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper around a {@link Path2D} enhancing it with some methods allowing traverlling along the path.
 * @author Benjamin Sigg
 */
public class EnhancedPath2D {
	private Path2D path;

	/** the end and start point of the segments of this path. If the path has n segments, then this array has n+1 points */
	private Point2D[] segments;
	/** the length of each segments of this path */
	private double[] segmentLengths;
	/** length of the path to the start point of a segment (excl. the segment) */
	private double[] segmentLengthsSum;
	/** total length of the path */
	private double totalLength;

	/**
	 * Creates a new path
	 * @param path the wrapped path
	 */
	public EnhancedPath2D( Path2D path ) {
		if( path == null ) {
			throw new IllegalArgumentException( "path must not be null" );
		}
		this.path = path;
		buildSegments();
		buildSegmentLengths();
	}

	private void buildSegments() {
		PathIterator iterator = path.getPathIterator( null, 1 );
		double[] coords = new double[6];
		List<Point2D> segments = new ArrayList<>();

		while( !iterator.isDone() ) {
			int type = iterator.currentSegment( coords );
			if( type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO ) {
				segments.add( new Point2D.Double( coords[0], coords[1] ) );
			}
			iterator.next();
		}

		this.segments = segments.toArray( new Point2D[segments.size()] );
	}

	private void buildSegmentLengths() {
		segmentLengths = new double[this.segments.length - 1];
		segmentLengthsSum = new double[this.segments.length - 1];

		for( int i = 0, n = segments.length - 1; i < n; i++ ) {
			Point2D start = segments[i];
			Point2D end = segments[i + 1];

			double length = start.distance( end );
			segmentLengths[i] = length;
		}

		for( int i = 1, n = segments.length - 1; i < n; i++ ) {
			segmentLengthsSum[i] = segmentLengthsSum[i - 1] + segmentLengths[i - 1];
		}
		for( double length : segmentLengths ) {
			totalLength += length;
		}
	}

	/**
	 * Gets an iterator traveling along the path, returning some of the points of the path. The points may not
	 * always line up exactly.
	 * @param start the start (incl) of the iterator, at least <code>0</code>, not more than <code>end</code>
	 * @param end the end (incl) of the iterator, at most <code>1</code>, at least <code>end</code>
	 * @param nPoints the number of points to return, at least <code>1</code>
	 * @return the iterator
	 */
	public Iterator<Point2D> getPoints( final float start, final float end, final int nPoints ) {
		if( nPoints == 1 ) {
			return new Iterator<Point2D>() {
				private boolean hasNext = true;

				@Override
				public boolean hasNext() {
					return hasNext;
				}

				@Override
				public Point2D next() {
					hasNext = false;
					return getPointAt( start + (end - start) / 2.0f );
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		} else {
			return new Iterator<Point2D>() {
				private int index;

				@Override
				public boolean hasNext() {
					return index < nPoints;
				}

				@Override
				public Point2D next() {
					float position = start + (end - start) * (index / (nPoints - 1.0f));
					index++;
					return getPointAt( position );
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}

	/**
	 * Gets a point on the path. The position <code>0</code> is at the beginning of the path, the position
	 * <code>1</code> is at the end of the path.
	 * @param position the position, between <code>0</code> and <code>1</code>
	 * @return a point that is on the path, or closely on the path
	 */
	public Point2D getPointAt( float position ) {
		if( position < 0 || position > 1 ) {
			throw new IllegalArgumentException( "position out of range: " + position );
		}

		int segment = segmentAt( position );
		double delta = position * totalLength - segmentLengthsSum[segment];
		delta /= segmentLengths[segment];

		Point2D start = segments[segment];
		Point2D end = segments[segment + 1];

		double x = start.getX() + delta * (end.getX() - start.getX());
		double y = start.getY() + delta * (end.getY() - start.getY());
		
		return new Point2D.Double( x, y );
	}

	/**
	 * Gets a vector of length <code>1</code> or <code>0</code>, describing the normal to the path at
	 * position <code>position</code>. The vector of length <code>0</code> is returned if no direction can
	 * be found.
	 * @param position the position on the path, between <code>0</code> and <code>1</code>
	 * @return the normal direction at <code>position</code>, pointing 90 degrees clockwise to the
	 * direction in which the path travels
	 */
	public Point2D getNormalAt( float position ){
		if( position < 0 || position > 1 ){
			throw new IllegalArgumentException( "position out ouf range: " + position );
		}
		
		int segment = segmentAt( position );
		
		Point2D start = segments[segment];
		Point2D end = segments[segment + 1];
		
		double distance = segmentLengths[segment];
		if( distance == 0 ){
			return new Point2D.Double( 0, 0 );
		}
		double dx = (end.getX() - start.getX()) / distance;
		double dy = (end.getY() - start.getY()) / distance;
		
		return new Point2D.Double( dy, -dx );
	}
	
	private int segmentAt( float position ) {
		int result = Arrays.binarySearch( segmentLengthsSum, position * totalLength );
		if( result >= 0 ) {
			return result;
		} else {
			return -2-result;
		}
	}
}
