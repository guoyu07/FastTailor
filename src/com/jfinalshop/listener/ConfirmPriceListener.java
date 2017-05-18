package com.jfinalshop.listener;

import java.io.IOException;

import com.jfinalshop.event.ConfirmPriceEvent;
import com.jfinalshop.util.MyWebSocket;

import net.dreamlu.event.core.ApplicationListener;
import net.dreamlu.event.core.Listener;
@Listener
public class ConfirmPriceListener implements ApplicationListener<ConfirmPriceEvent> {
	@Override
	public void onApplicationEvent(ConfirmPriceEvent event) {
		String aimId = (String) event.getSource();
		MyWebSocket item = MyWebSocket.getMyWebSocket(aimId);
		//System.out.println(Thread.currentThread().getName() + "\tsource:" + aimId+"收到一条新的询价" +"当前共："+item.webSocketSet.size());
		if(item!=null){
	
            try {
            	item.sendMessage("您的报价有人接受了哦！快到'新的订单'中查看吧~");
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
       
	
		//System.out.println("searching price now !"+"aim:"+event.getSource());
	}
}
