package graph.items.connection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

public class BezierLineConnection extends AbstractConnection implements GraphPaintable {
	private GraphSite site;
	private Color color = Color.BLACK;
	
	public BezierLineConnection( ConnectionArray source, ConnectionArray target ){
		super( source, target );
	}
	
	@Override
	public void set( GraphSite site ) {
		super.set( site );
		
		if( this.site != null ){
			this.site.remove( (GraphPaintable)this );
		}
		this.site = site;
		if( site != null ){
			site.add( (GraphPaintable)this );
		}
	}
	
	public void setColor( Color color ) {
		this.color = color;
		if( site != null ){
			site.regraph();
		}
	}
	
	@Override
	public void paint( Graphics2D g ) {
		EndPointAttachement source = getSourceEndPoint().getAttachement();
		EndPointAttachement target = getTargetEndPoint().getAttachement();
		
		Point sl = source.getLanding();
		Point sa = source.getApproach();
		Point ta = target.getApproach();
		Point tl = target.getLanding();
		
		g.setColor( color );
		
		if( sl.x != sa.x || sl.y != sa.y ){
			g.drawLine( sl.x, sl.y, sa.x, sa.y );
		}
		if( ta.x != tl.x || ta.y != tl.y ){
			g.drawLine( ta.x, ta.y, tl.x, tl.y );
		}
		
		double dist = Math.sqrt( Geom.dist2( sa.x, sa.y, ta.x, ta.y )) / 2;
		Point ctrl1 = Geom.pointAt( sa, source.getDirection(), dist );
		Point ctrl2 = Geom.pointAt( ta, target.getDirection(), dist );
		
		CubicCurve2D.Float cc = new CubicCurve2D.Float( sa.x, sa.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, ta.x, ta.y );
		
		g.draw( cc );
	}
}
