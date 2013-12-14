package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.FilledArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;

/**
 * A connection describing inheritance.
 * @author Benjamin Sigg
 */
public class ExtendsConnection extends AbstractConnection implements Connection{
	public static ConnectionFlavor EXTENDS = new ConnectionFlavor( "extends" );
	private CuttingEdgeLineConnection line;
	
	public ExtendsConnection( DefaultUmlDiagram diagram, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( diagram, sourceBox, targetBox );
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}
	
	public ExtendsConnection( DefaultUmlDiagram diagram ){
		super( diagram, null, null );
		initLine();
	}
	
	private void initLine(){
		line = new CuttingEdgeLineConnection();
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
	public PathedGraphConnection getGraphConnection() {
		return line;
	}
}
