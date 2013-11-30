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
	
	public GraphPanel(){
		setLayout( null );
		setBackground( Color.WHITE );
		setOpaque( true );
		setFocusable( true );
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
		public void remove( Regraphable regraphable ) {
			regraphables.remove( regraphable );
		}
		
		@Override
		public void regraph() {
			GraphPanel.this.regraph();	
		}
	}
}
