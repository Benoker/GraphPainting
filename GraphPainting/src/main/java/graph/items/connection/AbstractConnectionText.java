package graph.items.connection;

import graph.items.AbstractGraphItem;
import graph.items.PathedGraphConnection;
import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.util.EnhancedPath2D;
import graph.util.Geom;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
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
	 * @param parameters information about the text and the path
	 * @return the point at <code>position</code>
	 */
	protected Point2D getTextPosition( Parameters parameters ) {
		EnhancedPath2D path = getPath();
		return path.getPointAt( getPosition( parameters ) );
	}

	/**
	 * Gets the center of the text. This is the <code>position + distance * normal</code>, where
	 * <code>normal</code> is the normal vector of the {@link #getPath() path} at <code>position</code>.
	 * @param parameters information about the text and the path 
	 * @return the center of the text
	 */
	protected Point2D getTextCenter( Parameters parameters ) {
		Point2D base = getTextPosition( parameters );
		double distance = getDistance( parameters );

		if( distance == 0.0 ) {
			return base;
		}

		Point2D normal = path.getNormalAt( getPosition(parameters) );

		double x = base.getX() + distance * normal.getX();
		double y = base.getY() + distance * normal.getY();

		return new Point2D.Double( x, y );
	}

	@Override
	public void paint( Graphics2D g ) {
		char[] chars = text.toCharArray();
		FontMetrics fontMetrics = g.getFontMetrics();
		LineMetrics lineMetrics = fontMetrics.getLineMetrics( chars, 0, chars.length, g );
		Parameters parameters = new Parameters( chars, fontMetrics, lineMetrics );
		paint(g, parameters);
	}
	
	protected void paint( Graphics2D g, Parameters parameters ){
		LineMetrics lineMetrics = parameters.getLineMetrics();
		char[] chars = parameters.getText();
		
		Point2D center = getTextCenter(parameters);
		double translate = parameters.getCharsWidth() * getShift(parameters);

		g.rotate( getAngle(parameters), center.getX(), center.getY() );
		g.translate( -translate, lineMetrics.getAscent() / 2 );
		g.translate( center.getX(), center.getY() );

		if( backgroundColor != null ) {
			g.setColor( backgroundColor );
			g.fillRect( 0, -Geom.round( lineMetrics.getAscent() ), parameters.getCharsWidth(), Geom.round( lineMetrics.getHeight() ) );
		}

		g.setColor( foregroundColor );
		g.drawChars( chars, 0, chars.length, 0, 0 );
	}

	@Override
	public void paintOverlay( Graphics2D g ) {
		// ignore	
	}
	
	@Override
	public Rectangle getVisibleBoundaries( Graphics g ) {
		char[] chars = text.toCharArray();
		FontMetrics fontMetrics = g.getFontMetrics();
		LineMetrics lineMetrics = fontMetrics.getLineMetrics( chars, 0, chars.length, g );
		Parameters parameters = new Parameters( chars, fontMetrics, lineMetrics );
	
		Point2D center = getTextCenter(parameters);
		double translate = parameters.getCharsWidth() * getShift(parameters);

		AffineTransform transformation = new AffineTransform();
		
		transformation.rotate( getAngle(parameters), center.getX(), center.getY() );
		transformation.translate( -translate, lineMetrics.getAscent() / 2 );
		transformation.translate( center.getX(), center.getY() );

		int x = 0;
		int y = -Geom.round( lineMetrics.getAscent() );
		int w = parameters.getCharsWidth();
		int h = Geom.round( lineMetrics.getHeight() );
		
		
		double[] pts = {
			x, y,
			x, y+h,
			x+w, y,
			x+w, y+h
		};
		transformation.transform( pts, 0, pts, 0, 4 );
		
		double minx = Math.min( Math.min( pts[0], pts[2] ), Math.min( pts[4], pts[6] ) );
		double maxx = Math.max( Math.max( pts[0], pts[2] ), Math.max( pts[4], pts[6] ) );
		double miny = Math.min( Math.min( pts[1], pts[3] ), Math.min( pts[5], pts[7] ) );
		double maxy = Math.max( Math.max( pts[1], pts[3] ), Math.max( pts[5], pts[7] ) );
		
		return new Rectangle( Geom.round( minx ), Geom.round( miny ), Geom.round( maxx-minx ), Geom.round( maxy-miny ));
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
	 * Gets the position on the path, starting with <code>0</code> to <code>1</code>.
	 * @param parameters some information about the text and the path
	 * @return the position on the path
	 */
	protected abstract double getPosition( Parameters parameters );

	/**
	 * Gets the angle of the text, where a value of <code>0</code> means that the text is
	 * displayed horizontally, a value of {@link Math#PI} means the text is on its head,
	 * and the rotation is measured clockwise.
	 * @param parameters some information about the text and the path 
	 * @return the angle of the text
	 */
	protected abstract double getAngle( Parameters parameters );

	/**
	 * Gets the distance from <code>position</code> along a normal vector to the path until
	 * reaching the center of the text. Whether the normal vector points "upwards" or "downwards"
	 * depends on the direction in which the connection points. If the connection is traveling from
	 * "left" to "right", then a positive distance means that the text is above the connection.
	 * @param parameters some information about the text and the path
	 * @return the distance
	 */
	protected abstract double getDistance( Parameters parameters );

	/**
	 * Gets the shifting of text. A value of <code>0.5</code> means that the center of the
	 * text hovers over <code>position</code>. A value of <code>0.0</code> means that the 
	 * beginning of the first character hovers at <code>position</code>, and a value of
	 * <code>1.0</code> means that the end of the last character hovers at <code>position</code>.
	 * @param parameters some information about the text and the path
	 * @return the shifting, should be between <code>0.0</code> and <code>1.0</code>
	 */
	protected abstract double getShift( Parameters parameters );

	protected class Parameters {
		private char[] text;
		private LineMetrics lineMetrics;
		private FontMetrics fontMetrics;
		private int charsWidth = -1;

		public Parameters( char[] text, FontMetrics fontMetrics, LineMetrics lineMetrics ) {
			this.text = text;
			this.lineMetrics = lineMetrics;
			this.fontMetrics = fontMetrics;
		}
		
		public EnhancedPath2D getPath(){
			return AbstractConnectionText.this.getPath();
		}

		public char[] getText() {
			return text;
		}

		public LineMetrics getLineMetrics() {
			return lineMetrics;
		}

		public FontMetrics getFontMetrics() {
			return fontMetrics;
		}

		public int getCharsWidth() {
			if( charsWidth == -1 ) {
				charsWidth = fontMetrics.charsWidth( text, 0, text.length );
			}
			return charsWidth;
		}
	}
}
