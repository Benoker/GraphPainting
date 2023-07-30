package graph.uml;

/**
 * A {@link ConnectionLabelConfiguration} is an unmodifiable key telling a {@link ConnectionLabel} how
 * it should behave.<br>
 * Note that this is not an enumeration, comparing two configurations should always be done with
 * the {@link #equals(Object) equals method}.
 * @author Benjamin Sigg
 */
public class ConnectionLabelConfiguration {
	/** Special configuration describing a non-existing configuration */
	public static final ConnectionLabelConfiguration NONE = new ConnectionLabelConfiguration( "none" );

	/** Describes a label that appears near the source of a connection */
	public static final ConnectionLabelConfiguration SOURCE = new ConnectionLabelConfiguration( "source" );

	/** Describes a label that appears near the target of a connection */
	public static final ConnectionLabelConfiguration TARGET = new ConnectionLabelConfiguration( "target" );

	/** Describes a label that appears in the middle of a connection */
	public static final ConnectionLabelConfiguration MIDDLE = new ConnectionLabelConfiguration( "middle" );

	private String id;

	/**
	 * Creates a new configuration. It is the clients responsibility to actually associated some properties with
	 * the new configuration.
	 * @param id the unique identifier of the configuration
	 */
	public ConnectionLabelConfiguration( String id ) {
		if( id == null ) {
			throw new IllegalArgumentException( "id must not be null" );
		}
		this.id = id;
	}

	/**
	 * Gets the unique identifier that was used to create this configuration.
	 * @return the unique identifier, not <code>null</code>
	 */
	public String getId() {
		return id;
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) {
			return true;
		}
		if( obj == null || obj.getClass() != getClass() ) {
			return false;
		}
		ConnectionLabelConfiguration that = (ConnectionLabelConfiguration) obj;
		return that.id.equals( id );
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return id;
	}
}
