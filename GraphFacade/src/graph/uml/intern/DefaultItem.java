package graph.uml.intern;

import graph.items.AbstractGraphItem;
import graph.ui.Graph;
import graph.uml.Item;

/**
 * Implementation of an {@link Item}.
 * @author Benjamin Sigg
 */
public abstract class DefaultItem extends AbstractGraphItem implements Item{
	private Graph graph;
	private boolean disposed = false;
	private boolean visible = false;
	
	/**
	 * Creates a new item.
	 * @param graph the graph to which this item belongs
	 */
	public DefaultItem( Graph graph ){
		this.graph = graph;
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	/**
	 * Makes this item visible if it is not yet visible.
	 */
	public void makeVisible(){
		if( !visible ){
			visible = true;
			graph.addItem( this );
		}
	}
	
	@Override
	public void dispose() {
		if( !disposed ){	
			disposed = true;
			disposeDependentItems();
			graph.removeItem( this );
		}
	}
	
	protected abstract Iterable<Item> dependentItems();
	
	/**
	 * Calls {@link #dispose()} on all the items that depend on this item.
	 */
	protected void disposeDependentItems(){
		Iterable<Item> items = dependentItems();
		if( items != null ){
			for( Item item : items ){
				if( item != null ){
					item.dispose();
				}
			}
		}
	}
}
