package graph.ui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

import graph.model.GraphItem;
import graph.model.capability.CapabilityHandler;
import graph.model.capability.CapabilityHandlerSite;
import graph.model.capability.CapabilityName;

/**
 * Manages a set of {@link CapabilityHandler}s, allowing one of them to be triggered, and
 * forwarding events to them.
 * @author Benjamin Sigg
 */
public class CapabilityController {
	/** {@link Component} to which this controller can add input listeners */
	private GraphPanel graph;
	
	/** All the handlers known to this manager */
	private List<Site<?>> handlers = new ArrayList<>();
	
	/** The site that is currently triggered */
	private Site<?> triggeredSite;
	
	/**
	 * Creates a new controller.
	 * @param graph the {@link GraphPanel} for which this controller will be used
	 */
	public CapabilityController( GraphPanel graph ){
		this.graph = graph;
		
		graph.addMouseListener( new ForwardingMouseListener() );
		graph.addMouseMotionListener( new ForwardingMouseMotionListener() );
		graph.addKeyListener( new ForwardingKeyListener() );
	}
	
	/**
	 * Adds <code>handler</code> to the set of tools that are available to the user.
	 * @param name the name of the handler
	 * @param handler the new handler
	 */
	public <T> void register( CapabilityName<T> name, CapabilityHandler<T> handler ){
		Iterator<Site<?>> handlers = this.handlers.iterator();
		while( handlers.hasNext() ){
			Site<?> site = handlers.next();
			if( site.name.equals( name )){
				handlers.remove();
				site.dispose();
			}
		}
		
		if( handler != null ){
			this.handlers.add( new Site<>( name, handler ) );
		}
	}
	
	private void updateAllEnabled(){
		for( Site<?> handler : handlers ){
			handler.updateEnabled();
		}	
	}

	/**
	 * Added to the {@link GraphPanel}, this listener forwards all its events to the {@link CapabilityHandler}s.
	 */
	private class ForwardingMouseListener implements MouseListener{
		@Override
		public void mouseClicked( MouseEvent e ) {
			for( MouseListener listener : listeners( MouseListener.class )){
				listener.mouseClicked( e );
			}
		}
		
		@Override
		public void mouseEntered( MouseEvent e ) {
			for( MouseListener listener : listeners( MouseListener.class )){
				listener.mouseEntered( e );
			}
		}
		
		@Override
		public void mouseExited( MouseEvent e ) {
			for( MouseListener listener : listeners( MouseListener.class )){
				listener.mouseExited( e );
			}
		}
		
		@Override
		public void mousePressed( MouseEvent e ) {
			for( MouseListener listener : listeners( MouseListener.class )){
				listener.mousePressed( e );
			}
		}
		
		@Override
		public void mouseReleased( MouseEvent e ) {
			for( MouseListener listener : listeners( MouseListener.class )){
				listener.mouseReleased( e );
			}
		}
	}
	
	/**
	 * Added to the {@link GraphPanel}, this listener forwards all its events to the {@link CapabilityHandler}s.
	 */
	private class ForwardingMouseMotionListener implements MouseMotionListener{
		@Override
		public void mouseDragged( MouseEvent e ) {
			for( MouseMotionListener listener : listeners( MouseMotionListener.class )){
				listener.mouseDragged( e );
			}
		}
		
		@Override
		public void mouseMoved( MouseEvent e ) {
			for( MouseMotionListener listener : listeners( MouseMotionListener.class )){
				listener.mouseMoved( e );
			}
		}
	}
	
	/**
	 * Added to the {@link GraphPanel}, this listener forwards all its events to the {@link CapabilityHandler}s.
	 */
	private class ForwardingKeyListener implements KeyListener{
		@Override
		public void keyPressed( KeyEvent e ) {
			for( KeyListener listener : listeners( KeyListener.class )){
				listener.keyPressed( e );
			}
		}
		@Override
		public void keyReleased( KeyEvent e ) {
			for( KeyListener listener : listeners( KeyListener.class )){
				listener.keyReleased( e );
			}
		}
		@Override
		public void keyTyped( KeyEvent e ) {
			for( KeyListener listener : listeners( KeyListener.class )){
				listener.keyTyped( e );
			}	
		}
	}
	
	/**
	 * Gets an {@link Iterable} that can visit all the currently active listeners.
	 * @param type the type of the listeners
	 * @return the iteration of all the listeners
	 */
	private <T extends EventListener> Iterable<T> listeners( final Class<T> type ){
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return getListeners( type );
			}
		};
	}
	
	private <T extends EventListener> Iterator<T> getListeners( final Class<T> type ){
		return new Iterator<T>() {
			private Iterator<Site<?>> sites = handlers.iterator();
			private T[] listeners;
			private int index = 0;
			
			{
				advance();
			}
			
			@Override
			public boolean hasNext() {
				return listeners != null && index < listeners.length;
			}
			
			@Override
			public T next() {
				if( !hasNext() ){
					throw new IllegalStateException( "no next item" );
				}
				T result = listeners[index];
				advance();
				return result;
			}
			
			private void advance(){
				index++;
				
				while( (listeners == null || index >= listeners.length) && sites.hasNext() ){
					Site<?> site = sites.next();
					if( triggeredSite == site || triggeredSite == null ){
						listeners = site.getListeners( type );
						index = 0;
					}
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();	
			}
		};
	}
	
	/**
	 * Wrapper around one {@link CapabilityHandler}.
	 * @author Benjamin Sigg
	 */
	private class Site<T> implements CapabilityHandlerSite<T>{
		/** the unique name of this capability */
		private CapabilityName<T> name;
		
		/** a capability, some kind of action the user can perform */
		private CapabilityHandler<T> handler;
		
		/** all the listeners that were added to this site */
		private EventListenerList eventListeners = new EventListenerList();
		
		public Site( CapabilityName<T> name, CapabilityHandler<T> handler ){
			this.name = name;
			this.handler = handler;
			
			handler.init( this );
			updateEnabled();
		}
		
		public void dispose(){
			handler.dispose();
		}
		
		public <X extends EventListener> X[] getListeners(Class<X> type){
			return eventListeners.getListeners( type );
		}
		
		public void updateEnabled(){
			handler.setEnabled( triggeredSite == this );
		}

		@Override
		public void triggered() {
			if( triggeredSite == null ){
				triggeredSite = this;
				updateAllEnabled();
			}
		}

		@Override
		public void listening() {
			if( triggeredSite == this ){
				triggeredSite = null;
				updateAllEnabled();
			}
		}

		@Override
		public List<T> getCapabilities() {
			return graph.getCapabilities( name );
		}

		@Override
		public void addMouseListener( MouseListener listener ) {
			eventListeners.add( MouseListener.class, listener );
		}

		@Override
		public void removeMouseListener( MouseListener listener ) {
			eventListeners.remove( MouseListener.class, listener );
		}

		@Override
		public void addMouseMotionListener( MouseMotionListener listener ) {
			eventListeners.add( MouseMotionListener.class, listener );
		}

		@Override
		public void removeMouseMotionListener( MouseMotionListener listener ) {
			eventListeners.remove( MouseMotionListener.class, listener );
		}

		@Override
		public void addKeyListener( KeyListener listener ) {
			eventListeners.add( KeyListener.class, listener );
		}

		@Override
		public void removeKeyListener( KeyListener listener ) {
			eventListeners.add( KeyListener.class, listener );
		}
		
		@Override
		public void addItem( GraphItem item ) {
			graph.getGraph().addItem( item );
		}
		
		@Override
		public void removeItem( GraphItem item ) {
			graph.getGraph().removeItem( item );
		}
	}
}
