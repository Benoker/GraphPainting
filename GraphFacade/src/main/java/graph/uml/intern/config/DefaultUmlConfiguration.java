package graph.uml.intern.config;

import graph.uml.config.ConnectionConfiguration.PathShape;
import graph.uml.config.EndPointConfiguration.Decoration;
import graph.uml.config.EndPointConfiguration.Direction;
import graph.uml.config.UmlConfiguration;

public class DefaultUmlConfiguration implements UmlConfiguration {
	private DefaultConnectionConfiguration implementation = new DefaultConnectionConfiguration();
	private DefaultConnectionConfiguration inheritance = new DefaultConnectionConfiguration();
	private DefaultConnectionConfiguration assoziation = new DefaultConnectionConfiguration();
	private DefaultConnectionConfiguration composition = new DefaultConnectionConfiguration();
	private DefaultConnectionConfiguration aggregation = new DefaultConnectionConfiguration();
	private DefaultConnectionConfiguration comment = new DefaultConnectionConfiguration();

	public DefaultUmlConfiguration(){
		initImplementation();
		initInheritance();

		initAssoziation();
		initAggregation();
		initComposition();
		
		initComment();
	}
	
	private void initImplementation(){
		implementation.setPathShape( PathShape.DIRECT );
		implementation.withDottedLine();
		implementation.source().setDirection( Direction.DIRECT );
		implementation.target().setDirection( Direction.DIRECT );
		implementation.target().setDecoration( Decoration.CLOSE_ARROW );		
	}
	
	private void initInheritance(){
		inheritance.setPathShape( PathShape.DIRECT );
		inheritance.source().setDirection( Direction.DIRECT );
		inheritance.target().setDirection( Direction.DIRECT );
		inheritance.target().setDecoration( Decoration.CLOSE_ARROW);		
	}
	
	private void initAssoziation(){
		assoziation.setPathShape( PathShape.EDGED );
		assoziation.source().setDirection( Direction.ORTHOGONAL );
		assoziation.source().setDecoration( Decoration.NONE );
		assoziation.target().setDirection( Direction.ORTHOGONAL );
		assoziation.target().setDecoration( Decoration.OPEN_ARROW );
	}
	
	private void initComposition(){
		composition.setPathShape( PathShape.EDGED );
		composition.source().setDirection( Direction.ORTHOGONAL );
		composition.source().setDecoration( Decoration.FILLED_DIAMOND );
		composition.target().setDirection( Direction.ORTHOGONAL );
		composition.target().setDecoration( Decoration.OPEN_ARROW );
	}
	
	private void initAggregation(){
		aggregation.setPathShape( PathShape.EDGED );
		aggregation.source().setDirection( Direction.ORTHOGONAL );
		aggregation.source().setDecoration( Decoration.DIAMOND );
		aggregation.target().setDirection( Direction.ORTHOGONAL );
		aggregation.target().setDecoration( Decoration.OPEN_ARROW );
	}
	
	private void initComment(){
		comment.setPathShape( PathShape.DIRECT );
		comment.withDottedLine();
		comment.source().setDirection( Direction.DIRECT );
		comment.target().setDirection( Direction.DIRECT );
	}
			
	
	@Override
	public DefaultConnectionConfiguration getImplementation() {
		return implementation;
	}

	@Override
	public DefaultConnectionConfiguration getInheritance() {
		return inheritance;
	}

	@Override
	public DefaultConnectionConfiguration getAssoziation() {
		return assoziation;
	}
	
	@Override
	public DefaultConnectionConfiguration getComposition() {
		return composition;
	}

	@Override
	public DefaultConnectionConfiguration getAggregation() {
		return aggregation;
	}

	@Override
	public DefaultConnectionConfiguration getComment() {
		return comment;
	}

}
