package graph.items.capability;

import graph.model.Selection;
import graph.model.Selection.Importance;
import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityHandlerSite;
import graph.model.capability.SelectableCapability;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Describes the action of selecting and deselecting items.
 * @author Benjamin Sigg
 */
public class SelectableCapabilityHandler implements CapabilityHandler<SelectableCapability> {
	private CapabilityHandlerSite<SelectableCapability> site;
	private SelectableCapability newlySelected;

	@Override
	public void init( CapabilityHandlerSite<SelectableCapability> site ) {
		this.site = site;
		site.addMouseListener( mouseListener() );
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		// ignore
	}
	
	private SelectableCapability getSelectable( int x, int y ){
		for( SelectableCapability selectable : site.getCapabilities() ){
			if( selectable.contains( x, y )){
				return selectable;
			}
		}
		return null;
	}
	
	private void deselectAll(){
		for( SelectableCapability selectable : site.getCapabilities() ){
			selectable.setSelected( Selection.NO_SELECTION );
		}
	}
	
	private void ensureNoPrimary(){
		for( SelectableCapability selectable : site.getCapabilities() ){
			if( selectable.getSelected().getImportance() == Importance.PRIMARY ){
				selectable.setSelected( new Selection( true, Importance.SECONDARY ) );
			}
		}
	}
	
	private MouseListener mouseListener(){
		return new MouseAdapter() {
			@Override
			public void mousePressed( MouseEvent e ) {
				if( e.getButton() == MouseEvent.BUTTON1 ){
					newlySelected = null;
					
					SelectableCapability selectable = getSelectable( e.getX(), e.getY() );
					
					if( e.isControlDown() ){
						if( selectable != null ){
							if( !selectable.getSelected().isSelected() ){
								ensureNoPrimary();
								selectable.setSelected( new Selection( true, Importance.PRIMARY ) );
								newlySelected = selectable;
							}
						}
					}
					else{
						deselectAll();
						if( selectable != null ){
							selectable.setSelected( new Selection( true, Importance.PRIMARY ) );
						}
					}
				}
				else if( !e.isControlDown() ){
					if( (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0 ){
						deselectAll();
					}
				}
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( e.getButton() == MouseEvent.BUTTON1 ){
					if( e.isControlDown() ){
						SelectableCapability selectable = getSelectable( e.getX(), e.getY() );
						if( selectable != null && newlySelected != selectable ){
							if( selectable.getSelected().isSelected() ){
								selectable.setSelected( Selection.NO_SELECTION );
							}
						}
					}
					newlySelected = null;
				}
			}
		};
	}
}
