package graph.uml.intern;

import graph.items.ConnectionableCapability;
import graph.items.capability.ConnectionableCapabilityHandler;
import graph.model.capability.CapabilityName;
import graph.ui.GraphPanel;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramTools;
import graph.uml.UmlDiagramView;

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
}
