package graph.uml.intern;

import graph.ui.Graph;
import graph.uml.TypeBox;
import graph.uml.UmlDiagram;

/**
 * Default implementation of {@link UmlDiagram}.
 * @author Benjamin Sigg
 */
public class DefaultUmlDiagram implements UmlDiagram{
	private Graph graph;

	/**
	 * Creates a new diagram
	 * @param graph the graph to which this diagram will add items
	 */
	public DefaultUmlDiagram( Graph graph ){
		this.graph = graph;
	}
	
	@Override
	public TypeBox createType() {
		return new DefaultTypeBox( graph );
	}

}
