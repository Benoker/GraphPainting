package graph.uml;

/**
 * A {@link CommentBox} is some kind of text that is attached to another box
 * @author Benjamin Sigg
 */
public interface CommentBox extends Box{
	@Override
	public ItemKey<CommentBox> getKey();
	
	/**
	 * Gets the text that is written in this box.
	 * @return the text
	 */
	public String getText();
}
