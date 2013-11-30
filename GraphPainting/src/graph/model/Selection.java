package graph.model;

/**
 * Describe the selection state of a {@link GraphSelectable}, is immutable.
 * @author Benjamin Sigg
 */
public class Selection {
	/**
	 * The importance of a selection
	 * @author Benjamin Sigg
	 */
	public enum Importance{
		/** The {@link Selection} describes the one item that is selected by the user */
		PRIMARY,
		/** The {@link Selection} describes an item that is selected, but is not the one item that is selected by the user */
		SECONDARY
	}
	
	private boolean selected;
	private Importance importance;
	
	public Selection( boolean selected, Importance importance ){
		this.selected = selected;
		this.importance = importance;
	}
	
	/**
	 * Tells whether the {@link GraphSelectable} is selected in any way.
	 * @return whether the item is selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Tells how important this selection is.
	 * @return the importance of this selection, or <code>null</code> if {@link #isSelected()} is <code>false</code>
	 */
	public Importance getImportance() {
		return importance;
	}
}
