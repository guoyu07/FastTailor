package com.jfinal.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dreamlu.event.EventKit;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.jfinalshop.bean.ItemsAttitude;
import com.jfinalshop.bean.ProductImage;
import com.jfinalshop.bean.SystemConfig;
import com.jfinalshop.event.ConfirmPriceEvent;
import com.jfinalshop.event.SearchPriceEvent;
import com.jfinalshop.event.SetPriceEvent;
import com.jfinalshop.model.Admin;
import com.jfinalshop.model.Confirmitems;
import com.jfinalshop.model.Items;
import com.jfinalshop.model.Price;
import com.jfinalshop.security.ShiroUtils;
import com.jfinalshop.service.ProductImageService;
import com.jfinalshop.util.CommonUtil;
@ControllerBind(controllerKey = "/items")
public class ItemsSearchController extends BaseController<Items>{
	
	private List<UploadFile> ItemsImages;	
	private Items items = new Items();
	Map<String, String> searchConditions = new HashMap<String, String>();
	protected String id;
	protected String[] ids;
	/**
	 * 填写搜单条件页面
	 */
	public void index(){
		setAttr("itemStyleList", getItemStyleList());
		setAttr("itemCategoryList", getItemsCategoryList());
		setAttr("itemSoftList", getItemsSoftList());
		render("/items/items_search.html");
	}
	
	/**
	 * 所有搜单记录
	 */
	public void list(){
		findByPageWithItems();
		render("items_history.html");
	}
	
