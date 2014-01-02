package graph.uml.intern.config;

import java.awt.Color;

import graph.items.connection.OrthogonalEndPoint;
import graph.items.connection.UndirectedEndPoint;
import graph.items.uml.Diamond;
import graph.items.uml.FilledArrow;
import graph.items.uml.OpenArrow;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;
import graph.uml.config.EndPointConfiguration;

public class DefaultEndPointConfiguration implements EndPointConfiguration {
	private Direction direction = Direction.DIRECT;
	private Decoration decoration = Decoration.NONE;

	@Override
	public void setDecoration( Decoration decoration ) {
		if( decoration == null ) {
			throw new IllegalArgumentException( "decoration must not be null" );
		}
		this.decoration = decoration;
	}

	@Override
	public Decoration getDecoration() {
		return decoration;
	}

	@Override
	public void setDirection( Direction direction ) {
		if( direction == null ) {
			throw new IllegalArgumentException( "direction must not be null" );
		}
		this.direction = direction;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	public EndPointPaintable buildDecoration( EndPoint endPoint ) {
		switch( decoration ){
			case NONE:
				return null;
			case DIAMOND:
				return new Diamond( endPoint, Color.WHITE );
			case FILLED_DIAMOND:
				return new Diamond( endPoint, Color.BLACK );
			case OPEN_ARROW:
				return new OpenArrow( endPoint );
			case CLOSE_ARROW:
				return new FilledArrow( endPoint, Color.WHITE );
			case FILLED_ARROW:
				return new FilledArrow( endPoint, Color.BLACK );
			default:
				throw new IllegalStateException( "unknown enum: " + decoration );
		}
	}

	public EndPoint buildEndPoint() {
		switch( direction ){
			case DIRECT:
				return new UndirectedEndPoint();
			case ORTHOGONAL:
				return new OrthogonalEndPoint();
			default:
				throw new IllegalStateException( "unknown enum: " + direction );
		}
	}

}
