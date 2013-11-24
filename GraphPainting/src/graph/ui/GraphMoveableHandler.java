package graph.ui;

import graph.model.GraphMoveable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphMoveableHandler {
	private List<GraphMoveable> moveables = new ArrayList<>();
	private GraphMoveable current;
	
	public GraphMoveableHandler( GraphPanel panel ){
		panel.addMouseListener( mouseListener() );
		panel.addMouseMotionListener( mouseMotionListener() );
	}
	
	public void add( GraphMoveable moveable ){
		moveables.add( moveable );
	}
	
	public void remove( GraphMoveable moveable ){
		moveables.remove( moveable );
		if( moveable == current ){
			current = null;
		}
	}
	
	private GraphMoveable getMoveableAt( int x, int y ){
		for( GraphMoveable moveable : moveables ){
			if( moveable.contains( x, y )){
				return moveable;
			}
		}
		return null;
	}
	
	private MouseListener mouseListener(){
		return new MouseAdapter(){
			@Override
			public void mousePressed( MouseEvent e ) {
				if( current == null ){
					current = getMoveableAt( e.getX(), e.getY() );
					if( current != null ){
						current.startMoveAt( e.getX(), e.getY() );
					}
				}
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( current != null ){
					current.endMoveAt( e.getX(), e.getY() );
					current = null;
				}
			}
		};
	}
	
	private MouseMotionListener mouseMotionListener(){
		return new MouseMotionAdapter() {
			@Override
			public void mouseDragged( MouseEvent e ) {
				if( current != null ){
					current.moveTo( e.getX(), e.getY() );
				}
			}
		};
	}
}
