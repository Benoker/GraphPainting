package graph.model;

public interface Regraphable {

	/**
	 * Called when the graph has been invalidated, the item may invalidate itself and
	 * calculate things like size and shape once needed.
	 */
	public void regraphed();
}
