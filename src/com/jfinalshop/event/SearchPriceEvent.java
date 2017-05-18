package com.jfinalshop.event;

import net.dreamlu.event.core.ApplicationEvent;

public class SearchPriceEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6994987952247309131L;
	public SearchPriceEvent(Object source) {
		super(source);
	}

}
