package graph.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import graph.model.GraphItem;
import graph.model.GraphMoveable;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Regraphable;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	private List<GraphPaintable> paintables = new ArrayList<>();
	private List<Regraphable> regraphables = new ArrayList<>();
	private GraphMoveableHandler moveableHandler;
	
	private GraphSite site = new DefaultGraphSite();
	private boolean valid = false;
	
	public GraphPanel(){
		moveableHandler = new GraphMoveableHandler( this );
		
		setLayout( null );
		setBackground( Color.WHITE );
		setOpaque( true );
	}
	
	public void add( GraphItem box ){
		box.set( site );
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		
		for( GraphPaintable paintable : paintables ){
			Graphics2D g2 = (Graphics2D)g.create();
			paintable.paint( g2 );
			g2.dispose();
		}
	}
	
	private void regraph(){
		valid = false;
		EventQueue.invokeLater( new Runnable() {
			@Override
			public void run() {
				if( !valid ){
					resetGraph();
				}
			}
		});
	}
	
	private void resetGraph(){
		valid = true;
		
		for( Regraphable regraphable : regraphables ){
			regraphable.regraph();
		}
		
		repaint();
	}
	
	private class DefaultGraphSite implements GraphSite{
		@Override
		public void add( GraphPaintable paintable ) {
			paintables.add( paintable );
			regraph();
		}
		
		@Override
		public void add( JComponent component ) {
			GraphPanel.this.add( component );
			regraph();
		}
		
		@Override
		public void add( GraphMoveable moveable ) {
			moveableHandler.add( moveable );	
		}
		
		@Override
		public void add( Regraphable regraphable ) {
			regraphables.add( regraphable );	
		}
		
		@Override
		public void remove( GraphPaintable paintable ) {
			paintables.remove( paintable );
			regraph();
		}
		
		@Override
		public void remove( JComponent component ) {
			GraphPanel.this.remove( component );
			regraph();
		}
		
		@Override
		public void remove( GraphMoveable moveable ) {
			moveableHandler.remove( moveable );
		}
		
		@Override
		public void remove( Regraphable regraphable ) {
			regraphables.remove( regraphable );
		}
		
		@Override
		public void regraph() {
			GraphPanel.this.regraph();	
		}
	}
}
