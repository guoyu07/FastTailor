package com.jfinalshop.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.util.CommonUtil;

public class Confirmitems extends Model<Confirmitems> {
private static final long serialVersionUID = -57555379613217315L;
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	
	public static final Confirmitems dao = new Confirmitems();
	
	
	public List<Confirmitems> getAcceptedItems(String fid){
		
		String sql = "SELECT * FROM confirmitems WHERE fromId = '"+fid+"'";
		
		return Confirmitems.dao.find(sql);
	}
	public boolean save(Confirmitems ied){
		ied.set("id",CommonUtil.getUUID());
		if(ied.save()){
			return true;
		}
		return false;
	}
	public boolean isExit(String id){
		String sql = "select * from confirmitems where itemid = '" +id+"'";
		if(Confirmitems.dao.findFirst(sql)==null){
			return false;
		}else{
			return true;
		}
		
	}
	
	public int getCount(String fid){
		 List<Confirmitems> list= this.dao.find("select * from confirmitems where fromId= '"+fid+"'");
		return list.size();
	}
	
	public int getCount2(String fid){
		 List<Confirmitems> list= this.dao.find("select * from confirmitems where fid= '"+fid+"'");
		return list.size();
	}
	public List<Confirmitems> getConfirmedItemsByFId(String fid){
		String sql = "select * from confirmitems where fid = '"+fid+"' order by cfDate desc";
		return Confirmitems.dao.find(sql);
		
	}
	public  Page<Confirmitems> paginate( int pageNumber, int pageSize, List<Confirmitems> list){
		long totalRow = 0;
		int totalPage = 0;
		int size = list.size();
		if (size >= 1)
			totalRow = size;		// totalRow = (Long)result.get(0);		
		else
			return new Page<Confirmitems>(new ArrayList<Confirmitems>(0), pageNumber, pageSize, 0, 0);	
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<Confirmitems>(new ArrayList<Confirmitems>(0), pageNumber, pageSize, totalPage, (int)totalRow);
		return new Page<Confirmitems>(list, pageNumber, pageSize, totalPage, (int)totalRow);
	}
}
