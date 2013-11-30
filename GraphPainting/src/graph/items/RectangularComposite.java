package graph.items;

import graph.model.GraphSite;
import graph.model.connection.Rectangular;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class RectangularComposite extends AbstractGraphItem implements Rectangular{
	private Rectangle boundaries = new Rectangle();
	
	private List<Rectangular> items = new ArrayList<>();
	private GraphSite site;
	
	public void add( Rectangular rectangular ){
		items.add( rectangular );
		rectangular.setBoundaries( getBoundaries() );
		rectangular.set( site );
	}
	
	public void remove( Rectangular rectangular ){
		items.remove( rectangular );
		rectangular.set( null );
	}
	
	@Override
	public Rectangle getBoundaries() {
		return new Rectangle( boundaries );
	}
	
	public void setBoundaries( int x, int y, int width, int height ){
		setBoundaries( new Rectangle( x, y, width, height ) );
	}
	
	@Override
	public void setBoundaries( Rectangle boundaries ) {
		for( Rectangular rectangular : items ){	
			rectangular.setBoundaries( boundaries );
		}
		if( !getBoundaries().equals( boundaries )){
			this.boundaries.setBounds( boundaries );
			if( site != null ){
				site.regraph();
			}
		}
	}
	
	@Override
	public void set( GraphSite site ) {
		this.site = site;
		for( Rectangular item : items ){	
			item.set( site );
		}
	}
}
