package graph.uml.intern;

import graph.items.ConnectionableCapability;
import graph.items.capability.ConnectionableCapabilityHandler;
import graph.items.capability.OpenEndedLineConnectionStrategy;
import graph.model.capability.CapabilityName;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;
import graph.uml.UmlDiagramTools;
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
public class DefaultUmlDiagramTools implements UmlDiagramTools{
	private DefaultUmlDiagram diagram;
	private ConnectionableCapabilityHandler connecting;
	
	/**
	 * Creates a new set of tools applied to <code>diagram</code>.
	 * @param diagram the diagram to which the tools will be applied
	 */
	public DefaultUmlDiagramTools( DefaultUmlDiagram diagram ){
		this.diagram = diagram;
		
		connecting = new ConnectionableCapabilityHandler(){
			@Override
			protected void connected( GraphConnection connection, ConnectionableCapability sourceItem, ConnectionableCapability targetItem ) {
				UmlConnectionFactory factory = (UmlConnectionFactory)getFactory();
				UmlConnectionableCapability source = (UmlConnectionableCapability)sourceItem;
				UmlConnectionableCapability target = (UmlConnectionableCapability)targetItem;
				
				factory.finalize( (Connection)connection, source.getBox(), target.getBox() );
			}
		};
		connecting.setOpenEnded( new OpenEndedLineConnectionStrategy() );
		diagram.getGraph().setCapability( CapabilityName.CONNECTABLE, connecting );
	}
	
	@Override
	public void applyAddInheritsFromTool() {
		connecting.setFactory( new ExtendsConnectionFactory( diagram ) );
	}
	
	@Override
	public void applyAddImplementsFromTool() {
		connecting.setFactory( new ImplementsConnectionFactory( diagram ) );	
	}
	
	@Override
	public void applyAggregationTool() {
		connecting.setFactory( new AggregationConnectionFactory( diagram ) );
	}
	
	@Override
	public void applyCompositionTool() {
		connecting.setFactory( new CompositionConnectionFactory( diagram ) );
	}
	
	@Override
	public void applyDefaultTool() {
		connecting.setFactory( null );	
	}
}
