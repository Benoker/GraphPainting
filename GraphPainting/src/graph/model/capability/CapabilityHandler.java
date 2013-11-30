package graph.model.capability;

import graph.model.GraphItem;

import java.awt.event.MouseListener;

/**
 * A {@link CapabilityHandler} describes some kind of action the user
 * is able to perform with {@link GraphItem}s.<br>
 * Any {@link CapabilityHandler} gets its own {@link CapabilityHandlerSite} with which it can interact
 * with the application.
 * @see CapabilityHandlerSite
 * @author Benjamin Sigg
 */
public interface CapabilityHandler<T> {
	/**
	 * Called one time to initialize this handler. The handler may add
	 * listeners (e.g. a {@link MouseListener}) to the <code>site</code>.
	 * @param site the site of this handler, not <code>null</code>
	 * @throws IllegalStateException if called twice
	 */
	public void init( CapabilityHandlerSite<T> site );
	
	/**
	 * Enables or disables a capability. A disabled capability does not receive any events
	 * from the listeners that were added to the {@link CapabilityHandlerSite}.
	 * @param enabled whether this capability is enabled
	 */
	public void setEnabled( boolean enabled );
}
