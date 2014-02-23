package graph.items.connection;

import graph.items.PathedGraphConnection;
import graph.model.GraphSite;
import graph.model.Regraphable;
import graph.model.connection.ConnectionArray;
import graph.model.connection.EndPointAttachement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class DirectLineConnection extends PaintableConnection implements PathedGraphConnection, Regraphable {
	private Path2D path;
	private Path2D closedPath;

	/**
	 * Creates an unconfigured connection between <code>source</code> and <code>target</code>.
	 * @param source the source of the connection
	 * @param target the target of the connection
	 * @return the new connection
	 */
	public static DirectLineConnection connect( ConnectionArray source, ConnectionArray target ) {
		DirectLineConnection connection = new DirectLineConnection();
		source.add( connection.getSourceEndPoint() );
		target.add( connection.getTargetEndPoint() );
		return connection;
	}

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

	@Override
	public Path2D getOpenConnectionPath() {
		createPath();
		return path;
	}
	
	@Override
	public Rectangle getVisibleBoundaries( Graphics g ) {
		return getOpenConnectionPath().getBounds();
	}
	
	private void createPath() {
		if( path == null ) {
			EndPointAttachement source = getSourceEndPoint().getAttachement();
			EndPointAttachement target = getTargetEndPoint().getAttachement();

			Point sl = source.getLanding();
			Point sa = source.getApproach();
			Point ta = target.getApproach();
			Point tl = target.getLanding();

			Line2D.Float lineS = new Line2D.Float( sl.x, sl.y, sa.x, sa.y );
			Line2D.Float between = new Line2D.Float( sa.x, sa.y, ta.x, ta.y );
			Line2D.Float lineT = new Line2D.Float( ta.x, ta.y, tl.x, tl.y );
			Line2D.Float revLineT = new Line2D.Float( tl.x, tl.y, ta.x, ta.y );
			Line2D.Float revBetween = new Line2D.Float( ta.x, ta.y, sa.x, sa.y );
			Line2D.Float revLineS = new Line2D.Float( sa.x, sa.y, sl.x, sl.y );

			path = new Path2D.Float();
			path.append( lineS, true );
			path.append( between, true );
			path.append( lineT, true );

			closedPath = new Path2D.Float();
			closedPath.append( lineS, true );
			closedPath.append( between, true );
			closedPath.append( lineT, true );
			closedPath.append( revLineT, true );
			closedPath.append( revBetween, true );
			closedPath.append( revLineS, true );
		}
	}
}
