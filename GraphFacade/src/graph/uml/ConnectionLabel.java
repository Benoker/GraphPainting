package graph.uml;


/**
 * A piece of text that is attached to a {@link Connection}.
 * @author Benjamin Sigg
 */
public interface ConnectionLabel extends Item {
	/**
	 * Gets the connection to which this item is attached.
	 * @return the connection, not <code>null</code>
	 */
	public Connection getConnection();
	
	/**
	 * Sets the text that should be shown on this label. Note that HTML is <b>not</b> supported.
	 * @param text the text to show, can be empty or <code>null</code>
	 */
	public void setText( String text );

	/**
	 * Gets the text that is shown on this label.
	 * @return the text, may be empty
	 */
	public String getText();
	
	/**
	 * Sets position, angle and other properties of this label.
	 * @param configuration the position, angle and other properties, not <code>null</code>
	 */
	public void setConfiguration( ConnectionLabelConfiguration configuration );
	
	/**
	 * Gets the position, angle and other properties of this label.
	 * @return the position, angle and other properties, not <code>null</code>
	 */
	public ConnectionLabelConfiguration getConfiguration();
	
	@Override
	public ItemKey<ConnectionLabel> getKey();
}
