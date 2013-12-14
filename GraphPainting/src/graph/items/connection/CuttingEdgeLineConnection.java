package graph.items.connection;

import graph.items.PathedGraphConnection;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.EndPointAttachement;
import graph.util.Geom;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CuttingEdgeLineConnection extends PaintableConnection implements Regraphable, PathedGraphConnection{
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
		path = null;
		closedPath = null;
	}
	
	@Override
	protected void paintConnection( Graphics2D g ) {
		createPaths();
		g.draw( path );
	}
	
	@Override
	public Path2D getClosedConnectionPath() {
		createPaths();
		return closedPath;
	}
	
	private void createPaths(){
		if( path == null ){
			path = new Path2D.Float();
			closedPath = new Path2D.Float();
			
			Iterator<Point> line = createLine().iterator();
			if( line.hasNext() ){
				
				Point previous = line.next();
				
				List<Line2D> lines = new ArrayList<>();
				List<Line2D> reverse = new ArrayList<>();
				
				while( line.hasNext() ){
					Point current = line.next();
					
					lines.add( new Line2D.Float( previous.x, previous.y, current.x, current.y ) );
					reverse.add( new Line2D.Float( current.x, current.y, previous.x, previous.y ) );
					
					previous = current;
				}
				
				for( Line2D l : lines ){
					path.append( l, true );
					closedPath.append( l, true );
				}
				
				for( int i = reverse.size()-1; i >= 0; i-- ){
					closedPath.append( reverse.get( i ), true );
				}
			}
		}
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
