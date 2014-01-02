package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.GraphSite;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;
import graph.uml.ConnectionLabel;
import graph.uml.ConnectionLabelConfiguration;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.event.ItemSelectionListener;
import graph.uml.intern.config.DefaultUmlConfiguration;
import graph.uml.intern.keys.ConnectionKey;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class AbstractConnection extends DefaultItem<Connection> implements Connection, GraphConnection, PathedGraphConnection {
	private DefaultBox<?> sourceItem;
	private DefaultBox<?> targetItem;

	private ConnectionSelectionCapability selection;
	private PathedGraphConnection line;

	public AbstractConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceItem, ConnectionArray source, DefaultBox<?> targetItem, ConnectionArray target, ItemKey<Connection> key ) {
		super( diagram, key );

		setSourceItem( sourceItem );
		setTargetItem( targetItem );

		selection = new ConnectionSelectionCapability( this );
		addCapability( CapabilityName.SELECTABLE, selection );

		line = createLine( diagram.getRepository().getConfiguration() );
		addChild( line );
		
		if( source != null ) {
			source.add( line.getSourceEndPoint() );
		}
		if( target != null ) {
			target.add( line.getTargetEndPoint() );
		}
	}

	protected abstract PathedGraphConnection createLine( DefaultUmlConfiguration configuration );

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
	protected Iterable<? extends Item> dependentItems() {
		return getLabels();
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
	public PathedGraphConnection getGraphConnection() {
		return line;
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}

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
	public ConnectionLabel addLabel( ConnectionLabelConfiguration configuration ) {
		DefaultConnectionLabel label = new DefaultConnectionLabel( getDiagram(), this );
		label.setConfiguration( configuration );
		label.makeVisible();
		return label;
	}

	@Override
	public List<ConnectionLabel> getLabels() {
		List<ConnectionLabel> result = new ArrayList<>();

		for( Item item : getDiagram().getItems() ) {
			if( item instanceof ConnectionLabel ) {
				ConnectionLabel label = (ConnectionLabel) item;
				if( label.getConnection() == this ) {
					result.add( label );
				}
			}
		}

		return result;
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
