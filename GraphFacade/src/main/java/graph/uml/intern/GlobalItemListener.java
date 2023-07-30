package graph.uml.intern;

import graph.uml.Item;
import graph.uml.UmlDiagram;
import graph.uml.event.UmlDiagramListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This listener is added to an {@link UmlDiagram} and will add some kind of listener
 * to any {@link Item} that is added to the diagram.
 * @param <L> the kind of listener managed by this {@link GlobalItemListener}
 * @author Benjamin Sigg
 */
public abstract class GlobalItemListener<L> {
	private List<L> listeners = new ArrayList<>();
	private Listener listener = new Listener();

	public GlobalItemListener( UmlDiagram diagram ) {
		diagram.addUmlDiagramListener( listener );
	}

	/**
	 * Gets all the listeners that were added to this {@link GlobalItemListener}.
	 * @return all the listeners, may be empty
	 */
	protected Iterable<L> getListeners() {
		return new ArrayList<L>( listeners );
	}

	/**
	 * Called if the global listener should be added to an item.
	 * @param item a new item without global listener
	 */
	protected abstract void addGlobalListener( Item item );

	/**
	 * Called if the global listener should be removed from an item.
	 * @param item the item from which to remove the global listener
	 */
	protected abstract void removeGlobalListener( Item item );

	public void addListener( L listener ) {
		if( listener == null ) {
			throw new IllegalArgumentException( "listener must not be null" );
		}
		listeners.add( listener );
	}

	public void removeListener( L listener ) {
		listeners.remove( listener );
	}

	private class Listener implements UmlDiagramListener {
		@Override
		public void itemAdded( UmlDiagram diagram, Item item ) {
			addGlobalListener( item );
		}

		@Override
		public void itemRemoved( UmlDiagram diagram, Item item ) {
			removeGlobalListener( item );
		}
	}
}
