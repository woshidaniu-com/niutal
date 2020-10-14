package com.woshidaniu.globalweb.action;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.Operation;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.common.RSALoginService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.shiro.NoneRoleException;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.xml.BaseDataReader;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.captcha.CommonCode;
import com.woshidaniu.web.captcha.GenerateCode;

/**
 * 
 *@类名称		：LoginAction.java
 *@类描述		：登录相关处理action
 *@创建人		：kangzhidong
 *@创建时间	：Sep 6, 2016 8:02:08 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
@SuppressWarnings("serial")
public class LoginAction extends CommonBaseAction implements ModelDriven<LoginModel> {

    protected LoginModel model = new LoginModel();
    @Resource
    protected ILoginService loginService;
    @Resource
	protected IXtczmsService xtczmsService;

    protected BaseLog baseLog = LogEngineImpl.getInstance();
    
    protected static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    
    public void initialize(){
        // 做一些初始化操作（比如从缓存里读取一些信息），每个方法都调用
    }

    /***
     * 
     *@描述		：用户登录验证
     *@创建人		: kangzhidong
     *@创建时间	: Sep 5, 20164:10:43 PM
     *@return
     *@修改人		: 
     *@修改时间	: 
     *@修改描述	:
     */
	public String cxCheckYh(){
//    	HttpSession session = getSession();
//    	Map<String, Object> resultMap = new HashMap<String, Object>();
//    	int dlCount = 0;//错误登陆次数
//    	try {
//    		
//    		resultMap.put("status", SUCCESS);
//    		
//    		if(session.getAttribute("dlCount")!=null){
//        		dlCount = (Integer)session.getAttribute("dlCount");
//        		if(dlCount >= 3){
//        			String yzm = (String) session.getAttribute("yzm");
//        	    	if(!model.getYzm().equalsIgnoreCase(yzm)){
//        	    		resultMap.put("status", ERROR);
//        	    		resultMap.put("message", getText("login.info.error"));
//        	    		getValueStack().set(DATA, resultMap);
//        	    		return DATA;
//        	    	}
//        		}
//        	}   
//    		//查询用户账号状态信息
//    		Map<String, String> statusMap = getLoginService().cxYhxxStatus(model.getYhm(),model.getMm());
//    		//用户名不存在！
//    		if("0".equals(statusMap.get("NUM_1"))){
//    			resultMap.put("status", ERROR);
//    			resultMap.put("message", getText("login.user.notfound"));
//    			//用户名密码输入有误  错误登陆次数加1			
//    	    	if(session.getAttribute("dlCount")!=null){
//    	    		dlCount = (Integer)session.getAttribute("dlCount") + 1;	    		
//    	    	}else{
//    	    		dlCount = 1;
//    	    	}
//    		}
//    		//用户名或密码不正确
//    		else if("0".equals(statusMap.get("NUM_2"))){
//    			resultMap.put("status", ERROR);
//    			resultMap.put("message", getText("login.info.failed"));
//    			//用户名密码输入有误  错误登陆次数加1			
//    	    	if(session.getAttribute("dlCount")!=null){
//    	    		dlCount = (Integer)session.getAttribute("dlCount") + 1;	    		
//    	    	}else{
//    	    		dlCount = 1;
//    	    	}
//    		}
//    		//用户无所属角色
//    		else if("0".equals(statusMap.get("NUM_3"))){
//    			resultMap.put("status", ERROR);
//    			resultMap.put("message", getText("login.info.nonerole"));
//    		}
//    		// 账号被禁用
//			else if ("0".equals(statusMap.get("NUM_4"))) {
//				resultMap.put("status", ERROR);
//    			resultMap.put("message", getText("login.user.forbid"));
//			}
//    		session.setAttribute("dlCount", dlCount);
//    		
//    		/*String ts = getLoginService().checkYhxxStatus(model.getYhm(), model.getMm());
//    		map.put("ts", ts);*/
//		} catch (Exception e) {
//			logStackException(e);
//			resultMap.put("status", ERROR);
//			resultMap.put("message", getText("runtime.exception"));
//		}
//    	resultMap.put("dlCount", dlCount);
//    	getValueStack().set(DATA, resultMap);
    	return DATA;
    }
    
	
	
	/**
	 * 
	 * <p>
	 * 方法说明：获取RSA加密公钥
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a>
	 * <p>
	 * <p>
	 * 时间：2017年6月7日下午2:25:18
	 * <p>
	 * 
	 * @return
	 */
	public String getPublicKey() {
		try {
			ValueStack vs = getValueStack();
			RSALoginService rsaLoginService = new RSALoginService();
			RSAPublicKey publicKey = rsaLoginService.generateKey(request);

			byte[] modulus = publicKey.getModulus().toByteArray();
			byte[] exponent = publicKey.getPublicExponent().toByteArray();

			JSONObject json = new JSONObject();
			json.put("modulus", Base64.encodeBase64String(modulus));
			json.put("exponent", Base64.encodeBase64String(exponent));
			vs.set(DATA, json);
		} catch (Exception ex) {
			logException(ex);
			return ERROR;
		}
		return DATA;
	}

	/**
	 * 
	 * <p>
	 * 方法说明：shrio认证跳转
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a>
	 * <p>
	 * <p>
	 * 时间：2017年6月7日下午2:25:56
	 * <p>
	 * 
	 * @return
	 */
	public String slogin() {
		try {
			ValueStack vs = getValueStack();
			boolean authenticated = SubjectUtils.isAuthenticated();
			if (authenticated) {
				User user = getUser();
				// 设置角色参数
				vs.setParameter("roleid", user.getJsdm());
				// 设置时间戳参数
				vs.setParameter("nowTime", new Date().getTime());
				
				if (user.isStudent() && getXtczmsService().isHasXtczms("Login", "xs")) {
					XtczmsModel model = getXtczmsService().getXtczms("Login", "xs");
					// 操作描述信息不为空
					if (!BlankUtils.isBlank(model) && !BlankUtils.isBlank(model.getCzms())
							&& model.getCzms().trim().length() > 0) {
						// 设置跳转页面路径
						model.setDyym("/xtgl/login_loginIndex.html");
						getValueStack().set("model", model);
						return Result.RD_INDEX_DETAILS;
					}
				}
				return SUCCESS;
			}
			
			String ERROR_VALUE = (String) request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if (StringUtils.equals(IncorrectCaptchaException.class.getName(), ERROR_VALUE)) {
				vs.set("message", MessageUtil.getText("login.info.error"));
			} else if (StringUtils.equals(UnknownAccountException.class.getName(), ERROR_VALUE)) {
				vs.set("message", getText("login.user.notfound"));
			} else if (StringUtils.equals(AuthenticationException.class.getName(), ERROR_VALUE)) {
				vs.set("message", MessageUtil.getText("login.info.failed"));
			} else if (StringUtils.equals(DisabledAccountException.class.getName(), ERROR_VALUE)) {
				vs.set("message", MessageUtil.getText("login.user.forbid"));
			} else if (StringUtils.equals(NoneRoleException.class.getName(), ERROR_VALUE)) {
				vs.set("message", getText("login.info.nonerole"));
			} else if (StringUtils.equals(AccountException.class.getName(), ERROR_VALUE)) {
				vs.set("message", getText("login.user.outside"));
			} else {

			}

			String kick = request.getParameter("kickout");
			if (!StringUtils.isEmpty(kick)) {
				// 强制踢下线
				vs.set("message", MessageUtil.getText("login.user.kick"));
			}

			vs.set("languageList", BaseDataReader.getCachedBaseDataList("languageList"));
		} catch (Exception ex) {
			logException(ex);
			return ERROR;
		}
		return LOGIN;
	}
	
    /**
     * 
     *@描述		： 系统内部登录
     *@创建人		: kangzhidong
     *@创建时间	: Sep 5, 20164:12:30 PM
     *@return
     *@修改人		: 
     *@修改时间	: 
     *@修改描述	:
     */
   public String login() {

		try {

//			if (BlankUtils.isBlank(model.getYhm()) || BlankUtils.isBlank(model.getMm())) {
//				return Result.RD_LOGIN_OUT;
//			}
//
//			// 获取明文密码，用于下面的强度计算
//			String pwdString = model.getMm();
//			User user = getLoginService().cxYhxx(model);
//			if (null == user) {
//				getValueStack().set(Result.MESSAGE, getText("login.info.failed"));
//				return Result.EX_WARN;
//			} else if (("0").equals(user.getSfqy())) {
//				// 账号被禁用
//				getValueStack().set(Result.MESSAGE, getText("login.user.forbid"));
//				return Result.EX_WARN;
//			}
//			// 登录成功
//			if (!StringUtils.isEmpty(pwdString)) {
//				user.setYhmmdj(String.valueOf(StrengthUtils.getStrength(pwdString)));
//			}
//			// 会话作废前取出,原Locale
//			HttpSession session = getSession();
//			Locale locale = LocaleUtils.getSessionLocale(session);
//			// 创建新的会话后，将原Locale放到会话中
//			LocaleUtils.setSessionLocale(session, locale, getValueStack());
//
//			// 登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
//			session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.INNER.getKey());
//
//			// 在线人数统计对象，目前只能统计单个容器，多个容器后期需要进行回话共享改造
//			YhLog yhlog = new YhLog(user.getYhm());
//			session.setAttribute("yhLog", yhlog);
//
//			// 设置用户信息到session中
//			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
//			// 设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
//			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY), user.getJsdm());
//			session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY), user.getJsdm());
//			// session.setAttribute(user.getYhm(), indexService.cxJsxxLb(user));
//			
//			//记录登录成功日志
//			baseLog.login(user,getText("log.message.ywmc", new String[] { "登录系统","xg_xtgl_yhb" }),"系统管理", getText("log.message.czms", new String[] { "登录系统", "用户名", user.getYhm() }));
//
//			// 设置角色参数
//			getValueStack().setParameter("roleid", user.getJsdm());
//			// 设置时间戳参数
//			getValueStack().setParameter("nowTime", new Date().getTime());
//
//			// =============添加登录提醒跳转逻辑代码=======================
//			// 使用了系统操作描述功能
//			if (user.isStudent() && getXtczmsService().isHasXtczms("Login", "xs")) {
//				XtczmsModel model = getXtczmsService().getXtczms("Login", "xs");
//				// 操作描述信息不为空
//				if (!BlankUtils.isBlank(model) && !BlankUtils.isBlank(model.getCzms()) && model.getCzms().trim().length() > 0) {
//					// 设置跳转页面路径
//					model.setDyym("/xtgl/login_loginIndex.html");
//					getValueStack().set("model", model);
//					return Result.RD_INDEX_DETAILS;
//				} else {
//					return SUCCESS;
//				}
//			} else {
				return SUCCESS;
//			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
   }
   
   /**
    * 
    *@描述：index_xtczms.jsp中解决学生登录提示后记录日志后的跳转方法
    *@创建人:majun
    *@创建时间:2015-3-10上午11:47:24
    *@修改人:
    *@修改时间:
    *@修改描述:
    *@return
    */
   public String loginIndex(){
	   User user = getUser();
	   baseLog.log(user, "N01","N000003", Operation.OP_SURE ,"已阅读说明!");
	   return SUCCESS;
   }

   /**
    * 
    *@描述：login.jap中解决不同学校登录口个性化问题的跳转方法
    *@创建人:majun
    *@创建时间:2015-3-10上午11:47:24
    *@修改人:
    *@修改时间:
    *@修改描述:
    *@return
    */
	public String loginForward() {
		//获得是否登录认证的标记
		boolean authenticated = SubjectUtils.isAuthenticated();
		if (authenticated) {
	        // 设置时间戳参数
	 		getValueStack().setParameter("nowTime", new Date().getTime());
			User user = getUser();
			// 设置角色参数
			getValueStack().setParameter("roleid", user.getJsdm());
			return SUCCESS;
		}
		//先存储国际化标记
        getValueStack().setParameter("localeKey", LocaleUtils.getLocaleKey(getRequest()));
		// 该方法不受拦截器控制，手动进行国际化语言切换
		LocaleUtils.interceptLocale(getRequest(), getValueStack());
		getValueStack().set("languageList", BaseDataReader.getCachedBaseDataList("languageList"));
		return LOGIN;
	}
    
    public String pageIndex() {
    	return SUCCESS;
    }
    
    /**
     * 
     *@描述		：注销登录
     *@创建人		: kangzhidong
     *@创建时间	: Sep 6, 20167:59:10 PM
     *@return
     *@修改人		: 
     *@修改时间	: 
     *@修改描述	:
     */
    public String logout() {
    	
    	 //外部系统登录跳转路径；解决系统集成使用教务登录口问题
        String loginURL = MessageUtil.getText("system.loginURL");
        //登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
      	String loginType = String.valueOf(getSession().getAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY)));
      	 //先存储国际化标记
        getValueStack().setParameter("localeKey", LocaleUtils.getLocaleKey(getRequest()));
        // 设置时间戳参数
 		getValueStack().setParameter("nowTime", new Date().getTime());
 		
    	//获得是否登录认证的标记
		boolean authenticated = SubjectUtils.isAuthenticated();
		//已经登出
		if (!authenticated) {
			if(!BlankUtils.isBlank(loginURL) && (BlankUtils.isBlank(loginType) || !LoginType.INNER.getKey().equals(loginType))){
            	return Result.RD_LOGIN_OUT;
            }
        	return LOGIN;
		}
		
//    	HttpSession session = getSession();
        User user = getUser();
        if(null == user){
            if(!BlankUtils.isBlank(loginURL) && (BlankUtils.isBlank(loginType) || !LoginType.INNER.getKey().equals(loginType))){
            	return Result.RD_LOGIN_OUT;
            }
        	return LOGIN;
        }
        
        SubjectUtils.getSubject().logout();
        
        //记录退出日志
		baseLog.logout(user, getText("log.message.ywmc", new String[]{"登出系统", "xg_xtgl_yhb"}), "系统管理", getText("log.message.czms", new String[]{"登出系统", "用户名", user.getYhm()}));
		
        if(!BlankUtils.isBlank(loginURL) && (BlankUtils.isBlank(loginType) || !LoginType.INNER.getKey().equals(loginType))){
         	return Result.RD_LOGIN_OUT;
        }
        return LOGIN;
    }

    
    /**
     * 获取验证码
     *
     * @return
     */
    public String yzm() {
        return "yzm";
    }
    
    public String code() {
        GenerateCode.getInstance(new CommonCode()).generate(getRequest(),getResponse());
        return null;
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
