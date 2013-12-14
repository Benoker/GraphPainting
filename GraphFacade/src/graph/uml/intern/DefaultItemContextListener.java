package graph.uml.intern;

import java.util.ArrayList;
import java.util.List;

import graph.uml.Item;
import graph.uml.ItemContextEvent;
import graph.uml.ItemContextListener;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramListener;

/**
 * Handles a set of {@link ItemContextListener}s that are added to all {@link Item}s of an {@link UmlDiagram}
 * @author Benjamin Sigg
 */
public class DefaultItemContextListener {
	private List<ItemContextListener> itemContextListeners = new ArrayList<>();
	private UmlDiagram diagram;
	private Listener listener = new Listener();
	
	public DefaultItemContextListener( UmlDiagram diagram ){
		this.diagram = diagram;
	}
	
	private void addToAll(){
		for( Item item : diagram.getItems() ){
			item.addItemContextListener( listener );
		}
		diagram.addUmlDiagramListener( listener );
	}
	
	private void removeFromAll(){
		diagram.removeUmlDiagramListener( listener );
		for( Item item : diagram.getItems() ){
			item.removeItemContextListener( listener );
		}
	}
	
	public void addItemContextListener( ItemContextListener listener ) {
		if( listener == null ){
			throw new IllegalArgumentException( "listener must not be null" );
		}
		if( itemContextListeners.isEmpty() ){
			addToAll();
		}
		itemContextListeners.add( listener );
	}
	
	public void removeItemContextListener( ItemContextListener listener ) {
		itemContextListeners.remove( listener );
		if( itemContextListeners.isEmpty() ){
			removeFromAll();
		}
	}
	
	private class Listener implements ItemContextListener, UmlDiagramListener{
		@Override
		public void itemAdded( UmlDiagram diagram, Item item ) {
			item.addItemContextListener( this );
		}

		@Override
		public void itemRemoved( UmlDiagram diagram, Item item ) {
			item.removeItemContextListener( this );
		}

		@Override
		public void contextAction( ItemContextEvent event ) {
			ItemContextListener[] copy = itemContextListeners.toArray( new ItemContextListener[ itemContextListeners.size() ] );
			for( ItemContextListener listener : copy ){
				listener.contextAction( event );
			}
		}
	}
}
