package graph.uml.intern.tools;

import graph.model.capability.ContextCapability;
import graph.model.capability.ContextSite;
import graph.uml.Item;
import graph.uml.event.ItemContextEvent;
import graph.uml.event.ItemContextListener;
import graph.uml.intern.DefaultItem;
import graph.uml.intern.DefaultUmlDiagram;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * The capability of a {@link DefaultItem} to have a context menu.
 * @author Benjamin Sigg
 */
public class DefaultItemContextCapability implements ContextCapability{
	private DefaultUmlDiagram diagram;
	private DefaultItem<?> item;
	private List<ItemContextListener> listeners = new ArrayList<>( 1 );
	
	/**
	 * Creates a new capability.
	 * @param diagram the diagram that contains all the items
	 * @param item the item which is enriched by this capability
	 */
	public DefaultItemContextCapability( DefaultUmlDiagram diagram, DefaultItem<?> item ){
		this.diagram = diagram;
		this.item = item;
	}
	
	/**
	 * Adds a listener to which events from this capability are to be forwarded.
	 * @param listener the new listener
	 */
	public void addListener( ItemContextListener listener ){
		if( listener == null ){
			throw new IllegalArgumentException( "listener must not be null" );
		}
		listeners.add( listener );
	}
	
	/**
	 * Removes the observer <code>listener</code> from this capability.
	 * @param listener the listener to remove
	 */
	public void removeListener( ItemContextListener listener ){
		listeners.remove( listener );
	}
	
	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		if( listeners.isEmpty() ){
			return 0.f;
		}
		return item.isContextMenuEnabledAt( x, y );
	}
	
	@Override
	public void context( ContextSite site ) {
		ItemContextListener[] copy = listeners.toArray( new ItemContextListener[ listeners.size() ] );
		Event event = new Event( site );
		
		for( ItemContextListener listener : copy ){
			listener.contextAction( event );
		}
	}
	
	private class Event implements ItemContextEvent{
		private ContextSite site;
		private boolean consumed = false;
		
		public Event( ContextSite site ){
			this.site = site;
		}
		
		@Override
		public void consume() {
			consumed = true;	
		}
		
		@Override
		public boolean isConsumed() {
			return consumed;
		}
		
		@Override
		public int getX() {
			return site.getX();
		}

		@Override
		public int getY() {
			return site.getY();
		}

		@Override
		public Item getSource() {
			return item;
		}

		@Override
		public List<Item> getSelection() {
			return Collections.unmodifiableList( diagram.getSelection() );
		}

		@Override
		public Component getComponent() {
			return site.getInvoker();
		}

		@Override
		public void show( JPopupMenu menu ) {
			site.show( menu );
		}

		@Override
		public void show( Iterable<JComponent> menuEntries ) {
			site.showMenu( menuEntries );
		}
	}
}
