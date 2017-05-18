package com.jfinal.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.jfinal.aop.Duang;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.upload.UploadFile;
import com.jfinalshop.bean.ConditionConfig;
import com.jfinalshop.bean.ProductImage;
import com.jfinalshop.bean.SystemConfig;
import com.jfinalshop.model.Admin;
import com.jfinalshop.model.Confirmitems;
import com.jfinalshop.model.Items;
import com.jfinalshop.model.ItemsCategory;
import com.jfinalshop.model.ItemsSoft;
import com.jfinalshop.model.ItemsStyle;
import com.jfinalshop.security.ShiroUtils;
import com.jfinalshop.service.MenuService;
import com.jfinalshop.service.ProductImageService;

@ControllerBind(controllerKey = "/center")
public class CenterController  extends BaseController<Admin>{
	
	private UploadFile headImg;
	private String headImhPath="";
	private List<UploadFile> ItemsImages;
	private Admin admin;
	private MenuService menuService = Duang.duang(MenuService.class);

	public void search(){
		render("/items/items_search.html");
	}
    // 后台首页
	@RequiresRoles(value={"ROLE_ADMIN","ROLE_SERVICE"},logical=Logical.OR)
	@RequiresPermissions("admin")
	public void index(){	
		setAttr("userRole",getLoginAdminRole());
		setAttr("java_version", System.getProperty("java.version"));
		setAttr("os", System.getProperty("os.name"));
		setAttr("os_arch", System.getProperty("os.arch"));
		setAttr("os_version", System.getProperty("os.version"));
		setAttr("user_dir", System.getProperty("user.dir"));
		setAttr("tmpdir", System.getProperty("java.io.tmpdir"));		
		setAttr("unprocessedOrderCount", getUnprocessedOrderCount());// 未处理订单
		setAttr("paidUnshippedOrderCount", getPaidUnshippedOrderCount()); // 等待发货订单数
		setAttr("paidUnshippedOrderCount2", getPaidUnshippedOrderCount2()); // 等待发货订单数
		setAttr("unreadMessageCount", getUnreadMessageCount()); // 未读消息
		setAttr("storeAlertCount", getStoreAlertCount()); // 商品库存报警
		
	//			setAttr("marketableProductCount", getMarketableProductCount()); // 已上架商品
	//			setAttr("unMarketableProductCount", getUnMarketableProductCount()); // 已下架商品
	//			setAttr("memberTotalCount", getMemberTotalCount()); // 会员总数
	//			setAttr("articleTotalCount", getArticleTotalCount()); // 文章总数
		
		render("/center/admin_index.html");
	}	

	public Long getUnprocessedOrderCount(){
		Admin currentUser = ShiroUtils.getLoginAdmin();
		return currentUser.getUnreadOrderCount();
	}
		
	public Long getPaidUnshippedOrderCount(){
	
			return (long) Confirmitems.dao.getCount(getLoginAdminName());
	}
	
	public Long getPaidUnshippedOrderCount2(){
		
		return (long) Confirmitems.dao.getCount2(getLoginAdminId());
}
	public Long getUnreadMessageCount(){
		return (long) 1;
	}
	public Long getStoreAlertCount(){
		return (long) 1;
	}
	public void center(){
		
		setAttr("menuList",menuService.getMenuTree());
		setAttr("loginUsername", getLoginAdminName());
		setAttr("userRole", getLoginAdminRole());
		setAttr("loginUserImg",getLoginAdminAvatar());
		setAttr("countMail",getUnreadMessageCount());
		render("/center/admin_center.html");
	}
	
	/**
	 * 用户基本信息界面
	 */
	public void user(){
		
		Admin currentUser = ShiroUtils.getLoginAdmin();
		String id = currentUser.getStr("id");
		Admin admin = Admin.dao.findById(id);
		setAttr("admin",admin);
		setAttr("productImageList",currentUser.getFactoryImageList());
		setAttr("loginUserImg",getLoginAdminAvatar());
		render("/center/center_user.html");
	}
	
