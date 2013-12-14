package graph.uml.intern;

import graph.items.connection.SimpleRectangularConnectionArray;
import graph.uml.CommentBox;

import java.awt.Color;

/**
 * Default implementation of a {@link CommentBox}
 * @author Benjamin Sigg
 */
public class DefaultCommentBox extends DefaultBox implements CommentBox{
	private DefaultTypeBox type;
	private CommentConnection connection;
	private SimpleRectangularConnectionArray connectionArray;
	
	public DefaultCommentBox( DefaultUmlDiagram diagram, DefaultTypeBox type ){
		super( diagram );
		this.type = type;
		
		setBackground( new Color( 255, 255, 150 ) );
		setSelectedPrimary( new Color( 255, 255, 200 ) );
		setSelectedSecondary( new Color( 255, 255, 175 ) );
		
		connectionArray = new SimpleRectangularConnectionArray();
		getLabel().addChild( connectionArray );
		
		connection = new CommentConnection( diagram, this, connectionArray, type, type.getCommentConnections() );
		type.addDependent( this );
	}
	
	@Override
	public void makeVisible() {
		super.makeVisible();
		connection.makeVisible();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		type.removeDependent( this );
	}
}
