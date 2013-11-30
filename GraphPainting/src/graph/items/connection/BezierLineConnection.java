package graph.items.connection;

import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class BezierLineConnection extends PaintableConnection {
	public BezierLineConnection( ConnectionArray source, ConnectionArray target ){
		super( source, target );
	}

	@Override
	protected void paintConnection( Graphics2D g ) {
		EndPointAttachement source = getSourceEndPoint().getAttachement();
		EndPointAttachement target = getTargetEndPoint().getAttachement();
		
		Point sl = source.getLanding();
		Point sa = source.getApproach();
		Point ta = target.getApproach();
		Point tl = target.getLanding();
		
		Line2D.Float lineS = new Line2D.Float( sl.x, sl.y, sa.x, sa.y );
		Line2D.Float lineT = new Line2D.Float( ta.x, ta.y, tl.x, tl.y );
		
		double dist = Math.sqrt( Geom.dist2( sa.x, sa.y, ta.x, ta.y )) / 2;
		Point ctrl1 = Geom.pointAt( sa, source.getDirection(), dist );
		Point ctrl2 = Geom.pointAt( ta, target.getDirection(), dist );
		
		CubicCurve2D.Float cc = new CubicCurve2D.Float( sa.x, sa.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, ta.x, ta.y );
		
		Path2D path = new Path2D.Float();
		path.append( lineS, true );
		path.append( cc, true );
		path.append( lineT, true );
		g.draw( path );
	}
}
