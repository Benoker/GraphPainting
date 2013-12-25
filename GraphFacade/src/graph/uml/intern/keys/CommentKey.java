package graph.uml.intern.keys;

import graph.uml.CommentBox;
import graph.uml.intern.DefaultCommentBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key representing a {@link DefaultCommentBox}.
 * @author Benjamin Sigg
 */
public class CommentKey extends DefaultItemKey<CommentBox>{
	public CommentKey( DefaultUmlDiagram diagram ) {
		super( diagram, "comment" );
	}
}
