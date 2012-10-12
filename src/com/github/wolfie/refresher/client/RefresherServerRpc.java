package com.github.wolfie.refresher.client;

import com.vaadin.shared.communication.ServerRpc;

public interface RefresherServerRpc extends ServerRpc {
	void refresh();
}
