package graph.uml.intern;

import graph.ui.Graph;
import graph.uml.TypeBox;
import graph.uml.UmlDiagram;
import graph.uml.UmlDiagramTools;

/**
 * Default implementation of {@link UmlDiagram}.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagram implements UmlDiagram{
	private Graph graph;
	private UmlDiagramTools tools;

	/**
	 * Creates a new diagram
	 * @param graph the graph to which this diagram will add items
	 */
	public DefaultUmlDiagram( Graph graph ){
		this.graph = graph;
		tools = new DefaultUmlDiagramTools( this );
	}
	
	/**
	 * Gets the graph which is painting the items.
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}
	
	@Override
	public UmlDiagramTools getTools() {
		return tools;
	}
	
	@Override
	public TypeBox createType() {
		DefaultTypeBox result = new DefaultTypeBox( graph );
		result.makeVisible();
		return result;
	}
}
