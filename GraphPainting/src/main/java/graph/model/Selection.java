package graph.model;

/**
 * Describe the selection state of a {@link GraphSelectable}.
 * @author Benjamin Sigg
 */
public enum Selection {
	/**
	 * Not a selection.
	 */
	NOT_SELECTED,

	/**
	 * Primary selection.
	 */
	PRIMARY,

	/**
	 * Secondary selection.
	 */
	SECONDARY;

	/**
	 * Tells whether the {@link GraphSelectable} is selected in any way.
	 * @return whether the item is selected
	 */
	public boolean isSelected() {
		return this != NOT_SELECTED;
	}

	public boolean isPrimary() {
		return this == PRIMARY;
	}

	public boolean isSecondary() {
		return this == SECONDARY;
	}
}
