package graph.items;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Selection;
import graph.model.capability.SelectableCapability;
import graph.model.connection.Rectangular;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A border that is painted around a {@link Rectangular} when it is selected.
 * @author Benjamin Sigg
 */
public class RectangularSelection extends AbstractGraphItem implements SelectableCapability, GraphPaintable{
	private Rectangular parent;
	private Selection selection = Selection.NO_SELECTION;
	
	private Color primary = new Color( 128, 128, 255 );
	private Color secondary = new Color( 200, 200, 255 );
	
	public RectangularSelection( Rectangular parent ){
		this.parent = parent;
	}
	
	public void setPrimary( Color primary ) {
		this.primary = primary;
		regraph();
	}
	
	public void setSecondary( Color secondary ) {
		this.secondary = secondary;
		regraph();
	}
	
	@Override
	public void setSelected( Selection selection ) {
		this.selection = selection;
		regraph();
	}
	
	@Override
	public Selection getSelected() {
		return selection;
	}

	@Override
	public float contains( int x, int y ) {
		if( parent.getBoundaries().contains( x, y ) ){
			return 1.f;
		}
		else{
			return 0.f;
		}
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		site.removePaintable( this );
	}

	@Override
	protected void addTo( GraphSite site ) {
		site.addPaintable( this );
	}
	
	@Override
	public void paint( Graphics2D g ) {
		// ignore
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		if( selection.isSelected() ){	
			Color color;
			switch( selection.getImportance() ){
				case PRIMARY:
					color = primary;
					break;
				case SECONDARY:
					color = secondary;
					break;
				default:
					return;
			}
			
			Rectangle bounds = parent.getBoundaries();
			int x = bounds.x;
			int y = bounds.y;
			int w = bounds.width;
			int h = bounds.height;
			
			g.setColor( Color.RED );
			g.drawRect( x, y, w-1, h-1 );
			g.drawRect( x-1, y-1, w+1, h+1 );
			
			g.setColor( color );
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_ATOP, 0.5f ) );
			g.fillRect( x+1, y+1, w-1, h-1 );
		}
	}
}
