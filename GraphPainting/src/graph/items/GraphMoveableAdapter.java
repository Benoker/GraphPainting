package graph.items;

import graph.model.GraphItem;
import graph.model.GraphMoveable;
import graph.model.GraphMoveableItem;
import graph.model.GraphSite;

public class GraphMoveableAdapter implements GraphItem{
	private GraphSite site;
	private GraphMoveableItem item;
	private GraphMoveable moveable;
	
	public GraphMoveableAdapter( GraphMoveableItem item ){
		this.item = item;
		moveable = new MoveableAdapter();
	}
	
	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( moveable );
		}
		this.site = site;
		if( site != null ){
			site.add( moveable );
		}
	}
	
	private class MoveableAdapter implements GraphMoveable{
		private int offsetX;
		private int offsetY;
		
		@Override
		public boolean contains( int x, int y ) {
			return item.contains( x, y );
		}

		@Override
		public void startMoveAt( int x, int y ) {
			offsetX = item.getX() - x;
			offsetY = item.getY() - y;
		}
		
		@Override
		public void moveTo( int x, int y ) {
			item.moveTo( x + offsetX, y + offsetY );
		}

		@Override
		public void endMoveAt( int x, int y ) {
			moveTo( x, y );
		}
	}
}
