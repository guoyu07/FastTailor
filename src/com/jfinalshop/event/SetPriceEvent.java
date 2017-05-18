package com.jfinalshop.event;

import net.dreamlu.event.core.ApplicationEvent;

public class SetPriceEvent extends ApplicationEvent {
	private static final long serialVersionUID = 6994987958247309131L;
	public SetPriceEvent(Object source) {
		super(source);
	}
}
