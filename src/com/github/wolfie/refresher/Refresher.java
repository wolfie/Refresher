package com.github.wolfie.refresher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.wolfie.refresher.shared.RefresherServerRpc;
import com.github.wolfie.refresher.shared.RefresherState;
import com.vaadin.server.AbstractExtension;

/**
 * A Refresher is an non-visual component that polls the server for GUI updates.
 * <p/>
 * This makes asynchronous UI changes possible, that will be rendered even if
 * the user doesn't initiate a server-cycle explicitly.
 * 
 * @author Henrik Paul
 */
public class Refresher extends AbstractExtension {
	
	public interface RefreshListener extends Serializable {
		public void refresh(Refresher source);
	}
	
	private static final long serialVersionUID = -2818447361687554688L;
	private static final int DEFAULT_REFRESH_INTERVAL = 1000;
	
	private final List<RefreshListener> refreshListeners = new ArrayList<RefreshListener>();
	
	private final RefresherServerRpc rpc = new RefresherServerRpc() {
		private static final long serialVersionUID = -5572645605753743517L;
		
		@Override
		public void refresh() {
			fireRefreshEvents();
		}
	};
	
	public Refresher() {
		registerRpc(rpc);
		setRefreshInterval(DEFAULT_REFRESH_INTERVAL);
	}
	
	/**
	 * Define a refresh interval.
	 * 
	 * @param intervalInMillis
	 *          The desired refresh interval in milliseconds. An interval of zero
	 *          or less temporarily inactivates the refresh.
	 */
	public void setRefreshInterval(final int intervalInMillis) {
		getState().pollingInterval = intervalInMillis;
	}
	
	@Override
	public RefresherState getState() {
		return (RefresherState) super.getState();
	}
	
	/**
	 * Get the currently used refreshing interval.
	 * 
	 * @return The refresh interval in milliseconds. A result of zero or less
	 *         means that the refresher is currently inactive.
	 */
	public int getRefreshInterval() {
		return getState().pollingInterval;
	}
	
	private void fireRefreshEvents() {
		for (final RefreshListener listener : refreshListeners) {
			listener.refresh(this);
		}
	}
	
	/**
	 * Add a listener that will be triggered whenever this instance refreshes
	 * itself
	 * 
	 * @param listener
	 *          the listener
	 * @return <code>true</code> if the adding was successful. <code>false</code>
	 *         if the adding was unsuccessful, or <code>listener</code> is
	 *         <code>null</code>.
	 */
	public boolean addListener(final RefreshListener listener) {
		if (listener != null) {
			return refreshListeners.add(listener);
		} else {
			return false;
		}
	}
	
	/**
	 * Removes a {@link RefreshListener} from this instance.
	 * 
	 * @param listener
	 *          the listener to be removed.
	 * @return <code>true</code> if removal was successful. A <code>false</code>
	 *         most often means that <code>listener</code> wasn't added to this
	 *         instance to begin with.
	 */
	public boolean removeListener(final RefreshListener listener) {
		return refreshListeners.remove(listener);
	}
	
}
