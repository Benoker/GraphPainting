package graph.uml.intern;

import java.util.HashMap;
import java.util.Map;

import graph.uml.ConnectionLabelConfiguration;

/**
 * A map of {@link ConnectionLabelConfiguration}s and {@link DefaultConnectionLabelConfiguration}s.
 * @author Benjamin Sigg
 */
public class DefaultConnectionLabelConfigurations {
	private Map<ConnectionLabelConfiguration, DefaultConnectionLabelConfiguration> labelConfigurations = new HashMap<>();

	public DefaultConnectionLabelConfigurations() {
		putLabelConfiguration( ConnectionLabelConfiguration.NONE, DefaultConnectionLabelConfiguration.RESET );
		putLabelConfiguration( ConnectionLabelConfiguration.SOURCE, DefaultConnectionLabelConfiguration.SOURCE );
		putLabelConfiguration( ConnectionLabelConfiguration.TARGET, DefaultConnectionLabelConfiguration.TARGET );
		putLabelConfiguration( ConnectionLabelConfiguration.MIDDLE, DefaultConnectionLabelConfiguration.MIDDLE );
	}

	/**
	 * Stores or removes a configuration with name <code>name</code>. Changing a configuration does not have any effect
	 * on {@link DefaultConnectionLabel}s that already exist.
	 * @param name the name of the configuration
	 * @param config the configuration, can be <code>null</code>
	 */
	public void putLabelConfiguration( ConnectionLabelConfiguration name, DefaultConnectionLabelConfiguration config ) {
		if( config == null ) {
			labelConfigurations.remove( name );
		} else {
			labelConfigurations.put( name, config );
		}
	}

	/**
	 * Gets the configuration for a {@link DefaultConnectionLabel}.
	 * @param name the key of the configuration
	 * @return the configuration, may be <code>null</code>
	 */
	public DefaultConnectionLabelConfiguration getLabelConfiguration( ConnectionLabelConfiguration name ) {
		return labelConfigurations.get( name );
	}

}
