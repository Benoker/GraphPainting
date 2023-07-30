package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.items.ConnectionableCapability;
import graph.model.connection.ConnectionArray;
import graph.uml.TypeBox;
import graph.uml.intern.AggregationConnection;
import graph.uml.intern.AssoziationConnection;
import graph.uml.intern.CompositionConnection;
import graph.uml.intern.DefaultTypeBox;
import graph.uml.intern.ExtendsConnection;
import graph.uml.intern.ImplementsConnection;

/**
 * The capability allowing {@link TypeBox}es to be connected to other boxes.
 * @author Benjamin Sigg
 */
public class TypeConnectionableCapability extends UmlConnectionableCapability {
	private DefaultTypeBox box;

	public TypeConnectionableCapability( DefaultTypeBox box ) {
		super( box );
		this.box = box;
	}

	private boolean isTypeFlavor( ConnectionFlavor flavor ) {
		if( flavor.equals( ExtendsConnection.EXTENDS ) ) {
			return true;
		}
		if( flavor.equals( ImplementsConnection.IMPLEMENTS ) ) {
			return true;
		}
		if( flavor.equals( AssoziationConnection.ASSOZIATION ) ){
			return true;
		}
		if( flavor.equals( CompositionConnection.COMPOSITION ) ) {
			return true;
		}
		if( flavor.equals( AggregationConnection.AGGREGATION ) ) {
			return true;
		}
		return false;
	}

	private boolean allowSelfReference( ConnectionFlavor flavor ) {
		if( flavor.equals( AssoziationConnection.ASSOZIATION ) ) {
			return true;
		}
		
		if( flavor.equals( CompositionConnection.COMPOSITION ) ) {
			return true;
		}
		if( flavor.equals( AggregationConnection.AGGREGATION ) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSource( ConnectionFlavor flavor ) {
		return isTypeFlavor( flavor );
	}

	@Override
	public boolean isTarget( ConnectionFlavor flavor ) {
		return isTypeFlavor( flavor );
	}

	@Override
	public boolean allowSource( ConnectionableCapability item, ConnectionFlavor flavor ) {
		return item != this || allowSelfReference( flavor );
	}

	@Override
	public boolean allowTarget( ConnectionableCapability item, ConnectionFlavor flavor ) {
		return item != this || allowSelfReference( flavor );
	}

	@Override
	public ConnectionArray getSourceArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		if( isTypeFlavor( flavor ) ) {
			return box.getUmlDiagramConnections();
		}
		return null;
	}

	@Override
	public ConnectionArray getTargetArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		if( isTypeFlavor( flavor ) ) {
			return box.getUmlDiagramConnections();
		}
		return null;
	}

	@Override
	public ConnectionArray getSourceArray( ConnectionFlavor flavor ) {
		return box.getUmlDiagramConnections();
	}

	@Override
	public ConnectionArray getTargetArray( ConnectionFlavor flavor ) {
		if( isTypeFlavor( flavor ) ) {
			return box.getUmlDiagramConnections();
		} else {
			return box.getCommentConnections();
		}
	}
}
