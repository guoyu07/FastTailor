package com.jfinalshop.listener;

import java.io.IOException;

import net.dreamlu.event.core.ApplicationListener;
import net.dreamlu.event.core.Listener;

import com.jfinalshop.event.SearchPriceEvent;
import com.jfinalshop.util.MyWebSocket;
@Listener
public class SearchPriceListener implements  ApplicationListener<SearchPriceEvent> {

	@Override
	public void onApplicationEvent(SearchPriceEvent event) {
		String aimId = (String) event.getSource();
		MyWebSocket item = MyWebSocket.getMyWebSocket(aimId);
		//System.out.println(Thread.currentThread().getName() + "\tsource:" + aimId+"收到一条新的询价" +"当前共："+item.webSocketSet.size());
		if(item!=null){
	
            try {
            	item.sendMessage("您有新的询价单，请到'询价信息'中查看~");
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
       
	
		//System.out.println("searching price now !"+"aim:"+event.getSource());
	}



}
