package graph.model.connection;

import java.awt.Point;

public class EndPointAttachement {
	private Point landing;
	private Point approach;
	private Point direction;
	
	public EndPointAttachement( Point landing, Point approach, Point direction ){
		this.approach = approach;
		this.landing = landing;
		this.direction = direction;
	}
	
	public Point getApproach() {
		return approach;
	}
	
	public Point getLanding() {
		return landing;
	}
	
	/**
	 * Direction pointing from {@link #getLanding()} outwards
	 * @return the direction, not <code>null</code>
	 */
	public Point getDirection() {
		return direction;
	}
}
