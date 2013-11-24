package graph.items.connection;

import java.awt.Point;
import java.awt.Rectangle;

import graph.model.GraphSite;
import graph.model.connection.Connection;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;
import graph.model.connection.EndPointPosition;

public class SimpleEndPoint implements EndPoint{
	private Connection connection;
	private ConnectionArray array;
	
	@Override
	public void setConnection( Connection connection ) {
		this.connection = connection;	
	}
	
	@Override
	public void set( GraphSite site ) {
		// ignore
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
	public EndPointAttachement getAttachement() {
		return array.getAttachement( this );
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
