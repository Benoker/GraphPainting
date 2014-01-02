package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.intern.config.DefaultUmlConfiguration;

import java.util.Arrays;

public class CommentConnection extends AbstractConnection implements Connection {
	public static final ConnectionFlavor COMMENT = new ConnectionFlavor( "comment" );

	private DefaultCommentBox comment;

	public CommentConnection( DefaultUmlDiagram diagram, DefaultCommentBox sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
		this.comment = sourceBox;
	}

	@Override
	public void setSourceItem( DefaultBox<?> sourceItem ) {
		if( sourceItem != null ) {
			comment = (DefaultCommentBox) sourceItem;
			comment.setConnection( this );
		} else {
			comment = null;
		}
		super.setSourceItem( sourceItem );
	}

	public CommentConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public CommentConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected Iterable<Item> dependentItems() {
		return Arrays.<Item> asList( comment );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getComment().buildLine();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.COMMENT;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return COMMENT;
	}
}
