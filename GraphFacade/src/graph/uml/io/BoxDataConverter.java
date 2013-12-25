package graph.uml.io;

import graph.uml.Box;
import graph.uml.ItemKey;
import graph.uml.intern.DefaultBox;
import graph.uml.intern.DefaultUmlDiagram;

import java.awt.Rectangle;

/**
 * Converter for any kind of {@link DefaultBox}es.
 * @author Benjamin Sigg
 */
public abstract class BoxDataConverter<I extends Box, D extends BoxData<I>, T extends DefaultBox<I>> implements DataConverter<I,D,T>{

	@Override
	public D toData( T item ) {
		D data = createEmptyData( item.getKey() );
		
		Rectangle bounds = item.getLabel().getBoundaries();
		
		data.setX( bounds.x );
		data.setY( bounds.y );
		data.setWidth( bounds.width );
		data.setHeight( bounds.height );
		
		return data;
	}

	@Override
	public T toItem( D data, DefaultUmlDiagram diagram ) {
		T box = createEmptyBox( data.getKey(), diagram );
		box.setLocation( data.getX(), data.getY() );
		return box;
	}
	
	protected abstract D createEmptyData( ItemKey<I> key );
	
	protected abstract T createEmptyBox( ItemKey<I> key, DefaultUmlDiagram diagram );
}
