package com.jfinalshop.listener;

import java.io.IOException;
import com.jfinalshop.event.SetPriceEvent;
import com.jfinalshop.util.MyWebSocket;

import net.dreamlu.event.core.ApplicationListener;
import net.dreamlu.event.core.Listener;
@Listener
public class SetPriceListener implements ApplicationListener<SetPriceEvent> {

	@Override
	public void onApplicationEvent(SetPriceEvent event) {
		String aimId = (String) event.getSource();
		System.out.println(aimId+"<----目标");
		MyWebSocket item = MyWebSocket.getMyWebSocket(aimId);
		System.out.println(Thread.currentThread().getName() + "\tsource:" + aimId+"收到一条新的询价" +"当前共："+item.webSocketSet.size());
		if(item!=null){
	
            try {
            	item.sendMessage("您的询价单有人回复啦，快到'新的报价'里查看~");
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
       
	}



}
