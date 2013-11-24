package graph.items;

import graph.model.GraphItem;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.Rectangular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class GraphLabel extends MoveableRectangularGraphItem implements GraphItem, Regraphable, Rectangular {
	private GraphSite site;
	private JLabel label;
	
	public GraphLabel( String text ){
		label = new JLabel();
		
		label.setText( text );
		label.setOpaque( true );
		label.setBackground( new Color( 128, 128, 255 ) );
		label.setBorder( BorderFactory.createCompoundBorder( 
				BorderFactory.createLineBorder( Color.BLACK, 1 ), 
				BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) ) );
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setLocation( int x, int y ){
		Dimension size = label.getPreferredSize();
		setBoundaries( x, y, size.width, size.height );
	}
	
	@Override
	public void setBoundaries( Rectangle boundaries ) {
		label.setBounds( boundaries );
		super.setBoundaries( boundaries );
	}
	
	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( label );
			this.site.remove( (Regraphable)this );
		}
		this.site = site;
		if( site != null ){
			site.add( label );
			site.add( (Regraphable)this );
		}
		super.set( site );
	}
	
	@Override
	public void regraph() {
		Rectangle bounds = getBoundaries();
		bounds.setSize( label.getPreferredSize() );
		setBoundaries( bounds );	
	}
}