	public void newConfirmed(){
		findNewConfirmedItems();
		render("items_confirmed.html");
	}
	/**
	 * 指定询价单的厂家出价列表
	 */
	public void getPriced(){
		String id = getPara("itemId");
		List<Price> priceList = new ArrayList<Price>();
		priceList = Price.dao.getAllPriceList(id);
		Page<Price> pager = getModel(Price.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),priceList);
		setAttr("pager", pager);
		render("items_price.html");
	}
	
	/**
	 * 用户所有的询价单收到的报价
	 */
	public void getAllPriced(){
		findAllGivenPriceItems();
		render("items_givenprice.html");
	}
	
	public void getMyConfirmed(){
		findeAllAcceptedItems();
		render("items_accepted.html");
		
	}
	public void confirmPrice(){
		String id = getPara("id");
		Price price = Price.dao.findById(id);
		Items item = Items.dao.findById(price.getStr("itemid"));
		String itemId = item.getStr("id");
		Admin factory = Admin.dao.findById(price.getStr("factoryid"));
		item.set("c_payment", true);
		Confirmitems cfi = new Confirmitems();
		boolean flag = Confirmitems.dao.isExit(itemId);
		cfi.set("id", CommonUtil.getUUID()).set("itemid", itemId).set("cfprice", price.get("price")).set("cfDate", new Date()).set("fid", factory.get("id"))
		.set("fromId",item.get("c_userid"));
		if(flag){
			ajaxJsonErrorMessage("您已确认过了！请勿重新确认。");
		}else{
			boolean temp= item.update() && cfi.save();
			if(temp == true){
				EventKit.postEvent(new ConfirmPriceEvent(factory.getStr("u_username")));
				ajaxJsonSuccessMessage("立即通知厂家生产，您的订单编号为："+itemId);
			}else{
				ajaxJsonErrorMessage("确认失败！请联系客服人员");
			}
		}
		
		
	}
	/**
	 * 确认订单
	 */
	public void makePrice(){
		String id = getPara("id");
		Price price = Price.dao.findById(id);
		Items items = Items.dao.findById(price.getStr("itemid"));
		Admin factory = Admin.dao.findById(price.getStr("factoryid"));
		setAttr("items",items);
		setAttr("itemsImageList",items.getItemsImageList());
		setAttr("itemsPatternList",items.getItemsPatternImageList());
		setAttr("price",price);
		setAttr("factory",factory);
		render("items_sum.html");
	}
	/**
	 * 我的出价
	 */
	public void priceView(){
		String id = getPara("id");
		List<Price> priceList = new ArrayList<Price>();
		Admin curUser = ShiroUtils.getLoginAdmin();
		priceList = Price.dao.getOfferPriceList(curUser.getStr("id"), id);
		Page<Price> pager = getModel(Price.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),priceList);
		setAttr("pager", pager);
		render("items_myprice.html");
	}
	/**
	 * 修改询价单
	 */
	public void edit(){
		String orderId = getPara("id", "");
		if (StrKit.notBlank(orderId)) {
			items = Items.dao.findById(orderId);
		}
	
		setAttr("items", items);
		setAttr("itemsImageList",items.getItemsImageList());
		setAttr("itemsPatternList",items.getItemsPatternImageList());
		setAttr("itemStyleList", getItemStyleList());
		setAttr("itemCategoryList", getItemsCategoryList());
		setAttr("itemSoftList", getItemsSoftList());
		render("/items/items_edit.html");
	
	}
	
	/**
	 * 展示所有来自用户的询价单
	 */
	public void info(){
		findWithUnread();
		render("/items/items_allinfo.html");
	}
	
	
	public void removeDelItem(){
		String id = getPara("id");
		Items item = Items.dao.findById(id);
		Admin curUser = ShiroUtils.getLoginAdmin();
		Admin admin = Admin.dao.findById(curUser.get("id"));
		admin.removeSearchItemsFromList(item);
		if(admin.update()){
			ajaxJsonSuccessMessage("系统自动移除本条记录！");
		}
	}
	/**
	 * 厂家出价
	 */
	public void setPrice(){	
		String price = getPara("price");
		String msg = getPara("msg");
		String id = getPara("itemId");
		Items item = Items.dao.findById(id);
		
		if(item.getBoolean("isDelete")){
			Admin curUser = ShiroUtils.getLoginAdmin();
			Admin admin = Admin.dao.findById(curUser.get("id"));
			admin.removeSearchItemsFromList(item);
			if(admin.update()){
				ajaxJsonErrorMessage("对方已撤销订单，出价失败");
			}
					
		}else if(item.isExitPrice(id)){
			ajaxJsonErrorMessage("您已出过价，请勿重复提交");
		}else{
			item.addPriceToList(id, price, msg);
			item.set("c_state", true);
			boolean temp = item.update();
			if(temp){
				System.out.println("厂家出价中！");
				 EventKit.postEvent(new SetPriceEvent(item.getStr("c_userid")));
				ajaxJsonSuccessMessage("出价成功！");
				
			}else{
				ajaxJsonErrorMessage("系统出错，请稍后重试！");
			}
			
		}
		
		
	}
	/**
	 * 修改搜单
	 * ！注意：******如果订单成交则不允许修改。需要实现判断*********
	 */
	public void update(){
		ItemsImages = getFiles();
		Items items = getModel(Items.class,"items");
		if(ItemsImages!=null && ItemsImages.size()>0 ){
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if(StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return;
			}
			for(int i = 0; i < ItemsImages.size(); i++){
					File images = ItemsImages.get(i).getFile();
					String productImageExtension =  StringUtils.substringAfterLast(images.getName(), ".").toLowerCase();
					String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
					if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
						addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
						return;
					}
					if (getSystemConfig().getUploadLimit() != 0 && images.length() > getSystemConfig().getUploadLimit() * 1024) {
						addActionError("此上传文件大小超出限制!");
						return;
					}
			}
		}
				
		List<ProductImage> itemsImageList = new ArrayList<ProductImage>();  
		if (ItemsImages != null && ItemsImages.size() > 0) {
			for(int i = 0; i < ItemsImages.size(); i ++) {					
				ProductImage productImage = ProductImageService.service.buildProductImage(ItemsImages.get(i).getFile());
				itemsImageList.add(productImage);										
			}
		}	
		items.setItemImageList(itemsImageList);
		items.set("modifyDate", new Date());
		items.update();
		redirect("/items/list");
		
	}
	/**
	 * 搜单详细信息
	 */
	public void view(){
		String orderId = getPara("items.id", "");
		if (StrKit.notBlank(orderId)) {
			items = Items.dao.findById(orderId);
		}
		Admin curUser = ShiroUtils.getLoginAdmin();
		setAttr("role",curUser.get("role"));
		setAttr("items", items);
		setAttr("itemsImageList",items.getItemsImageList());
		setAttr("itemsPatternList",items.getItemsPatternImageList());
		render("items_detail.html");
	}
	
	/**
	 * 我的搜单记录
	 */
	public void searchHistory(){
		String searchId = getPara("items.id", "");
		Items searchItem = Items.dao.findById(searchId);
		Integer resultMax = searchItem.getInt("s") + searchItem.getInt("m") + searchItem.getInt("l") + searchItem.getInt("xl");
		String type = searchItem.getStr("c_type");
		String category = searchItem.getStr("c_category");
		String soft = searchItem.getStr("c_isbeoffer");
		
		List<Admin> underLimitFactoryList = new ArrayList<Admin>();
		underLimitFactoryList = Admin.dao.getUnderMaxAdmin(resultMax.toString());
		//System.out.println("before"+underLimitFactoryList.size());
		for(int i=0; i<underLimitFactoryList.size(); i++){
			String conds = underLimitFactoryList.get(i).getConditions();
			if(!conds.contains(type) || !conds.contains(category) || !conds.contains(soft)){
				underLimitFactoryList.remove(i);
			}
		}
		//System.out.println("after"+underLimitFactoryList.size());
		Page<Admin> pager = getModel(Admin.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),underLimitFactoryList);
		setAttr("pager", pager);
		setAttr("searchId",searchId);
		render("items_results.html");
	}
	
	/**
	 * 开始搜单，生产搜单记录
	 */
	public void search(){
		ItemsImages = getFiles();
	
		items = getModel(Items.class);
		//图片处理

		if(ItemsImages!=null && ItemsImages.size()>0 ){
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if(StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return;
			}
			for(int i = 0; i < ItemsImages.size(); i++){
				File images = ItemsImages.get(i).getFile();
				String productImageExtension =  StringUtils.substringAfterLast(images.getName(), ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return;
				}
				if (getSystemConfig().getUploadLimit() != 0 && images.length() > getSystemConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return;
				}
			}
		}
		
		List<ProductImage> productImageList = new ArrayList<ProductImage>(); //样衣图片
		List<ProductImage> itemsImageList = new ArrayList<ProductImage>();  //样衣图案
		if (ItemsImages != null && ItemsImages.size() > 0) {
			for(int i = 0; i < ItemsImages.size(); i ++) {
				String fileCategory = ItemsImages.get(i).getParameterName();
				ProductImage productImage = ProductImageService.service.buildProductImage(ItemsImages.get(i).getFile());
				if(StringUtils.contains(fileCategory, "productImages")){
					productImageList.add(productImage);
				} else{
					itemsImageList.add(productImage);
				}
				
			}
		}	
	
		items.setItemImageList(itemsImageList);
		items.setProductImageList(productImageList);
		items.save(items);
		
		//加入搜索条件
		Integer resultMax = getParaToInt("items.s")+getParaToInt("items.m")+getParaToInt("items.l")+getParaToInt("items.xl");
		String type = getPara("items.c_type");
		String category = getPara("items.c_category");
		String soft = getPara("items.c_isbeoffer");
		
		List<Admin> underLimitFactoryList = new ArrayList<Admin>();
		underLimitFactoryList = Admin.dao.getUnderMaxAdmin(resultMax.toString());
		for(int i=0; i<underLimitFactoryList.size(); i++){
			String conds = underLimitFactoryList.get(i).getConditions();
			if(!conds.contains(type) || !conds.contains(category) || !conds.contains(soft)){
				underLimitFactoryList.remove(i);
			}
		}
		setAttr("searchId",items.getStr("id"));
		Page<Admin> pager = getModel(Admin.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),underLimitFactoryList);
		setAttr("pager", pager);
		render("items_results.html");
	}
	
	public void getItemsJson(){
		Integer start = getParaToInt("start");
		Integer count = getParaToInt("count");
		List<Items> radomList = new ArrayList<Items>();
		radomList = Items.dao.getNewestItems(start,count);
		renderJson("LIST",radomList);
	}
	public void getOneItem(){
		render("items_random.html");
	}
	/**
	 * 返回未回复的售价单数目
	 */
	public void findWithUnread(){
		Admin curUser = ShiroUtils.getLoginAdmin();
		String id = curUser.get("id");
		Admin admin = Admin.dao.findById(id);
		List<ItemsAttitude> list = admin.getSearchItemsList();
		List<Items> underLimitFactoryList = new ArrayList<Items>();
		if(list!=null && list.size()>0){
			for(ItemsAttitude ie:list){
				Items item = Items.dao.findById(ie.getId());
//				System.out.println(ie.getCreateDate()+ie.getId());
				if(item!=null){
					if(item.get("shortDate")==null){
						item.set("shortDate", ie.getCreateDate());
						item.update();
					}
					
					underLimitFactoryList.add(item);
				}
			}	
		}
				
		Page<Items> pager = getModel(Items.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),underLimitFactoryList);
		setAttr("pager", pager);
	}

	/**
	 * 通用分页查找
	 */
	public void findByPage() {
		String select = "select *";
		String sqlExceptSelect = "from " + "admin" + " where 1 = 1 ";
		if (StrKit.notBlank(getProperty()) && StrKit.notBlank(getKeyword())) {
			sqlExceptSelect += "and " + getProperty() + " like '%" + getKeyword() + "%'";
		}		
		sqlExceptSelect += " order by id desc ";				
		Page<Admin> pager = getModel(Admin.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), select, sqlExceptSelect);
		setAttr("pager", pager);
	}
	
	/**
	 * 查找当前用户所有搜单记录
	 */
	public void findByPageWithItems() {
		String currentUserName = getLoginAdminName();
		String select = "select *";
		String sqlExceptSelect = "from " + "items" + " where 1 = 1 ";
		
		sqlExceptSelect += "and " + "c_userid" + " like '%" + currentUserName + "%'"
				+ "and isDelete = b'0' ";
			
		sqlExceptSelect += " order by createDate desc ";				
		Page<Items> pager = getModel(Items.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), select, sqlExceptSelect);
		setAttr("pager", pager);
		
	}
	
	/**
	 * 查找当前用户所有已有报价的记录
	 */
	public void findAllGivenPriceItems() {
		String currentUserName = getLoginAdminName();
		List<Price> priceList = new ArrayList<Price>();
		priceList = Price.dao.findByOwnerId(currentUserName);
		Page<Price> pager = getModel(Price.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),priceList);
		setAttr("pager", pager);
		
	}
	
	/**
	 * 获取用户确认过的订单
	 */
	public void findNewConfirmedItems(){
		String fid = getLoginAdminId();
		List<Confirmitems> cfedList = new ArrayList<Confirmitems>();
		cfedList = Confirmitems.dao.getConfirmedItemsByFId(fid);
		Page<Confirmitems> pager = getModel(Confirmitems.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),cfedList);
		setAttr("pager", pager);
		
	}
	
	/**
	 * 用户获取自己确认过的订单
	 */
	public void findeAllAcceptedItems(){
		String fid = getLoginAdminName();
		List<Confirmitems> accList = new ArrayList<Confirmitems>();
		accList = Confirmitems.dao.getAcceptedItems(fid);	
		Page<Confirmitems> pager = getModel(Confirmitems.class).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),accList);
		setAttr("pager", pager);
	}
	/**
	 * 点击发起询价：向厂方发出询价请求
	 */
	public void searchPrice(){
		String searchId = getPara("searchId");
		String factoryId = getPara("factoryId");
		Admin currentFactory = Admin.dao.findById(factoryId);
		Items currentItems = Items.dao.findById(searchId);
		if(!currentFactory.getBoolean("isAccept")){
			ajaxJsonWarnMessage("厂家当前不接受订单！");
		}else if(currentItems!=null && currentFactory.isExitItems(searchId)){
			ajaxJsonWarnMessage("您已经提交过寻价单，请勿重复提交！");
		}else{
			currentFactory.addSearchItemsToList(currentItems);
			boolean temp = currentFactory.update();
			if (temp) {
			    EventKit.postEvent(new SearchPriceEvent(currentFactory.getStr("u_username")));
			}
			ajaxJsonWarnMessage("成功发送您的询价单！询价单号是:"+currentItems.getStr("id"));
		}
		
		
	}
	
	/**
	 * 批量发出询价单
	 */
	public void getPriceAll(){
		ids = getParaValues("ids");
		String searchId = getPara("searchId");
		Items items = Items.dao.findById(searchId);
		if (ids != null && ids.length > 0) {
			for (String id : ids) {			
				Admin admin = Admin.dao.findById(id);
				if(!admin.getBoolean("isAccept")){
					ajaxJsonWarnMessage("厂家当前不接受订单！");
				}else if(admin!=null && admin.isExitItems(searchId)){
					ajaxJsonWarnMessage("您已经提交过寻价单，请勿重复提交！");
				}else{
					admin.addSearchItemsToList(items);
					boolean temp = admin.update();
					if(temp){
						  EventKit.postEvent(new SearchPriceEvent(admin.getStr("u_username")));
					}
					ajaxJsonWarnMessage("成功发送您的询价单！询价单号是:"+items.getStr("id"));
				}		
			}
			
		}
	}
	
	/**
	 * 批量删除:仅作标记处理，表明记录isDelete
	 */
	public void delete(){
		ids = getParaValues("ids");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				Items items = Items.dao.findById(id);
				items.set("isDelete", true);
				if(items.update()){
					ajaxJsonSuccessMessage("删除成功！");
				}else{
					ajaxJsonSuccessMessage("删除失败！");
				}			
			}
			
		}
	}
	
	/**
	 * 删除单条记录
	 */
	public void deleteByOne(){
		id = getPara("id");		
		Items items = Items.dao.findById(id);
		items.set("isDelete", true);
		if(items.update()){
			ajaxJsonSuccessMessage("删除成功！");
		}else{
			ajaxJsonSuccessMessage("删除失败！");
		}			
		
	}

}
