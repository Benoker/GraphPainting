package graph.uml.intern;

import graph.items.connection.DirectLineConnection;
import graph.items.connection.UndirectedEndPoint;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.Connection;

import java.awt.BasicStroke;

public class CommentConnection extends DefaultConnection implements Connection{
	public CommentConnection( Graph graph, ConnectionArray source, ConnectionArray target ){
		super( graph, source, target );
		
		DirectLineConnection line = new DirectLineConnection();
		float[] dash = {8.0f};
		line.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f ) );
		addChild( line );
		
		line.setSourcePoint( new UndirectedEndPoint() );
		line.setTargetPoint( new UndirectedEndPoint() );
		
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
