package com.github.wolfie.refresher.shared;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.client.VRefresher;
import com.google.gwt.core.client.GWT;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(Refresher.class)
public class RefresherConnector extends AbstractComponentConnector {
	private static final long serialVersionUID = 7295403302950163716L;
	private RefresherServerRpc rpc;

	@Override
	protected void init() {
		super.init();
		rpc = RpcProxy.create(RefresherServerRpc.class, this);

		getWidget().addListener(new VRefresher.ClientRefreshListener() {
			@Override
			public void refreshed() {
				rpc.refresh();
			}
		});
	}

	@Override
	public void onStateChanged(final StateChangeEvent stateChangeEvent) {
		getWidget().setPollingEnabled(getState().enabled);
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
	protected VRefresher createWidget() {
		return GWT.create(VRefresher.class);
	}

}
