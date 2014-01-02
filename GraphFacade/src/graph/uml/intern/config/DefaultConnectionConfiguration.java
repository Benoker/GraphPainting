package graph.uml.intern.config;

import graph.items.PathedGraphConnection;
import graph.items.connection.BezierLineConnection;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.connection.DirectLineConnection;
import graph.items.connection.OrthogonalEndPoint;
import graph.items.connection.PaintableConnection;
import graph.model.connection.EndPointPaintable;
import graph.uml.config.ConnectionConfiguration;
import graph.uml.config.EndPointConfiguration;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class DefaultConnectionConfiguration implements ConnectionConfiguration {
	private DefaultEndPointConfiguration source = new DefaultEndPointConfiguration();
	private DefaultEndPointConfiguration target = new DefaultEndPointConfiguration();

	private Stroke stroke;
	private PathShape path;

	@Override
	public EndPointConfiguration source() {
		return source;
	}

	@Override
	public EndPointConfiguration target() {
		return target;
	}

	@Override
	public void setStroke( Stroke stroke ) {
		this.stroke = stroke;
	}

	@Override
	public Stroke getStroke() {
		return stroke;
	}

	@Override
	public void withContinuousLine() {
		setStroke( null );
	}

	@Override
	public void withDottedLine() {
		float[] dash = { 8.0f };
		setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f ) );
	}

	@Override
	public void setPathShape( PathShape shape ) {
		if( shape == null ) {
			throw new IllegalArgumentException( "shape must not be null" );
		}
		this.path = shape;
	}

	@Override
	public PathShape getPathShape() {
		return path;
	}

	/**
	 * Creates a new {@link PathedGraphConnection}.
	 * @return the new connection
	 */
	public PathedGraphConnection buildLine() {
		switch( path ){
			case DIRECT:
				return buildLine( new DirectLineConnection() );
			case EDGED:
				return buildLine( new CuttingEdgeLineConnection() );
			case CURVED:
				return buildLine( new BezierLineConnection() );
			default:
				throw new IllegalStateException( "unknown enum: " + path );
		}
	}

	private <T extends PaintableConnection & PathedGraphConnection> T buildLine( T line ) {
		line.setStroke( stroke );

		if( path == PathShape.EDGED ) {
			line.setSourcePoint( new OrthogonalEndPoint() );
			line.setTargetPoint( new OrthogonalEndPoint() );
		} else {
			line.setSourcePoint( source.buildEndPoint() );
			line.setTargetPoint( target.buildEndPoint() );
		}

		EndPointPaintable sourcePaint = source.buildDecoration( line.getSourceEndPoint() );
		EndPointPaintable targetPaint = target.buildDecoration( line.getTargetEndPoint() );

		if( sourcePaint != null ) {
			line.addChild( sourcePaint );
		}
		if( targetPaint != null ) {
			line.addChild( targetPaint );
		}
		return line;
	}
}
