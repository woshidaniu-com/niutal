package com.woshidaniu.api.action;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.log.YhLog;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;

/**
 * 
 *@类名称		： LoginAction.java
 *@类描述		：登录相关处理action
 *@创建人		：kangzhidong
 *@创建时间	：Sep 6, 2016 8:02:08 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
@SuppressWarnings("serial")
public class ICESsoLoginAction extends BaseAction implements ModelDriven<LoginModel> {

    protected LoginModel model = new LoginModel();
    @Resource
    private ILoginService loginService;
    //系统操作描述service
    @Resource
	protected IXtczmsService xtczmsService;

    protected BaseLog baseLog = LogEngineImpl.getInstance();
        
    /**
     * 
     *@描述		： 单点登录:默认实现金智的单点登录
     *@创建人		: kangzhidong
     *@创建时间	: Sep 6, 20167:22:47 PM
     *@return
     *@修改人		: 
     *@修改时间	: 
     *@修改描述	:
     */
    public String ssoLogin() {
 		try {
 			HttpSession session = getSession();
 			// 用户名
 			String yhm = StringUtils.getSafeStr(model.getYhm(), String.valueOf(session.getAttribute("userName")));
 			if (yhm == null || yhm.equals("null")) {
 				getValueStack().set(Result.MESSAGE,getText("login.info.user_empty"));
 				return Result.EX_WARN;
 			}
 			// 查询用户账号信息状态
 			model.setYhm(yhm);
 	   		Map<String, String> statusMap = getLoginService().cxYhxxStatus(model.getYhm() , model.getMm());
 	   		//用户名不存在！
 	   		if("0".equals(statusMap.get("NUM_1"))){
 	       		getValueStack().set(Result.MESSAGE, getText("login.user.notfound"));
 	            return Result.EX_WARN;
 	   		}
 	   		//用户名或密码不正确
 	   		else if("0".equals(statusMap.get("NUM_2"))){
 	   			//单点登录忽略密码检查
 	   		}
 	   		//用户无所属角色
 	   		else if("0".equals(statusMap.get("NUM_3"))){
 	   			getValueStack().set(Result.MESSAGE, getText("login.user.nonerole"));
 	            return Result.EX_WARN;
 	   		}
 	   		// 账号被禁用
 			else if ("0".equals(statusMap.get("NUM_4"))) {
 				getValueStack().set(Result.MESSAGE,getText("login.user.forbid"));
 				return Result.EX_WARN;
 			}
 	   		
 	   		// 查询user对象
 			User user = getLoginService().cxYhxxWithoutPwd(model);
 			 
 			// 会话作废前取出,原Locale
 			Locale locale = LocaleUtils.getSessionLocale(session);
 			// 创建新的会话后，将原Locale放到会话中
 			LocaleUtils.setSessionLocale(session, locale, getValueStack());
 			
 			// 登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
 			session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.SSO.getKey());

 			//在线人数统计对象，目前只能统计单个容器，多个容器后期需要进行回话共享改造
 			YhLog yhlog = new YhLog(user.getYhm());
 			session.setAttribute("yhLog", yhlog);
 			
 			// 把用户加到session里
 			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
 			// 设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
 			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY), user.getJsdm());
 			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY), user.getJsdm());
 			
 			//记录登录成功日志
 			baseLog.login( user, getText("log.message.ywmc", new String[] { "登录系统", "xg_xtgl_yhb" }), "系统管理", getText("log.message.czms", new String[] { "登录系统", "用户名", user.getYhm() }));

 			// 设置角色参数
			getValueStack().setParameter("roleid", user.getJsdm());
 			// 设置时间戳参数
 			getValueStack().setParameter("nowTime", new Date().getTime());
 			
 			// =============添加登录提醒跳转逻辑代码=======================
 			// 使用了系统操作描述功能
 			if (user.isStudent() && getXtczmsService().isHasXtczms("Login", "xs")) {
 				XtczmsModel model = getXtczmsService().getXtczms("Login", "xs");
 				// 操作描述信息不为空
 				if (!BlankUtils.isBlank(model) && !BlankUtils.isBlank(model.getCzms()) && model.getCzms().trim().length() > 0) {
 					// 设置跳转页面路径
 					model.setDyym("/xtgl/login_loginIndex.html");
 					getValueStack().set("model", model);
 					return Result.RD_INDEX_DETAILS;
 				} else {
 					return SUCCESS;
 				}
 			} else {
 				return SUCCESS;
 			}
 		} catch (Exception e) {
 			logException(e);
 			return ERROR;
 		}
    }
 
    public LoginModel getModel() {
        return model;
    }

    public ILoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

	public IXtczmsService getXtczmsService() {
		return xtczmsService;
	}

	public void setXtczmsService(IXtczmsService xtczmsService) {
		this.xtczmsService = xtczmsService;
	} 
	
    
}
