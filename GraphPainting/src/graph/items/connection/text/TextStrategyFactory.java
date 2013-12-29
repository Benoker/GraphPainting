package graph.items.connection.text;

import java.awt.FontMetrics;
import java.awt.geom.Point2D;

public final class TextStrategyFactory {
	public static TextPositionStrategy position( final double position ) {
		return new TextPositionStrategy() {
			@Override
			public double getPosition( TextStrategyParameters parameters ) {
				return position;
			}
		};
	}

	public static TextPositionStrategy positionNearSource() {
		return new TextPositionStrategy() {
			@Override
			public double getPosition( TextStrategyParameters parameters ) {
				int amountOfPixelsAway = parameters.getFontMetrics().getAscent();
				return getPositionNearSource( amountOfPixelsAway, parameters );
			}
		};
	}

	public static TextPositionStrategy positionNearSource( final int amountOfPixelsAway ) {
		return new TextPositionStrategy() {
			@Override
			public double getPosition( TextStrategyParameters parameters ) {
				return getPositionNearSource( amountOfPixelsAway, parameters );
			}
		};
	}

	private static final double getPositionNearSource( int amountOfPixelsAway, TextStrategyParameters parameters ) {
		double length = parameters.getPath().getTotalLength();
		if( length == 0 ) {
			return 0;
		}
		double position = amountOfPixelsAway / length;
		return Math.max( 0, Math.min( 0.5, position ) );
	}
	
	public static TextPositionStrategy positionNearTarget(){
		return new TextPositionStrategy() {
			@Override
			public double getPosition( TextStrategyParameters parameters ) {
				int amountOfPixelsAway = parameters.getFontMetrics().getAscent();
				return getPositionNearTarget( amountOfPixelsAway, parameters );
			}
		};
	}

	public static TextPositionStrategy positionNearTarget( final int amountOfPixelsAway ) {
		return new TextPositionStrategy() {
			@Override
			public double getPosition( TextStrategyParameters parameters ) {
				return getPositionNearTarget( amountOfPixelsAway, parameters );
			}
		};
	}

	private static final double getPositionNearTarget( int amountOfPixelsAway, TextStrategyParameters parameters ) {
		double length = parameters.getPath().getTotalLength();
		if( length == 0 ) {
			return 1;
		}
		double position = 1.0 - amountOfPixelsAway / length;
		return Math.max( 0.5, Math.min( 1, position ) );
	}

	public static TextShiftStrategy shift( final double shift ) {
		return new TextShiftStrategy() {
			@Override
			public double getShift( TextStrategyParameters parameters ) {
				return shift;
			}
		};
	}
	
	public static TextShiftStrategy shiftInvardsByPosition(){
		return new TextShiftStrategy() {
			@Override
			public double getShift( TextStrategyParameters parameters ) {
				double position = parameters.getPosition();
				Point2D normal = parameters.getPath().getNormalAt( position );
				if( normal.getY() > 0 ){
					position = 1.0-position;
				}
				return position;
			}
		};
	}

	public static TextDistanceStrategy distance( final double distance ) {
		return new TextDistanceStrategy() {
			@Override
			public double getDistance( TextStrategyParameters parameters ) {
				return distance;
			}
		};
	}

	public static TextDistanceStrategy distanceUpdwards( final TextDistanceStrategy strategy ) {
		return new TextDistanceStrategy() {
			@Override
			public double getDistance( TextStrategyParameters parameters ) {
				double distance = strategy.getDistance( parameters );
				Point2D normal = parameters.getPath().getNormalAt( parameters.getPosition() );
				if( normal.getY() > 0 ) {
					distance = -distance;
				}
				return distance;
			}
		};
	}

	public static TextDistanceStrategy distanceDownwards( final TextDistanceStrategy strategy ) {
		return distanceInverted( distanceUpdwards( strategy ) );
	}

	public static TextDistanceStrategy distanceBaslineFloating() {
		return new TextDistanceStrategy() {
			@Override
			public double getDistance( TextStrategyParameters parameters ) {
				FontMetrics metrics = parameters.getFontMetrics();
				return metrics.getAscent() / 2 + metrics.getDescent();
			}
		};
	}

	public static TextDistanceStrategy distanceInverted( final TextDistanceStrategy strategy ) {
		return new TextDistanceStrategy() {
			@Override
			public double getDistance( TextStrategyParameters parameters ) {
				return -strategy.getDistance( parameters );
			}
		};
	}

	public static TextAngleStrategy angle( final double radians ) {
		return new TextAngleStrategy() {
			@Override
			public double getAngle( TextStrategyParameters parameters ) {
				return radians;
			}
		};
	}

	public static TextAngleStrategy angleParallel() {
		return new TextAngleStrategy() {
			@Override
			public double getAngle( TextStrategyParameters parameters ) {
				Point2D normal = parameters.getPath().getNormalAt( parameters.getPosition() );
				double dx = normal.getX();
				double dy = normal.getY();
				if( dx == 0 && dy == 0 ) {
					return 0.0;
				}

				double angle = Math.atan2( dy, dx ) + Math.PI / 2;
				if( angle < 0 ) {
					angle += Math.PI * 2;
				}
				if( angle > Math.PI / 2 && angle < 3 * Math.PI / 2 ) {
					angle -= Math.PI;
				}
				return angle;
			}
		};
	}
}
