package graph.uml.io;

import graph.ui.Graph;
import graph.uml.Item;
import graph.uml.ItemKey;
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
	private Map<Class<? extends ItemKey<?>>, DataConverter<?, ?, ?>> converters = new HashMap<>(); 
	
	public UmlDiagramConverter(){
		addConverter( CommentKey.class, new CommentDataConverter() );
		addConverter( ConnectionKey.class, new ConnectionDataConverter() );
		addConverter( TypeKey.class, new TypeDataConverter() );
	}
	
	public <I extends Item> void addConverter( Class<? extends ItemKey<I>> keyType, DataConverter<I, ? extends Data<I>, ? extends DefaultItem<I>> converter){
		converters.put( keyType, converter );
	}
	
	@SuppressWarnings( "unchecked" )
	private DataConverter<Item, Data<Item>, DefaultItem<Item>> getConverterFor( ItemKey<?> key ){
		DataConverter<?, ?, ?> result = converters.get( key.getClass() );
		if( result == null ){
			throw new IllegalArgumentException( "unknown type of data: " + key.getClass() );
		}
		return (DataConverter<Item, Data<Item>, DefaultItem<Item>>)result;
	}
	
	@SuppressWarnings( "unchecked" )
	public UmlDiagramData toData( DefaultUmlDiagram diagram ){
		UmlDiagramData diagramData = new UmlDiagramData();
		diagramData.setNextUnqiueId( diagram.currentUniqueId() );
		
		for( DefaultItem<?> item : diagram.getDefaultItems() ){
			DataConverter<?, ?, DefaultItem<Item>> converter = getConverterFor( item.getKey() );
			Data<?> data = converter.toData( (DefaultItem<Item>)item );
			data.validate();
			diagramData.addData( data );
		}
		
		return diagramData;
	}
	
	@SuppressWarnings( "unchecked" )
	public DefaultUmlDiagram toDiagram( UmlDiagramData data ){
		Graph graph = new Graph();
		DefaultUmlDiagram diagram = new DefaultUmlDiagram( graph );
		diagram.setCurrentUniqueId( data.getNextUnqiueId() );
		
		for( Data<?> itemData : data ){
			DataConverter<?, Data<Item>, ?> converter = getConverterFor( itemData.getKey() );
			DefaultItem<?> item = converter.toItem( (Data<Item>)itemData, diagram );
			item.makeVisible();
		}
		
		return diagram;
	}
}
