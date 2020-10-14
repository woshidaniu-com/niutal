package com.woshidaniu.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.cache.core.ICacheClient;
import com.woshidaniu.common.log.User;

@Controller
@Scope("prototype")
public class BaseAction extends ActionSupport implements ServletResponseAware,	ServletRequestAware, Preparable, ApplicationContextAware {

	protected  static final transient Logger log = LoggerFactory.getLogger(BaseAction.class);
	private static final long serialVersionUID = 7038570508703577185L;
	
	public final String FAILED = "false";// 返回失败
	public final String OPER_SAVE = "save";// 操作保存
	
	/**全局返回类型*/
	public static final String DATA = "data";
	/**通用操作*/
	public static final String QUERY = "query";
	public static final String UPDATE = "update";
	public static final String SAVE = "save";
	public static final String DELETE = "delele";
	public static final String BATCH_DELETE = "batch_delete";
	/**通用操作*/
	public static final String STREAM = "stream";
	public static final String BUSINESSEXCEPTION = "businessException";
	public static final String UNAUTHORIZEDEXCEPTION = "unauthorizedException";
	/**
	 * 全局异常对象
	 */
	protected Throwable exception;
	
	public static final String USER_INFO_KEY = "user";
	public String key = "";
	
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected ApplicationContext springContext;
	/**缓存实例*/
	protected ICacheClient cache;
	protected File file;

	/**
	 * 返回值栈对象
	 */
	protected ValueStack getValueStack() {
		ActionContext actionContext = ActionContext.getContext();
		return actionContext.getValueStack();
	}

	/**
	 * <p>方法说明：设置栈值<p>
	 */
	protected void setKVToValuseStack(String key, Object value){
		getValueStack().set(key, value);
	}
	/**
	 * 返回request对象
	 */
	protected HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 返回response对象
	 */
	protected HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * 返回session对象
	 */
	protected HttpSession getSession() {
		return this.request.getSession();
	}
	
	/**
	 * 获取用户信息
	 */

	protected User getUser() {
		if(SecurityUtils.getSubject().getPrincipals() == null){
			return null;
		}
		return (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		//return (User) this.getSession().getAttribute("user");
	}
	
	
	/**
	 * 公用异常处理
	 */
	protected void logException(Exception ex) {
		try {
			// 调试打印控制台-发包后可删掉
			ex.printStackTrace();
			// 日志记录异常信息
			log.error("", ex);
			// request 设置错误消息，用于返回客户端
			getRequest().setAttribute("msg", ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepare() throws Exception {
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	/**
	 * 公用输出流
	 * @return
	 */
	public InputStream getInputStream(){
		try {
			ValueStack stack = getValueStack();
			stack.set("fileName", file.getName());
			return new FileInputStream(file);
		} catch (IOException e) {
			logException(e);
		}
		return null;
	}
	
	/**
	 * 校验防伪请求提交随机码
	 * @return
	 */
	protected boolean validateCsrfToken(){
		return Boolean.TRUE;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		springContext = applicationContext;
	}
}
