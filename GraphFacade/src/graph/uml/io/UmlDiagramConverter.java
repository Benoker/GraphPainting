package graph.uml.io;

import graph.ui.Graph;
import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.TypeBox;
import graph.uml.UmlDiagramRepository;
import graph.uml.intern.DefaultItem;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.keys.CommentKey;
import graph.uml.intern.keys.ConnectionKey;
import graph.uml.intern.keys.TypeKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts a {@link DefaultUmlDiagram} to {@link UmlDiagramData} and back.
 * @author Benjamin Sigg
 */
public class UmlDiagramConverter {
	/** all the converters required to read and write data */
	private Map<Class<? extends ItemKey<?>>, DataConverter<?, ?, ?>> convertersByKeyType = new HashMap<>();
	private Map<Class<? extends Item>, DataConverter<?, ?, ?>> convertersByItemType = new HashMap<>();

	public UmlDiagramConverter() {
		addConverter( CommentBox.class, CommentKey.class, new CommentDataConverter() );
		addConverter( Connection.class, ConnectionKey.class, new ConnectionDataConverter() );
		addConverter( TypeBox.class, TypeKey.class, new TypeDataConverter() );
	}

	public <I extends Item> void addConverter( Class<I> itemType, Class<? extends ItemKey<I>> keyType, DataConverter<I, ? extends Data<I>, ? extends DefaultItem<I>> converter ) {
		convertersByItemType.put( itemType, converter );
		convertersByKeyType.put( keyType, converter );
	}

	@SuppressWarnings( "unchecked" )
	public <T extends Item> ItemKey<T> readKey( String uniqueId, Class<T> type ) {
		DataConverter<T, ?, ?> converter = (DataConverter<T, ?, ?>)convertersByItemType.get( type );
		if(converter == null ){
			throw new IllegalArgumentException( "unknown type of data: " + type );
		}
		return converter.readKey( uniqueId );
	}
	
	@SuppressWarnings( "unchecked" )
	public <T extends Item> ItemKey<? extends T> readBoundedKey( String uniqueId, Class<T> type ){
		for( Map.Entry<Class<? extends Item>, DataConverter<?, ?, ?>> entry : convertersByItemType.entrySet() ){
			if( type.isAssignableFrom( entry.getKey() )){
				DataConverter<? extends T, ?, ?> converter = (DataConverter<? extends T, ?, ?>)entry.getValue();
				if( converter.isKey( uniqueId )){
					return converter.readKey( uniqueId );
				}
			}
		}
		throw new IllegalArgumentException( "unable to read key: " + uniqueId );
	}
	
	@SuppressWarnings( "unchecked" )
	private DataConverter<Item, Data<Item>, DefaultItem<Item>> getConverterFor( ItemKey<?> key ) {
		DataConverter<?, ?, ?> result = convertersByKeyType.get( key.getClass() );
		if( result == null ) {
			throw new IllegalArgumentException( "unknown type of data: " + key.getClass() );
		}
		return (DataConverter<Item, Data<Item>, DefaultItem<Item>>) result;
	}

	@SuppressWarnings( "unchecked" )
	public UmlDiagramData toData( DefaultUmlDiagram diagram ) {
		UmlDiagramData diagramData = new UmlDiagramData();
		diagramData.setNextUnqiueId( diagram.currentUniqueId() );

		for( DefaultItem<?> item : diagram.getDefaultItems() ) {
			DataConverter<?, ?, DefaultItem<Item>> converter = getConverterFor( item.getKey() );
			Data<?> data = converter.toData( (DefaultItem<Item>) item );
			if( data != null ){
				data.validate();
				diagramData.addData( data );
			}
		}

		return diagramData;
	}

	@SuppressWarnings( "unchecked" )
	public DefaultUmlDiagram toDiagram( UmlDiagramData data, UmlDiagramRepository repository ) {
		Graph graph = new Graph();
		DefaultUmlDiagram diagram = new DefaultUmlDiagram( graph, repository );
		diagram.setCurrentUniqueId( data.getNextUnqiueId() );

		for( Data<?> itemData : data ) {
			DataConverter<?, Data<Item>, ?> converter = getConverterFor( itemData.getKey() );
			DefaultItem<?> item = converter.toItem( (Data<Item>) itemData, diagram );
			item.makeVisible();
		}

		return diagram;
	}
}
