package graph.uml.intern;

import graph.items.connection.FluentRectangularConnectionArray;
import graph.items.connection.SimpleRectangularConnectionArray;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.ItemKey;
import graph.uml.TypeBox;
import graph.uml.intern.keys.TypeKey;
import graph.uml.intern.tools.TypeConnectionableCapability;

/**
 * Default implementation of a {@link TypeBox}
 * @author Benjamin Sigg
 */
public class DefaultTypeBox extends DefaultBox<TypeBox> implements TypeBox {
	private FluentRectangularConnectionArray umlDiagramConnections;
	private SimpleRectangularConnectionArray commentConnections;
	
	/**
	 * Creates a new {@link TypeBox}.
	 * @param diagram the diagram on which this box will be displayed
	 */
	public DefaultTypeBox( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}
	
	public DefaultTypeBox( DefaultUmlDiagram diagram, ItemKey<TypeBox> key ) {
		super( diagram, key );
		
		umlDiagramConnections = new FluentRectangularConnectionArray();
		commentConnections = new SimpleRectangularConnectionArray();
		
		getLabel().addChild( umlDiagramConnections );
		getLabel().addChild( commentConnections );
		
		setCapability( CapabilityName.CONNECTABLE, new TypeConnectionableCapability( this ) );
	}
	
	@Override
	protected ItemKey<TypeBox> createKey( DefaultUmlDiagram diagram ) {
		return new TypeKey( diagram );
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
		
		ExtendsConnection result = new ExtendsConnection( getDiagram(), this, source, (DefaultTypeBox)superType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addImplementsFrom( TypeBox interfaceType ) {
		ConnectionArray target = ((DefaultTypeBox)interfaceType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		ImplementsConnection result = new ImplementsConnection( getDiagram(), this, source, (DefaultTypeBox)interfaceType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addAggregation( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		AggregationConnection result = new AggregationConnection( getDiagram(), this, source, (DefaultTypeBox)parentType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public Connection addComposition( TypeBox parentType ) {
		ConnectionArray target = ((DefaultTypeBox)parentType).getUmlDiagramConnections();
		ConnectionArray source = getUmlDiagramConnections();
		
		CompositionConnection result = new CompositionConnection( getDiagram(), this, source, (DefaultTypeBox)parentType, target );
		result.makeVisible();
		return result;
	}
	
	@Override
	public CommentBox addComment( String text ) {
		DefaultCommentBox box = new DefaultCommentBox( getDiagram(), this );
		box.setText( text );
		box.makeVisible();
		return box;
	}
}
