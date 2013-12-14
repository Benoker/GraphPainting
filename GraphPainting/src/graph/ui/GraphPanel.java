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
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A panel that shows some {@link GraphItem}s. Clients should not interact directly with
 * {@link GraphPanel}, instead they should interact with {@link Graph} that provides a cleaner
 * interface and is able to fire event.
 * @author Benjamin Sigg
 */
public class GraphPanel extends JPanel{
	private Graph graph;
	
	private List<GraphPaintable> paintables = new ArrayList<>();
	private List<Regraphable> regraphables = new ArrayList<>();
	
	private GraphSite site = new DefaultGraphSite();
	private boolean valid = false;
	
	private JPanel canvas;
	private JPanel overlay;

	/**
	 * Creates a new panel
	 * @param graph the graph that is painted on this panel
	 */
	public GraphPanel( Graph graph ){
		this.graph = graph;
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
		
		graph.addGraphListener( graphListener() );
	}
	
	private GraphListener graphListener(){
		return new GraphListener() {
			@Override
			public void itemRemoved( Graph source, GraphItem item ) {
				item.set( null );	
			}
			
			@Override
			public void itemAdded( Graph source, GraphItem item ) {
				item.set( site );
			}
		};
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
	
	public Graph getGraph() {
		return graph;
	}
	
	@Override
	public void doLayout() {
		int width = getWidth();
		int height = getHeight();
		
		canvas.setBounds( 0, 0, width, height );
		overlay.setBounds( 0, 0, width, height );
	}
	
	/**
	 * Iterates over all known {@link GraphItem}s and asks for their 
	 * {@link GraphItem#getCapability(CapabilityName) capability} to support <code>name</code>.
	 * @param name the capability to search
	 * @return all the available capabilities
	 */
	public <T> List<T> getCapabilities( CapabilityName<T> name ){ 
		List<T> result = new ArrayList<>();
		for( GraphItem item : graph.getItems() ){
			T capability = item.getCapability( name );
			if( capability != null ){
				result.add( capability );
			}
		}
		return result;
	}
	
	private void regraph(){
		valid = false;
		if( EventQueue.isDispatchThread() ){
			EventQueue.invokeLater( new Runnable() {
				@Override
				public void run() {
					if( !valid ){
						resetGraph();
					}
				}
			});
		}
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
			graph.addItem( item );
		}
		
		@Override
		public void removeItem( GraphItem item ) {
			graph.removeItem( item );
		}
		
		@Override
		public void addPaintable( GraphPaintable paintable ) {
			paintables.add( paintable );
			regraph();
		}
		
		@Override
		public void addComponent( JComponent component ) {
			canvas.add( component );
			regraph();
		}
		
		@Override
		public void addRegraphable( Regraphable regraphable ) {
			regraphables.add( regraphable );	
		}
		
		@Override
		public void removePaintable( GraphPaintable paintable ) {
			paintables.remove( paintable );
			regraph();
		}
		
		@Override
		public void removeComponent( JComponent component ) {
			canvas.remove( component );
			regraph();
		}
		
		@Override
		public void removeRegraphable( Regraphable regraphable ) {
			regraphables.remove( regraphable );
		}
		
		@Override
		public void regraph() {
			GraphPanel.this.regraph();	
		}
	}
}
