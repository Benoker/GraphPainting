package graph.items.connection;

import graph.model.GraphPaintable;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class DirectLineConnection extends AbstractConnection implements GraphPaintable{
	private GraphSite site;
	
	public DirectLineConnection( ConnectionArray source, ConnectionArray target ){
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
	
	@Override
	public void paint( Graphics2D g ) {
		EndPointAttachement source = getSourceEndPoint().getAttachement();
		EndPointAttachement target = getTargetEndPoint().getAttachement();
		
		Point sl = source.getLanding();
		Point sa = source.getApproach();
		Point ta = target.getApproach();
		Point tl = target.getLanding();
		
		g.setColor( Color.BLACK );
		if( sl.x != sa.x || sl.y != sa.y ){
			g.drawLine( sl.x, sl.y, sa.x, sa.y );
		}
		if( sa.x != ta.x || sa.y != ta.y ){
			g.drawLine( sa.x, sa.y, ta.x, ta.y );
		}
		if( ta.x != tl.x || ta.y != tl.y ){
			g.drawLine( ta.x, ta.y, tl.x, tl.y );
		}
	}
}
