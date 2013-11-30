package graph.items.connection;

import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

import java.awt.Graphics2D;
import java.awt.Point;

public class CuttingEdgeLineConnection extends PaintableConnection{
	public CuttingEdgeLineConnection( ConnectionArray source, ConnectionArray target ) {
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
		
		if( sl.x != sa.x || sl.y != sa.y ){
			g.drawLine( sl.x, sl.y, sa.x, sa.y );
		}
		if( ta.x != tl.x || ta.y != tl.y ){
			g.drawLine( ta.x, ta.y, tl.x, tl.y );
		}
		
		Point intersection = Geom.intersection( sa, sl, ta, tl );
		if( intersection != null ){
			g.drawLine( sa.x, sa.y, intersection.x, intersection.y );
			g.drawLine( ta.x, ta.y, intersection.x, intersection.y );
		}
		else{
			Point middle = Geom.middle( sa, ta );
			Point ds = new Point( sa.x - sl.x, sa.y - sl.y );
			Point dt = new Point( ta.x - tl.x, ta.y - tl.y );
			Point os = new Point( ds.y, -ds.x );
			Point ot = new Point( dt.y, -dt.x );
			
			Point ms;
			Point mt;
			
			if( Geom.closingIn( sa, Geom.plus( sa, ds ), ta, Geom.plus( ta, dt ) )){
				ms = Geom.intersection( sa, Geom.plus(  sa, ds ), middle, Geom.plus( middle, os ));
				mt = Geom.intersection( ta, Geom.plus( ta, dt ), middle, Geom.plus( middle, ot ));
			}
			else{
				ms = Geom.intersection( sa, Geom.plus(  sa, os ), middle, Geom.plus( middle, ds ));
				mt = Geom.intersection( ta, Geom.plus( ta, os ), middle, Geom.plus( middle, dt ));
			}
			g.drawLine( sa.x, sa.y, ms.x, ms.y );
			g.drawLine( ms.x, ms.y, mt.x, mt.y );
			g.drawLine( mt.x, mt.y, ta.x, ta.y );
			
		}
	}
}
