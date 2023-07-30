package graph.uml.io;

import graph.uml.Item;
import graph.uml.ItemKey;

/**
 * Data describing an {@link Item}, without being an {@link Item}.
 * @author Benjamin Sigg
 */
public abstract class Data<T extends Item> {
	private ItemKey<T> key;
	
	/**
	 * Creates new data.
	 * @param key the unique identifier of the {@link Item}
	 */
	public Data(ItemKey<T> key){
		if( key == null ){
			throw new IllegalArgumentException( "key must not be null" );
		}
		this.key = key;
	}
	
	/**
	 * Gets the unique identifier of the {@link Item} that is described.
	 * @return the unique identifier, not <code>null</code>
	 */
	public ItemKey<T> getKey() {
		return key;
	}
	
	/**
	 * Validates the contents of this {@link Data}.
	 * @throws IllegalStateException if there is some data missing
	 */
	public void validate(){
		if( key == null ){
			throw new IllegalStateException( "key is null" );
		}
	}
	
	/**
	 * Visits <code>this</code> by <code>visitor</code>.
	 * @param visitor the callback
	 */
	public abstract void visit( DataVisitor visitor );
}
