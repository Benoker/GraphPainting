package graph.uml.intern;

import graph.items.connection.FluentRectangularConnectionArray;
import graph.items.connection.SimpleRectangularConnectionArray;
import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.TypeBox;

/**
 * Default implementation of a {@link TypeBox}
 * @author Benjamin Sigg
 */
public class DefaultTypeBox extends DefaultBox implements TypeBox {
	private FluentRectangularConnectionArray umlDiagramConnections;
	private SimpleRectangularConnectionArray commentConnections;
	
	/**
	 * Creates a new {@link TypeBox}.
	 * @param graph the graph on which this box will be displayed
	 */
	public DefaultTypeBox( Graph graph ) {
		super( graph );
		
		umlDiagramConnections = new FluentRectangularConnectionArray();
		commentConnections = new SimpleRectangularConnectionArray();
		
		getLabel().addChild( umlDiagramConnections );
		getLabel().addChild( commentConnections );
	}
	
	/**
	 * Gets the connection array which should be used to connect this box to other {@link TypeBox}es.
	 * @return the type connection array
	 */
	public ConnectionArray getUmlDiagramConnections() {
		return umlDiagramConnections;
	}
	
	/**
	 * Gets the connection array which should be used to connect this box to other {@link CommentBox}es.
	 * @return the comment connection array
	 */
	public ConnectionArray getCommentConnections() {
		return commentConnections;
	}
	
	@Override
	public Connection addInheritsFrom( TypeBox superType ) {
		ConnectionArray target = ((DefaultTypeBox)superType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		return new ExtendsConnection( getGraph(), source, target );
	}
	
	@Override
	public Connection addImplementsFrom( TypeBox interfaceType ) {
		ConnectionArray target = ((DefaultTypeBox)interfaceType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		return new ImplementsConnection( getGraph(), source, target );
	}
	
	@Override
	public Connection addAggregation( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		return new AggregationConnection( getGraph(), source, target );
	}
	
	@Override
	public Connection addComposition( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		return new CompositionConnection( getGraph(), source, target );
	}
	
	@Override
	public CommentBox addComment( String text ) {
		DefaultCommentBox box = new DefaultCommentBox( getGraph(), this );
		box.setText( text );
		return box;
	}
}
