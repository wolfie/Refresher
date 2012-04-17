package com.github.wolfie.refresher.client;

import com.vaadin.terminal.gwt.client.ComponentState;

public class RefresherState extends ComponentState {
	private static final long serialVersionUID = 1642430935071877079L;
	int pollingInterval = -1;
	
	public int getPollingInterval() {
		return pollingInterval;
	}
	
	public void setPollingInterval(final int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
}
