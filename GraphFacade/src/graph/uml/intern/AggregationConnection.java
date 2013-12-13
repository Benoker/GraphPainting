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
 * Describes an aggregation between two types
 * @author Benjamin Sigg
 */
public class AggregationConnection extends DefaultConnection implements Connection{
	public AggregationConnection( Graph graph, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( graph, sourceBox, source, targetBox, target );
		
		CuttingEdgeLineConnection line = new CuttingEdgeLineConnection();
		addChild( line );
		
		Diamond diamond = new Diamond( line.getTargetEndPoint(), Color.WHITE );
		addChild( diamond );
		
		OpenArrow arrow = new OpenArrow( line.getSourceEndPoint() );
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
