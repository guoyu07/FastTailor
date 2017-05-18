package com.jfinal.controller;

import com.jfinal.ext.route.ControllerBind;
import com.jfinalshop.model.Advice;

@ControllerBind(controllerKey = "/advice")
public class AdviceController extends BaseController<Advice> {
	
	public void saveAdvice(){
		Advice ae = getModel(Advice.class,"Advice");
		if(ae.save()){
			ajaxJsonSuccessMessage("感谢您的反馈！");
		}else{
			ajaxJsonSuccessMessage("网络异常，您的意见未被提交！");
		}
	}
	
	public void showinfo(){
		render("center_contact.html");
	}
	
	public void advice(){
		render("center_advice.html");
	}
}
