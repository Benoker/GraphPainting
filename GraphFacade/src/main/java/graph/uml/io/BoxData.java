package graph.uml.io;

import graph.uml.Box;
import graph.uml.ItemKey;

/**
 * Some data describing a {@link Box}.
 * @author Benjamin Sigg
 */
public abstract class BoxData<T extends Box> extends Data<T> {
	private int x;
	private int y;
	private int width;
	private int height;

	public BoxData( ItemKey<T> key ) {
		super( key );
	}

	public int getX() {
		return x;
	}

	public void setX( int x ) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY( int y ) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth( int width ) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight( int height ) {
		this.height = height;
	}
	
	@Override
	public void validate() {
		super.validate();
		if( width <= 0 ){
			throw new IllegalStateException( "width is <= 0: " + width );
		}
		if( height <= 0 ){
			throw new IllegalStateException( "height is <= 0: " + height );
		}
	}
}
