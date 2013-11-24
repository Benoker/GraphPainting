package graph.items;

import java.awt.Point;

import graph.items.connection.FluentRectangularConnectionArray;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;

public class TextBox extends GraphLabel implements ConnectionArray{
	private ConnectionArray array;
	
	public TextBox( String text ){
		super( text );
		
		FluentRectangularConnectionArray array = new FluentRectangularConnectionArray();
		add( array );
		this.array = array;
	}

	@Override
	public void add( EndPoint endPoint ) {
		array.add( endPoint );
	}

	@Override
	public void remove( EndPoint endPoint ) {
		array.remove( endPoint );
	}

	@Override
	public Point getCenter() {
		return array.getCenter();
	}

	@Override
	public EndPointAttachement getAttachement( EndPoint endPoint ) {
		return array.getAttachement( endPoint );
	}
}
