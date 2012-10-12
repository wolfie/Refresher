package com.github.wolfie.refresher.shared;

import com.vaadin.shared.ComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class RefresherState extends ComponentState {
	private static final long serialVersionUID = 1642430935071877079L;

	@DelegateToWidget
	public int pollingInterval = -1;
}
