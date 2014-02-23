package graph.ui;

import graph.items.capability.ContextCapabilityHandler;
import graph.items.capability.MoveableCapabilityHandler;
import graph.items.capability.SelectableCapabilityHandler;
import graph.model.GraphItem;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityName;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A panel that shows some {@link GraphItem}s. Clients should not interact directly with
 * {@link GraphPanel}, instead they should interact with {@link Graph} that provides a cleaner
 * interface and is able to fire event.
 * @author Benjamin Sigg
 */
public class GraphPanel extends JPanel {
	private Graph graph;
	private GraphListener graphListener;

	private List<GraphPaintable> paintables = new ArrayList<>();
	private List<Regraphable> regraphables = new ArrayList<>();

	private GraphSite site = new DefaultGraphSite();
	private boolean valid = false;

	private JPanel canvas;
	private JPanel overlay;

	private CapabilityController capabilityController;

	/**
	 * Creates a new panel
	 */
	public GraphPanel() {
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

		graphListener = graphListener();

		capabilityController = new CapabilityController( this );
		capabilityController.register( CapabilityName.MOVEABLE, new MoveableCapabilityHandler() );
		capabilityController.register( CapabilityName.SELECTABLE, new SelectableCapabilityHandler() );
		capabilityController.register( CapabilityName.CONTEXT_MENU, new ContextCapabilityHandler() );

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				regraph();
			}

			public void componentShown( ComponentEvent e ) {
				regraph();
			}
		} );
	}

	private GraphListener graphListener() {
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

	private JPanel createCanvas() {
		return new JPanel() {
			@Override
			protected void paintComponent( Graphics g ) {
				super.paintComponent( g );
				for( GraphPaintable paintable : paintables ) {
					Graphics2D g2 = (Graphics2D) g.create();
					paintable.paint( g2 );
					g2.dispose();
				}
			}
		};
	}

	private JPanel createOverlay() {
		return new JPanel() {
			@Override
			protected void paintComponent( Graphics g ) {
				for( GraphPaintable paintable : paintables ) {
					Graphics2D g2 = (Graphics2D) g.create();
					paintable.paintOverlay( g2 );
					g2.dispose();
				}
			}
		};
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph( Graph graph ) {
		if( this.graph != graph ) {
			if( this.graph != null ) {
				disconnect();
			}

			this.graph = graph;

			if( this.graph != null ) {
				connect();
			}

			regraph();
		}
	}

	private void connect() {
		graph.addGraphListener( graphListener );
		for( GraphItem item : items() ) {
			graphListener.itemAdded( graph, item );
		}
	}

	private void disconnect() {
		graph.removeGraphListener( graphListener );
		for( GraphItem item : items() ) {
			graphListener.itemRemoved( graph, item );
		}
	}

	private GraphItem[] items() {
		List<GraphItem> items = graph.getItems();
		return items.toArray( new GraphItem[items.size()] );
	}

	/**
	 * Calculates the area in which this panel actually paints stuff.
	 * @param g the graphics context that will paint the panel, not <code>null</code>
	 * @return the boundaries, never <code>null</code> 
	 */
	public Rectangle getVisibleBoundaries( Graphics g ){
		Rectangle result = null;
		
		for( GraphPaintable paintable : paintables ){
			Rectangle boundaries = paintable.getVisibleBoundaries( g );
			result = union( result, boundaries );
		}
		
		for( Component child : canvas.getComponents() ){
			Rectangle boundaries = child.getBounds();
			result = union( result, boundaries );
		}
		
		if( result == null ){
			result = new Rectangle( 0, 0, 50, 50 );
		}
		return result;
	}
	
	private Rectangle union( Rectangle a, Rectangle b ){
		if( a == null ){
			return b;
		}
		if( b == null ){
			return a;
		}
		return a.union( b );
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
	public <T> List<T> getCapabilities( CapabilityName<T> name ) {
		if( graph == null ) {
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<>();
		for( GraphItem item : graph.getItems() ) {
			T capability = item.getCapability( name );
			if( capability != null ) {
				result.add( capability );
			}
		}
		return result;
	}

	/**
	 * Adds a new capability to this graph.
	 * @param name the name of the capability
	 * @param handler the new handler, may be <code>null</code> to remove an existing handler
	 */
	public <T> void setCapability( CapabilityName<T> name, CapabilityHandler<T> handler ) {
		capabilityController.register( name, handler );
	}

	private void regraph() {
		valid = false;
		if( EventQueue.isDispatchThread() ) {
			EventQueue.invokeLater( new Runnable() {
				@Override
				public void run() {
					if( !valid ) {
						resetGraph();
					}
				}
			} );
		}
	}

	private void resetGraph() {
		valid = true;

		for( Regraphable regraphable : regraphables ) {
			regraphable.regraphed();
		}

		repaint();
	}

	private class DefaultGraphSite implements GraphSite {
		@Override
		public void addItem( GraphItem item ) {
			if( graph == null ){
				throw new IllegalStateException( "cannot add items because there is no graph set" );
			}
			graph.addItem( item );
		}

		@Override
		public void removeItem( GraphItem item ) {
			if( graph == null ){
				throw new IllegalStateException( "cannot remove items because there is no graph set" );
			}
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
		
		@Override
		public void repaint() {
			GraphPanel.this.repaint();
		}
	}
}
