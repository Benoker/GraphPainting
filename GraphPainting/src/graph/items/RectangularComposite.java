package graph.items;

import graph.model.GraphItem;
import graph.model.GraphSite;
import graph.model.connection.Rectangular;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class RectangularComposite extends AbstractGraphItem implements Rectangular{
	private Rectangle boundaries = new Rectangle();
	
	private List<Rectangular> items = new ArrayList<>();
	
	public void addChild( Rectangular item ) {
		items.add( item );
		item.setBoundaries( getBoundaries() );
		addChild( (GraphItem)item );
	}
	
	public void removeChild( Rectangular item ){
		items.remove( item );
		removeChild( (GraphItem)item );
	}
	
	@Override
	public Rectangle getBoundaries() {
		return new Rectangle( boundaries );
	}
	
	public void setBoundaries( int x, int y, int width, int height ){
		setBoundaries( new Rectangle( x, y, width, height ) );
	}
	
	@Override
	public boolean contains( int x, int y ) {
		return boundaries.contains( x, y );
	}
	
	@Override
	public void setBoundaries( Rectangle boundaries ) {
		for( Rectangular rectangular : items ){	
			rectangular.setBoundaries( boundaries );
		}
		if( !getBoundaries().equals( boundaries )){
			this.boundaries.setBounds( boundaries );
			regraph();
		}
	}
	
	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore	
	}
}
