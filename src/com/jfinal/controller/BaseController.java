package com.jfinal.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.bean.SystemConfig;
import com.jfinalshop.interceptor.AdminInterceptor;
import com.jfinalshop.model.ItemsCategory;
import com.jfinalshop.model.ItemsSoft;
import com.jfinalshop.model.ItemsStyle;
import com.jfinalshop.security.ShiroUtils;
import com.jfinalshop.util.SystemConfigUtil;

@Before(AdminInterceptor.class)
public class BaseController <M extends Model<M>> extends Controller {

	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	
	protected String id;
	protected String[] ids;
	protected String redirectionUrl = "redirectionUrl";// 操作提示后的跳转URL,为null则返回前一页
	

	// 排序方式
	public enum OrderType{
		asc, desc
	}
	private String property;// 查找属性名称
	private String keyword;// 查找关键字
	private String orderBy;// 排序字段
	private String orderType;// 排序方式
	
	public BaseController() {
		// 把class的变量保存起来，不用每次去取
		this.setModelClass(getClazz());
	}
	
	/**
	 * 获取M的class
	 * 
	 * @return M
	 */
	@SuppressWarnings("unchecked")
	public Class<M> getClazz() {
		Type t = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) t).getActualTypeArguments();
		return (Class<M>) params[0];
	}
		
	protected Class<M> modelClass;

	public Class<M> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<M> modelClass) {
		this.modelClass = modelClass;
	}
	
	public String getProperty() {
		property = getPara("property","");
		return property;
	}

	public String getKeyword() {
		keyword = getPara("keyword","");
		return keyword;
	}

	public String getOrderBy() {
		orderBy = getPara("orderBy","createDate");
		return orderBy;
	}

	public String getOrderType() {
		orderType = getPara("orderType",OrderType.desc.name());
		return orderType;
	}
	
	
	/**
	 * 
	 * 所有服装种类
	 */
	public List<ItemsStyle> getItemStyleList(){
		return ItemsStyle.dao.find("select * from itemsstyle order  by id");
	}
	
	/**
	 * 所有服装品类
	 */
	public List<ItemsCategory> getItemsCategoryList(){
		return ItemsCategory.dao.getAll();
	}
	
	public List<ItemsSoft> getItemsSoftList(){
		return ItemsSoft.dao.getAll();
	}
	
	// 获取系统配置信息
	public SystemConfig getSystemConfig() {
		return SystemConfigUtil.getSystemConfig();
	}		

	public void addActionError(String error){
		setAttr("errorMessages", error);
		render("/login/error.html");	
	}
	
	
	// 输出JSON成功消息，返回null
	public void ajaxJsonSuccessMessage(String message) {
		setAttr(STATUS, SUCCESS);
		setAttr(MESSAGE, message);
		renderJson();
	}

	// 输出JSON错误消息，返回null
	public void ajaxJsonErrorMessage(String message) {
		setAttr(STATUS, ERROR);
		setAttr(MESSAGE, message);
		renderJson();
	}
	
	// 输出JSON警告消息，返回null
	public void ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		renderJson(jsonMap);
	}
	public void renderErrorMessage(String message) {
		setAttr("errorMessages", message);
		setAttr("systemConfig", SystemConfigUtil.getSystemConfig());
		setAttr("base", getRequest().getContextPath());
		render("/login/error.html");
	}
	
	/**
	 * 通用修改
	 * 
	 */
	public void updated(Model<M> model){
		model.set("modifyDate", new Date());
		model.update();
	}
	
	
	/**
	 * 获取当前登录管理员,若未登录则返回null.
	 * 
	 * @return 当前登录管理员名称
	 */
	public String getLoginAdminName() {
		String loginAdminName = ShiroUtils.getLoginAdminName();
		if (StrKit.isBlank(loginAdminName)) {
			return null;
		} else {
			return loginAdminName;
		}
	}
	
	public String getLoginAdminId() {
		return ShiroUtils.getLoginAdminId();
	}
	
	/**
	 * 获取当前登录管理员权限,若未登录则返回null.
	 * 
	 * @return 当前登录管理员名称
	 */
	public Integer getLoginAdminRole() {
		return  ShiroUtils.getLoginAdminRole();
		
	}
	
	/**
	 * 获取当前登录用户头像.
	 * 
	 * @return 当前登录用户头像 
	 */
	public String getLoginAdminAvatar() {
		String loginAdminHeadImg = ShiroUtils.getAdminHeadImg();
		if (StrKit.isBlank(loginAdminHeadImg)) {
			return null;
		} else {
			return loginAdminHeadImg;
		}
	}
	
	
	/**
	 * 通用分页查找
	 */
	public void findByPage() {
		String select = "select *";
		String sqlExceptSelect = "from " + getModelClass().getSimpleName() + " where 1 = 1 ";
		if (StrKit.notBlank(getProperty()) && StrKit.notBlank(getKeyword())) {
			sqlExceptSelect += "and " + getProperty() + " like '%" + getKeyword() + "%'";
		}		
		sqlExceptSelect += " order by createDate desc ";				
		Page<M> pager = getModel(getModelClass()).paginate(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), select, sqlExceptSelect);
		setAttr("pager", pager);
	}
}
