package com.jfinalshop.bean;
import java.util.Date;
public class ItemsAttitude {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getDeal() {
		return deal;
	}
	public void setDeal(long deal) {
		this.deal = deal;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	private String id;
	public ItemsAttitude(String id, Date createDate,long deal) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.deal = deal;
	}
	public ItemsAttitude(){}
	private Date createDate;
	private long deal;
}
