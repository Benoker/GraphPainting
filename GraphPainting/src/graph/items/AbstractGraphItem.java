package graph.items;

import graph.model.GraphItem;
import graph.model.GraphSite;
import graph.model.capability.CapabilityName;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of a {@link GraphItem} offering some default implementations of methods
 * that are often used.
 * @author Benjamin Sigg
 */
public abstract class AbstractGraphItem implements GraphItem {
	private CapabilityMap capabilities = new CapabilityMap();
	private GraphSite site;
	private List<GraphItem> children = new ArrayList<GraphItem>( 0 );

	/**
	 * Adds a capability that is represented by a {@link GraphItem}.
	 * @param name the name of the capability
	 * @param capability the {@link GraphItem} that is at the same time the capability
	 */
	public <T extends GraphItem> void addCapability( CapabilityName<? super T> name, T capability ) {
		if( getCapability( name ) != null ) {
			throw new IllegalStateException( "there is already a capability for " + name );
		}
		setCapability( name, capability );
		addChild( capability );
	}

	/**
	 * Adds or removes a capability of this item.
	 * @param name the name of the capability
	 * @param capability the capability, can be <code>null</code>
	 */
	public <T> void setCapability( CapabilityName<T> name, T capability ) {
		capabilities.put( name, capability );
	}

	@Override
	public <T> T getCapability( CapabilityName<T> name ) {
		return capabilities.get( name );
	}

	@Override
	public final void set( GraphSite site ) {
		if( this.site != null ) {
			for( GraphItem item : children ) {
				this.site.removeItem( item );
			}
			removeFrom( this.site );
		}
		this.site = site;
		if( site != null ) {
			addTo( site );
			for( GraphItem item : children ) {
				site.addItem( item );
			}
		}
	}

	/**
	 * Removes all resources of this {@link GraphItem} from <code>site</code>
	 * @param site the site to remove resources from
	 */
	protected abstract void removeFrom( GraphSite site );

	/**
	 * Adds all resources of this {@link GraphItem} to <code>site</code>
	 * @param site the site to which resources can be added
	 */
	protected abstract void addTo( GraphSite site );

	/**
	 * Adds a child {@link GraphItem} to this item, the child will be added and removed from the
	 * graph together with <code>this</code> item.
	 * @param item the new child item
	 */
	protected void addChild( GraphItem item ) {
		children.add( item );
		if( site != null ) {
			site.addItem( item );
		}
	}

	/**
	 * Removes a child item from this item, <code>item</code> will be removed from the graph as well.
	 * @param item the item to remove
	 */
	protected void removeChild( GraphItem item ) {
		children.remove( item );
		if( site != null ) {
			site.removeItem( item );
		}
	}

	/**
	 * Gets the currently used {@link GraphSite}
	 * @return the currently used site
	 */
	protected GraphSite getSite() {
		return site;
	}

	/**
	 * Calls {@link GraphSite#regraph()} on the {@link #getSite() current site}.
	 */
	protected void regraph() {
		if( site != null ) {
			site.regraph();
		}
	}
}
