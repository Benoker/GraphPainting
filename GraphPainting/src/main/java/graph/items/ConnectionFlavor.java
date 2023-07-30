package graph.items;

/**
 * Describes the type of a connection. Clients may create new flavors. This class is final, and instances
 * are immutable. 
 * @author Benjamin Sigg
 */
public final class ConnectionFlavor {
	/** a generic flavor that has no meaning at all */
	public static final ConnectionFlavor GENERIC = new ConnectionFlavor( "generic" );
	
	private final String id;
	
	public ConnectionFlavor( String uniqueId ){
		this.id = uniqueId;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + "]";
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == this ){
			return true;
		}
		if( obj == null || obj.getClass() != getClass() ){
			return false;
		}
		ConnectionFlavor that = (ConnectionFlavor)obj;
		return that.id.equals( id );
	}
}
