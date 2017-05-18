package com.jfinalshop.model;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


public class Price extends Model<Price> {
	private static final long serialVersionUID = -57551249613217315L;
	public static final Price dao = new Price();
	/**
	 * 获取item的所有出价信息
	 * @param id
	 * @return
	 */
	public List<Price> getAllPriceList(String id){
		String sql = "select * from price where itemid='"+id
						+"' order by replyDate";
		return Price.dao.find(sql);
	}
	
	public List<Price> findByItemId(String id){
		return Price.dao.find("select * from price where itemid = ? ", id);
	}
	
	public List<Price> findByOwnerId(String id){
		return Price.dao.find("select * from price where ownerid = ? ",id);
	}

	public List<Price> getOfferPriceList(String id1,String id2){
		String sql = "select * from price where factoryid='"+id1
					+"' and itemid ='"+ id2 +"' order by replyDate";
		return Price.dao.find(sql);
	}
	
	public  Page<Price> paginate( int pageNumber, int pageSize, List<Price> list){
		long totalRow = 0;
		int totalPage = 0;
		int size = list.size();
		if (size >= 1)
			totalRow = size;		// totalRow = (Long)result.get(0);		
		else
			return new Page<Price>(new ArrayList<Price>(0), pageNumber, pageSize, 0, 0);	
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<Price>(new ArrayList<Price>(0), pageNumber, pageSize, totalPage, (int)totalRow);
		return new Page<Price>(list, pageNumber, pageSize, totalPage, (int)totalRow);
	}
}
