package graph.model;

import graph.model.capability.CapabilityName;
import graph.ui.GraphPanel;

/**
 * Represents an element that may be painted or interacts with the user on
 * a {@link GraphPanel}. 
 * @author Benjamin Sigg
 */
public interface GraphItem {
	/**
	 * Connects this box to a {@link GraphPanel} that is represented by <code>site</code>.
	 * A value of <code>null</code> means that this box has to unregister itself from
	 * the last known <code>site</code>.
	 * @param site the panel painting this box, or <code>null</code>
	 */
	public void set( GraphSite site );
	
	/**
	 * Gets an optional capability of this item. The capabilities of an item may change during
	 * its life time.
	 * @param name the name of a capability
	 * @return the capability <code>name</code> or <code>null</code> if this item does not support
	 * <code>name</code>
	 */
	public <T> T getCapability( CapabilityName<T> name );
}
