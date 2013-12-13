package graph.items.connection;

import graph.items.AbstractGraphItem;
import graph.model.connection.GraphConnection;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;
import graph.model.connection.EndPointPosition;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractEndPoint extends AbstractGraphItem implements EndPoint{
	private GraphConnection connection;
	private ConnectionArray array;
	
	@Override
	public void setConnection( GraphConnection connection ) {
		this.connection = connection;	
	}
	
	@Override
	public EndPointPosition getEndPointPosition() {
		return EndPointPosition.AUTOMATIC;
	}

	@Override
	public EndPoint getOtherEndPoint() {
		if( connection.getSourceEndPoint() == this ){
			return connection.getTargetEndPoint();
		}
		else{
			return connection.getSourceEndPoint();
		}
	}

	@Override
	public void setArray( ConnectionArray array ) {
		this.array = array;
	}
	
	@Override
	public ConnectionArray getArray() {
		return array;
	}
	
	@Override
	public EndPointAttachement getAttachement() {
		return array.getAttachement( this );
	}
	
	@Override
	public Point getLanding() {
		return array.getLanding( this );
	}

	@Override
	public Point getLanding( EndPointAttachementSite site ) {
		return site.getLanding();
	}
	
	@Override
	public Point getArrayCenter() {
		return array.getCenter();
	}
	
	@Override
	public Rectangle getArrayBoundaries() {
		return array.getBoundaries();
	}
}
