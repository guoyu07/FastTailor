package com.jfinalshop.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class ItemsStyle extends Model<ItemsStyle> {
	
	private static final long serialVersionUID = -9170862348235959429L;
	
	public static final ItemsStyle dao = new ItemsStyle();
	
	public List<ItemsStyle> getAll() {
		String sql = "select * from itemsstyle t  order by t.name asc ";
		List<ItemsStyle> allProductCategoryList = dao.find(sql);
		return allProductCategoryList;
	}
}
