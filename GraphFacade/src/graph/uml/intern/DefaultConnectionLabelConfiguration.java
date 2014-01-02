package graph.uml.intern;

import graph.items.connection.text.TextStrategyFactory;

/**
 * Configures position, angle and other properties of a {@link DefaultConnectionLabel}.
 * @author Benjamin Sigg
 */
public interface DefaultConnectionLabelConfiguration {
	/** Resets all properties of a label to the default settings */
	public static final DefaultConnectionLabelConfiguration RESET = new DefaultConnectionLabelConfiguration() {
		@Override
		public void applyTo( DefaultConnectionLabel label ) {
			label.setAngle( TextStrategyFactory.angleParallel() );
			label.setBackgroundColor( null );
			label.setDistance( TextStrategyFactory.distanceDownwards( TextStrategyFactory.distanceAscent() ) );
			label.setPosition( TextStrategyFactory.position( 0.5 ) );
			label.setShift( TextStrategyFactory.shiftInvardsByPosition() );
		}
	};

	/** Makes sure a label appears near the source of the connection */
	public static final DefaultConnectionLabelConfiguration SOURCE = new DefaultConnectionLabelConfiguration() {
		@Override
		public void applyTo( DefaultConnectionLabel label ) {
			label.setPosition( TextStrategyFactory.positionNearSource() );
		}
	};

	/** Makes sure a label appears near the target of the connection */
	public static final DefaultConnectionLabelConfiguration TARGET = new DefaultConnectionLabelConfiguration() {
		@Override
		public void applyTo( DefaultConnectionLabel label ) {
			label.setPosition( TextStrategyFactory.positionNearTarget() );
		}
	};

	/** Makes sure a label appears at the middle of the connection */
	public static final DefaultConnectionLabelConfiguration MIDDLE = new DefaultConnectionLabelConfiguration() {
		@Override
		public void applyTo( DefaultConnectionLabel label ) {
			label.setPosition( TextStrategyFactory.position( 0.5 ) );
			label.setShift( TextStrategyFactory.shift( 0.5 ) );
		}
	};

	/**
	 * Configures the properties of <code>label</code>.
	 * @param label the label to configure
	 */
	public void applyTo( DefaultConnectionLabel label );
}
