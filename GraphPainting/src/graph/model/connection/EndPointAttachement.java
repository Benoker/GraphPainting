package graph.model.connection;

import java.awt.Point;

public class EndPointAttachement {
	private Point landing;
	private Point approach;
	
	public EndPointAttachement( Point approach, Point landing ){
		this.landing = landing;
		this.approach = approach;
	}
	
	public Point getApproach() {
		return approach;
	}
	
	public Point getLanding() {
		return landing;
	}
}
