package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.event.ItemSelectionListener;
import graph.uml.intern.keys.ConnectionKey;

import java.awt.geom.Path2D;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class AbstractConnection extends DefaultItem<Connection> implements Connection, GraphConnection, PathedGraphConnection {
	private DefaultBox<?> sourceItem;
	private DefaultBox<?> targetItem;

	private ConnectionSelectionCapability selection;

	public AbstractConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceItem, DefaultBox<?> targetItem, ItemKey<Connection> key ) {
		super( diagram, key );

		setSourceItem( sourceItem );
		setTargetItem( targetItem );

		selection = new ConnectionSelectionCapability( this );
		addCapability( CapabilityName.SELECTABLE, selection );
	}

	@Override
	protected ItemKey<Connection> createKey( DefaultUmlDiagram diagram ) {
		return new ConnectionKey( diagram );
	}

	/**
	 * Sets the source of this connection
	 * @param sourceItem the source, may be <code>null</code>
	 */
	public void setSourceItem( DefaultBox<?> sourceItem ) {
		if( this.sourceItem != null ) {
			this.sourceItem.removeDependent( this );
		}
		this.sourceItem = sourceItem;
		if( this.sourceItem != null ) {
			this.sourceItem.addDependent( this );
		}
	}

	public DefaultBox<?> getSourceItem() {
		return sourceItem;
	}

	/**
	 * Sets the target of this connection
	 * @param targetItem the target, may be <code>null</code>
	 */
	public void setTargetItem( DefaultBox<?> targetItem ) {
		if( this.targetItem != null ) {
			this.targetItem.removeDependent( this );
		}
		this.targetItem = targetItem;
		if( this.targetItem != null ) {
			this.targetItem.addDependent( this );
		}
	}

	public DefaultBox<?> getTargetItem() {
		return targetItem;
	}

	@Override
	public void dispose() {
		super.dispose();
		sourceItem.removeDependent( this );
		targetItem.removeDependent( this );

		dispose( getSourceEndPoint() );
		dispose( getTargetEndPoint() );
	}

	private void dispose( EndPoint endPoint ) {
		if( endPoint != null ) {
			ConnectionArray array = endPoint.getArray();
			if( array != null ) {
				array.remove( endPoint );
			}
		}
	}

	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		SelectableCapability selection = getCapability( CapabilityName.SELECTABLE );
		if( selection != null ) {
			return selection.contains( x, y );
		} else {
			return 0.f;
		}
	}

	@Override
	protected Iterable<Item> dependentItems() {
		return null;
	}

	/**
	 * Gets the flavor of this connection type
	 * @return the flavor
	 */
	public abstract ConnectionFlavor getFlavor();

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
	public Path2D getOpenConnectionPath() {
		return getGraphConnection().getOpenConnectionPath();
	}

	@Override
	public EndPoint getSourceEndPoint() {
		return getGraphConnection().getSourceEndPoint();
	}

	@Override
	public EndPoint getTargetEndPoint() {
		return getGraphConnection().getTargetEndPoint();
	}

	@Override
	public void addItemSelectionListener( ItemSelectionListener listener ) {
		selection.addListener( listener );
	}

	@Override
	public void removeItemSelectionListener( ItemSelectionListener listener ) {
		selection.removeListener( listener );
	}
}
