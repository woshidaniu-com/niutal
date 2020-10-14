package com.woshidaniu.globalweb.action;


import com.google.code.kaptcha.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.common.RSALoginService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.shiro.InvalidStateException;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.shiro.realm.AccountRealm;
import com.woshidaniu.shiro.realm.DefaultAccountRealm;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：LoginAction
 * 类描述： 登录相关处理action
 * 创建人：hhy
 * 创建时间：2011-12-20 上午10:49:06
 * 修改人：hhy
 * 修改时间：2011-12-20 上午10:49:06
 * 修改备注：
 * 
 * modify:zxb
 * modify date: 2015-04-10
 */

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction implements ModelDriven<LoginModel> {

    private static final long serialVersionUID = 1L;
    private LoginModel model = new LoginModel();
    @Autowired
    private ILoginService loginService;
    @Autowired
    private IYhglService yhglService;
    @Autowired
    private IJsglService jsglService;
    
    private BaseLog baseLog = LogEngineImpl.getInstance();

    private static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

    public void initialize(){
    }
    
    /**
     * 获取加密登录公共key
     * @return
     */
    public String getPublicKey(){
    	try {
	    	RSALoginService rsaLoginService = new RSALoginService();
	    	RSAPublicKey publicKey = rsaLoginService.generateKey(request);
	
	    	byte[] modulus = publicKey.getModulus().toByteArray();
	    	byte[] exponent = publicKey.getPublicExponent().toByteArray();
	
	    	Map<String,String> map = new HashMap<String,String>();
	
	    	map.put("modulus", org.apache.commons.codec.binary.Base64.encodeBase64String(modulus));
	    	map.put("exponent", org.apache.commons.codec.binary.Base64.encodeBase64String(exponent));
	    	getValueStack().set(DATA, map);
    	} catch(Exception e){
    		logException(e);
    	}
    	return DATA;
    }
    
    
    
    /**
     * 方法名: slogin
     * 方法描述: 登录
     * 修改时间：2011-12-20 上午10:49:38
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
    public String slogin() {
    	boolean authenticated = SubjectUtils.isAuthenticated();
    	
    	/**
    	 * 如果用户已登录，直接返回登录页面
    	 */
    	if(authenticated){
    		return SUCCESS;
    	}
    	
    	ValueStack vs = getValueStack();
    	getSession().setAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY, null);
        String ERROR_VALUE = (String) getRequest().getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        /**
         * 验证码错误返回失败
         */
        if(StringUtils.equals(IncorrectCaptchaException.class.getName(), ERROR_VALUE)){
        	vs.set("message", getText("login.info.error"));
        	return FAILED;
        /**
         * 账户或密码错误返回失败
         */
        }else if(StringUtils.equals(AccountException.class.getName(), ERROR_VALUE)){
        	vs.set("message", getText("login.info.failed"));
            return FAILED;
         /**
          * 账户没有启用返回失败
          */
        }else if(StringUtils.equals(InvalidStateException.class.getName(), ERROR_VALUE)){
        	vs.set("message", getText("login.user.forbid"));
            return FAILED;
        }    
        String kick = getRequest().getParameter("kickout");
        if(StringUtils.equals("1", kick)){
        	vs.set("message", getText("login.user.kick"));
        	return FAILED;
        }
        
        return FAILED;
    }
    
    /**
	 * 用户切换身份
	 * @return
	 */
	public String sRole(){
		try {
			HttpSession session = request.getSession();
			String jsdm = model.getSwitchRole();
			if(StringUtils.isNotBlank(jsdm) && (!StringUtils.equals(jsdm, getUser().getJsdm()))){
				//切换当前的角色信息
				getUser().setJsdm(jsdm);
				
				//刷新shiro缓存
				AccountRealm shiroRealm = ServiceFactory.getService(DefaultAccountRealm.class);
				shiroRealm.clearAuthorizationCache();
				//刷新shiro缓存
				//删除用户数据范围标识
				session.removeAttribute(GlobalString.SESSION_YHJSSJFWZ_LIST);
				//删除一级菜单缓存
			}
			return Action.SUCCESS;
		}catch(Exception ex){//刷新shiro缓存
			return Action.ERROR;
		}
	}
    
    /**
     * 方法名: login
     * 方法描述: 登录
     * 修改时间：2011-12-20 上午10:49:38
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
    @Deprecated
    public String login() {
        ValueStack vs = getValueStack();
        HttpSession session = getSession();
        String yzm = (String) session.getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
        if (model.getYzm().equalsIgnoreCase(yzm)) {
            try {
                User user = loginService.cxYhxx(model);
                if (null == user) {
                    vs.set("message", getText("login.info.failed"));
                    return FAILED;
                } else if(user != null && ("0").equals(user.getSfqy())){
                	//账号被禁用
                	vs.set("message", getText("login.user.forbid"));
                    return FAILED;
                }
                
                //设置用户
                session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
                baseLog.login(user, getText("log.message.ywmc",
                new String[]{"登陆系统", "xg_xtgl_yhb"}),
                "系统管理", getText("log.message.czms",
                new String[]{"登陆系统", "用户名", user.getYhm()}));
                
              return SUCCESS;
                
            } catch (Exception e) {
                logException(e);
                return ERROR;
            }
        } else {
            //验证码不正确
            vs.set("message", getText("login.info.error"));
        }
        //来过后让session的验证码失效！防止Ajax重复提交
        session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY, null);
        return FAILED;
    }

    public String pageIndex() {
    	return SUCCESS;
    }
    /**
     * 方法名: logout
     * 方法描述: 注销
     * 修改时间：2011-12-20 上午10:49:50
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
//    public String logout() {
//        HttpSession session = getSession();
//        User user = getUser();
//        if(null == user)
//        {
//        	return LOGIN;
//        }
//        baseLog.logout(user, getText("log.message.ywmc",
//                new String[]{"登出系统", "xg_xtgl_yhb"}),
//                "系统管理", getText("log.message.czms",
//                new String[]{"登出系统", "用户名", user.getYhm()}));
//
//        return LOGIN;
//    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String yzm() {
        return "yzm";
    }

    /**
     * 使用更好的验证码
     * @return
     */
    //@Deprecated
    //public String code() {
    //   GenerateCode.getInstance(new CommonCode()).generate(getRequest(),getResponse());
    //    return null;
    //}

    public LoginModel getModel() {
        return model;
    }


    public ILoginService getLoginService() {
        return loginService;
    }


    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }


    
    public IYhglService getYhglService() {
        return yhglService;
    }

    
    public void setYhglService(IYhglService yhglService) {
        this.yhglService = yhglService;
    }

    
    public IJsglService getJsglService() {
        return jsglService;
    }

    
    public void setJsglService(IJsglService jsglService) {
        this.jsglService = jsglService;
    }

}
