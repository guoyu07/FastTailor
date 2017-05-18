package com.jfinalshop.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

	public class ItemsSoft extends Model<ItemsSoft>{
		private static final long serialVersionUID = -9170862353235959429L;
	
	public static final ItemsSoft dao = new ItemsSoft();
	
	public List<ItemsSoft> getAll() {
		String sql = "select * from itemssoft t  order by t.name asc ";
		List<ItemsSoft> alItemsSoftList = dao.find(sql);
		return alItemsSoftList;
	}
}
