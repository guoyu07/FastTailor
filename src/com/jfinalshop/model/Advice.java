package com.jfinalshop.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Advice extends Model<Advice> {
	private static final  long serialVersionUID = -57555979613217315L;
	public static final Advice dao = new Advice();
	public void save(Advice ae){
		ae.set("createDate",new Date());
		ae.save();
	}
}
