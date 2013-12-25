package graph.uml.io;

import graph.uml.CommentBox;
import graph.uml.ItemKey;

/**
 * Some data that describes a comment.
 * @author Benjamin Sigg
 */
public class CommentData extends BoxData<CommentBox> {
	private String text;

	public CommentData( ItemKey<CommentBox> key ) {
		super( key );
	}

	public String getText() {
		return text;
	}

	public void setText( String text ) {
		this.text = text;
	}
	
	@Override
	public void visit( DataVisitor visitor ) {
		visitor.visit( this );	
	}
}
