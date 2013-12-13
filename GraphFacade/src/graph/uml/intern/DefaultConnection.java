package graph.uml.intern;

import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.Connection;
import graph.uml.Item;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class DefaultConnection extends DefaultItem implements Connection{
	private DefaultBox sourceItem;
	private DefaultBox targetItem;
	
	private ConnectionArray source;
	private ConnectionArray target;
	
	public DefaultConnection( Graph graph, DefaultBox sourceItem, ConnectionArray source, DefaultBox targetItem, ConnectionArray target ){
		super( graph );
		this.source = source;
		this.target = target;
		
		sourceItem.addDependent( this );
		targetItem.addDependent( this );
		
		this.sourceItem = sourceItem;
		this.targetItem = targetItem;
	}
	
	public ConnectionArray getSource() {
		return source;
	}
	
	public ConnectionArray getTarget() {
		return target;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		sourceItem.removeDependent( this );
		targetItem.removeDependent( this );
	}
	
	@Override
	protected Iterable<Item> dependentItems() {
		return null;
	}
}
