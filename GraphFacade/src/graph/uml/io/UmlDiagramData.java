package graph.uml.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graph.uml.UmlDiagram;

/**
 * Data that is required to describe an {@link UmlDiagram}.
 * @author Benjamin Sigg
 */
public class UmlDiagramData implements Iterable<Data<?>>{
	private int nextUnqiueId;
	private List<Data<?>> data = new ArrayList<>();
	
	public int getNextUnqiueId() {
		return nextUnqiueId;
	}
	
	public void setNextUnqiueId( int nextUnqiueId ) {
		this.nextUnqiueId = nextUnqiueId;
	}
	
	public void addData( Data<?> data ){
		this.data.add( data );
	}
	
	@Override
	public Iterator<Data<?>> iterator() {
		return data.iterator();
	}
}
