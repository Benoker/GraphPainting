package graph.uml.intern.keys;

import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * Default implementation of an {@link ItemKey}.
 * @author Benjamin Sigg
 */
public abstract class DefaultItemKey<T extends Item> implements ItemKey<T>{
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
	protected DefaultItemKey( DefaultUmlDiagram diagram, String typeName ){
		this.id = diagram.nextUniqueId();
		this.typeName = typeName;
		
		if( !typeName.matches( "[a-zA-Z0-9]+" )){
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
	public String getTypeName(){
		return typeName;
	}
	
	@Override
	public String toUniqueString() {
		return typeName + ":" + id;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == this ){
			return true;
		}
		if( obj == null || obj.getClass() != getClass() ){
			return false;
		}
		DefaultItemKey<?> that = (DefaultItemKey<?>)obj;
		return that.id == id && that.typeName.equals( typeName );
	}
	
	@Override
	public int hashCode() {
		return typeName.hashCode() + id;
	}

}
