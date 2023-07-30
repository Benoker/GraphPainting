package graph.uml.intern;

import graph.items.connection.text.ConfigurableConnectionText;
import graph.items.connection.text.TextAngleStrategy;
import graph.items.connection.text.TextDistanceStrategy;
import graph.items.connection.text.TextPositionStrategy;
import graph.items.connection.text.TextShiftStrategy;
import graph.model.GraphSite;
import graph.uml.ConnectionLabel;
import graph.uml.ConnectionLabelConfiguration;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.event.ItemSelectionListener;
import graph.uml.intern.keys.ConnectionLabelKey;

import java.awt.Color;

/**
 * A configurable label that is attached to an {@link AbstractConnection}.
 * @author Benjamin Sigg
 */
public class DefaultConnectionLabel extends DefaultItem<ConnectionLabel> implements ConnectionLabel {
	private AbstractConnection connection;
	private ConfigurableConnectionText connectionText;
	private ConnectionLabelConfiguration configuration = ConnectionLabelConfiguration.NONE;

	/**
	 * Creates the label.
	 * @param diagram the diagram on which this label is shown
	 * @param connection the connection to which this label is attached
	 */
	public DefaultConnectionLabel( DefaultUmlDiagram diagram, AbstractConnection connection ) {
		this( diagram, null, connection );
	}

	/**
	 * Creates the label.
	 * @param diagram the diagram on which this label is shown
	 * @param key the unique identifier of this label
	 * @param connection the connection to which this label is attached
	 */
	public DefaultConnectionLabel( DefaultUmlDiagram diagram, ItemKey<ConnectionLabel> key, AbstractConnection connection ) {
		super( diagram, key );
		this.connection = connection;

		connectionText = new ConfigurableConnectionText( connection );

		addChild( connectionText );
	}

	@Override
	public AbstractConnection getConnection() {
		return connection;
	}

	@Override
	public void addItemSelectionListener( ItemSelectionListener listener ) {
		// not supported (yet)
	}

	@Override
	public void removeItemSelectionListener( ItemSelectionListener listener ) {
		// not supported (yet)		
	}

	@Override
	protected ItemKey<ConnectionLabel> createKey( DefaultUmlDiagram diagram ) {
		return new ConnectionLabelKey( diagram );
	}

	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		// context menu is not supported
		return -1f;
	}

	@Override
	protected Iterable<Item> dependentItems() {
		return null;
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// nothing to remove
	}

	@Override
	protected void addTo( GraphSite site ) {
		// nothing to add
	}

	@Override
	public void setText( String text ) {
		connectionText.setText( text );
	}

	@Override
	public String getText() {
		return connectionText.getText();
	}

	@Override
	public void setConfiguration( ConnectionLabelConfiguration configuration ) {
		if( configuration == null ) {
			throw new IllegalArgumentException( "configuration must not be null" );
		}
		if( !this.configuration.equals( configuration ) ) {
			this.configuration = configuration;
			
			DefaultConnectionLabelConfiguration config = getDiagram().getRepository().getLabelConfiguration( configuration );
			apply( config );
		}
	}

	private void apply( DefaultConnectionLabelConfiguration config ) {
		DefaultConnectionLabelConfiguration.RESET.applyTo( this );
		if( config != null ) {
			config.applyTo( this );
		}
	}

	@Override
	public ConnectionLabelConfiguration getConfiguration() {
		return configuration;
	}

	public void setAngle( TextAngleStrategy angle ) {
		connectionText.setAngle( angle );
	}

	public void setDistance( TextDistanceStrategy distance ) {
		connectionText.setDistance( distance );
	}

	public void setPosition( TextPositionStrategy position ) {
		connectionText.setPosition( position );
	}

	public void setShift( TextShiftStrategy shift ) {
		connectionText.setShift( shift );
	}

	public void setBackgroundColor( Color backgroundColor ) {
		connectionText.setBackgroundColor( backgroundColor );
	}
}
