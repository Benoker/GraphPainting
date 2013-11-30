package graph.uml.intern;

import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.Diamond;
import graph.items.uml.OpenArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.Connection;

import java.awt.Color;

/**
 * Describes a composition between two types
 * @author Benjamin Sigg
 */
public class CompositionConnection extends DefaultConnection implements Connection{
	public CompositionConnection( Graph graph, ConnectionArray source, ConnectionArray target ){
		super( graph, source, target );
		
		CuttingEdgeLineConnection line = new CuttingEdgeLineConnection( source, target );
		addChild( line );
		
		Diamond diamond = new Diamond( line.getTargetPoint(), Color.BLACK );
		addChild( diamond );
		
		OpenArrow arrow = new OpenArrow( line.getSourceEndPoint() );
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
