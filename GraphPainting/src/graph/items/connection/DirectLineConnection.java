package graph.items.connection;

import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;

import java.awt.Graphics2D;
import java.awt.Point;

public class DirectLineConnection extends PaintableConnection{
	/**
	 * Creates an unconfigured connection between <code>source</code> and <code>target</code>.
	 * @param source the source of the connection
	 * @param target the target of the connection
	 * @return the new connection
	 */
	public static DirectLineConnection connect( ConnectionArray source, ConnectionArray target ){
		DirectLineConnection connection = new DirectLineConnection();
		source.add( connection.getSourceEndPoint() );
		target.add( connection.getTargetEndPoint() );
		return connection;
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
		g.drawPolyline( xs, ys, 4 );
	}
}
