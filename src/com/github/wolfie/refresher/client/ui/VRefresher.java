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

package com.github.wolfie.refresher.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class VRefresher extends Widget {
	
	public interface ClientRefreshListener {
		void refreshed();
	}
	
	public static final String TAGNAME = "refresher";
	private static final int STOP_THRESHOLD = 0;
	public static final String VARIABLE_REFRESH_EVENT = "r";
	
	private final Poller poller;
	private boolean pollerSuspendedDueDetach;
	
	private int pollingInterval;
	
	private final List<ClientRefreshListener> listeners = new ArrayList<ClientRefreshListener>();
	
	public VRefresher() {
		setElement(Document.get().createDivElement());
		poller = new Poller();
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if (pollerSuspendedDueDetach) {
			poller.scheduleRepeating(pollingInterval);
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if (pollingInterval > STOP_THRESHOLD) {
			poller.cancel();
			pollerSuspendedDueDetach = true;
		}
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
}
