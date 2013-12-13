package graph.model;

import java.awt.Component;

import javax.swing.JComponent;

public interface GraphSite extends GraphItemParent{
	/**
	 * Adds <code>paintable</code> to the list of items to paint. The paintables are
	 * painted in the order of their registration, meaning that the first added
	 * paintable is at the back.<br>
	 * Note that paintables will always be behind {@link #addComponent(JComponent) components}
	 * @param paintable the new item to paint, not <code>null</code>
	 */
	public void addPaintable( GraphPaintable paintable );
	
	/**
	 * Adsd <code>component</code> to the list of items to paint. Components are
	 * painted in the order of their registration, using the standard Swing mechanism
	 * of painting {@link Component}s.<br>
	 * There is no layout, each component is responsible for its position and size.
	 * @param component the component to paint, not <code>null</code>
	 */
	public void addComponent( JComponent component );
	
	public void addRegraphable( Regraphable regraphable );
	
	public void removePaintable( GraphPaintable paintable );
	
	public void removeComponent( JComponent component );
	
	public void removeRegraphable( Regraphable regraphable );
	
	/**
	 * Recalculates and repaints the entire graph (asynchronous)
	 */
	public void regraph();
}
