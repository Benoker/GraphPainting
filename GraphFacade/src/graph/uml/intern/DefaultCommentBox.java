package graph.uml.intern;

import graph.items.connection.SimpleRectangularConnectionArray;
import graph.uml.CommentBox;
import graph.uml.ItemKey;
import graph.uml.intern.keys.CommentKey;

import java.awt.Color;

/**
 * Default implementation of a {@link CommentBox}
 * @author Benjamin Sigg
 */
public class DefaultCommentBox extends DefaultBox<CommentBox> implements CommentBox{
	private DefaultBox<?> type;
	private CommentConnection connection;
	private SimpleRectangularConnectionArray connectionArray;
	
	public DefaultCommentBox( DefaultUmlDiagram diagram, DefaultTypeBox type ){
		this( diagram, type, null );
	}
	
	public DefaultCommentBox( DefaultUmlDiagram diagram, DefaultTypeBox type, ItemKey<CommentBox> key ){
		super( diagram, key );
		
		setBackground( new Color( 255, 255, 150 ) );
		setSelectedPrimary( new Color( 255, 255, 200 ) );
		setSelectedSecondary( new Color( 255, 255, 175 ) );
		
		connectionArray = new SimpleRectangularConnectionArray();
		getLabel().addChild( connectionArray );

		if( type != null ){
			this.type = type;
			connection = new CommentConnection( diagram, this, connectionArray, type, type.getCommentConnections() );
			type.addDependent( this );
		}
	}
	
	public void setConnection( CommentConnection connection ) {
		if( this.connection != null ){
			throw new IllegalStateException( "connection is already set" );
		}
		this.connection = connection;
		this.type = connection.getTargetItem();
		this.type.addDependent( this );
	}
	
	@Override
	protected ItemKey<CommentBox> createKey( DefaultUmlDiagram diagram ) {
		return new CommentKey( diagram );
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
