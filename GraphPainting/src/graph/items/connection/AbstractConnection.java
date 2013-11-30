package graph.items.connection;

import graph.items.AbstractGraphItem;
import graph.model.connection.Connection;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;

public abstract class AbstractConnection extends AbstractGraphItem implements Connection{
	private ConnectionArray source;
	private ConnectionArray target;
	
	private EndPoint sourcePoint;
	private EndPoint targetPoint;
 
	public AbstractConnection( ConnectionArray source, ConnectionArray target ){
		this.source = source;
		this.target = target;
		
		setSourcePoint( new OrthogonalEndPoint() );
		setTargetPoint( new OrthogonalEndPoint() );
	}
	
	public void setSourcePoint( EndPoint sourcePoint ) {
		if( this.sourcePoint != null ){
			removeChild( this.sourcePoint );
			this.sourcePoint.setConnection( null );
			source.remove( this.sourcePoint );
		}
		this.sourcePoint = sourcePoint;
		if( this.sourcePoint != null ){
			this.sourcePoint.setConnection( this );
			source.add( this.sourcePoint );
			addChild( sourcePoint );
		}
	}
	
	public EndPoint getSourcePoint() {
		return sourcePoint;
	}
	
	public void setTargetPoint( EndPoint targetPoint ) {
		if( this.targetPoint != null ){
			removeChild( this.targetPoint );
			this.targetPoint.setConnection( null );
			target.remove( this.targetPoint );
		}
		this.targetPoint = targetPoint;
		if( this.targetPoint != null ){
			this.targetPoint.setConnection( this );
			target.add( this.targetPoint );
			addChild( targetPoint );
		}
	}
	
	public EndPoint getTargetPoint() {
		return targetPoint;
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
