package graph.items.connection.text;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;

import graph.items.PathedGraphConnection;
import graph.items.connection.AbstractConnectionText;
import graph.util.EnhancedPath2D;

/**
 * A text that sticks on a {@link PathedGraphConnection}, calculation of various properties can be configured. 
 * @author Benjamin Sigg
 */
public class ConfigurableConnectionText extends AbstractConnectionText {
	private TextPositionStrategy position = TextStrategyFactory.position( 0.5 );
	private TextShiftStrategy shift = TextStrategyFactory.shift( 0.5 );
	private TextDistanceStrategy distance = TextStrategyFactory.distance( 0.0 );
	private TextAngleStrategy angle = TextStrategyFactory.angle( 0.0 );

	private TextParameters textParameters;

	public ConfigurableConnectionText( PathedGraphConnection connection ) {
		super( connection );
	}

	@Override
	protected void paint( Graphics2D g, Parameters parameters ) {
		textParameters = new TextParameters( parameters );
		super.paint( g, parameters );
		textParameters = null;
	}

	public void setPosition( TextPositionStrategy position ) {
		if( position == null ) {
			throw new IllegalArgumentException( "position must not be null" );
		}
		this.position = position;
		regraph();
	}

	@Override
	protected double getPosition( Parameters parameters ) {
		double result = position.getPosition( textParameters );
		textParameters.position = result;
		return result;
	}

	public void setAngle( TextAngleStrategy angle ) {
		if( angle == null ) {
			throw new IllegalArgumentException( "angle must not be null" );
		}
		this.angle = angle;
		regraph();
	}

	@Override
	protected double getAngle( Parameters parameters ) {
		return angle.getAngle( textParameters );
	}

	public void setDistance( TextDistanceStrategy distance ) {
		if( distance == null ) {
			throw new IllegalArgumentException( "distance must not be null" );
		}
		this.distance = distance;
		regraph();
	}

	@Override
	protected double getDistance( Parameters parameters ) {
		return distance.getDistance( textParameters );
	}

	public void setShift( TextShiftStrategy shift ) {
		if( shift == null ) {
			throw new IllegalArgumentException( "shift must not be null" );
		}
		this.shift = shift;
		regraph();
	}

	@Override
	protected double getShift( Parameters parameters ) {
		return shift.getShift( textParameters );
	}

	private class TextParameters implements TextStrategyParameters {
		private Parameters parameters;
		private double position;

		public TextParameters( Parameters parameters ) {
			this.parameters = parameters;
		}

		@Override
		public EnhancedPath2D getPath() {
			return parameters.getPath();
		}

		@Override
		public double getPosition() {
			return position;
		}

		@Override
		public FontMetrics getFontMetrics() {
			return parameters.getFontMetrics();
		}

		@Override
		public LineMetrics getLineMetrics() {
			return parameters.getLineMetrics();
		}

	}
}
