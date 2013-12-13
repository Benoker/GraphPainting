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
	public ExtendsConnection( Graph graph, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( graph, sourceBox, source, targetBox, target );
		
		CuttingEdgeLineConnection line = new CuttingEdgeLineConnection();
		addChild( line );
		
		FilledArrow arrow = new FilledArrow( line.getTargetEndPoint() );
		addChild( arrow );
		
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
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
