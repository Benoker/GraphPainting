package graph.uml.intern;

import graph.items.PathedGraphConnection;
import graph.items.connection.DirectLineConnection;
import graph.items.connection.UndirectedEndPoint;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.Item;
import graph.uml.ItemKey;

import java.awt.BasicStroke;
import java.util.Arrays;

public class CommentConnection extends AbstractConnection implements Connection {
	private DirectLineConnection line;
	private DefaultCommentBox comment;

	public CommentConnection( DefaultUmlDiagram diagram, DefaultCommentBox sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, targetBox, null );
		this.comment = sourceBox;
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}

	@Override
	public void setSourceItem( DefaultBox<?> sourceItem ) {
		if( sourceItem != null ){
			comment = (DefaultCommentBox)sourceItem;
			comment.setConnection( this );
		} else{
			comment = null;
		}
		super.setSourceItem( sourceItem );
	}
	
	public CommentConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public CommentConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, key );
		initLine();
	}

	@Override
	protected Iterable<Item> dependentItems() {
		return Arrays.<Item> asList( comment );
	}

	private void initLine() {
		line = new DirectLineConnection();
		float[] dash = { 8.0f };
		line.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f ) );
		addChild( line );

		line.setSourcePoint( new UndirectedEndPoint() );
		line.setTargetPoint( new UndirectedEndPoint() );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}

	@Override
	public PathedGraphConnection getGraphConnection() {
		return line;
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.COMMENT;
	}
}
