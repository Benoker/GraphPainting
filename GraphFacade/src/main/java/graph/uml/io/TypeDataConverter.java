package graph.uml.io;

import graph.uml.ItemKey;
import graph.uml.TypeBox;
import graph.uml.intern.DefaultTypeBox;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.keys.TypeKey;

/**
 * Converter for {@link DefaultTypeBox}es.
 * @author Benjamin Sigg
 */
public class TypeDataConverter extends BoxDataConverter<TypeBox, TypeData, DefaultTypeBox>{

	@Override
	public TypeData toData( DefaultTypeBox item ) {
		TypeData data = super.toData( item );
		data.setText( item.getText() );
		return data;
	}
	
	@Override
	public DefaultTypeBox toItem( TypeData data, DefaultUmlDiagram diagram ) {
		DefaultTypeBox item = super.toItem( data, diagram );
		item.setText( data.getText() );
		return item;
	}
	
	@Override
	protected TypeData createEmptyData( ItemKey<TypeBox> key ) {
		return new TypeData( key );
	}

	@Override
	protected DefaultTypeBox createEmptyBox( ItemKey<TypeBox> key, DefaultUmlDiagram diagram ) {
		return new DefaultTypeBox( diagram, key );
	}
	
	@Override
	public boolean isKey( String uniqueId ) {
		return TypeKey.isKey( uniqueId );
	}
	
	@Override
	public ItemKey<TypeBox> readKey( String uniqueId ) {
		return TypeKey.readKey( uniqueId );
	}
}
