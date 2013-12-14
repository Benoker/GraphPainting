package graph.items.connection;

import graph.items.PathedGraphConnection;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class BezierLineConnection extends PaintableConnection implements PathedGraphConnection, Regraphable{
	private Path2D path;
	private Path2D closedPath;
	
	@Override
	protected void addTo( GraphSite site ) {
		super.addTo( site );
		site.addRegraphable( this );
	}
	
	@Override
	protected void removeFrom( GraphSite site ) {
		super.removeFrom( site );
		site.removeRegraphable( this );
	}
	
	@Override
	public void regraphed() {
		closedPath = null;
		path = null;	
	}
	
	@Override
	protected void paintConnection( Graphics2D g ) {
		createPath();
		g.draw( path );
	}
	
	@Override
	public Path2D getClosedConnectionPath() {
		createPath();
		return closedPath;
	}
	
	private void createPath(){
		if( path == null ){
			EndPointAttachement source = getSourceEndPoint().getAttachement();
			EndPointAttachement target = getTargetEndPoint().getAttachement();
			
			Point sl = source.getLanding();
			Point sa = source.getApproach();
			Point ta = target.getApproach();
			Point tl = target.getLanding();
			
			Line2D.Float lineS = new Line2D.Float( sl.x, sl.y, sa.x, sa.y );
			Line2D.Float lineT = new Line2D.Float( ta.x, ta.y, tl.x, tl.y );
			Line2D.Float revLineS = new Line2D.Float( sa.x, sa.y, sl.x, sl.y );
			Line2D.Float revLineT = new Line2D.Float( tl.x, tl.y, ta.x, ta.y );
			
			double dist = Math.sqrt( Geom.dist2( sa.x, sa.y, ta.x, ta.y )) / 2;
			Point ctrl1 = Geom.pointAt( sa, source.getDirection(), dist );
			Point ctrl2 = Geom.pointAt( ta, target.getDirection(), dist );
			
			CubicCurve2D.Float cc = new CubicCurve2D.Float( sa.x, sa.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, ta.x, ta.y );
			CubicCurve2D.Float revCc = new CubicCurve2D.Float( ta.x, ta.y, ctrl2.x, ctrl2.y, ctrl1.x, ctrl1.y, sa.x, sa.y );
			
			path = new Path2D.Float();
			path.append( lineS, true );
			path.append( cc, true );
			path.append( lineT, true );
			
			closedPath = new Path2D.Float();
			closedPath.append( lineS, true );
			closedPath.append( cc, true );
			closedPath.append( lineT, true );
			closedPath.append( revLineT, true );
			closedPath.append( revCc, true );
			closedPath.append( revLineS, true );
		}
	}
}
