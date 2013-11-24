package graph.model.connection;

import graph.model.GraphItem;

public interface Connection extends GraphItem{
	public EndPoint getSourceEndPoint();
	
	public EndPoint getTargetEndPoint();
}
