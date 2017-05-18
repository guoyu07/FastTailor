package com.jfinalshop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class WebSocketHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		// TODO Auto-generated method stub
		 //对于websocket 不交予 jfinal 处理
        int index = target.indexOf("/websocket");
        if (index == -1) {
            nextHandler.handle(target, request, response, isHandled);
        }
	}

}
