package graph.items;

import graph.model.GraphItem;
import graph.model.capability.CapabilityName;

/**
 * Abstract implementation of a {@link GraphItem} offering some default implementations of methods
 * that are often used.
 * @author Benjamin Sigg
 */
public abstract class AbstractGraphItem implements GraphItem{
	private CapabilityMap capabilities = new CapabilityMap();
	
	/**
	 * Adds or removes a capability of this item.
	 * @param name the name of the capability
	 * @param capability the capability, can be <code>null</code>
	 */
	protected <T> void setCapability( CapabilityName<T> name, T capability ){
		capabilities.put( name, capability );
	}
	
	@Override
	public <T> T getCapability( CapabilityName<T> name ) {
		return capabilities.get( name );
	}
}
