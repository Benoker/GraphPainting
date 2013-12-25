package graph.uml.intern.keys;

import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * Default implementation of an {@link ItemKey}.
 * @author Benjamin Sigg
 */
public abstract class DefaultItemKey<T extends Item> implements ItemKey<T> {
	protected static boolean isKey( String uniqueString, String typeName ) {
		String prefix = typeName + ":";
		return uniqueString.startsWith( prefix );
	}

	public final long serialVersionUID = 1;

	/** unique identifier of this key */
	private int id;

	/** name of the type of this key */
	private String typeName;

	/**
	 * Creates a new key.
	 * @param diagram the diagram from which to draw a new unique identifier
	 * @param typeName A simple, human readable name containing only letters and numbers, describing
	 * the type of this key
	 */
	protected DefaultItemKey( DefaultUmlDiagram diagram, String typeName ) {
		this.id = diagram.nextUniqueId();
		this.typeName = typeName;
		checkTypeName( typeName );
	}

	/**
	 * Creates a new key.
	 * @param uniqueString a string that was created by {@link #toUniqueString()}
	 * @param typeName A simple, human readable name containing only letters and numbers, describing
	 * the type of this key 
	 */
	protected DefaultItemKey( String uniqueString, String typeName ) {
		checkTypeName( typeName );
		String prefix = typeName + ":";
		if( uniqueString.startsWith( prefix ) ) {
			id = Integer.parseInt( uniqueString.substring( prefix.length() ) );
			this.typeName = typeName;
		} else {
			throw new IllegalArgumentException( "wrong format for uniqueString" );
		}
	}

	private void checkTypeName( String typeName ) {
		if( !typeName.matches( "[a-zA-Z0-9]+" ) ) {
			throw new IllegalArgumentException( "typeName contains not supported characters: " + typeName );
		}
	}

	/**
	 * Gets the unique value of this key.
	 * @return the unique value of this key
	 */
	public int getId() {
		return id;
	}

	/**
	 *  Gets the type of this key.
	 * @return the type of this key, not <code>null</code>, must always return the sa
	 */
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String toUniqueString() {
		return typeName + ":" + id;
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) {
			return true;
		}
		if( obj == null || obj.getClass() != getClass() ) {
			return false;
		}
		DefaultItemKey<?> that = (DefaultItemKey<?>) obj;
		return that.id == id && that.typeName.equals( typeName );
	}

	@Override
	public int hashCode() {
		return typeName.hashCode() + id;
	}

}
