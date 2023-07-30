package graph.items.connection;

import java.awt.Point;

public interface EndPointAttachementSite {
	public Point getLanding();

	public Point getPointFarthestAwayFromLanding();

	public Point getOtherLanding();
}
