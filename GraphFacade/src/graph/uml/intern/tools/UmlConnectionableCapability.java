package graph.uml.intern.tools;

import graph.items.ConnectionableCapability;
import graph.uml.intern.DefaultBox;

/**
 * Connectionability of a {@link DefaultBox}.
 * @author Benjamin Sigg
 */
public abstract class UmlConnectionableCapability implements ConnectionableCapability{
	private DefaultBox box;
	
	/**
	 * Creates a new capability
	 * @param box the box represented by this capability
	 */
	public UmlConnectionableCapability( DefaultBox box ){
		this.box = box;
	}
	
	/**
	 * Gets the box that is described by this capability.
	 * @return the box
	 */
	public DefaultBox getBox(){
		return box;
	}
	
	@Override
	public float containsConnectionable( int x, int y ) {
		if( box.getLabel().contains( x, y )){
			return 1.0f;
		}
		else{
			return 0.0f;
		}
	}
}
