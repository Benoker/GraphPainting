package graph.uml.intern.keys;

import graph.uml.TypeBox;
import graph.uml.intern.DefaultTypeBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key that represents a {@link DefaultTypeBox}.
 * @author Benjamin Sigg
 */
public class TypeKey extends DefaultItemKey<TypeBox>{
	private static final String TYPE = "type";
	
	public static boolean isKey( String uniqueString ){
		return isKey( uniqueString, TYPE );
	}
	
	public static TypeKey readKey( String uniqueString ){
		return new TypeKey( uniqueString );
	}
	
	public TypeKey( DefaultUmlDiagram diagram ) {
		super( diagram, TYPE );
	}
	
	private TypeKey( String uniqueString ){
		super( uniqueString, TYPE );
	}
}
