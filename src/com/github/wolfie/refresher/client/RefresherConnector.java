package com.github.wolfie.refresher.client;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.client.ui.VRefresher;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.communication.RpcProxy;
import com.vaadin.terminal.gwt.client.communication.StateChangeEvent;
import com.vaadin.terminal.gwt.client.ui.AbstractComponentConnector;
import com.vaadin.terminal.gwt.client.ui.Connect;

@Connect(Refresher.class)
public class RefresherConnector extends AbstractComponentConnector {
	private static final long serialVersionUID = 7295403302950163716L;
	private RefresherServerRpc rpc;
	
	@Override
	protected void init() {
		super.init();
		rpc = RpcProxy.create(RefresherServerRpc.class, this);
	}
	
	@Override
	public void onStateChanged(final StateChangeEvent stateChangeEvent) {
		getWidget().setPollingInterval(getState().getPollingInterval());
		getWidget().setPollingEnabled(getState().isEnabled());
	}
	
	@Override
	public RefresherState getState() {
		return (RefresherState) super.getState();
	}
	
	@Override
	public VRefresher getWidget() {
		return (VRefresher) super.getWidget();
	}
	
	@Override
	protected Widget createWidget() {
		final VRefresher widget = GWT.create(VRefresher.class);
		widget.addListener(new VRefresher.ClientRefreshListener() {
			public void refreshed() {
				rpc.refresh();
			}
		});
		return widget;
	}
	
}
