package com.jfinalshop.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.jfinalshop.util.SystemConfigUtil;

public class MemberValidator extends Validator {

	protected String actionKey;
	@Override
	protected void validate(Controller c) {
		actionKey = getActionKey();
		if (actionKey.equals("/login/registerNow")) { // 注册验证
    		validateRequiredString("admin.u_username", "errorMessages", "用户名不允许为空!");
        	validateRequiredString("admin.u_pwd", "errorMessages", "密码不允许为空!");
    		//validateRequiredString("admin.email", "errorMessages", "E-mail不允许为空!");    		
    		//validateString("admin.username", 2, 20, "errorMessages", "用户名长度必须在【2】到【20】之间!");
    		//validateString("admin.password", 4, 20, "errorMessages", "密码长度必须在【4】到【20】之间!");    		
    		//validateEmail("admin.email", "errorMessages", "E-mail格式错误!");
    		//validateRegex("admin.username", "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", "errorMessages", "用户名只允许包含中文、英文、数字和下划线!");    	
    		validateEqualField("admin.u_pwd", "passworded", "errorMessages", "两次密码输入不一致");
    	
    	} 
	}

	@Override
	protected void handleError(Controller c) {
	

		c.setAttr("systemConfig", SystemConfigUtil.getSystemConfig());
	
		if (actionKey.equals("/shop/member/ajaxRegister")) { // 注册验证
    		c.renderText("注册信息错误!");
    	} else if (actionKey.equals("/shop/member/ajaxLogin")) { // Ajax会员登录验证	
    		c.renderText("Ajax会员登录验证失败");
    	} else {
    		c.render("/login/error.html");
    	}

	}

}
