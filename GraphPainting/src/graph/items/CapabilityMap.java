package graph.items;

import graph.model.capability.CapabilityName;

import java.util.HashMap;
import java.util.Map;

/**
 * A map between {@link CapabilityName}s and capabilities.
 * @author Benjamin Sigg
 */
public class CapabilityMap {
	private Map<CapabilityName<?>, Object> capabilities = new HashMap<>();
	
	/**
	 * Stores a capability.
	 * @param name the name of the capability
	 * @param capability the capability, or <code>null</code>
	 */
	public <T> void put( CapabilityName<T> name, T capability ){
		if( capability == null ){
			capabilities.remove( name );
		}
		else{
			capabilities.put( name, capability );
		}
	}
	
	/**
	 * Gets a capability.
	 * @param name the name of the capability
	 * @return the capability or <code>null</code>
	 */
	@SuppressWarnings( "unchecked" )
	public <T> T get( CapabilityName<T> name ){
		return (T)capabilities.get( name );
	}
}
