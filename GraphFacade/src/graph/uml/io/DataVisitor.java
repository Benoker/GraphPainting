package graph.uml.io;

/**
 * Visitor for visiting different subclasses of {@link Data}.
 * @author Benjamin Sigg
 */
public interface DataVisitor {
	public void visit( UmlDiagramData diagram );
	
	public void visit( ConnectionData connection );
	
	public void visit( CommentData comment );
	
	public void visit( TypeData type );
}
