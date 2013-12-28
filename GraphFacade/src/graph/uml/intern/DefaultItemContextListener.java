package graph.uml.intern;

import graph.uml.Item;
import graph.uml.UmlDiagram;
import graph.uml.event.ItemContextEvent;
import graph.uml.event.ItemContextListener;

/**
 * Handles a set of {@link ItemContextListener}s that are added to all {@link Item}s of an {@link UmlDiagram}
 * @author Benjamin Sigg
 */
public class DefaultItemContextListener extends GlobalItemListener<ItemContextListener> {
	private ItemContextListener global;

	public DefaultItemContextListener( UmlDiagram diagram ) {
		super( diagram );
		global = global();
	}

	private ItemContextListener global() {
		return new ItemContextListener() {
			@Override
			public void contextAction( ItemContextEvent event ) {
				for( ItemContextListener listener : getListeners() ) {
					listener.contextAction( event );
				}
			}
		};
	}

	@Override
	protected void addGlobalListener( Item item ) {
		item.addItemContextListener( global );
	}

	@Override
	protected void removeGlobalListener( Item item ) {
		item.removeItemContextListener( global );
	}
}
