package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.items.ConnectionableCapability;
import graph.model.connection.ConnectionArray;
import graph.uml.CommentBox;
import graph.uml.intern.CommentConnection;
import graph.uml.intern.DefaultCommentBox;

/**
 * The capability allowing {@link CommentBox}es to be connected to other boxes
 * @author Benjamin Sigg
 */
public class CommentConnectionableCapability extends UmlConnectionableCapability {
	private DefaultCommentBox box;

	public CommentConnectionableCapability( DefaultCommentBox box ) {
		super( box );
		this.box = box;
	}

	private boolean isComment( ConnectionFlavor flavor ) {
		return flavor.equals( CommentConnection.COMMENT );
	}

	@Override
	public boolean isSource( ConnectionFlavor flavor ) {
		return isComment( flavor );
	}

	@Override
	public boolean isTarget( ConnectionFlavor flavor ) {
		return isComment( flavor );
	}

	@Override
	public boolean allowSource( ConnectionableCapability item, ConnectionFlavor flavor ) {
		if( item == this ){
			return false;
		}
		return isComment( flavor );
	}

	@Override
	public boolean allowTarget( ConnectionableCapability item, ConnectionFlavor flavor ) {
		if( item == this ){
			return false;
		}
		return isComment( flavor );
	}

	@Override
	public ConnectionArray getSourceArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		return box.getConnectionArray();
	}

	@Override
	public ConnectionArray getSourceArray( ConnectionFlavor flavor ) {
		return box.getConnectionArray();
	}

	@Override
	public ConnectionArray getTargetArray( int x, int y, ConnectionFlavor flavor, boolean constructing ) {
		return box.getConnectionArray();
	}

	@Override
	public ConnectionArray getTargetArray( ConnectionFlavor flavor ) {
		return box.getConnectionArray();
	}
}
