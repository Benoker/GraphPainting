package graph.uml.io;

import graph.uml.ItemKey;
import graph.uml.TypeBox;

/**
 * Data describing a {@link TypeBox}.
 * @author Benjamin Sigg
 */
public class TypeData extends BoxData<TypeBox>{
	private String text;
	
	public TypeData( ItemKey<TypeBox> key ) {
		super( key );
	}
	
	public void setText( String text ) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
