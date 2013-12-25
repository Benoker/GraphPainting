package graph.uml.intern;

import graph.items.ConnectionableCapability;
import graph.items.capability.ConnectionableCapabilityHandler;
import graph.items.capability.OpenEndedLineConnectionStrategy;
import graph.model.capability.CapabilityName;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;
import graph.uml.UmlDiagramTools;
import graph.uml.intern.tools.AbstractUmlConnectionFactory;
import graph.uml.intern.tools.AggregationConnectionFactory;
import graph.uml.intern.tools.CompositionConnectionFactory;
import graph.uml.intern.tools.ExtendsConnectionFactory;
import graph.uml.intern.tools.ImplementsConnectionFactory;
import graph.uml.intern.tools.UmlConnectionFactory;
import graph.uml.intern.tools.UmlConnectionableCapability;

/**
 * Default tools for {@link DefaultUmlDiagram}s.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagramTools implements UmlDiagramTools {
	private DefaultUmlDiagramView view;
	private ConnectionableCapabilityHandler connecting;
	private AbstractUmlConnectionFactory factory;

	/**
	 * Creates a new set of tools applied to <code>diagram</code>.
	 * @param diagram the diagram to which the tools will be applied
	 */
	public DefaultUmlDiagramTools( DefaultUmlDiagramView diagram ) {
		this.view = diagram;

		connecting = new ConnectionableCapabilityHandler() {
			@Override
			protected void connected( GraphConnection connection, ConnectionableCapability sourceItem, ConnectionableCapability targetItem ) {
				UmlConnectionFactory factory = (UmlConnectionFactory) getFactory();
				UmlConnectionableCapability source = (UmlConnectionableCapability) sourceItem;
				UmlConnectionableCapability target = (UmlConnectionableCapability) targetItem;

				factory.finalize( (Connection) connection, source.getBox(), target.getBox() );
			}
		};
		connecting.setOpenEnded( new OpenEndedLineConnectionStrategy() );
		view.setCapability( CapabilityName.CONNECTABLE, connecting );
	}

	@Override
	public void applyAddInheritsFromTool() {
		setFactory( new ExtendsConnectionFactory() );
	}

	@Override
	public void applyAddImplementsFromTool() {
		setFactory( new ImplementsConnectionFactory() );
	}

	@Override
	public void applyAggregationTool() {
		setFactory( new AggregationConnectionFactory() );
	}

	@Override
	public void applyCompositionTool() {
		setFactory( new CompositionConnectionFactory() );
	}

	@Override
	public void applyDefaultTool() {
		setFactory( null );
	}
	
	public void reapply(){
		if( factory != null ){
			factory.setDiagram( view.getDiagram() );
		}
	}

	private void setFactory( AbstractUmlConnectionFactory factory ) {
		this.factory = factory;
		if( factory != null ) {
			factory.setDiagram( view.getDiagram() );
		}
		connecting.setFactory( factory );
	}
}
