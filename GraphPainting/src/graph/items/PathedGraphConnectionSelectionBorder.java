package graph.items;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Selection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;

public class PathedGraphConnectionSelectionBorder extends AbstractGraphItem implements GraphPaintable{
	private PathedGraphConnection connection;
	private Selection selection;
	
	private Color color = Color.LIGHT_GRAY;
	private Stroke stroke;
	
	public PathedGraphConnectionSelectionBorder( PathedGraphConnection connection, Stroke stroke ){
		this.connection = connection;
		this.stroke = stroke;
	}
	
	public void setSelection( Selection selection ) {
		this.selection = selection;
		repaint();
	}
	
	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}
	
	@Override
	public void paint( Graphics2D g ) {
		if( selection != null && selection.isSelected() ){
			Path2D path = connection.getOpenConnectionPath();
			
			if( stroke != null ){
				g.setStroke( stroke );
			}
			g.setColor( color );
			g.draw( path );
		}
	}
	
	@Override
	protected void addTo( GraphSite site ) {
		site.addPaintable( this );	
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		site.removePaintable( this );
	}
}
