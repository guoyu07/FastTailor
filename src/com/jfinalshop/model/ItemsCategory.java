package com.jfinalshop.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class ItemsCategory extends Model<ItemsCategory> {
	private static final long serialVersionUID = -9173332348235959429L;
	
	public static final ItemsCategory dao = new ItemsCategory();
	
	public List<ItemsCategory> getAll() {
		String sql = "select * from itemscategory t  order by t.name asc ";
		List<ItemsCategory> allItemsCategoryList = dao.find(sql);
		return allItemsCategoryList;
	}
}
