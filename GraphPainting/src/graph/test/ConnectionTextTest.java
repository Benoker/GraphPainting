package graph.test;

import graph.items.ColoredRectangle;
import graph.items.PathedGraphConnection;
import graph.items.connection.AbstractConnectionText;
import graph.items.connection.BezierLineConnection;
import graph.items.connection.FluentRectangularConnectionArray;
import graph.items.connection.text.ConfigurableConnectionText;
import graph.items.connection.text.TextStrategyFactory;
import graph.ui.Graph;
import graph.ui.GraphPanel;
import graph.util.Geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

public class ConnectionTextTest {
	public static void main( String[] args ) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		GraphPanel panel = new GraphPanel();
		frame.add( panel );
		frame.setBounds( 20, 20, 1000, 900 );

		Graph graph = new Graph();
		panel.setGraph( graph );

		for( int i = 0; i < 8; i++ ) {
			ColoredRectangle rectA = new ColoredRectangle( Color.BLUE );
			rectA.setBoundaries( 50, 50 + i * 100, 50, 50 );
			FluentRectangularConnectionArray arrayA = new FluentRectangularConnectionArray();
			rectA.addChild( arrayA );
			graph.addItem( rectA );

			ColoredRectangle rectB = new ColoredRectangle( Color.GREEN );
			rectB.setBoundaries( 800, 50 + i * 100, 50, 50 );
			FluentRectangularConnectionArray arrayB = new FluentRectangularConnectionArray();
			rectB.addChild( arrayB );
			graph.addItem( rectB );

			BezierLineConnection bezier = new BezierLineConnection();
			arrayA.add( bezier.getSourceEndPoint() );
			arrayB.add( bezier.getTargetEndPoint() );
			graph.addItem( bezier );

			for( int j = 0; j < 10; j++ ) {
				AbstractConnectionText t = textFor( bezier, i, j / 9.0f );
				if( t != null ) {
					// t.setBackgroundColor( Color.GREEN );
					graph.addItem( t );
				}
			}
		}

		frame.setVisible( true );
	}

	private static AbstractConnectionText textFor( PathedGraphConnection connection, int i, float j ) {
		switch( i ){
			case 0:
				return positionTest( connection, j );
			case 1:
				return distanceTest( connection, j );
			case 2:
				return shiftTest( connection, j );
			case 3:
				return angleTest( connection, j );
			case 4:
				return multiTest( connection, j );
			case 5:
				return distanceUpwardsTest( connection, j );
			case 6:
				return angleParallelTest( connection, j );
			case 7:
				return goodLocation( connection, j );
			default:
				throw new IllegalArgumentException();
		}
	}

	private static TestText positionTest( PathedGraphConnection connection, float j ) {
		return new TestText( connection, "pos=" + round( j ), j, 0, 0, 0 );
	}

	private static TestText distanceTest( PathedGraphConnection connection, float j ) {
		float dist = 20 * j;
		return new TestText( connection, "dist=" + round( dist ), j, dist, 0, 0 );
	}

	private static TestText shiftTest( PathedGraphConnection connection, float j ) {
		return new TestText( connection, "shift=" + round( j ), j, 0, j, 0 );
	}

	private static TestText angleTest( PathedGraphConnection connection, float j ) {
		double angle = Math.PI * 2 * j;
		return new TestText( connection, "angle=" + round( Math.toDegrees( angle ) ), j, 0, 0, angle );
	}

	private static TestText multiTest( PathedGraphConnection connection, float j ) {
		return new TestText( connection, "multi=" + round( j ), j, 20 * j, j, Math.PI * 2 * j );
	}

	private static ConfigurableConnectionText distanceUpwardsTest( PathedGraphConnection connection, float j ) {
		ConfigurableConnectionText text = new ConfigurableConnectionText( connection );
		text.setText( "upwards=" + round( j ) );
		text.setPosition( TextStrategyFactory.position( j ) );
		text.setDistance( TextStrategyFactory.distanceUpdwards( TextStrategyFactory.distance( 20 ) ) );
		return text;
	}

	private static ConfigurableConnectionText angleParallelTest( PathedGraphConnection connection, float j ) {
		ConfigurableConnectionText text = new ConfigurableConnectionText( connection );
		text.setText( "parallel=" + round( j ) );
		text.setPosition( TextStrategyFactory.position( j ) );
		text.setAngle( TextStrategyFactory.angleParallel() );
		return text;
	}

	private static ConfigurableConnectionText goodLocation( PathedGraphConnection connection, float j ) {
		if( j == 0 ) {
			ConfigurableConnectionText text = new ConfigurableConnectionText( connection );
			text.setText( "Source" );
			text.setPosition( TextStrategyFactory.positionNearSource() );
			text.setAngle( TextStrategyFactory.angleParallel() );
			text.setShift( TextStrategyFactory.shiftInvardsByPosition() );
			text.setDistance( TextStrategyFactory.distanceDownwards( TextStrategyFactory.distanceBaslineFloating() ) );
			return text;
		} else if( j == 1 ) {
			ConfigurableConnectionText text = new ConfigurableConnectionText( connection );
			text.setText( "Target" );
			text.setPosition( TextStrategyFactory.positionNearTarget() );
			text.setAngle( TextStrategyFactory.angleParallel() );
			text.setShift( TextStrategyFactory.shiftInvardsByPosition() );
			text.setDistance( TextStrategyFactory.distanceDownwards( TextStrategyFactory.distanceBaslineFloating() ) );
			return text;
		} else {
			return null;
		}
	}

	private static String round( double value ) {
		return String.format( "%3.1f", value );
	}

	private static class TestText extends AbstractConnectionText {
		private double position;
		private double distance;
		private double shift;
		private double angle;

		public TestText( PathedGraphConnection connection, String text, double position, double distance, double shift, double angle ) {
			super( connection );
			this.position = position;
			this.distance = distance;
			this.shift = shift;
			this.angle = angle;
			setText( text );
		}

		@Override
		public void paint( Graphics2D g, Parameters parameters ) {
			g.setColor( Color.RED );
			Point2D base = getTextPosition( parameters );
			Point2D center = getTextCenter( parameters );

			int bx = Geom.round( base.getX() );
			int by = Geom.round( base.getY() );
			int cx = Geom.round( center.getX() );
			int cy = Geom.round( center.getY() );

			g.fillOval( bx - 2, by - 2, 4, 4 );
			g.fillOval( cx - 2, cy - 2, 4, 4 );
			g.drawLine( bx, by, cx, cy );

			super.paint( g, parameters );
		}

		@Override
		protected double getPosition( Parameters parameters ) {
			return position;
		}

		@Override
		protected double getAngle( Parameters parameters ) {
			return angle;
		}

		@Override
		protected double getDistance( Parameters parameters ) {
			return distance;
		}

		@Override
		protected double getShift( Parameters parameters ) {
			return shift;
		}
	}
}
