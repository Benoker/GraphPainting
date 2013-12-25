package graph.uml;

/**
 * A representation of a type like a class or an interface.
 * @author Benjamin Sigg
 */
public interface TypeBox extends Box{
	/**
	 * Adds a connection between this box and <code>superType</code> representing an
	 * inheritance.
	 * @param superType the box representing the super type
	 * @return the connection
	 */
	public Connection addInheritsFrom( TypeBox superType );
	
	/**
	 * Adds a connection between this box and <code>interfaceType</code> representing this
	 * class implementing an interface.
	 * @param interfaceType the interface that is implemented
	 * @return the connection 
	 */
	public Connection addImplementsFrom( TypeBox interfaceType );
	
	/**
	 * Adds a connection between this box and <code>parentType</code> describing a composition.
	 * @param parentType the parent 
	 * @return the connection
	 */
	public Connection addComposition( TypeBox parentType );
	
	/**
	 * Adds a connection between this box and <code>parentType</code> describing an aggregation. 
	 * @param parentType the parent 
	 * @return the connection
	 */
	public Connection addAggregation( TypeBox parentType );
	
	/**
	 * Adds a comment to this box.
	 * @param text the text to show in the comment
	 * @return the newly created comment
	 */
	public CommentBox addComment( String text );
	
	@Override
	public ItemKey<TypeBox> getKey();
}
