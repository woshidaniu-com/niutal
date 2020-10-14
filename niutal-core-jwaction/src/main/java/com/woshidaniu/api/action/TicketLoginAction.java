package com.woshidaniu.api.action;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.api.utils.TicketTokenUtils;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.log.YhLog;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.entities.TicketTokenModel;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.common.ICommonQueryService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;

/**
 * 
 *@类名称		： TicketLoginAction.java
 *@类描述		：简单的票据认证登录
 *
 * 以下方法是为了提供其他系统的安全无密码登录或不需要登录即可访问数据接口
 *
 *	1、为每个应用分配appid
 *	
 *	appid是唯一的值 
 *	
 *	2、获取和验证token
 *	
 *	#获取授权token
 *	access_token_get_url = http://niutal.edu.cn/api/ticket_access_token?appid={0}
 *	#检验授权凭证（token）是否有效
 *	access_token_valid_url =  http://niutal.edu.cn/api/ticket_valid_token?openid={0}&token={1}
 *	
 *	3、访问统一的票据认证登录入口，方法检验授权凭证（token）是否有效
 *	
 *	票据登录地址：http://niutal.edu.cn/api/login_tickitLogin.html?token={0}&verify={1}&userid={2}&roleid={3}&time={4}
 *
 *@创建人		：kangzhidong
 *@创建时间	：Sep 8, 2016 7:23:22 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
@SuppressWarnings("serial")
public class TicketLoginAction extends BaseAction implements ModelDriven<TicketTokenModel> {

    protected TicketTokenModel model = new TicketTokenModel();
    @Resource
    private ILoginService loginService;
    //系统操作描述service
    @Resource
	protected IXtczmsService xtczmsService;
    @Resource
    protected ICommonQueryService queryService;

    protected BaseLog baseLog = LogEngineImpl.getInstance();
        
   /**
    * 
    *@描述		： 票据登录（通过握手秘钥等参数认证登录）
    *@创建人		: kangzhidong
    *@创建时间	: Sep 6, 20167:01:21 PM
    *@return
    *@修改人		: 
    *@修改时间	: 
    *@修改描述	:
    */
   public String tickitLogin() {
		try {

			// 用户ID
			String userid = StringUtils.getSafeStr(model.getUserid(), getRequest().getParameter("userid"));
			// 角色ID，xs,js：方便区别用户角色
			String roleid = StringUtils.getSafeStr(model.getRoleid(), getRequest().getParameter("roleid"));
			// 验证用户名和用户类型
			if (BlankUtils.isBlank(userid) || BlankUtils.isBlank(roleid)) {
				// 跳转到登录口
				return Result.RD_LOGIN_OUT;
			}

			// 学校代码
			String xxdm = BaseConstant.XXDM;
			// 系统双方约定的秘钥:基于Des + Base64加密的值
			String token = StringUtils.getSafeStr(model.getToken(), getRequest().getParameter("token"));
			// 32位MD5加密信息（大写）:格式为：(卡号-用户类型-时间戳-token)值组合的MD5值
			String verify = StringUtils.getSafeStr(model.getVerify(), getRequest().getParameter("verify"));
			// 时间戳;格式: yyyyMMddHHmmssSSS
			String timestamp = StringUtils.getSafeStr(model.getTime(), getRequest().getParameter("time"));
			// 数据库时间
			String dbTime = getQueryService().getDatabaseTime();
			// 验证token
			if (!TicketTokenUtils.validToken(xxdm, token, dbTime)) {
				// 无效的token值
				getValueStack().set(Result.MESSAGE,getText("login.tickit.token"));
				return Result.EX_WARN;
			}
			// 验证verify
			else if (!TicketTokenUtils.validVerify(verify, userid + "-" + roleid + "-" + timestamp + "-" + token)) {
				// 不合法的verify值
				getValueStack().set(Result.MESSAGE,getText("login.tickit.verify"));
				return Result.EX_WARN;
			}
			// 验证timestamp
			else if (!TicketTokenUtils.validTimestamp(timestamp, dbTime)) {
				// 无效的timestamp值
				getValueStack().set(Result.MESSAGE,getText("login.tickit.timestamp"));
				return Result.EX_WARN;
			}

			//查询用户账号状态信息
			Map<String, String> statusMap = getLoginService().cxYhxxStatus( userid, "");
			// 用户名不存在！
			if ("0".equals(statusMap.get("NUM_1"))) {
				getValueStack().set(Result.MESSAGE,getText("login.user.notfound"));
				return Result.EX_WARN;
			}
			// 用户名或密码不正确
			else if ("0".equals(statusMap.get("NUM_2"))) {
				// 票据登录忽略密码检查
			}
			// 用户无所属角色
			else if ("0".equals(statusMap.get("NUM_3"))) {
				getValueStack().set(Result.MESSAGE,getText("login.user.nonerole"));
				return Result.EX_WARN;
			}
			// 账号被禁用
			else if ("0".equals(statusMap.get("NUM_4"))) {
				getValueStack().set(Result.MESSAGE,getText("login.user.forbid"));
				return Result.EX_WARN;
			}
			
			// 查询user对象
			User user = getLoginService().cxYhxxWithoutPwd(userid);

			// 会话作废前取出,原Locale
			HttpSession session = getSession();
			Locale locale = LocaleUtils.getSessionLocale(session);
			// 创建新的会话后，将原Locale放到会话中
			LocaleUtils.setSessionLocale(session, locale, getValueStack());

			// 登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
			session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.TICKIT.getKey());

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
			logStackException(e);
			// 票据登录认证失败
			getValueStack().set(Result.MESSAGE, getText("login.tickit.failed"));
			return Result.EX_WARN;
		}
   }
 
    public TicketTokenModel getModel() {
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

	public ICommonQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(ICommonQueryService queryService) {
		this.queryService = queryService;
	}
	
    
    
}
