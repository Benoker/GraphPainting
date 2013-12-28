package graph.uml.intern;

import java.util.ArrayList;
import java.util.List;

import graph.model.Selection;
import graph.model.capability.SelectableCapability;
import graph.uml.Box;
import graph.uml.event.ItemSelectionEvent;
import graph.uml.event.ItemSelectionListener;

/**
 * Capability to select a {@link Box}
 * @author Benjamin Sigg
 */
public class BoxSelectionCapability implements SelectableCapability {
	private DefaultBox<?> box;
	private Selection selection = Selection.NOT_SELECTED;
	private List<ItemSelectionListener> listeners = new ArrayList<>( 1 );

	/**
	 * Creates a new box selection capability.
	 * @param box the box that may be selected
	 */
	public BoxSelectionCapability( DefaultBox<?> box ) {
		this.box = box;
	}

	public void addListener( ItemSelectionListener listener ) {
		listeners.add( listener );
	}

	public void removeListener( ItemSelectionListener listener ) {
		listeners.remove( listener );
	}

	private ItemSelectionListener[] listeners() {
		return listeners.toArray( new ItemSelectionListener[listeners.size()] );
	}

	@Override
	public void setSelected( Selection selection ) {
		this.selection = selection;
		box.updateSelected();
		
		ItemSelectionEvent event = new ItemSelectionEvent( box, selection );
		for( ItemSelectionListener listener : listeners() ){
			listener.itemSelectionChanged( event );
		}
	}

	@Override
	public Selection getSelected() {
		return selection;
	}

	@Override
	public float contains( int x, int y ) {
		return box.getLabel().contains( x, y );
	}
}
