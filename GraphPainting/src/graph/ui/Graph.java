package graph.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graph.items.capability.ContextCapabilityHandler;
import graph.items.capability.MoveableCapabilityHandler;
import graph.items.capability.SelectableCapabilityHandler;
import graph.model.GraphItem;
import graph.model.GraphItemParent;
import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityName;

import javax.swing.JComponent;

/**
 * Wrapper around a {@link GraphPanel} and its surrounding classes. Allows for easier setup of 
 * graphs.
 * @author Benjamin Sigg
 */
public class Graph implements GraphItemParent{
	private GraphPanel panel;
	private CapabilityController capabilityController;
	private List<GraphListener> listeners = new ArrayList<>();
	private List<GraphItem> items = new ArrayList<>();
	
	public Graph(){
		panel = new GraphPanel( this );
		
		capabilityController = new CapabilityController( panel );
		capabilityController.register( CapabilityName.MOVEABLE, new MoveableCapabilityHandler() );
		capabilityController.register( CapabilityName.SELECTABLE, new SelectableCapabilityHandler() );
		capabilityController.register( CapabilityName.CONTEXT_MENU, new ContextCapabilityHandler() );
	}
	
	/**
	 * Adds the observer <code>listener</code> to this graph.
	 * @param listener a new observer, not <code>null</code>
	 */
	public void addGraphListener( GraphListener listener ){
		listeners.add( listener );
	}

	/**
	 * Removes the observer <code>listener</code> from this graph.
	 * @param listener the observer to remove
	 */
	public void removeGraphListener( GraphListener listener ){
		listeners.remove( listener );
	}
	
	private GraphListener[] listeners(){
		return listeners.toArray( new GraphListener[ listeners.size() ] );
	}
	
	/**
	 * Gets a {@link JComponent} that paints the graph.
	 * @return the component that paints the graph
	 */
	public JComponent getView() {
		return panel;
	}
	
	/**
	 * Adds <code>item</code> to the graph.
	 * @param item the new item
	 */
	public void addItem( GraphItem item ){
		items.add( item );
		for( GraphListener listener : listeners() ){
			listener.itemAdded( this, item );
		}
	}
	
	/**
	 * Removes <code>item</code> from the graph.
	 * @param item the item to remove
	 */
	public void removeItem( GraphItem item ){
		items.remove( item );
		for( GraphListener listener : listeners() ){
			listener.itemRemoved( this, item );
		}
	}
	
	/**
	 * Gets an unmodifiable view on the items of this {@link Graph}. Adding or removing
	 * items on the graph will affect the list.
	 * @return the list of root-items of this graph
	 */
	public List<GraphItem> getItems(){
		return Collections.unmodifiableList( items );
	}
	
	/**
	 * Adds a new capability to this graph.
	 * @param name the name of the capability
	 * @param handler the new handler, may be <code>null</code> to remove an existing handler
	 */
	public <T> void setCapability( CapabilityName<T> name, CapabilityHandler<T> handler ){
		capabilityController.register( name, handler );
	}
}
