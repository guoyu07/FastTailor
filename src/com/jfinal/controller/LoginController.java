package com.jfinal.controller;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.dreampie.captcha.CaptchaRender;
import cn.dreampie.shiro.core.SubjectKit;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.ext.plugin.shiro.ShiroMethod;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalshop.bean.SystemConfig;
import com.jfinalshop.model.Admin;
import com.jfinalshop.util.SystemConfigUtil;
import com.jfinalshop.validator.MemberValidator;

public class LoginController extends BaseController<Admin> {
	
	private Admin admin;
	// 登录页面
	@Clear
	public void login(){
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		setAttr("systemConfig", systemConfig);
		setAttr("base", getRequest().getContextPath());
		render("/login/login.html");
	}

	
	// 管理员退出
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (SecurityUtils.getSubject().getSession() != null) {
			currentUser.logout();
		}
		redirect("/login/login");
	}
	
	@Clear
	public void register(){
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		setAttr("systemConfig", systemConfig);
		setAttr("base", getRequest().getContextPath());
		render("/login/register.html");
	}
	@Clear
	@Before({MemberValidator.class,Tx.class})
	public void registerNow(){
		admin = getModel(Admin.class);
		String username = admin.getStr("u_username");
		if(admin.getAdminByUsername(username)!=null){
			renderErrorMessage("该用户已存在，请重新注册!");
			return;
		}
		admin.set("createDate", new Date());
		admin.save(admin);
		render("/login/login.html");
	}
	@Clear
	public void singIn(){
		String username = getPara("username","");
		String password = getPara("password","");		
		String captchaToken = getPara("captchaToken");
		boolean rememberMe = getPara("remember","").equals("on") ? true : false;
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			renderErrorMessage("账号或密码不能为空!");
			return;
		}
		
		if (!SubjectKit.doCaptcha("captcha", captchaToken)) {
			renderErrorMessage("验证码错误!");
			return;
		}
		Admin admin = Admin.dao.getAdminByUsername(username);	
		// 开始验证
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		try {
			subject.login(token);
		} catch (UnknownAccountException ue) {
			token.clear();
			renderErrorMessage("登录失败，您输入的账号不存在!");
			return;
		} catch (IncorrectCredentialsException ie) {
			token.clear();			
			renderErrorMessage("您的用户名或密码错误!");
		
		}  catch (RuntimeException re) {
			re.printStackTrace();
			token.clear();
			renderErrorMessage("登录失败");
			return;
		}
		// 登录成功后
		if(ShiroMethod.authenticated()) {
			admin.set("loginDate", new Date());
			admin.set("loginIp", getRequest().getRemoteAddr());	
			updated(admin);
			redirect("/center/center");
		} 
			
	}
	/**
	 * 验证码
	 */
	@Clear
	public void captcha() {
		int width = 0, height = 0, minnum = 0, maxnum = 0, fontsize = 0;
		CaptchaRender captcha = new CaptchaRender();
		if (isParaExists("width")) {
			width = getParaToInt("width");
		}
		if (isParaExists("height")) {
			height = getParaToInt("height");
		}
		if (width > 0 && height > 0)
			captcha.setImgSize(width, height);
		if (isParaExists("minnum")) {
			minnum = getParaToInt("minnum");
		}
		if (isParaExists("maxnum")) {
			maxnum = getParaToInt("maxnum");
		}
		if (minnum > 0 && maxnum > 0)
			captcha.setFontNum(minnum, maxnum);
		if (isParaExists("fontsize")) {
			fontsize = getParaToInt("fontsize");
		}
		if (fontsize > 0)
			captcha.setFontSize(fontsize, fontsize);
		// 干扰线数量 默认0
		captcha.setLineNum(1);
		// 噪点数量 默认50
		captcha.setArtifactNum(10);
		// 使用字符 去掉0和o 避免难以确认
		captcha.setCode("123456789");
		 //验证码在session里的名字 默认 captcha,创建时间为：名字_time
		// captcha.setCaptchaName("captcha");
	    //验证码颜色 默认黑色
		// captcha.setDrawColor(new Color(255,0,0));
	    //背景干扰物颜色  默认灰
		// captcha.setDrawBgColor(new Color(0,0,0));
	    //背景色+透明度 前三位数字是rgb色，第四个数字是透明度  默认透明
		// captcha.setBgColor(new Color(225, 225, 0, 100));
	    //滤镜特效 默认随机特效 //曲面Curves //大理石纹Marble //弯折Double //颤动Wobble //扩散Diffuse
		captcha.setFilter(CaptchaRender.FilterFactory.Curves);
		// 随机色 默认黑验证码 灰背景元素
		captcha.setRandomColor(true);
		render(captcha);
	}
			
}
