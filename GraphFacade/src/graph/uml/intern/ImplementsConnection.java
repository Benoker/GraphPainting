package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.FilledArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.model.connection.GraphConnection;
import graph.uml.Connection;

import java.awt.BasicStroke;

/**
 * A connection between a class that implements an interface.
 * @author Benjamin Sigg
 */
public class ImplementsConnection extends AbstractConnection implements Connection{
	public static ConnectionFlavor IMPLEMENTS = new ConnectionFlavor( "implements" );
	private CuttingEdgeLineConnection line;
	
	public ImplementsConnection( DefaultUmlDiagram diagram, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( diagram, sourceBox, targetBox );
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}
	
	public ImplementsConnection( DefaultUmlDiagram diagram ){
		super( diagram, null, null );
		initLine();
	}
	
	private void initLine(){
		line = new CuttingEdgeLineConnection();
		
		float[] dash = {8.0f};
		line.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f ) );
		addChild( line );
		
		FilledArrow arrow = new FilledArrow( line.getTargetEndPoint() );
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
	
	@Override
	public GraphConnection getGraphConnection() {
		return line;
	}
}
