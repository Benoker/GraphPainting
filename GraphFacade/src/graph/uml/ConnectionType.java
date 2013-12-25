package graph.uml;

/**
 * A description differentiating the different kind of {@link Connection}s that exist.
 * @author Benjamin Sigg
 */
public enum ConnectionType {
	/** implementing an interface */
	IMPLEMENTATION( "impl" ),
	/** extending a class */
	INHERITANCE( "inherit" ),

	/** some type owning another type */
	COMPOSITION( "composition" ),
	/** some type using another type */
	AGGREGATION( "aggregation" ),

	/** a connection between any kind of item and a comment */
	COMMENT( "comment" );

	private String persistentName;

	private ConnectionType( String persistentName ) {
		this.persistentName = persistentName;
	}

	public String getPersistentName() {
		return persistentName;
	}
	
	public static ConnectionType byPersistentName( String name ){
		for( ConnectionType type : values() ){
			if( type.getPersistentName().equals( name )){
				return type;
			}
		}
		throw new IllegalArgumentException( "unknown name: " + name );
	}
}
