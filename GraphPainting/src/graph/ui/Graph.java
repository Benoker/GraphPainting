package graph.ui;

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
	
	public Graph(){
		panel = new GraphPanel();
		
		capabilityController = new CapabilityController( panel );
		capabilityController.register( CapabilityName.MOVEABLE, new MoveableCapabilityHandler() );
		capabilityController.register( CapabilityName.SELECTABLE, new SelectableCapabilityHandler() );
		capabilityController.register( CapabilityName.CONTEXT_MENU, new ContextCapabilityHandler() );
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
		panel.add( item );
	}
	
	/**
	 * Removes <code>item</code> from the graph.
	 * @param item the item to remove
	 */
	public void removeItem( GraphItem item ){
		panel.remove( item );
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
