package com.woshidaniu.globalweb.interceptor;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.WebContext;

/**
 *@类名称:PageForwardInterceptor.java
 *@类描述：页面跳转拦截器，用于修改result返回页面。 如：学校访问该学校代码下页面，若找不到则访问通用页面
 *@创建人：Penghui.Qu
 *@创建时间：2014-8-6 上午11:11:59
 *@修改人:majun 
 *@修改时间：20140806
 *@版本号:v1.0
 */
public class RequestPageForwardInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	public static final String KEY = "xxdm";

	private PreResultListener preResultListener = new PreResultListener() {
		@Override
		public void beforeResult(ActionInvocation invocation, String resultCode) {
			// 获取response响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			// Servlet上下文对象
			ServletContext servletContext = ServletActionContext.getServletContext();
			//获取配置
			Map<String,ResultConfig> resultMap = invocation.getProxy().getConfig().getResults();
			//根据返回结果字符查找对应的配置
			ResultConfig resultConfig = (ResultConfig) resultMap.get(resultCode);
			if (resultConfig != null){
				Map<String,String> map = resultConfig.getParams();
				String location = map.get("location");
				//如果返回页面中使用的了对应的表达式则判断返回视图页面是否存在
				if (!StringUtils.isEmpty(location) && location.indexOf("${"+KEY+"}") > -1){
					String realLocation = location.replace("${"+KEY+"}", BaseConstant.XXDM);
					String viewPath = servletContext.getRealPath(realLocation);
					File file = new File(viewPath);
					ValueStack stack = invocation.getStack();
					if (file.exists()){
						stack.setParameter(KEY, BaseConstant.XXDM);
					}else{
						//如果返回视图页面不存在，则把表达式值设为空，以保证返回结果能跳转到通用页面
						stack.setParameter(KEY, "");
					}
				}
				if(resultCode.equals(Result.RD_LOGIN_OUT)){
					//请求的Session对象
					HttpSession session = ServletActionContext.getRequest().getSession();
					//不是教务系统登录入口登录的用户
			        if(!LoginType.INNER.getKey().equals(String.valueOf(session.getAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY))))){
			        	String loginURL = MessageUtil.getText("system.loginURL");
			            if(!BlankUtils.isBlank(loginURL)){
			            	try {
			            		response.sendRedirect(loginURL);
								invocation.setResultCode(Action.NONE); 
							} catch (IOException e) {
								e.printStackTrace();
							}  
			            }
			        }
				}
				invocation.getStack().setParameter("nowTime", new Date().getTime());
			}
		}
	};
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//invocation.getAction();
		//采用struts2监听器，在返回视图前调用其beforeResult 10335
		invocation.addPreResultListener(preResultListener);
		User user     = WebContext.getUser();
		invocation.getStack().set("sessionUser", user != null ? user.getYhm() : "");
		return  invocation.invoke();
	}

}