	/*
	 * 第三方查看用户信息界面
	 */
	public void userinfo(){
		String username =  getPara("id");
		Admin userInfo = Admin.dao.getAdminByUsername(username);
		setAttr("userInfo",userInfo);
		render("/center/center_userinfo.html");
	}
	/**
	 * 用户头像上传
	 */
	public void saveimg(){
		
		headImg = getFile();//表单里file必须加name属性 否则获取不到文件
		admin = ShiroUtils.getLoginAdmin();
		if(headImg!=null){
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if(StringUtils.isEmpty(allowedUploadImageExtension)){
				addActionError("不允许上传图片文件!");
				return;
			}
			File images = headImg.getFile();
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
			ProductImage productImage = ProductImageService.service.buildProductImage(images);
			headImhPath = productImage.getSmallProductImagePath();
		}
		
		admin.set("u_avatar_url", headImhPath);
		admin.update();
		redirect("/center/user");
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
			currentFactory.update();
			ajaxJsonWarnMessage("成功发送您的询价单！");
		}
		
		
	}
	
	/**
	 * 查看厂方基本信息
	 */
	public void detail(){
		String id = getPara("factoryId");
		String searchId = getPara("searchId");
		Admin factory = Admin.dao.findById(id);
		setAttr("factoryImageList",factory.getFactoryImageList());
		setAttr("factory",factory);
		setAttr("searchId",searchId);
		render("/center/center_factory_detail.html");
	}
	
	/**
	 * 厂方接单设置
	 */
	public void setting(){
		Admin currentUser = ShiroUtils.getLoginAdmin();
		String id = currentUser.getStr("id");
		Admin admin = Admin.dao.findById(id);
		setAttr("admin",admin);
		setAttr("itemStyleList", getItemStyleList());
		setAttr("itemCategoryList", getItemsCategoryList());
		setAttr("itemSoftList", getItemsSoftList());
		setAttr("conditonsText",admin.getConditions());
		render("/center/center_factory.html");
	}
	
	/**
	 * 厂方资料设置
	 */
	public void saveFactory(){
		ItemsImages = getFiles();
		//System.out.println("========="+ItemsImages.size());
		admin = ShiroUtils.getLoginAdmin();
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
		admin.setFactoryImageList(itemsImageList);
		admin.update();
		render("/center/user");
	}
	
	
	/**
	 * 保存用户信息
	 */
	public void saveUser(){
		
		ItemsImages = getFiles();
		admin = getModel(Admin.class,"admin");
		//System.out.print("===="+admin.getStr("u_email"));
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
		admin.setFactoryImageList(itemsImageList);
		if(admin.update()){
			ajaxJsonSuccessMessage("修改成功！");
		}else{
			ajaxJsonErrorMessage("系统异常！修改失败！");
		}
		
		redirect("/center/user");
	}
	
	
	/**
	 * 保存搜索条件
	 */
	public void saveConditions(){
		Admin admin = getModel(Admin.class);
		List<ItemsStyle> styleList = getItemStyleList();
		List<ItemsCategory> cateList = getItemsCategoryList();
		List<ItemsSoft> softList = getItemsSoftList();
		String conditions="";
		for(ItemsStyle x:styleList){
			String str = getPara((String)x.getStr("name"));
			if(str!=null)
				conditions+=str;
		}
		for(ItemsCategory x:cateList){
			String str = getPara((String)x.getStr("name"));
			if(str!=null)
				conditions+=str;
		}
		for(ItemsSoft x:softList){
			String str = getPara((String)x.getStr("name"));
			if(str!=null)
				conditions+=str;
		}
		ConditionConfig cfg = ConditionConfig.service.buildCondition(conditions, getParaToInt("admin.maxNumber"));
		String isAccept = getPara("isAccept");
		if(isAccept!=null){
			admin.set("isAccept", isAccept.equals("on")?true:false);
		}else{
			admin.set("isAccept",false);
		}
		admin.setConditions(cfg);
		if(admin.update()){
			ajaxJsonSuccessMessage("保存成功！");
		}else{
			ajaxJsonSuccessMessage("保存失败！");
		}	
		redirect("/center/setting");
	}

	
}
