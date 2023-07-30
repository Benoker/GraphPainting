package graph.uml.intern;

import graph.items.ConnectionableCapability;
import graph.items.capability.ConnectionableCapabilityHandler;
import graph.model.capability.CapabilityName;
import graph.ui.GraphPanel;
import graph.uml.Item;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramTools;
import graph.uml.UmlDiagramView;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * A view showing {@link DefaultUmlDiagram}s.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagramView implements UmlDiagramView {
	private DefaultUmlDiagramRepository repository;
	private GraphPanel panel;
	private DefaultUmlDiagramTools tools;
	private DefaultUmlDiagram diagram;

	public DefaultUmlDiagramView( DefaultUmlDiagramRepository repository ) {
		this.repository = repository;
		panel = new GraphPanel();
		tools = new DefaultUmlDiagramTools( this );
	}

	@Override
	public void setDiagram( UmlDiagram diagram ) {
		if( diagram == null ) {
			panel.setGraph( null );
			this.diagram = null;
		} else {
			this.diagram = repository.toDefault( diagram );
			panel.setGraph( this.diagram.getGraph() );
		}
		tools.reapply();
	}

	@Override
	public JComponent getComponent() {
		return panel;
	}

	@Override
	public UmlDiagramTools getTools() {
		return tools;
	}

	public void setCapability( CapabilityName<ConnectionableCapability> name, ConnectionableCapabilityHandler handler ) {
		panel.setCapability( name, handler );
	}
	
	public DefaultUmlDiagram getDiagram() {
		return diagram;
	}
	
	@Override
	public BufferedImage paintImage() {
		return paintImage( BufferedImage.TYPE_INT_ARGB );	
	}
	
	@Override
	public BufferedImage paintImage( int imageType ) {
		List<Item> selection = new ArrayList<>( diagram.getSelectedItems() );
		diagram.setSelectedItem( null );
		try{
			BufferedImage image = new BufferedImage( 1, 1, imageType );
			Graphics g = image.createGraphics();
			Rectangle bounds = panel.getVisibleBoundaries( g );
			g.dispose();
			
			image = new BufferedImage( bounds.width+10, bounds.height+10, imageType );
			g = image.createGraphics();
			g.translate( -bounds.x+5, -bounds.y+5 );
			panel.print( g );
			g.dispose();
			return image;
		}
		finally{
			diagram.setSelectedItems( selection );
		}
	}
}
