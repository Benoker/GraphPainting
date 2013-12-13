package graph.uml.intern;

import graph.model.connection.EndPoint;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.Connection;
import graph.uml.Item;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class AbstractConnection extends DefaultItem implements Connection, GraphConnection{
	private DefaultBox sourceItem;
	private DefaultBox targetItem;
	
	public AbstractConnection( Graph graph, DefaultBox sourceItem, DefaultBox targetItem ){
		super( graph );
		
		setSourceItem( sourceItem );
		setTargetItem( targetItem );
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
	}
	
	@Override
	protected Iterable<Item> dependentItems() {
		return null;
	}
	
	/**
	 * Gets the internal representation of this connection.
	 * @return the internal representation, not <code>null</code>
	 */
	public abstract GraphConnection getGraphConnection();
	
	@Override
	public EndPoint getSourceEndPoint() {
		return getGraphConnection().getSourceEndPoint();
	}
	
	@Override
	public EndPoint getTargetEndPoint() {
		return getGraphConnection().getTargetEndPoint();
	}
}
