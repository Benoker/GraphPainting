package graph.items.connection;

import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;

import java.awt.Graphics2D;
import java.awt.Point;

public class DirectLineConnection extends PaintableConnection{
	public DirectLineConnection( ConnectionArray source, ConnectionArray target ){
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
		if( sa.x != ta.x || sa.y != ta.y ){
			g.drawLine( sa.x, sa.y, ta.x, ta.y );
		}
		if( ta.x != tl.x || ta.y != tl.y ){
			g.drawLine( ta.x, ta.y, tl.x, tl.y );
		}
	}
}
