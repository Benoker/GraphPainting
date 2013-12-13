package graph.uml.intern;

import graph.items.connection.FluentRectangularConnectionArray;
import graph.items.connection.SimpleRectangularConnectionArray;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.TypeBox;
import graph.uml.intern.tools.TypeConnectionableCapability;

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
		
		setCapability( CapabilityName.CONNECTABLE, new TypeConnectionableCapability( this ) );
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
		
		ExtendsConnection result = new ExtendsConnection( getGraph(), this, source, (DefaultTypeBox)superType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addImplementsFrom( TypeBox interfaceType ) {
		ConnectionArray target = ((DefaultTypeBox)interfaceType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		ImplementsConnection result = new ImplementsConnection( getGraph(), this, source, (DefaultTypeBox)interfaceType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addAggregation( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		AggregationConnection result = new AggregationConnection( getGraph(), this, source, (DefaultTypeBox)parentType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addComposition( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		CompositionConnection result = new CompositionConnection( getGraph(), this, source, (DefaultTypeBox)parentType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public CommentBox addComment( String text ) {
		DefaultCommentBox box = new DefaultCommentBox( getGraph(), this );
		box.setText( text );
		box.makeVisible();
		return box;
	}
}
