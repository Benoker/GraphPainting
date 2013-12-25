package graph.uml.io;

import graph.uml.CommentBox;
import graph.uml.ItemKey;
import graph.uml.intern.DefaultCommentBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * Converter for {@link DefaultCommentBox}es.
 * @author Benjamin Sigg
 */
public class CommentDataConverter extends BoxDataConverter<CommentBox, CommentData, DefaultCommentBox> {

	@Override
	public CommentData toData( DefaultCommentBox item ) {
		CommentData data = super.toData( item );
		data.setText( item.getText() );
		return data;
	}
	
	@Override
	public DefaultCommentBox toItem( CommentData data, DefaultUmlDiagram diagram ) {
		DefaultCommentBox item = super.toItem( data, diagram );
		item.setText( data.getText() );
		return item;
	}
	
	@Override
	protected CommentData createEmptyData( ItemKey<CommentBox> key ) {
		return new CommentData( key );
	}

	@Override
	protected DefaultCommentBox createEmptyBox( ItemKey<CommentBox> key, DefaultUmlDiagram diagram ) {
		return new DefaultCommentBox( diagram, null, key );
	}
}
