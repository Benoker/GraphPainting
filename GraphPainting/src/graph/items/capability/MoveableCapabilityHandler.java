package graph.items.capability;

import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityHandlerSite;
import graph.model.capability.MoveableCapability;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes the action of moving around items.
 * @author Benjamin Sigg
 */
public class MoveableCapabilityHandler implements CapabilityHandler<MoveableCapability>{
	private CapabilityHandlerSite<MoveableCapability> site;
	
	private boolean moving = false;
	private List<MoveableCapability> items = new ArrayList<>();
	private Point mousePressedPoint;
	
	@Override
	public void init( CapabilityHandlerSite<MoveableCapability> site ) {
		this.site = site;
		
		site.addMouseListener( mouseListener() );
		site.addMouseMotionListener( mouseMotionListener() );
	}

	@Override
	public void setEnabled( boolean enabled ) {
		if( !enabled ){
			moving = false;
			mousePressedPoint = null;
			items.clear();
		}
	}

	private MouseListener mouseListener(){
		return new MouseAdapter(){
			@Override
			public void mousePressed( MouseEvent e ) {
				mousePressedPoint = e.getPoint();
			}
			
			@Override
			public void mouseReleased( MouseEvent e ) {
				if( moving ){
					for( MoveableCapability item : items ){
						item.endMoveAt( e.getX(), e.getY() );
					}
				}
				items.clear();
				mousePressedPoint = null;
				moving = false;
			}
		};
	}
	
	private void initMove( int x, int y ){
		for( MoveableCapability item : site.getCapabilities() ){
			if( item.isMovingEnabledAt( x, y )){
				items.add( item );
				item.startMoveAt( x, y );
			}
		}
		moving = true;
	}
	
	private MouseMotionListener mouseMotionListener(){
		return new MouseMotionAdapter() {
			@Override
			public void mouseDragged( MouseEvent e ) {
				if( !moving ){
					if( mousePressedPoint != null ){
						initMove( mousePressedPoint.x, mousePressedPoint.y );
					}
				}
				if( moving ){
					for( MoveableCapability item : items ){
						item.moveTo( e.getX(), e.getY() );
					}
				}
			}
		};
	}
}
