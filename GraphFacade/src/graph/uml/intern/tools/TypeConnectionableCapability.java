package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.items.ConnectionableCapability;
import graph.model.connection.ConnectionArray;
import graph.uml.TypeBox;
import graph.uml.intern.DefaultTypeBox;
import graph.uml.intern.ExtendsConnection;

/**
 * The capability allowing {@link TypeBox}es to be connected to other boxes.
 * @author Benjamin Sigg
 */
public class TypeConnectionableCapability extends UmlConnectionableCapability{
	private DefaultTypeBox box;
	
	public TypeConnectionableCapability( DefaultTypeBox box ) {
		super( box );
		this.box = box;
	}

	@Override
	public boolean isSource( ConnectionFlavor flavor ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return true;
		}
		return false;
	}

	@Override
	public boolean isTarget( ConnectionFlavor flavor ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return true;
		}
		return false;
	}

	@Override
	public boolean allowSource( ConnectionableCapability item, ConnectionFlavor flavor ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return item != this;
		}
		return false;
	}

	@Override
	public boolean allowTarget( ConnectionableCapability item, ConnectionFlavor flavor ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return item != this;
		}
		return false;
	}

	@Override
	public ConnectionArray getSourceArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return box.getUmlDiagramConnections();
		}
		return null;
	}

	@Override
	public ConnectionArray getTargetArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		if( flavor.equals( ExtendsConnection.EXTENDS )){
			return box.getUmlDiagramConnections();
		}
		return null;
	}	
}
