package graph.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graph.items.ColoredRectangle;
import graph.items.ConnectionFlavor;
import graph.items.ConnectionableCapability;
import graph.items.capability.ConnectionFactory;
import graph.items.capability.ConnectionableCapabilityHandler;
import graph.items.capability.OpenEndedLineConnectionStrategy;
import graph.items.connection.BezierLineConnection;
import graph.items.connection.FluentRectangularConnectionArray;
import graph.model.GraphItem;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.model.connection.GraphConnection;

import javax.swing.JFrame;

public class ConnectionTest {
	public static void main( String[] args ) {
		JFrame frame = new JFrame( "Graph" );
		frame.setBounds( 20, 20, 800, 600 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Graph graph = new Graph();
		
		for( GraphItem item : createTestItems() ){
			graph.addItem( item );
		}
		
		ConnectionableCapabilityHandler connectability = new ConnectionableCapabilityHandler();
		connectability.setOpenEnded( new OpenEndedLineConnectionStrategy() );
		connectability.setFactory( factory() );
		graph.setCapability( CapabilityName.CONNECTABLE, connectability );
		
		frame.add( graph.getView() );
		frame.setVisible( true );
	}

	private static List<GraphItem> createTestItems(){
		List<GraphItem> result = new ArrayList<>();
		
		for( int x = 0; x < 4; x++ ){
			for( int y = 0; y < 4; y++ ){
				ColoredRectangle rect = new ColoredRectangle( Color.GRAY );
				FluentRectangularConnectionArray array = new FluentRectangularConnectionArray();
				rect.setBoundaries( 200+x*200, 200+y*200, 50, 50 );
				rect.addChild( array );
				rect.setCapability( CapabilityName.CONNECTABLE, connect( array ) );
				result.add( rect );
			}
		}
		
		return result;
	}
	
	
	private static ConnectionFactory factory() {
		return new ConnectionFactory() {
			@Override
			public ConnectionFlavor getFlavor() {
				return ConnectionFlavor.GENERIC;
			}
			
			@Override
			public GraphConnection createConnection() {
				BezierLineConnection connection = new BezierLineConnection();
				return connection;
			}
		}; 
	}

	private static ConnectionableCapability connect( final FluentRectangularConnectionArray array ) {
		return new ConnectionableCapability() {
			@Override
			public boolean isTarget( ConnectionFlavor flavor ) {
				return true;
			}
			
			@Override
			public boolean isSource( ConnectionFlavor flavor ) {
				return true;
			}
			
			@Override
			public ConnectionArray getTargetArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
				return array;
			}
			
			@Override
			public ConnectionArray getSourceArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
				return array;
			}
			
			@Override
			public float containsConnectionable( int x, int y ) {
				if( array.contains( x, y )){
					return 1.0f;
				}
				else{
					return 0.0f;
				}
			}
			
			@Override
			public boolean allowTarget( ConnectionableCapability item, ConnectionFlavor flavor ) {
				return item != this;
			}
			
			@Override
			public boolean allowSource( ConnectionableCapability item, ConnectionFlavor flavor ) {
				return item != this;
			}
		};
	}
}
