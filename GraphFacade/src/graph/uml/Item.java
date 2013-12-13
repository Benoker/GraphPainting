package graph.uml;

/**
 * An item is some kind of graphical effect that is part of an UML diagram.
 * @author Benjamin Sigg
 */
public interface Item {
	/**
	 * Removes this item (and all items that depend on this item) from the graph.
	 */
	public void dispose();
}
