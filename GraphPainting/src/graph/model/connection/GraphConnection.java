package graph.model.connection;

import graph.model.GraphItem;

public interface GraphConnection extends GraphItem{
	public EndPoint getSourceEndPoint();
	
	public EndPoint getTargetEndPoint();
}
