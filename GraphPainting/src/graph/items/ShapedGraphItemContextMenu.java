package graph.items;

import javax.swing.JPopupMenu;

import graph.model.GraphSite;
import graph.model.capability.ContextCapability;
import graph.model.capability.ContextSite;

public class ShapedGraphItemContextMenu extends AbstractGraphItem implements ContextCapability{
	private ShapedGraphItem item;
	private JPopupMenu menu;
	
	public ShapedGraphItemContextMenu( ShapedGraphItem item ){
		this.item = item;
		menu = new JPopupMenu();
	}
	
	public JPopupMenu getMenu() {
		return menu;
	}

	@Override
	public boolean isContextMenuEnabledAt( int x, int y ) {
		return item.contains( x, y ) >= 1.f;
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
