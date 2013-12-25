package graph.uml;

/**
 * A description differentiating the different kind of {@link Connection}s that exist.
 * @author Benjamin Sigg
 */
public enum ConnectionType {
	/** implementing an interface */
	IMPLEMENTATION, 
	/** extending a class */
	INHERITANCE, 
	
	/** some type owning another type */
	COMPOSITION,
	/** some type using another type */
	AGGREGATION,
	
	/** a connection between any kind of item and a comment */
	COMMENT
}
