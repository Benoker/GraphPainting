package graph.ui;

import graph.items.ColoredRectangle;
import graph.items.RectangularComposite;
import graph.items.RectangularSelection;
import graph.items.TextBox;
import graph.items.connection.AbstractConnection;
import graph.items.connection.BezierLineConnection;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.connection.DirectLineConnection;
import graph.items.connection.FluentRectangularConnectionArray;
import graph.items.connection.PointConnector;
import graph.items.connection.SimpleRectangularConnectionArray;
import graph.items.uml.Diamond;
import graph.items.uml.FilledArrow;
import graph.items.uml.OpenArrow;
import graph.model.GraphItem;
import graph.model.capability.CapabilityName;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	public static void main( String[] args ) {
		JFrame frame = new JFrame( "Graph" );
		frame.setBounds( 20, 20, 800, 600 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Graph graph = new Graph();
		
		for( GraphItem item : createTestItems() ){
			graph.addItem( item );
		}
		
		frame.add( graph.getView() );
		frame.setVisible( true );
	}
	
	private static List<GraphItem> createTestItems(){
		List<GraphItem> result = new ArrayList<>();
		
		ColoredRectangle red = new ColoredRectangle( Color.RED );
		// SimpleRectangularConnectionArray redArray = new SimpleRectangularConnectionArray();
		FluentRectangularConnectionArray redArray = new FluentRectangularConnectionArray();
		red.setBoundaries( 200, 200, 50, 200 );
		red.addChild( redArray );
		
		TextBox a = new TextBox( "Alpha" );
		TextBox b = new TextBox( "Beta" );
		TextBox c = new TextBox( "Gamma" );
		TextBox d = new TextBox( "Delta" );
		
		a.addCapability( CapabilityName.SELECTABLE, new RectangularSelection( a ) );
		b.addCapability( CapabilityName.SELECTABLE, new RectangularSelection( b ) );
		c.addCapability( CapabilityName.SELECTABLE, new RectangularSelection( c ) );
		d.addCapability( CapabilityName.SELECTABLE, new RectangularSelection( d ) );
		
		a.setLocation( 20, 20 );
		b.setLocation( 20, 50 );
		c.setLocation( 20, 80 );
		d.setLocation( 20, 110 );
		
		result.add( a );
		result.add( b );
		result.add( c );
		result.add( d );
		result.add( red );
		
		BezierLineConnection bezier = new BezierLineConnection( redArray, a );
		bezier.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1.0f, new float[]{10.0f}, 1.0f ) );
		
		addLine( result, bezier );
		addLine( result, new DirectLineConnection( redArray, b ) );
		addLine( result, new DirectLineConnection( redArray, c ) );
		addLine( result, new DirectLineConnection( redArray, d ) );
		
		ColoredRectangle blue = new ColoredRectangle( Color.BLUE );
		SimpleRectangularConnectionArray blueArray = new SimpleRectangularConnectionArray();
		blue.setBoundaries( 400, 300, 150, 50 );
		blue.addChild( blueArray );
		result.add( blue );
		
		CuttingEdgeLineConnection blueToRed = new CuttingEdgeLineConnection( blueArray, redArray );
//		blueToRed.setSourcePoint( new UndirectedEndPoint() );
//		blueToRed.setTargetPoint( new UndirectedEndPoint() );
		
		result.add( blueToRed );
		result.add( new Diamond( blueToRed.getSourceEndPoint(), Color.GREEN ));
		result.add( new OpenArrow( blueToRed.getTargetEndPoint() ));
		
		return result;
	}
	
	private static void addLine( List<GraphItem> result, AbstractConnection connection ){
		result.add( connection );
		result.add( new PointConnector( connection.getTargetEndPoint() ) );
		result.add( new FilledArrow( connection.getSourceEndPoint() ) );
	}
}
