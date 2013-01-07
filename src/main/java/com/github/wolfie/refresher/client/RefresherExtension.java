/*
 * Copyright 2009 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.wolfie.refresher.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;

public class RefresherExtension {
	
	public interface ClientRefreshListener {
		void refreshed();
	}
	
	private static final int STOP_THRESHOLD = 0;
	
	private final Poller poller;
	private boolean pollerSuspendedDueDetach;
	
	private int pollingInterval;
	
	private final List<ClientRefreshListener> listeners = new ArrayList<ClientRefreshListener>();
	
	public RefresherExtension() {
		poller = new Poller();
	}
	
	public void unregister() {
		poller.cancel();
	}
	
	class Poller extends Timer {
		@Override
		public void run() {
			for (final ClientRefreshListener listener : listeners) {
				listener.refreshed();
			}
		}
	}
	
	public void setPollingInterval(final int pollingInterval) {
		this.pollingInterval = pollingInterval;
		poller.cancel();
		
		if (pollingInterval > STOP_THRESHOLD) {
			poller.scheduleRepeating(this.pollingInterval);
		}
	}
	
	public void addListener(final ClientRefreshListener clientRefreshListener) {
		listeners.add(clientRefreshListener);
	}
	
	public void setPollingEnabled(final boolean enabled) {
		if (enabled) {
			setPollingInterval(pollingInterval);
		} else {
			poller.cancel();
		}
	}
}
