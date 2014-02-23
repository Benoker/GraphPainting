package graph.items;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Selection;
import graph.model.connection.Rectangular;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A selection border can be drawn around a {@link Rectangular} shape, the border appears
 * as thick black line.
 * @author Benjamin Sigg
 */
public class RectangularSelectionBorder extends AbstractGraphItem implements GraphPaintable{
	private Rectangular item;
	private Selection selection;
	
	private Color color = Color.BLACK;
	private int thickness = 2;
	
	public RectangularSelectionBorder( Rectangular item ){
		this.item = item;
	}

	public void setSelection( Selection selection ) {
		this.selection = selection;
		repaint();
	}
	
	@Override
	public void paint( Graphics2D g ) {
		if( selection != null && selection.isSelected() ) {
			g.setColor( color );
			
			Rectangle bounds = item.getBoundaries();
			int x = bounds.x-1;
			int y = bounds.y-1;
			int w = bounds.width+1;
			int h = bounds.height+1;
			
			for(int i = 0; i < thickness; i++ ){
				g.drawRect( x, y, w, h );
				x--;
				y--;
				w += 2;
				h += 2;
			}
		}
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		// nothing	
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
	public Rectangle getVisibleBoundaries( Graphics g ) {
		return null;
	}
}
