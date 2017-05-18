package com.jfinalshop.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.bean.ConditionConfig;
import com.jfinalshop.bean.ItemsAttitude;
import com.jfinalshop.bean.ProductImage;
import com.jfinalshop.util.CommonUtil;

/**
 * 实体类 - 管理员
 * 
 */
public class Admin extends Model<Admin>{

	private static final long serialVersionUID = -57555379613217315L;
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	
	public static final Admin dao = new Admin();
	public String getTel(String username){
		String sql = "select * from admin where u_username='" + username +"'";
		Admin admin = this.dao.findFirst(sql);
		return admin.getStr("u_tel");
	}
	
	public List <Admin> getUnderMaxAdmin(String max){
		String sql = "select * from admin "
				+"where maxItem >= '" +max+"'";
		return Admin.dao.find(sql);
				
	}
	
	public List<Role> getRoleList() {
		String sql ="" 
			 +" select r.*"
			 +"  from admin_role a left outer join role r on a.roleset_id = r.id " 
			 +" where a.adminset_id = ?"; 
		return Role.dao.find(sql,getStr("id"));
	}
	
	/**
	 * 根据用户名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username) {
		return dao.findFirst("select * from admin where u_username = ?",username);
	}
	
	/**
	 * 根据用户名、密码验证管理员
	 * 
	 * @param username
	 *            用户名
	 *            
	 * @param password
	 *            密码
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifyAdmin(String username, String password) {
		Admin member = getAdminByUsername(username);
		if (member != null && member.getStr("password").equals(DigestUtils.md5Hex(password))) {
			return true;
		} else {
			return false;
		}
	}
	
		
	/**
	 * 根据用户ID查询该用户所拥有的角色列表
	 * @param userId
	 * @return
	 */
	public List<String> getRolesName(String adminId) {
		String sql =  "" 
				 +"select r.value"
				 +"  from admin a" 
				 +"  left outer join admin_role ar" 
				 +"    on a.id = ar.adminset_id" 
				 +"  left outer join role r" 
				 +"    on r.id = ar.roleset_id" 
				 +" where a.id = ?";

		return Db.query(sql, adminId);
	}
	
	/**
	 * 根据用户ID查询该用户所拥有的权限列表
	 * @param userId
	 * @return
	 */
	public List<String> getAuthoritiesName(String adminId) {
		String sql = "" 
				 + "select a.value"
				 +"  from admin u" 
				 +"  left outer join admin_role ru" 
				 +"    on u.id = ru.adminset_id" 
				 +"  left outer join role r" 
				 +"    on ru.roleset_id = r.id" 
				 +"  left outer join role_resource ra" 
				 +"    on r.id = ra.roleset_id" 
				 +"  left outer join resource a" 
				 +"    on ra.resourceset_id = a.id" 
				 +" where u.id = ?";		
		return Db.query(sql, adminId);
	}
	
	public void save(Admin admin){
		admin.set("id", CommonUtil.getUUID());
		admin.save();
	}
	
	/**
	 * 获取所有询价单
	 * @return
	 */
	public List<Items> getSendingItemsList(){
		String sendIngListStore = getStr("sending");
		if (StringUtils.isEmpty(sendIngListStore)) {
			return null;
		}		
		List<Items> list = JSON.parseArray(sendIngListStore,Items.class);
		return list;
	}
	
	public Long getUnreadOrderCount(){
		String sendIngListStore = getStr("sending");	
		List<ItemsAttitude> list = JSON.parseArray(sendIngListStore,ItemsAttitude.class);
		long count=0;
		if(list!=null){
			for(ItemsAttitude ct:list){
				if(ct.getDeal()==1){					
					count++;
				}
			}
		}
		return count;
	}
	
	
	public List<ItemsAttitude> getSearchItemsList(){
		String sendIngListStore = getStr("sending");
		if (StringUtils.isEmpty(sendIngListStore)) {
			return null;
		}		
		List<ItemsAttitude> list = JSON.parseArray(sendIngListStore,ItemsAttitude.class);
		return list;
	}
	/**
	 * 判断是否提交过搜价单
	 * @param id
	 * @return
	 */
	public boolean isExitItems(String id){
		
		String sendIngListStore = getStr("sending");	
		List<ItemsAttitude> list = JSON.parseArray(sendIngListStore,ItemsAttitude.class);
		if(list!=null){
			for(ItemsAttitude ct:list){
				if(ct.getId().equals(id)){
					System.out.println("yes!");
					return true;
				}
			}
		}
		return false;
	}
	public void addSearchItemsToList(Items searchItem){
		String sendIngListStore = getStr("sending");	
		List<ItemsAttitude> list = JSON.parseArray(sendIngListStore,ItemsAttitude.class);
		if(list==null){
			list = new ArrayList<ItemsAttitude>();
		}	
		ItemsAttitude ia = new ItemsAttitude(searchItem.getStr("id"),new Date(),1);
		list.add(ia);
		String jsonText = JSON.toJSONString(list, true);
		set("sending", jsonText);
		
	}
	
	public void removeSearchItemsFromList(Items searchItem){
		String sendIngListStore = getStr("sending");	
		List<ItemsAttitude> list = JSON.parseArray(sendIngListStore,ItemsAttitude.class);
		if(list!=null){
			for(int i=0; i<list.size(); i++){
				if(searchItem.get("id").equals(list.get(i).getId())){
					list.remove(i);
				}
			}
		}	
		System.out.println(list.size()+"all");
		String jsonText = JSON.toJSONString(list, true);
		set("sending", jsonText);
		
	}
	/**
	 * 获取厂家图片
	 * @return
	 */
	public List<ProductImage> getFactoryImageList() {
		String productImageListStore = getStr("factory_img");
		if (StringUtils.isEmpty(productImageListStore)) {
			return null;
		}		
		List<ProductImage> list = JSON.parseArray(productImageListStore, ProductImage.class); 		
 		return list;
	}
	
	/**
	 * 设置厂家图片
	 * @param itemImageList
	 */
	public void setFactoryImageList(List<ProductImage> itemImageList) {
		if (itemImageList == null || itemImageList.size() == 0) {
			set("factory_img", null);
			return;
		}
		String jsonText = JSON.toJSONString(itemImageList, true);
		set("factory_img", jsonText);
	}
	
	public void setConditions(ConditionConfig cfg){
		if(cfg==null){
			set("conditionKey",null);
			return;
		}
		String jsonText = JSON.toJSONString(cfg, true);
		set("conditionKey",jsonText);
	}
	
	public String getConditions(){
		String cond = getStr("conditionKey");
		//System.out.println(cond);
		if (StringUtils.isEmpty(cond)) {
			return null;
		}	
		ConditionConfig cfg = JSON.parseObject(cond,ConditionConfig.class);
		return cfg.getConditions();
	}
	
	public  Page<Admin> paginate( int pageNumber, int pageSize, List<Admin> list){
		long totalRow = 0;
		int totalPage = 0;
		int size = list.size();
		if (size >= 1)
			totalRow = size;		// totalRow = (Long)result.get(0);		
		else
			return new Page<Admin>(new ArrayList<Admin>(0), pageNumber, pageSize, 0, 0);	
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<Admin>(new ArrayList<Admin>(0), pageNumber, pageSize, totalPage, (int)totalRow);
		return new Page<Admin>(list, pageNumber, pageSize, totalPage, (int)totalRow);
	}
}
