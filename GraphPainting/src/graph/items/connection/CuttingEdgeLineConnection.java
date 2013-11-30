package graph.items.connection;

import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CuttingEdgeLineConnection extends PaintableConnection implements Regraphable{
	private GraphSite site;
	private List<Point> line;
	
	public CuttingEdgeLineConnection( ConnectionArray source, ConnectionArray target ) {
		super( source, target );
	}
	
	@Override
	public void set( GraphSite site ) {
		if( this.site != null ){
			this.site.remove( (Regraphable)this );
		}
		super.set( site );
		this.site = site;
		if( site != null ){
			site.add( (Regraphable)this );
		}
	}

	@Override
	public void regraph() {
		line = null;	
	}
	
	@Override
	protected void paintConnection( Graphics2D g ) {
		if( line == null ){
			line = createLine();
		}
		int[] pointx = new int[ line.size() ];
		int[] pointy = new int[ line.size() ];
		for( int i = 0, n = line.size(); i<n;  i++ ){
			Point p = line.get( i );
			pointx[i] = p.x;
			pointy[i] = p.y;
		}
		g.drawPolyline( pointx, pointy, line.size() );
	}
	
	private List<Point> createLine(){
		List<Point> line = new ArrayList<>();
		
		EndPointAttachement source = getSourceEndPoint().getAttachement();
		EndPointAttachement target = getTargetEndPoint().getAttachement();
		
		Point sl = source.getLanding();
		Point sa = source.getApproach();
		Point ta = target.getApproach();
		Point tl = target.getLanding();
		
		if( sl.x != sa.x || sl.y != sa.y ){
			line.add( sl );
		}
		line.add( sa );
		
		Point intersection = Geom.intersection( sa, sl, ta, tl );
		if( intersection != null ){
			line.add( intersection );
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
			line.add( ms );
			line.add( mt );
		}
		
		line.add( ta );
		if( ta.x != tl.x || ta.y != tl.y ){
			line.add( tl );
		}
		
		return line;
	}
}
