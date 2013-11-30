package graph.model.capability;

import graph.model.GraphItem;
import graph.ui.GraphPanel;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * Given to a {@link CapabilityHandler} this site allows the handler to interact with the application. 
 * Interaction includes:<br>
 * <ul>
 * 	<li>Adding or removing listeners like the {@link MouseListener}</li>
 *  <li>Disabling all other capabilities by calling {@link #triggered()}</li>
 *  <li>Enable all other capabilities by calling {@link #listening()}</li>
 * </ul>
 * @author Benjamin Sigg
 */
public interface CapabilityHandlerSite<T> {
	/**
	 * Informs the application that the owning {@link CapabilityHandler} has been triggered. All the other
	 * capabilities are disabled, and any {@link InputEvent}s are forwarded exclusively to the owning handler.<br>
	 * Please note that the user cannot change the selection while a handler is triggered.
	 */
	public void triggered();
	
	/**
	 * Informs the application that the owning {@link CapabilityHandler} is no longer triggered. All the other
	 * capabilities are enabled, and {@link InputEvent}s are shared between the them.
	 */
	public void listening();
	
	/**
	 * Asks the {@link GraphItem}s for their {@link GraphItem#getCapability(CapabilityName) capabilities}
	 * using the name of the owning {@link CapabilityHandler}.
	 * @return the capabilities of the selected items, may be empty but not <code>null</code>
	 */
	public List<T> getCapabilities();
	
	/**
	 * Adds <code>listener</code> to the application. This listener will only receive events 
	 * if the {@link CapabilityHandler#setEnabled(boolean) is enabled}.<br>
	 * Clients should not make any assumptions regarding the {@link InputEvent#getSource() source} 
	 * of the event. The source may be the {@link GraphPanel}, it may be another {@link Component}, or
	 * it may not be set at all.
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addMouseListener( MouseListener listener );
	
	/**
	 * Removes <code>listener</code> from the application.
	 * @param listener the listener to remove, not <code>null</code>
	 */
	public void removeMouseListener( MouseListener listener );

	/**
	 * Adds <code>listener</code> to the application. This listener will only receive events 
	 * if the {@link CapabilityHandler#setEnabled(boolean) is enabled}.<br>
	 * Clients should not make any assumptions regarding the {@link InputEvent#getSource() source} 
	 * of the event. The source may be the {@link GraphPanel}, it may be another {@link Component}, or
	 * it may not be set at all.
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addMouseMotionListener( MouseMotionListener listener );
	
	/**
	 * Removes <code>listener</code> from the application.
	 * @param listener the listener to remove, not <code>null</code>
	 */
	public void removeMouseMotionListener( MouseMotionListener listener );
	
	/**
	 * Adds <code>listener</code> to the application. This listener will only receive events 
	 * if the {@link CapabilityHandler#setEnabled(boolean) is enabled}.<br>
	 * Clients should not make any assumptions regarding the {@link InputEvent#getSource() source} 
	 * of the event. The source may be the {@link GraphPanel}, it may be another {@link Component}, or
	 * it may not be set at all.
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addKeyListener( KeyListener listener );
	
	/**
	 * Removes <code>listener</code> from the application.
	 * @param listener the listener to remove, not <code>null</code>
	 */
	public void removeKeyListener( KeyListener listener );
}
