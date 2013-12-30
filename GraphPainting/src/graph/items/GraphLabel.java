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
	private JLabel label;
	
	public GraphLabel( String text ){
		label = new JLabel();
		
        label.setMinimumSize(new Dimension(5, 5));
        setBoundaries(0, 0, 5, 5);
        
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
	protected void addTo( GraphSite site ) {
		site.addComponent( label );
		site.addRegraphable( (Regraphable)this );
		super.addTo( site );
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		site.removeComponent( label );
		site.removeRegraphable( (Regraphable)this );
		super.removeFrom( site );
	}
	
	@Override
	public void regraphed() {
		Rectangle bounds = getBoundaries();
		bounds.setSize( label.getPreferredSize() );
		setBoundaries( bounds );	
	}
}
