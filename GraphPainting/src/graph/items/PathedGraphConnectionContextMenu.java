package graph.items;

import graph.model.GraphSite;
import graph.model.capability.ContextCapability;
import graph.model.capability.ContextSite;

import javax.swing.JPopupMenu;

public class PathedGraphConnectionContextMenu extends AbstractGraphItem implements ContextCapability{
	private PathedGraphConnection connection;
	private JPopupMenu menu;
	
	public PathedGraphConnectionContextMenu( PathedGraphConnection connection ){
		this.connection = connection;
		menu = new JPopupMenu();
	}
	
	public JPopupMenu getMenu() {
		return menu;
	}

	@Override
	public float isContextMenuEnabledAt( int x, int y ) {
		if( connection.getOpenConnectionPath().intersects( x, y, 1, 1 )){
			return 1;
		}
		if( connection.getOpenConnectionPath().intersects( x-1, y-1, 3,  3 )){
			return 0.5f;
		}
		if( connection.getOpenConnectionPath().intersects( x-2, y-2, 5,  5 )){
			return 0.25f;
		}
		if( connection.getOpenConnectionPath().intersects( x-3, y-3, 7,  7 )){
			return 0.125f;
		}
		return 0f;
	}

	@Override
	public void context( ContextSite site ) {
		site.show( menu );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}
}
