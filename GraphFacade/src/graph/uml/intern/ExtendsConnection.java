package graph.uml.intern;

import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.FilledArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.Connection;

/**
 * A connection describing inheritance.
 * @author Benjamin Sigg
 */
public class ExtendsConnection extends DefaultConnection implements Connection{
	public ExtendsConnection( Graph graph, ConnectionArray source, ConnectionArray target ){
		super( graph, source, target );
		
		CuttingEdgeLineConnection line = new CuttingEdgeLineConnection( source, target );
		addChild( line );
		
		FilledArrow arrow = new FilledArrow( line.getTargetPoint() );
		addChild( arrow );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}
}
