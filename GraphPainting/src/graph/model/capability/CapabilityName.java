package graph.model.capability;


/**
 * Extendable enumeration describing capabilities of a {@link GraphSelectable}.
 * @author Benjamin Sigg
 */
public class CapabilityName<T> {
	public static final CapabilityName<SelectableCapability> SELECTABLE = new CapabilityName<>( "selectable" );
	
	/** The name of the capability that allows items to be moved around */
	public static final CapabilityName<MoveableCapability> MOVEABLE = new CapabilityName<>( "moveable" );
	
	/** The name of the capability that allows items to show a context menu */
	public static final CapabilityName<ContextCapability> CONTEXT_MENU = new CapabilityName<ContextCapability>( "context" );
	
	private String id;
	
	public CapabilityName( String id ){
		this.id = id;
	}
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ){
		if( obj == this ){
			return true;
		}
		if( obj == null || obj.getClass() != getClass() ){
			return false;
		}
		CapabilityName<?> that = (CapabilityName<?>)obj;
		return id.equals( that.id );
	}
	
	@Override
	public String toString() {
		return id;
	}
}
