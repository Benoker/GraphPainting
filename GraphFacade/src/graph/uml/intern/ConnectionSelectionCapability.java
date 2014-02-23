package graph.uml.intern;

import java.util.ArrayList;
import java.util.List;

import graph.items.PathSelection;
import graph.model.Selection;
import graph.uml.event.ItemSelectionEvent;
import graph.uml.event.ItemSelectionListener;

/**
 * The capability to select {@link AbstractConnection}s.
 * @author Benjamin Sigg
 */
public class ConnectionSelectionCapability extends PathSelection{
	private List<ItemSelectionListener> listeners = new ArrayList<>(1);
	private AbstractConnection connection;
	
	/**
	 * Creates the new capability.
	 * @param connection the connection to manage
	 */
	public ConnectionSelectionCapability( AbstractConnection connection ){
		super( connection );
		this.connection = connection;
	}
	
	public void addListener( ItemSelectionListener listener ){
		listeners.add( listener );
	}
	
	public void removeListener( ItemSelectionListener listener ){
		listeners.remove( listener );
	}
	
	private ItemSelectionListener[] listeners(){
		return listeners.toArray( new ItemSelectionListener[ listeners.size() ] );
	}
	
	@Override
	public void setSelected( Selection selection ) {
		super.setSelected( selection );
		connection.updateSelection();
		
		ItemSelectionEvent event = new ItemSelectionEvent( connection, selection );
		for( ItemSelectionListener listener : listeners()){
			listener.itemSelectionChanged( event );
		}
	}
}
