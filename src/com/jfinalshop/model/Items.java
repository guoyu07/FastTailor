package com.jfinalshop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.bean.HtmlConfig;
import com.jfinalshop.bean.PriceList;
import com.jfinalshop.bean.ProductImage;
import com.jfinalshop.security.ShiroUtils;
import com.jfinalshop.util.CommonUtil;
import com.jfinalshop.util.TemplateConfigUtil;

public class Items extends Model<Items> {
	private static final long serialVersionUID = -57555379613214315L;
	
	public static final Items dao = new Items();
	
	public List<Items> getNewestItems(Integer start,Integer count){
		
		String sql = "SELECT * FROM items WHERE isDelete != b'1' ORDER BY createDate DESC LIMIT "+start+","+count+" ";
		return Items.dao.find(sql);
	}
	
	

	/**
	 * 获取样衣图片
	 * @return
	 */
	public List<ProductImage> getItemsImageList() {
		String productImageListStore = getStr("c_clothe_img");
		if (StringUtils.isEmpty(productImageListStore)) {
			return null;
		}		
		List<ProductImage> list = JSON.parseArray(productImageListStore, ProductImage.class); 		
 		return list;
	}
	
	/**
	 * 获取样衣图案
	 * @return
	 */
	public List<ProductImage> getItemsPatternImageList() {
		String productImageListStore = getStr("c_pattern_img");
		if (StringUtils.isEmpty(productImageListStore)) {
			return null;
		}		
		List<ProductImage> list = JSON.parseArray(productImageListStore, ProductImage.class); 		
 		return list;
	}
	
	/**
	 * 设置样衣图案
	 * @param 
	 */
	public void setItemImageList(List<ProductImage> itemImageList) {
		if (itemImageList == null || itemImageList.size() == 0) {
			set("c_pattern_img", null);
			return;
		}
		String jsonText = JSON.toJSONString(itemImageList, true);
		set("c_pattern_img", jsonText);
	}
	
	/**
	 * 判断是否出价过
	 * @param id
	 * @return
	 */
	public boolean isExitPrice(String id){
		
		String sendIngListStore = getStr("priceList");	
		List<PriceList> list = JSON.parseArray(sendIngListStore,PriceList.class);
		if(list!=null){
			for(PriceList ct:list){
				if(ct.getId().equals(id)){
					System.out.println("yes!");
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取出价列表
	 * @return
	 */
	public List<PriceList> getPriceList() {
		String productImageListStore = getStr("c_pattern_img");
		if (StringUtils.isEmpty(productImageListStore)) {
			return null;
		}		
		List<PriceList> list = JSON.parseArray(productImageListStore, PriceList.class); 
		for(PriceList ie:list){
			Price price = new Price();
			price.set("price", ie.getPrice());
			price.set("id",ie.getId());
			price.set("replyDate",ie.getTime());
			price.save();
		}
 		return list;
	}
	
	
	/**
	 * 添加出价信息
	 * @param id
	 * @param price
	 */
	public void addPriceToList(String id,String price,String msg){
		String priceStr = getStr("priceList");
		List<PriceList> priceList =JSON.parseArray(priceStr,PriceList.class);
		if(priceList==null){
			priceList = new ArrayList<PriceList>();
		}
		//Date day = new Date();
		Admin admin = ShiroUtils.getLoginAdmin();
		Items item = Items.dao.findById(id);
		
		PriceList newPrice = new PriceList(new Date(),id,price);
		priceList.add(newPrice);
		Price priceModel = new Price();
		priceModel.set("price", price);
		priceModel.set("msg", msg);
		priceModel.set("itemid",id);
		priceModel.set("replyDate",new Date());
		priceModel.set("factoryid",admin.get("id"));
		priceModel.set("name",admin.get("factory_name"));
		priceModel.set("factorytel",admin.get("factory_tel"));
		priceModel.set("itemtel",admin.getTel(item.getStr("c_userid")));
		priceModel.set("ownerid",item.getStr("c_userid"));
		priceModel.save();
		String jsonText = JSON.toJSONString(priceList, true);
		set("priceList", jsonText);
	}
	
	/**
	 * 设置样衣图片
	 * @param 
	 */
	public void setProductImageList(List<ProductImage> itemImageList) {
		if (itemImageList == null || itemImageList.size() == 0) {
			set("c_clothe_img", null);
			return;
		}
		String jsonText = JSON.toJSONString(itemImageList, true);
		set("c_clothe_img", jsonText);
	}
	
	public void save(Items items) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		items.set("id", CommonUtil.getUUID());
		items.set("htmlFilePath",htmlFilePath);
		items.set("createDate", new Date());
		Admin currentUser = ShiroUtils.getLoginAdmin();//获取当前用户
		items.set("c_userid",currentUser.getStr("u_username"));
		items.save();
	}

	public  Page<Items> paginate( int pageNumber, int pageSize, List<Items> list){
		long totalRow = 0;
		int totalPage = 0;
		int size = list.size();
		if (size >= 1)
			totalRow = size;		// totalRow = (Long)result.get(0);		
		else
			return new Page<Items>(new ArrayList<Items>(0), pageNumber, pageSize, 0, 0);	
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<Items>(new ArrayList<Items>(0), pageNumber, pageSize, totalPage, (int)totalRow);
		return new Page<Items>(list, pageNumber, pageSize, totalPage, (int)totalRow);
	}
}
