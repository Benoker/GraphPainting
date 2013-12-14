package graph.uml.intern;

import java.awt.geom.Path2D;

import graph.items.PathSelection;
import graph.items.PathedGraphConnection;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;
import graph.uml.Item;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class AbstractConnection extends DefaultItem implements Connection, GraphConnection, PathedGraphConnection{
	private DefaultBox sourceItem;
	private DefaultBox targetItem;
	
	public AbstractConnection( DefaultUmlDiagram diagram, DefaultBox sourceItem, DefaultBox targetItem ){
		super( diagram );
		
		setSourceItem( sourceItem );
		setTargetItem( targetItem );
		
		addCapability( CapabilityName.SELECTABLE, new PathSelection( this ) );
	}
	
	/**
	 * Sets the source of this connection
	 * @param sourceItem the source, may be <code>null</code>
	 */
	public void setSourceItem( DefaultBox sourceItem ) {
		if( this.sourceItem != null ){
			this.sourceItem.removeDependent( this );
		}
		this.sourceItem = sourceItem;
		if( this.sourceItem != null ){
			this.sourceItem.addDependent( this );
		}
	}
	
	/**
	 * Sets the target of this connection
	 * @param targetItem the target, may be <code>null</code>
	 */
	public void setTargetItem( DefaultBox targetItem ) {
		if( this.targetItem != null ){
			this.targetItem.removeDependent( this );
		}
		this.targetItem = targetItem;
		if( this.targetItem != null ){
			this.targetItem.addDependent( this );
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		sourceItem.removeDependent( this );
		targetItem.removeDependent( this );
		
		dispose( getSourceEndPoint() );
		dispose( getTargetEndPoint() );
	}
	
	private void dispose( EndPoint endPoint ){
		if( endPoint != null ){
			ConnectionArray array = endPoint.getArray();
			if( array != null ){
				array.remove( endPoint );
			}
		}
	}
	
	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		SelectableCapability selection = getCapability( CapabilityName.SELECTABLE );
		if( selection != null ){
			return selection.contains( x, y );
		}
		else{
			return 0.f;
		}
	}
	
	@Override
	protected Iterable<Item> dependentItems() {
		return null;
	}
	
	/**
	 * Gets the internal representation of this connection.
	 * @return the internal representation, not <code>null</code>
	 */
	public abstract PathedGraphConnection getGraphConnection();
	
	@Override
	public Path2D getClosedConnectionPath() {
		return getGraphConnection().getClosedConnectionPath();
	}
	
	@Override
	public EndPoint getSourceEndPoint() {
		return getGraphConnection().getSourceEndPoint();
	}
	
	@Override
	public EndPoint getTargetEndPoint() {
		return getGraphConnection().getTargetEndPoint();
	}
}
