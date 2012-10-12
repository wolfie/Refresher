package com.github.wolfie.refresher.shared;

import com.vaadin.shared.communication.ServerRpc;

public interface RefresherServerRpc extends ServerRpc {
	void refresh();
}
