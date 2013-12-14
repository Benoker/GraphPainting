package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.Diamond;
import graph.items.uml.OpenArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;

import java.awt.Color;

/**
 * Describes a composition between two types
 * @author Benjamin Sigg
 */
public class CompositionConnection extends AbstractConnection implements Connection{
	public static final ConnectionFlavor COMPOSITION = new ConnectionFlavor( "composition" );
	private CuttingEdgeLineConnection line;
	
	public CompositionConnection( DefaultUmlDiagram diagram, DefaultBox sourceBox, ConnectionArray source, DefaultBox targetBox, ConnectionArray target ){
		super( diagram, sourceBox, targetBox );
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}
	
	public CompositionConnection( DefaultUmlDiagram diagram ){
		super( diagram, null, null );
		initLine();
	}
	
	private void initLine(){
		line = new CuttingEdgeLineConnection();
		addChild( line );
		
		Diamond diamond = new Diamond( line.getTargetEndPoint(), Color.BLACK );
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
	
	@Override
	public PathedGraphConnection getGraphConnection() {
		return line;
	}
}
