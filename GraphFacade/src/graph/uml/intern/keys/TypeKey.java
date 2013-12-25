package graph.uml.intern.keys;

import graph.uml.TypeBox;
import graph.uml.intern.DefaultTypeBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key that represents a {@link DefaultTypeBox}.
 * @author Benjamin Sigg
 */
public class TypeKey extends DefaultItemKey<TypeBox>{
	public TypeKey( DefaultUmlDiagram diagram ) {
		super( diagram, "type" );
	}
}
