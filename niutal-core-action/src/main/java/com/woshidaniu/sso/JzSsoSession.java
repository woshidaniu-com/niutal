package com.woshidaniu.sso;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.ValueStack;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.util.base.MessageUtil;

@Controller
@Scope("prototype")
public class JzSsoSession extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String USERTYPE_STUDENT = "student";
	public static final String USERTYPE_TEACHER = "teacher";
	public static final String USERTYPE_COMPANY = "company";

	/**
	 * 金智登录口
	 * @return
	 */
	public String jzLogin() {
		ValueStack vs=getValueStack();
		try {
			//验证登录口是否开放
			if(!"1".equals(MessageUtil.getText("jxsso.state"))){
				//单点登录未启动
				vs.set("msg", "单点登录未启动!");
				return ERROR;
			}
			
			//验证用户名是否存在
			String userName=getUserName();
			if(StringUtils.isEmpty(userName)){
				//未登录
				vs.set("msg", "用户还未登录或已超时!");
				return ERROR;
			}
			
			//验证用户是否存在
			User user=getUserInfo(userName);
			if(user == null){
				//用户不存在
				vs.set("msg", "登录用户不存在!");
				return ERROR;
			}
			
			//登录成功
			setSession(user, getRequest());
			if(user.getYhlx().equals("student")){
				return "xsjzLogin";
			}
		} catch (Exception e) {
			logException(e);
			//用户不存在
			vs.set("msg", "登录异常!");
			return ERROR;
		}
		return "jzLogin";
	}
	
	/**
	 * 获取用户信息
	 * @param yhm
	 * @return
	 */
	private String getUserName() throws Exception {
		HttpServletRequest request = getRequest();
		String curUser = "";

		// 调用金智接口获取认证后的用户名
		//获取错误临时目录
		String is_config=ServletActionContext.getServletContext().getRealPath("/client.properties");
		Cookie all_cookies[] = request.getCookies();
		Cookie myCookie;
		String decodedCookieValue = null;
		if (all_cookies != null) {
			for (int i = 0; i < all_cookies.length; i++) {
				myCookie = all_cookies[i];
				if (myCookie.getName().equals("iPlanetDirectoryPro")) {
					decodedCookieValue = URLDecoder.decode(myCookie.getValue(),
							"GB2312");
				}
			}
		}

		IdentityFactory factory = IdentityFactory.createFactory(is_config);
		IdentityManager im = factory.getIdentityManager();

		if (decodedCookieValue != null) {
			// 获取到用户名
			curUser = im.getCurrentUser(decodedCookieValue);
		}
		return curUser;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	private User getUserInfo(String userName) {
		ILoginService loginService = ((ILoginService) ServiceFactory
				.getService("loginService"));
		LoginModel loginModel = new LoginModel();
		loginModel.setYhm(userName);
		User user = loginService.cxYhxx(loginModel);
		return user;
	}

	/**
	 * 设置session
	 * @param userName
	 * @param request
	 */
	private void setSession(User user, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (USERTYPE_TEACHER.equals(user.getYhlx())) {
			// 老师加载权限
			session.setAttribute("user", user);
			session.setAttribute("userType", user.getYhlx());
		} else if (USERTYPE_STUDENT.equals(user.getYhlx())) {
			// 学生加载权限
			session.setAttribute("user", user);
			session.setAttribute("userType", user.getYhlx());
		} else {
			session.setAttribute("user", user);
			session.setAttribute("userType", user.getYhlx());
		}
	}
}
