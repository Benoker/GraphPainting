package graph.uml.intern;

import graph.uml.Item;
import graph.uml.UmlDiagram;
import graph.uml.event.ItemSelectionEvent;
import graph.uml.event.ItemSelectionListener;

/**
 * Handles a set of {@link ItemSelectionListener}s that are added to all {@link Item}s of an {@link UmlDiagram}
 * @author Benjamin Sigg
 */
public class DefaultItemSelectionListener extends GlobalItemListener<ItemSelectionListener> {
	private ItemSelectionListener global;

	public DefaultItemSelectionListener( UmlDiagram diagram ) {
		super( diagram );
		global = global();
	}

	private ItemSelectionListener global() {
		return new ItemSelectionListener() {
			@Override
			public void itemSelectionChanged( ItemSelectionEvent event ) {
				for( ItemSelectionListener listener : getListeners() ) {
					listener.itemSelectionChanged( event );
				}
			}
		};
	}

	@Override
	protected void addGlobalListener( Item item ) {
		item.addItemSelectionListener( global );
	}

	@Override
	protected void removeGlobalListener( Item item ) {
		item.removeItemSelectionListener( global );
	}
}
