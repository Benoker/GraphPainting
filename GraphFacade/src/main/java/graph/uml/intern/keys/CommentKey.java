package graph.uml.intern.keys;

import graph.uml.CommentBox;
import graph.uml.intern.DefaultCommentBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key representing a {@link DefaultCommentBox}.
 * @author Benjamin Sigg
 */
public class CommentKey extends DefaultItemKey<CommentBox>{
	private static final String COMMENT = "comment";
	
	public static boolean isKey( String uniqueString ){
		return isKey( uniqueString, COMMENT );
	}
	
	public static CommentKey readKey( String uniqueString ){
		return new CommentKey( uniqueString );
	}
	
	public CommentKey( DefaultUmlDiagram diagram ) {
		super( diagram, COMMENT );
	}
	
	private CommentKey( String uniqueString ){
		super( uniqueString, COMMENT );
	}
}
