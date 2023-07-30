package graph.uml.intern;

import graph.items.AbstractGraphItem;
import graph.model.Selection;
import graph.model.capability.CapabilityName;
import graph.model.capability.SelectableCapability;
import graph.ui.Graph;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.event.ItemContextListener;
import graph.uml.intern.tools.DefaultItemContextCapability;

/**
 * Implementation of an {@link Item}.
 * @param <T> the type of this item
 * @author Benjamin Sigg
 */
public abstract class DefaultItem<T extends Item> extends AbstractGraphItem implements Item {
	private DefaultUmlDiagram diagram;
	private boolean disposed = false;
	private boolean visible = false;
	private DefaultItemContextCapability contextCapability;
	private ItemKey<T> key;

	/**
	 * Creates a new item.
	 * @param graph the graph to which this item belongs
	 * @param key the unique identifier of this item, can be <code>null</code>
	 */
	public DefaultItem( DefaultUmlDiagram diagram, ItemKey<T> key ) {
		this.diagram = diagram;

		contextCapability = new DefaultItemContextCapability( diagram, this );
		setCapability( CapabilityName.CONTEXT_MENU, contextCapability );

		if( key == null ) {
			key = createKey( diagram );
		}
		this.key = key;
	}

	/**
	 * Creates the unique identifier of this {@link Item} 
	 * @param diagram the diagram from which to draw a new unique id
	 * @return the new key, must not be <code>null</code>
	 */
	protected abstract ItemKey<T> createKey( DefaultUmlDiagram diagram );

	@Override
	public ItemKey<T> getKey() {
		return key;
	}

	public Graph getGraph() {
		return diagram.getGraph();
	}

	public DefaultUmlDiagram getDiagram() {
		return diagram;
	}

	/**
	 * Makes this item visible if it is not yet visible.
	 */
	public void makeVisible() {
		if( !visible ) {
			visible = true;
			getGraph().addItem( this );
		}
	}

	@Override
	public void dispose() {
		if( !disposed ) {
			disposed = true;
			disposeDependentItems();
			getGraph().removeItem( this );
		}
	}

	/**
	 * Tells whether this item can have a context menu at <code>x/y</code>
	 * @param x the x-coordinate of the mouse
	 * @param y the y-coordinate of the mouse
	 * @return whether a context menu is available, a value below <code>0.f</code> means no,
	 * a value greater than <code>1.f</code> means yes
	 */
	public abstract float isContextMenuEnabledAt( int x, int y );

	@Override
	public void addItemContextListener( ItemContextListener listener ) {
		contextCapability.addListener( listener );
	}

	@Override
	public void removeItemContextListener( ItemContextListener listener ) {
		contextCapability.removeListener( listener );
	}

	/**
	 * Searches the {@link SelectableCapability} of this item and returns its {@link Selection}. If 
	 * this item does not have any {@link SelectableCapability}, then {@link Selection#NOT_SELECTED}
	 * is returned.
	 * @return the selection state of this item
	 */
	public Selection getSelection() {
		SelectableCapability selection = getCapability( CapabilityName.SELECTABLE );
		if( selection == null ) {
			return Selection.NOT_SELECTED;
		} else {
			return selection.getSelected();
		}
	}

	@Override
	public boolean isSelected() {
		return getSelection().isSelected();
	}

	@Override
	public boolean isPrimarySelected() {
		return getSelection().isPrimary();
	}

	@Override
	public boolean isSecondarySelected() {
		return getSelection().isSecondary();
	}

	/**
	 * Changes the selection state of this item to <code>selection</code>. The method fails silently
	 * if this item does not support selection.
	 * @param selection the new selection state
	 */
	public void setSelected( Selection selection ) {
		SelectableCapability capability = getCapability( CapabilityName.SELECTABLE );
		if( capability != null ) {
			Selection old = capability.getSelected();
			if( !old.equals( selection ) ) {
				capability.setSelected( selection );
			}
		}
	}

	protected abstract Iterable<? extends Item> dependentItems();

	/**
	 * Calls {@link #dispose()} on all the items that depend on this item.
	 */
	protected void disposeDependentItems() {
		Iterable<? extends Item> items = dependentItems();
		if( items != null ) {
			for( Item item : items ) {
				if( item != null ) {
					item.dispose();
				}
			}
		}
	}
}
