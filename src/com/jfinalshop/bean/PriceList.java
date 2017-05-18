package com.jfinalshop.bean;

import java.util.Date;
/**
 * * 
*	 @Description:   出价信息 包括:出价者id、时间 、报价
*	
*	 @author by run

*    @date 2016-3-3
 */
public class PriceList {
	
	private Date time; 
	private String id;
	private String price;
	
	public PriceList(Date time, String id, String price) {
		super();
		this.time = time;
		this.id = id;
		this.price = price;
	}
	public PriceList(){}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	
}
