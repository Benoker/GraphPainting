package graph.uml.event;

import graph.model.Selection;
import graph.uml.Item;

/**
 * An event that describes the change of the selection state of an {@link Item}
 * @author Benjamin Sigg
 */
public class ItemSelectionEvent {
	private Item item;
	private Selection selection;

	public ItemSelectionEvent( Item item, Selection selection ) {
		this.item = item;
		this.selection = selection;
	}

	/**
	 * Gets the {@link Item} whose state changed.
	 * @return the item that changed, not <code>null</code>
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Gets the selection state itself.
	 * @return the selection state, not <code>null</code>
	 */
	public Selection getSelection() {
		return selection;
	}
	
	/**
	 * Tells whether the {@link Item} is selected or not.
	 * @return whether the item is currently selected
	 */
	public boolean isSelected() {
		return selection.isSelected();
	}

	/**
	 * Tells whether the {@link Item} is the primary selected {@link Item}, there can be only
	 * one primary selected item per diagram.
	 * @return whether the item is the primary item
	 */
	public boolean isPrimarySelected() {
		return selection.isPrimary();
	}

	/**
	 * Tells whether the {@link Item} is a secondary selected {@link Item}, there can be many
	 * secondary selected items per diagram.
	 * @return whether the item is a secondary item
	 */
	public boolean isSecondarySelected() {
		return selection.isSecondary();
	}
}
