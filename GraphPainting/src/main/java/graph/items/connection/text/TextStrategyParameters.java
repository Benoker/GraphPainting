package graph.items.connection.text;

import java.awt.FontMetrics;
import java.awt.font.LineMetrics;

import graph.util.EnhancedPath2D;

/**
 * Parameters that can be helpful when calculating the properties of a {@link ConfigurableConnectionText}.
 * @author Benjamin Sigg
 */
public interface TextStrategyParameters {
	/**
	 * The path which is followed by the text.
	 * @return the path
	 */
	public EnhancedPath2D getPath();
	
	/**
	 * Gets the position that is or was calcualted by the {@link TextPositionStrategy}.
	 * @return the position, or 0 if it is not yet calculated
	 */
	public double getPosition();
	
	/**
	 * Information about the font that is painted.
	 * @return detailed information about the font
	 */
	public FontMetrics getFontMetrics();
	
	/**
	 * Information about the line that is painted
	 * @return detailed information about the line
	 */
	public LineMetrics getLineMetrics();
}
