package com.github.wolfie.refresher.shared;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.client.RefresherExtension;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Refresher.class)
public class RefresherConnector extends AbstractExtensionConnector {
	private static final long serialVersionUID = 7295403302950163716L;
	private RefresherServerRpc rpc;
	private final RefresherExtension extension = new RefresherExtension();
	
	@Override
	protected void init() {
		super.init();
		rpc = RpcProxy.create(RefresherServerRpc.class, this);
		
		extension.addListener(new RefresherExtension.ClientRefreshListener() {
			@Override
			public void refreshed() {
				rpc.refresh();
			}
		});
	}
	
	@Override
	public void onUnregister() {
		extension.unregister();
	}
	
	@Override
	public void onStateChanged(final StateChangeEvent stateChangeEvent) {
		extension.setPollingInterval(getState().pollingInterval);
		extension.setPollingEnabled(getState().enabled);
	}
	
	@Override
	public RefresherState getState() {
		return (RefresherState) super.getState();
	}
	
}
