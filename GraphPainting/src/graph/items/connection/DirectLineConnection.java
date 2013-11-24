package graph.items.connection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.Connection;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPoint;
import graph.model.connection.EndPointAttachement;

public class DirectLineConnection implements Connection, GraphPaintable{
	private ConnectionArray source;
	private ConnectionArray target;
	
	private EndPoint sourcePoint;
	private EndPoint targetPoint;
	private GraphSite site;
	
	public DirectLineConnection( ConnectionArray source, ConnectionArray target ){
		this.source = source;
		this.target = target;
		
		setSourcePoint( new SimpleEndPoint() );
		setTargetPoint( new SimpleEndPoint() );
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
		
		if( this.site != null ){
			this.site.remove( this );
		}
		this.site = site;
		if( site != null ){
			site.add( this );
		}
	}
	
	@Override
	public void paint( Graphics2D g ) {
		EndPointAttachement source = this.sourcePoint.getAttachement();
		EndPointAttachement target = this.targetPoint.getAttachement();
		
		Point sl = source.getLanding();
		Point sa = source.getApproach();
		Point ta = target.getApproach();
		Point tl = target.getLanding();
		
		g.setColor( Color.BLACK );
		g.drawLine( sl.x, sl.y, sa.x, sa.y );
		g.drawLine( sa.x, sa.y, ta.x, ta.y );
		g.drawLine( ta.x, ta.y, tl.x, tl.y );
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
