package graph.uml.intern;

import graph.items.connection.DirectLineConnection;
import graph.items.connection.UndirectedEndPoint;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.Connection;

import java.awt.BasicStroke;

public class CommentConnection extends AbstractConnection implements Connection{
	private DirectLineConnection line;
	
	public CommentConnection( Graph graph, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( graph, sourceBox, targetBox );
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}
	
	public CommentConnection( Graph graph ){
		super( graph, null, null );
		initLine();
	}
	
	private void initLine(){
		line = new DirectLineConnection();
		float[] dash = {8.0f};
		line.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f ) );
		addChild( line );
		
		line.setSourcePoint( new UndirectedEndPoint() );
		line.setTargetPoint( new UndirectedEndPoint() );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}
	
	@Override
	public GraphConnection getGraphConnection() {
		return line;
	}
}
