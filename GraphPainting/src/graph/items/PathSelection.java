package graph.items;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.Selection;
import graph.model.capability.SelectableCapability;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;

/**
 * Allows selecting a {@link PathedGraphConnection}. This class is also paints some minor 
 * graphical effects around the selected connection.
 * @author Benjamin Sigg
 */
public class PathSelection extends AbstractGraphItem implements SelectableCapability, GraphPaintable {
	private PathedGraphConnection connection;
	private Selection selection = Selection.NOT_SELECTED;
	private int precision = 10;

	public PathSelection( PathedGraphConnection connection ) {
		this.connection = connection;
	}

	/**
	 * Gets the connection which is selectable because of <code>this</code>.
	 * @return the connection
	 */
	public PathedGraphConnection getConnection() {
		return connection;
	}

	/**
	 * Sets how far away the mouse can be from the connection and still be considered a hit.
	 * @param precision the maximal distance of the mouse to the connection
	 */
	public void setPrecision( int precision ) {
		this.precision = precision;
	}

	public int getPrecision() {
		return precision;
	}

	@Override
	protected void addTo( GraphSite site ) {
		site.addPaintable( this );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		site.removePaintable( this );
	}

	@Override
	public void setSelected( Selection selection ) {
		this.selection = selection;
		regraph();
	}

	@Override
	public Selection getSelected() {
		return selection;
	}

	@Override
	public float contains( int x, int y ) {
		Path2D path = connection.getClosedConnectionPath();
		if( path == null ) {
			return 0.f;
		}
		for( int i = 0; i <= precision; i++ ) {
			int delta = 2 * i + 1;
			if( path.intersects( x - i, y - i, delta, delta ) ) {
				return (precision + 1.f - i) / (precision + 1.f);
			}
		}
		return 0.f;
	}

	@Override
	public void paint( Graphics2D g ) {
		// ignore	
	}

	@Override
	public void paintOverlay( Graphics2D g ) {
		if( selection.isSelected() ) {
			Color color = Color.GRAY;
			if( selection.isPrimary() ) {
				color = Color.GREEN;
			}

			Point p1 = connection.getSourceEndPoint().getAttachement().getLanding();
			Point p2 = connection.getTargetEndPoint().getAttachement().getLanding();

			g.setColor( color );
			g.fillOval( p1.x - 3, p1.y - 3, 7, 7 );
			g.fillOval( p2.x - 3, p2.y - 3, 7, 7 );

			g.setColor( Color.BLACK );
			g.drawOval( p1.x - 3, p1.y - 3, 7, 7 );
			g.drawOval( p2.x - 3, p2.y - 3, 7, 7 );
		}
	}
}
