package graph.items.uml;

import graph.model.connection.EndPoint;
import graph.model.connection.EndPointPaintable;

import java.awt.Color;
import java.awt.Graphics2D;

public class FilledArrow extends EndPointPaintable {
	private int size = 20;
	private Color color;

	public FilledArrow( EndPoint endPoint ) {
		this( endPoint, Color.WHITE );
	}

	public FilledArrow( EndPoint endPoint, Color color ) {
		super( endPoint );
		setColor( color );
	}

	public void setColor( Color color ) {
		this.color = color;
		regraph();
	}

	public Color getColor() {
		return color;
	}

	@Override
	protected void paintPointingDownwards( Graphics2D g ) {
		int[] xs = { 0, size / 2, -size / 2 };
		int[] ys = { 0, size, size };

		g.setColor( color );
		g.fillPolygon( xs, ys, 3 );

		g.setColor( Color.BLACK );
		g.drawPolygon( xs, ys, 3 );
	}
}
