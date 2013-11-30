package graph.ui;

import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.capability.CapabilityName;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	private List<GraphPaintable> paintables = new ArrayList<>();
	private List<Regraphable> regraphables = new ArrayList<>();
	
	private GraphSite site = new DefaultGraphSite();
	private boolean valid = false;
	
	private List<GraphItem> items = new LinkedList<>();
	
	private JPanel canvas;
	private JPanel overlay;
	
	public GraphPanel(){
		setLayout( null );
		
		canvas = createCanvas();
		canvas.setLayout( null );
		canvas.setBackground( Color.WHITE );
		canvas.setOpaque( true );
		canvas.setFocusable( true );
		
		overlay = createOverlay();
		overlay.setOpaque( false );
		overlay.setFocusable( false );

		add( overlay );
		add( canvas );
	}
	
	private JPanel createCanvas(){
		return new JPanel(){
			@Override
			protected void paintComponent( Graphics g ) {
				super.paintComponent( g );
				for( GraphPaintable paintable : paintables ){
					Graphics2D g2 = (Graphics2D)g.create();
					paintable.paint( g2 );
					g2.dispose();
				}
			}
		};
	}
	
	private JPanel createOverlay(){
		return new JPanel(){
			@Override
			protected void paintComponent( Graphics g ) {
				for( GraphPaintable paintable : paintables ){
					Graphics2D g2 = (Graphics2D)g.create();
					paintable.paintOverlay( g2 );
					g2.dispose();
				}
			}
		};
	}
	
	@Override
	public void doLayout() {
		int width = getWidth();
		int height = getHeight();
		
		canvas.setBounds( 0, 0, width, height );
		overlay.setBounds( 0, 0, width, height );
	}
	
	/**
	 * Adds another item to the graph.
	 * @param item the new item, not <code>null</code>
	 */
	public void add( GraphItem item ){
		item.set( site );
		items.add( item );
	}
	
	/**
	 * Removes an item from the graph.
	 * @param item the item to remove, not <code>null</code>
	 */
	public void remove( GraphItem item ){
		items.remove( item );
		item.set( null );
	}
	
	/**
	 * Iterates over all known {@link GraphItem}s and asks for their 
	 * {@link GraphItem#getCapability(CapabilityName) capability} to support <code>name</code>.
	 * @param name the capability to search
	 * @return all the available capabilities
	 */
	public <T> List<T> getCapabilities( CapabilityName<T> name ){ 
		List<T> result = new ArrayList<>();
		for( GraphItem item : items ){
			T capability = item.getCapability( name );
			if( capability != null ){
				result.add( capability );
			}
		}
		return result;
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
			regraphable.regraphed();
		}
		
		repaint();
	}
	
	private class DefaultGraphSite implements GraphSite{
		@Override
		public void addItem( GraphItem item ) {
			GraphPanel.this.add( item );	
		}
		
		@Override
		public void removeItem( GraphItem item ) {
			GraphPanel.this.remove( item );
		}
		
		@Override
		public void add( GraphPaintable paintable ) {
			paintables.add( paintable );
			regraph();
		}
		
		@Override
		public void add( JComponent component ) {
			canvas.add( component );
			regraph();
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
			canvas.remove( component );
			regraph();
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
