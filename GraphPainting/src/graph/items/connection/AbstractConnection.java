package graph.items.connection;

import graph.items.AbstractGraphItem;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.GraphConnection;

public abstract class AbstractConnection extends AbstractGraphItem implements GraphConnection{
	private EndPoint sourcePoint;
	private EndPoint targetPoint;
 
	public AbstractConnection(){
		setSourcePoint( new OrthogonalEndPoint() );
		setTargetPoint( new OrthogonalEndPoint() );
	}
	
	public void setSourcePoint( EndPoint sourcePoint ) {
		ConnectionArray array = null;
		
		if( this.sourcePoint != null ){
			removeChild( this.sourcePoint );
			array = this.sourcePoint.getArray();
			if( array != null ){
				array.remove( this.sourcePoint );
			}
			this.sourcePoint.setConnection( null );
		}
		this.sourcePoint = sourcePoint;
		if( this.sourcePoint != null ){
			this.sourcePoint.setConnection( this );
			addChild( sourcePoint );
			if( array != null ){
				array.add( this.sourcePoint );
			}
		}
	}
	
	public void setTargetPoint( EndPoint targetPoint ) {
		ConnectionArray array = null;
		
		if( this.targetPoint != null ){
			removeChild( this.targetPoint );
			array = this.targetPoint.getArray();
			if( array != null ){
				array.remove( this.targetPoint );
			}
			this.targetPoint.setConnection( null );
		}
		this.targetPoint = targetPoint;
		if( this.targetPoint != null ){
			this.targetPoint.setConnection( this );
			addChild( targetPoint );
			if( array != null ){
				array.add( this.targetPoint );
			}
		}
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
