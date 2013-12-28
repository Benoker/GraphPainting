package graph.uml.intern;

import graph.items.GraphLabel;
import graph.items.MoveableItemCapability;
import graph.model.GraphSite;
import graph.model.Selection;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.uml.Box;
import graph.uml.Item;
import graph.uml.ItemKey;
import graph.uml.event.ItemSelectionListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of a {@link Box}. Offers a list of {@link ConnectionArray}s, for different kind of connections. 
 * @author Benjamin Sigg
 */
public abstract class DefaultBox<T extends Box> extends DefaultItem<T> implements Box {
	private GraphLabel label;

	private Color background = new Color( 200, 200, 200 );
	private Color selectedPrimary = new Color( 200, 255, 200 );
	private Color selectedSecondary = new Color( 200, 220, 200 );

	private BoxSelectionCapability selection;

	/** all the dependent items that are to be disposed together with this box */
	private List<Item> dependent = new ArrayList<>();

	/**
	 * Creates a new box.
	 * @param graph the graph which shows this box
	 */
	public DefaultBox( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public DefaultBox( DefaultUmlDiagram diagram, ItemKey<T> key ) {
		super( diagram, key );
		label = new GraphLabel( "" );
		label.setCapability( CapabilityName.SELECTABLE, null );

		addChild( label );

		selection = new BoxSelectionCapability( this );
		setCapability( CapabilityName.SELECTABLE, selection );

		MoveableItemCapability moveable = new MoveableItemCapability( label );
		label.setMoveableIfSelected( true );
		setCapability( CapabilityName.MOVEABLE, moveable );

		updateSelected();
	}

	@Override
	public void setBackground( Color background ) {
		this.background = background;
		updateSelected();
	}

	@Override
	public void setSelectedPrimary( Color selectedPrimary ) {
		this.selectedPrimary = selectedPrimary;
		updateSelected();
	}

	@Override
	public void setSelectedSecondary( Color selectedSecondary ) {
		this.selectedSecondary = selectedSecondary;
		updateSelected();
	}

	/**
	 * Updates the color of this box depending on the current selection state
	 */
	public void updateSelected() {
		Selection selected = selection.getSelected();
		switch( selected ){
			case PRIMARY:
				label.getLabel().setBackground( selectedPrimary );
				break;
			case SECONDARY:
				label.getLabel().setBackground( selectedSecondary );
				break;
			default:
				label.getLabel().setBackground( background );
				break;
		}
	}

	/**
	 * Gets the label that actually paints the text.
	 * @return the label that paints the code
	 */
	public GraphLabel getLabel() {
		return label;
	}

	@Override
	public void setLocation( int x, int y ) {
		label.setLocation( x, y );
	}

	@Override
	public void setText( String text ) {
		label.getLabel().setText( text );
		regraph();
	}

	public String getText() {
		return label.getLabel().getText();
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}

	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		return label.contains( x, y );
	}

	@Override
	protected Iterable<Item> dependentItems() {
		return new ArrayList<Item>( dependent );
	}

	/**
	 * Adds <code>item</code> to this box, <code>item</code> will be {@link Item#dispose() disposed}
	 * together this box.
	 * @param item the item to dispose
	 */
	public void addDependent( Item item ) {
		dependent.add( item );
	}

	/**
	 * Removes <code>item</code> from the list of items to dispose.
	 * @param item the item to remove
	 */
	public void removeDependent( Item item ) {
		dependent.remove( item );
	}

	@Override
	public void addItemSelectionListener( ItemSelectionListener listener ) {
		selection.addListener( listener );
	}

	@Override
	public void removeItemSelectionListener( ItemSelectionListener listener ) {
		selection.removeListener( listener );
	}
}
