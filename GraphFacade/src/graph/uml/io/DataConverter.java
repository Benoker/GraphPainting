package graph.uml.io;

import graph.uml.Item;
import graph.uml.intern.DefaultItem;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * An algorithm used to convert some {@link DefaultItem} to its {@link Data} object
 * and the other way around
 * @author Benjamin Sigg
 */
public interface DataConverter<I extends Item, D extends Data<I>, T extends DefaultItem<I>> {
	public D toData( T item );
	
	public T toItem( D data, DefaultUmlDiagram diagram );
}
