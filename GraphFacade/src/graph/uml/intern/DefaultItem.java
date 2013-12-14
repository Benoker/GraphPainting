package graph.uml.intern;

import graph.items.AbstractGraphItem;
import graph.model.capability.CapabilityName;
import graph.ui.Graph;
import graph.uml.Item;
import graph.uml.ItemContextListener;
import graph.uml.intern.tools.DefaultItemContextCapability;

/**
 * Implementation of an {@link Item}.
 * @author Benjamin Sigg
 */
public abstract class DefaultItem extends AbstractGraphItem implements Item{
	private DefaultUmlDiagram diagram;
	private boolean disposed = false;
	private boolean visible = false;
	private DefaultItemContextCapability contextCapability;
	
	/**
	 * Creates a new item.
	 * @param graph the graph to which this item belongs
	 */
	public DefaultItem( DefaultUmlDiagram diagram ){
		this.diagram = diagram;
		
		contextCapability = new DefaultItemContextCapability( diagram, this );
		setCapability( CapabilityName.CONTEXT_MENU, contextCapability );
	}
	
	public Graph getGraph() {
		return diagram.getGraph();
	}
	
	public DefaultUmlDiagram getDiagram() {
		return diagram;
	}
	
	/**
	 * Makes this item visible if it is not yet visible.
	 */
	public void makeVisible(){
		if( !visible ){
			visible = true;
			getGraph().addItem( this );
		}
	}
	
	@Override
	public void dispose() {
		if( !disposed ){	
			disposed = true;
			disposeDependentItems();
			getGraph().removeItem( this );
		}
	}
	
	/**
	 * Tells whether this item can have a context menu at <code>x/y</code>
	 * @param x the x-coordinate of the mouse
	 * @param y the y-coordinate of the mouse
	 * @return whether a context menu is available, a value below <code>0.f</code> means no,
	 * a value greater than <code>1.f</code> means yes
	 */
	public abstract float isContextMenuEnabledAt( int x, int y );
	
	@Override
	public void addItemContextListener( ItemContextListener listener ) {
		contextCapability.addListener( listener );
	}
	
	@Override
	public void removeItemContextListener( ItemContextListener listener ) {
		contextCapability.removeListener( listener );
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
