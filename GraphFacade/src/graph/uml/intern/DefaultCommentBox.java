package graph.uml.intern;

import java.awt.Color;

import graph.items.connection.SimpleRectangularConnectionArray;
import graph.ui.Graph;
import graph.uml.CommentBox;

/**
 * Default implementation of a {@link CommentBox}
 * @author Benjamin Sigg
 */
public class DefaultCommentBox extends DefaultBox implements CommentBox{
	private CommentConnection connection;
	private SimpleRectangularConnectionArray connectionArray;
	
	public DefaultCommentBox( Graph graph, DefaultTypeBox type ){
		super( graph );
		
		setBackground( new Color( 255, 255, 150 ) );
		setSelectedPrimary( new Color( 255, 255, 200 ) );
		setSelectedSecondary( new Color( 255, 255, 175 ) );
		
		connectionArray = new SimpleRectangularConnectionArray();
		getLabel().addChild( connectionArray );
		
		connection = new CommentConnection( graph, connectionArray, type.getCommentConnections() );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		super.setVisible( visible );
		connection.setVisible( visible );
	}
}
