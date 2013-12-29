package graph.items.connection;

import graph.items.AbstractGraphItem;
import graph.items.PathedGraphConnection;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.util.EnhancedPath2D;
import graph.util.Geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Point2D;

/*
 *  *--------* (position)----------------------*
 *           |
 *           | (distance)
 *           |
 *        ---*---- (angle and shift)
 */

/**
 * A {@link AbstractConnectionText} can be attached to a {@link PathedGraphConnection} and paints some text
 * close to the path of the connection.
 * @author Benjamin Sigg
 */
public abstract class AbstractConnectionText extends AbstractGraphItem implements GraphPaintable {
	private String text;
	private float position = 0.5f;
	private Color backgroundColor = null;
	private Color foregroundColor = Color.BLACK;

	/**
	 * The connection to which this text is attached.
	 */
	private PathedGraphConnection connection;

	/**
	 * The path to which this text sticks. A value of <code>null</code> implies that the
	 * path is invalid and needs to be calculated again.
	 */
	private EnhancedPath2D path;

	private Regraphable regraphable;

	/**
	 * Creates a new text.
	 * @param connection the connection to which this text is attached
	 */
	public AbstractConnectionText( PathedGraphConnection connection ) {
		this.connection = connection;

		regraphable = new Regraphable() {
			@Override
			public void regraphed() {
				path = null;
			}
		};
	}

	@Override
	protected void addTo( GraphSite site ) {
		site.addRegraphable( regraphable );
		site.addPaintable( this );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		site.removeRegraphable( regraphable );
		site.removePaintable( this );
	}

	/**
	 * Gets the path to which this text is attached to.
	 * @return the path, not <code>null</code>
	 */
	protected EnhancedPath2D getPath() {
		if( path == null ) {
			path = new EnhancedPath2D( connection.getOpenConnectionPath() );
		}
		return path;
	}

	/**
	 * Gets a point at <code>position</code>.
	 * @return the point at <code>position</code>
	 */
	protected Point2D getTextPosition() {
		EnhancedPath2D path = getPath();
		return path.getPointAt( getPosition() );
	}

	/**
	 * Gets the center of the text. This is the <code>position + distance * normal</code>, where
	 * <code>normal</code> is the normal vector of the {@link #getPath() path} at <code>position</code>. 
	 * @return the center of the text
	 */
	protected Point2D getTextCenter() {
		Point2D base = getTextPosition();
		double distance = getDistance();

		if( distance == 0.0 ) {
			return base;
		}

		Point2D normal = path.getNormalAt( getPosition() );

		double x = base.getX() + distance * normal.getX();
		double y = base.getY() + distance * normal.getY();

		return new Point2D.Double( x, y );
	}

	@Override
	public void paint( Graphics2D g ) {
		char[] chars = text.toCharArray();
		Point2D center = getTextCenter();

		LineMetrics metrics = g.getFontMetrics().getLineMetrics( chars, 0, chars.length, g );
		int charsWidth = g.getFontMetrics().charsWidth( chars, 0, chars.length );
		double translate = charsWidth * getShift();

		g.rotate( getAngle(), center.getX(), center.getY() );
		g.translate( -translate, metrics.getAscent() / 2 );
		g.translate( center.getX(), center.getY() );

		if( backgroundColor != null ) {
			g.setColor( backgroundColor );
			g.fillRect( 0, -Geom.round( metrics.getAscent() ), charsWidth, Geom.round( metrics.getHeight() ) );
		}

		g.setColor( foregroundColor );
		g.drawChars( chars, 0, chars.length, 0, 0 );
	}

	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}

	/**
	 * Sets the position of the text along the path. 
	 * @param position the position, a value between <code>0</code> and <code>1</code>
	 */
	public void setPosition( float position ) {
		if( position < 0 || position > 1 ) {
			throw new IllegalArgumentException( "position out of bounds: " + position );
		}
		this.position = position;
		regraph();
	}

	/**
	 * Gets the position of the text.
	 * @return the position, a value between <code>0</code> and <code>1</code>
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * Sets the text that should be displayed.
	 * @param text the text to show
	 */
	public void setText( String text ) {
		this.text = text;
		regraph();
	}

	/**
	 * Gets the text that is displayed.
	 * @return the displayed text, may be <code>null</code>
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the {@link Color} with which to paint the background of the text.
	 * @param backgroundColor the background, can be <code>null</code> if the text should
	 * be transparent
	 */
	public void setBackgroundColor( Color backgroundColor ) {
		this.backgroundColor = backgroundColor;
		regraph();
	}

	/**
	 * Gets the background color of this text.
	 * @return the background color or <code>null</code>
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the color of the text.
	 * @param foregroundColor the color of the text, not <code>null</code>
	 */
	public void setForegroundColor( Color foregroundColor ) {
		if( foregroundColor == null ) {
			throw new IllegalArgumentException( "foregroundColor must not be null" );
		}
		this.foregroundColor = foregroundColor;
		regraph();
	}

	/**
	 * Gets the color of the text.
	 * @return the color of the text
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Gets the angle of the text, where a value of <code>0</code> means that the text is
	 * displayed horizontally, a value of {@link Math#PI} means the text is on its head,
	 * and the rotation is measured clockwise. 
	 * @return the angle of the text
	 */
	protected abstract double getAngle();

	/**
	 * Gets the distance from <code>position</code> along a normal vector to the path until
	 * reaching the center of the text. Whether the normal vector points "upwards" or "downwards"
	 * depends on the direction in which the connection points. If the connection is traveling from
	 * "left" to "right", then a positive distance means that the text is above the connection.
	 * @return the distance
	 */
	protected abstract double getDistance();

	/**
	 * Gets the shifting of text. A value of <code>0.5</code> means that the center of the
	 * text hovers over <code>position</code>. A value of <code>0.0</code> means that the 
	 * beginning of the first character hovers at <code>position</code>, and a value of
	 * <code>1.0</code> means that the end of the last character hovers at <code>position</code>.
	 * @return the shifting, should be between <code>0.0</code> and <code>1.0</code>
	 */
	protected abstract double getShift();
}
