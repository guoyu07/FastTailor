package com.jfinalshop.event;

import net.dreamlu.event.core.ApplicationEvent;

public class ConfirmPriceEvent extends ApplicationEvent {
	private static final long serialVersionUID = -57555379610217315L;
	public ConfirmPriceEvent(Object source) {
		super(source);
	}

}
