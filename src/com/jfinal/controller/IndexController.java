package com.jfinal.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
@ControllerBind(controllerKey="/")
public class IndexController extends Controller {

	public void index(){
		render("/index/index.html");
	}
	
	public void blog(){
		render("/index/about.html");
	}
	
	public void login(){
		render("/index/login.html");
	}
	

}
