package com.github.wolfie.refresher.shared;

import com.vaadin.shared.communication.SharedState;

public class RefresherState extends SharedState {
	private static final long serialVersionUID = 1642430935071877079L;
	
	/*-
	 * can't use @DelegateToWidget here, since we also need to delegate "enabled"
	 * to the widget, and things get called out-of-order if we let Vaadin call
	 * them as it pleases. 
	 *
	 * See: com.github.wolfie.refresher.shared.RefresherConnector.onStateChanged(StateChangeEvent)
	 */
	public int pollingInterval = -1;
}
