package com.github.wolfie.refresher.client;

import com.vaadin.terminal.gwt.client.communication.ServerRpc;

public interface RefresherServerRpc extends ServerRpc {
	void refresh();
}
