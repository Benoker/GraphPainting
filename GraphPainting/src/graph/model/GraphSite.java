package graph.model;

import graph.ui.Graph;

import java.awt.Component;

import javax.swing.JComponent;

public interface GraphSite {
	/**
	 * Adds <code>paintable</code> to the list of items to paint. The paintables are
	 * painted in the order of their registration, meaning that the first added
	 * paintable is at the back.<br>
	 * Note that paintables will always be behind {@link #add(JComponent) components}
	 * @param paintable the new item to paint, not <code>null</code>
	 */
	public void add( GraphPaintable paintable );
	
	/**
	 * Adsd <code>component</code> to the list of items to paint. Components are
	 * painted in the order of their registration, using the standard Swing mechanism
	 * of painting {@link Component}s.<br>
	 * There is no layout, each component is responsible for its position and size.
	 * @param component the component to paint, not <code>null</code>
	 */
	public void add( JComponent component );
	
	public void add( Regraphable regraphable );
	
	public void remove( GraphPaintable paintable );
	
	public void remove( JComponent component );
	
	public void remove( Regraphable regraphable );
	
	/**
	 * Adds <code>item</code> to the graph. The behavior of this method is equal to calling
	 * {@link Graph#addItem(GraphItem)}.
	 * @param item the new item
	 */
	public void addItem( GraphItem item );
	
	/**
	 * Removes <code>item</code> from the graph.
	 * @param item the item to remove
	 */
	public void removeItem( GraphItem item );
	
	/**
	 * Recalculates and repaints the entire graph (asynchronous)
	 */
	public void regraph();
}
