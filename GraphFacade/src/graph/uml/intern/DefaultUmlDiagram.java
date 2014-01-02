package graph.uml.intern;

import graph.model.GraphItem;
import graph.model.Selection;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.ui.Graph;
import graph.ui.GraphListener;
import graph.uml.Box;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.TypeBox;
import graph.uml.UmlDiagram;
import graph.uml.event.ItemContextListener;
import graph.uml.event.ItemSelectionListener;
import graph.uml.event.UmlDiagramListener;
import graph.uml.intern.keys.DefaultItemKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Default implementation of {@link UmlDiagram}.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagram implements UmlDiagram {
	private Graph graph;
	private DefaultItemContextListener itemContextListener;
	private DefaultItemSelectionListener itemSelectionListener;
	private List<UmlDiagramListener> umlDiagramListeners = new ArrayList<>();
	private AtomicInteger nextUniqueId = new AtomicInteger( 1 );
	private DefaultUmlDiagramRepository repository;

	/**
	 * Creates a new diagram.
	 * @param graph the graph to which this diagram will add items
	 * @param repository the repository that created and manages this diagram
	 */
	public DefaultUmlDiagram( Graph graph, DefaultUmlDiagramRepository repository ) {
		this.repository = repository;
		this.graph = graph;
		graph.addGraphListener( graphListener() );
		itemContextListener = new DefaultItemContextListener( this );
		itemSelectionListener = new DefaultItemSelectionListener( this );
	}

	private GraphListener graphListener() {
		return new GraphListener() {
			private UmlDiagramListener[] listeners() {
				return umlDiagramListeners.toArray( new UmlDiagramListener[umlDiagramListeners.size()] );
			}

			@Override
			public void itemRemoved( Graph source, GraphItem graphItem ) {
				if( graphItem instanceof Item ) {
					Item item = (Item) graphItem;
					for( UmlDiagramListener listener : listeners() ) {
						listener.itemRemoved( DefaultUmlDiagram.this, item );
					}
				}
			}

			@Override
			public void itemAdded( Graph source, GraphItem graphItem ) {
				if( graphItem instanceof Item ) {
					Item item = (Item) graphItem;
					for( UmlDiagramListener listener : listeners() ) {
						listener.itemAdded( DefaultUmlDiagram.this, item );
					}
				}
			}
		};
	}

	/**
	 * Gets the graph which is painting the items.
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Gets the repository that created and manages this diagram.
	 * @return the repository, not <code>null</code>
	 */
	public DefaultUmlDiagramRepository getRepository() {
		return repository;
	}

	@Override
	public TypeBox createType() {
		DefaultTypeBox result = new DefaultTypeBox( this );
		result.makeVisible();
		return result;
	}

	@Override
	public List<Item> getSelection() {
		List<Item> result = new ArrayList<>();
		for( GraphItem item : graph.getItems() ) {
			SelectableCapability selectable = item.getCapability( CapabilityName.SELECTABLE );
			if( selectable != null ) {
				if( selectable.getSelected().isSelected() ) {
					if( item instanceof Item ) {
						result.add( (Item) item );
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<Item> getItems() {
		List<Item> result = new ArrayList<>();
		for( GraphItem item : graph.getItems() ) {
			if( item instanceof Item ) {
				result.add( (Item) item );
			}
		}
		return Collections.unmodifiableList( result );
	}

	/**
	 * Gets all the {@link DefaultItem}s that are stored in this diagram.
	 * @return the items
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public List<DefaultItem<?>> getDefaultItems() {
		// all the Items are DefaultItems
		return (List) getItems();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public <T extends Item> T getItem( ItemKey<T> key ) {
		for( GraphItem item : graph.getItems() ) {
			if( item instanceof Item ) {
				if( ((Item) item).getKey().equals( key ) ) {
					return (T) item;
				}
			}
		}
		return null;
	}

	@SuppressWarnings( "unchecked" )
	public <T extends Box> DefaultBox<T> getDefaultBox( ItemKey<T> key ) {
		return (DefaultBox<T>) getItem( key );
	}

	@Override
	public Item getSelectedItem() {
		for( Item item : getItems() ) {
			if( item.isPrimarySelected() ) {
				return item;
			}
		}
		return null;
	}

	@Override
	public List<Item> getSelectedItems() {
		List<Item> result = new LinkedList<>();
		for( Item item : getItems() ) {
			if( item.isPrimarySelected() ) {
				result.add( 0, item );
			} else if( item.isSecondarySelected() ) {
				result.add( item );
			}
		}
		return result;
	}

	@Override
	public void setSelectedItem( Item item ) {
		List<Item> list = new ArrayList<>( 1 );
		list.add( item );
		setSelectedItems( list );
	}

	@Override
	public void setSelectedItems( Iterable<Item> items ) {
		Item primary = null;
		Set<Item> selected = new HashSet<>();
		if( items != null ) {
			for( Item item : items ) {
				if( primary == null ) {
					primary = item;
				}
				selected.add( item );
			}
		}

		for( DefaultItem<?> item : getDefaultItems() ) {
			if( item == primary ) {
				item.setSelected( Selection.PRIMARY );
			} else if( selected.contains( item ) ) {
				item.setSelected( Selection.SECONDARY );
			} else {
				item.setSelected( Selection.NOT_SELECTED );
			}
		}
	}

	@Override
	public void addItemContextListener( ItemContextListener listener ) {
		itemContextListener.addListener( listener );
	}

	@Override
	public void removeItemContextListener( ItemContextListener listener ) {
		itemContextListener.removeListener( listener );
	}

	@Override
	public void addItemSelectionListener( ItemSelectionListener listener ) {
		itemSelectionListener.addListener( listener );
	}

	@Override
	public void removeItemSelectionListener( ItemSelectionListener listener ) {
		itemSelectionListener.removeListener( listener );
	}

	@Override
	public void addUmlDiagramListener( UmlDiagramListener listener ) {
		if( listener == null ) {
			throw new IllegalArgumentException( "listener must not be null" );
		}
		umlDiagramListeners.add( listener );
	}

	@Override
	public void removeUmlDiagramListener( UmlDiagramListener listener ) {
		umlDiagramListeners.remove( listener );
	}

	/**
	 * Gets a unique identifier (in the scope of this {@link DefaultUmlDiagram}) that can be used
	 * to create new {@link DefaultItemKey}s.
	 * @return the next unique id
	 */
	public int nextUniqueId() {
		return nextUniqueId.getAndIncrement();
	}

	/**
	 * Gets the unique identifier that will be used for the next {@link ItemKey} that is being generated.
	 * @return the unique identifier
	 */
	public int currentUniqueId() {
		return nextUniqueId.get();
	}

	/**
	 * Sets the unique identifier that will be used for the next {@link ItemKey} that is generated.
	 * @param id the unique identifier
	 */
	public void setCurrentUniqueId( int id ) {
		nextUniqueId.set( id );
	}
}
