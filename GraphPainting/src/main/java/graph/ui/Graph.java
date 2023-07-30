package graph.ui;

import graph.model.GraphItem;
import graph.model.GraphItemParent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper around a {@link GraphPanel} and its surrounding classes. Allows for easier setup of 
 * graphs.
 * @author Benjamin Sigg
 */
public class Graph implements GraphItemParent{
	private List<GraphListener> listeners = new ArrayList<>();
	private List<GraphItem> items = new ArrayList<>();
	
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
}
