package graph.items.capability;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityHandlerSite;
import graph.model.capability.ContextCapability;
import graph.model.capability.ContextSite;

/**
 * A handler that is used to open context menus.
 * @author Benjamin Sigg
 */
public class ContextCapabilityHandler implements CapabilityHandler<ContextCapability>{
	private CapabilityHandlerSite<ContextCapability> site;
	
	private JPopupMenu menu;
	
	@Override
	public void init( CapabilityHandlerSite<ContextCapability> site ) {
		this.site = site;
		
		site.addMouseListener( mouseListener() );
	}
	
	@Override
	public void dispose() {
		// ignore
	}
	
	private MouseListener mouseListener(){
		return new MouseAdapter() {
			@Override
			public void mousePressed( MouseEvent e ) {
				closeMenu();
				if( e.isPopupTrigger() ){
					popup( e.getX(), e.getY(), e.getComponent() );
				}
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( e.isPopupTrigger() ){
					popup( e.getX(), e.getY(), e.getComponent() );	
				}
			}
		};
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		if( !enabled ){
			closeMenu();
		}
	}
	
	private void closeMenu(){
		if( menu != null ){
			menu.setVisible( false );
			menu = null;
		}
	}
	
	private void popup( int x, int y, Component invoker ){
		ContextCapability item = getContext( x, y );
		if( item != null ){
			item.context( site( x, y, invoker ) );
		}
	}
	
	private ContextCapability getContext( int x, int y ){
		float best = 0.f;
		ContextCapability result = null;
		
		for( ContextCapability item : site.getCapabilities() ){
			float value = item.isContextMenuEnabledAt( x, y );
			if( value >= best && value > 0.f ){
				best = value;
				result = item;
			}
		}
		
		return result;
	}
	
	private ContextSite site( final int x, final int y, final Component invoker ){
		return new ContextSite(){
			private JPopupMenu menu = new JPopupMenu();
			
			@Override
			public int getX() {
				return x;
			}

			@Override
			public int getY() {
				return y;
			}
			
			@Override
			public Component getInvoker() {
				return invoker;
			}
			
			@Override
			public JPopupMenu getMenu() {
				return menu;
			}
			
			@Override
			public void show( JPopupMenu menu ) {
				ContextCapabilityHandler.this.menu = menu;
				menu.show( invoker, x, y );
			}
			
			@Override
			public void showMenu( Iterable<JComponent> menuEntries ) {
				for( JComponent entry : menuEntries ){
					menu.add( entry );
				}
				show( menu );
			}
		};
	}
}
