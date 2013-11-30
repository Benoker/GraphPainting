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
		
		int[] xs = { sl.x, sa.x, ta.x, tl.x };
		int[] ys = { sl.y, sa.y, ta.y, tl.y };
		g.drawPolygon( xs, ys, 4 );
	}
}
