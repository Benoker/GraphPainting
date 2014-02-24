package graph.items.capability;

import graph.model.Selection;
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
	public void dispose() {
		// ignore
	}

	@Override
	public void setEnabled( boolean enabled ) {
		// ignore
	}

	private SelectableCapability getSelectable( int x, int y ) {
		float best = 0.f;
		SelectableCapability result = null;

		for( SelectableCapability selectable : site.getCapabilities() ) {
			float contains = selectable.contains( x, y );
			if( contains >= best && contains > 0 ) {
				best = contains;
				result = selectable;
			}
		}
		return result;
	}

	private void deselectAll( SelectableCapability ignore ) {
		for( SelectableCapability selectable : site.getCapabilities() ) {
			if( selectable != ignore ) {
				if( selectable.getSelected().isSelected() ) {
					selectable.setSelected( Selection.NOT_SELECTED );
				}
			}
		}
	}

	private void ensureNoPrimary() {
		for( SelectableCapability selectable : site.getCapabilities() ) {
			if( selectable.getSelected().isPrimary() ) {
				selectable.setSelected( Selection.SECONDARY );
			}
		}
	}

	private MouseListener mouseListener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed( MouseEvent e ) {
				if( e.getButton() == MouseEvent.BUTTON1 || e.isPopupTrigger() ) {
					newlySelected = null;

					SelectableCapability selectable = getSelectable( e.getX(), e.getY() );
					
					if( e.isShiftDown() ) {
						if( selectable != null ) {
							if( selectable.getSelected().isSelected() ){
								selectable.setSelected( Selection.NOT_SELECTED );
							}
							else{
								if( !selectable.getSelected().isPrimary() ) {
									ensureNoPrimary();
									selectable.setSelected( Selection.PRIMARY );
								}
								newlySelected = selectable;
							}
						}
					} else if(isLeftClick(e) && !isPopupTriggerSelection( selectable, e )) {
						deselectAll( selectable );
						if( selectable != null && !selectable.getSelected().isPrimary() ) {
							selectable.setSelected( Selection.PRIMARY );
						}
					}
				} else if( !e.isShiftDown() ) {
					if( (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0 ) {
						deselectAll( null );
					}
				}
			}

			private boolean isPopupTriggerSelection( SelectableCapability selectable, MouseEvent e ){
				if( !e.isPopupTrigger() ){
					return false;
				}
				if( selectable == null ){
					return false;
				}
				return selectable.getSelected().isSelected();
			}
			
			private boolean isLeftClick( MouseEvent e ){
				return e.getButton() == MouseEvent.BUTTON1;
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( e.isPopupTrigger() ) {
					return;
				}

				if( e.getButton() == MouseEvent.BUTTON1 ) {
					if( e.isControlDown() ) {
						SelectableCapability selectable = getSelectable( e.getX(), e.getY() );
						if( selectable != null && newlySelected != selectable ) {
							if( selectable.getSelected().isSelected() ) {
								selectable.setSelected( Selection.NOT_SELECTED );
							}
						}
					}
					newlySelected = null;
				}
			}
		};
	}
}
