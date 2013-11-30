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
	
	@Override
	public void setVisible( boolean visible ) {
		if( this.visible != visible ){
			if( visible ){
				graph.addItem( this );
			}
			else{
				graph.removeItem( this );
			}
			this.visible = visible;
		}
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
}
