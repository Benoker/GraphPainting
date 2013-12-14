package graph.uml.intern;

import graph.model.Selection;
import graph.model.capability.SelectableCapability;
import graph.uml.Box;

/**
 * Capability to select a {@link Box}
 * @author Benjamin Sigg
 */
public class BoxSelectionCapability implements SelectableCapability{
	private DefaultBox box;
	private Selection selection = Selection.NO_SELECTION;
	
	/**
	 * Creates a new box selection capability.
	 * @param box the box that may be selected
	 */
	public BoxSelectionCapability( DefaultBox box ){
		this.box = box;
	}
	
	@Override
	public void setSelected( Selection selection ) {
		this.selection = selection;
		box.updateSelected();
	}

	@Override
	public Selection getSelected() {
		return selection;
	}

	@Override
	public float contains( int x, int y ) {
		return box.getLabel().contains( x, y );
	}
}
