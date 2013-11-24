package graph.items.connection;

import graph.model.GraphSite;
import graph.model.connection.Connection;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;

public class AbstractConnection implements Connection{
	private ConnectionArray source;
	private ConnectionArray target;
	
	private EndPoint sourcePoint;
	private EndPoint targetPoint;
 
	private GraphSite site;
	
	public AbstractConnection( ConnectionArray source, ConnectionArray target ){
		this.source = source;
		this.target = target;
		
		setSourcePoint( new OrthogonalEndPoint() );
		setTargetPoint( new OrthogonalEndPoint() );
	}
	
	public void setSourcePoint( EndPoint sourcePoint ) {
		if( this.sourcePoint != null ){
			this.sourcePoint.setConnection( null );
			this.sourcePoint.set( null );
			source.remove( this.sourcePoint );
		}
		this.sourcePoint = sourcePoint;
		if( this.sourcePoint != null ){
			this.sourcePoint.setConnection( this );
			this.sourcePoint.set( site );
			source.add( this.sourcePoint );
		}
	}
	
	public EndPoint getSourcePoint() {
		return sourcePoint;
	}
	
	public void setTargetPoint( EndPoint targetPoint ) {
		if( this.targetPoint != null ){
			this.targetPoint.setConnection( null );
			this.targetPoint.set( null );
			target.remove( this.targetPoint );
		}
		this.targetPoint = targetPoint;
		if( this.targetPoint != null ){
			this.targetPoint.setConnection( this );
			this.targetPoint.set( site );
			target.add( this.targetPoint );
		}
	}
	
	public EndPoint getTargetPoint() {
		return targetPoint;
	}
	
	@Override
	public void set( GraphSite site ) {
		targetPoint.set( site );
		sourcePoint.set( site );
		this.site = site;
	}
	
	@Override
	public EndPoint getSourceEndPoint() {
		return sourcePoint;
	}

	@Override
	public EndPoint getTargetEndPoint() {
		return targetPoint;
	}
}
