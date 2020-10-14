package com.woshidaniu.globalweb.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.AnnotationUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.annotation.RefererAccess;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.core.http.HttpStatus;
/**
 * 
 *@类名称:RequestRefererLimitInterceptor.java
 *@类描述：防盗链拦截器
 *<pre>
 *	1、添加拦截器，对请求头中的Referer参数所指定的请求来源进行逻辑判断，防止部分盗链问题
 *</pre>
 *@创建人：kangzhidong
 *@创建时间：Oct 16, 2015 5:06:53 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Deprecated
@SuppressWarnings("serial")
public class RequestRefererLimitInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	protected String domain = null;
	
	@Override
	public void init() {
		domain = StringUtils.getSafeStr(MessageUtil.getText("referer.domain"),  "*");
	}
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("RequestRefererLimitInterceptor...");  
		//如果未设置或为"*"表示不进行访问源的逻辑判断
		if(BlankUtils.isBlank(domain) || "*".equals(domain.trim())){
			//执行下一个拦截器
			return invocation.invoke();
		}else{
			
			//action 将要调用的Action对象的类型
			Class<?> actionClass  = invocation.getProxy().getAction().getClass();
			//action 将要执行的方法名称
			String methodName = invocation.getProxy().getMethod();
			//action 将要执行的方法对象
			Method method = ReflectionUtils.getMethod(actionClass, methodName);
			//会话用户与指定参数值一致性权限注解：标注哪个请求参数需与会话中用户ID一致
			RefererAccess refererAccess = AnnotationUtils.findAnnotation(method, RefererAccess.class);
			//指定值
			String refererDomain = null;
			//有注解
			if(refererAccess != null){
				refererDomain = refererAccess.value();
				 
			}
			if(BlankUtils.isBlank(refererDomain)){
				refererAccess = AnnotationUtils.findAnnotation(actionClass, RefererAccess.class);
				//有注解
				if(refererAccess != null){
					refererDomain = refererAccess.value();
				}
			}
			if(BlankUtils.isBlank(refererDomain)){
				refererDomain = domain;
			}
			//refererKey 不为空，表示在请求的Method或者Action上有要求进行防盗链校验的注解
			if(!BlankUtils.isBlank(refererDomain)){
				
				//请求的request对象
				HttpServletRequest request = ServletActionContext.getRequest();
				//获取请求访问来源；referer为客户端带来的请求头 
				String referer = request.getHeader("Referer");
				/*  request.getHeader("Referer")获取来访者地址。
					只有通过链接访问当前页的时候，才能获取上一页的地址；否则request.getHeader("Referer")的值为Null，
					通过window.open打开当前页或者直接输入地址，也为Null。
				*/
				LOG.info("Referer:" + referer);
				//来源为空
				if(BlankUtils.isBlank(referer)){
					return interceptRequired(invocation, "W-N000000-01002", new String[]{"Referer"});
				}
				//来源不为空
				if(!referer.startsWith(refererDomain)){ 
					
					LOG.debug("危险的请求来源，即将跳转至本站首页...");  
					
					//判断是否ajax请求
					boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
					LOG.debug("isAjaxRequest:"+ isAjaxRequest);
				
					//判断是否ajax请求
					if( isAjaxRequest ){ 

						//响应的response对象
						HttpServletResponse response  =  ServletActionContext.getResponse();
						//HTTP Status 908（HTTP 危险来源）    -> 请求来源不明，服务器为了安全会对象范围来源进行逻辑判断，如果不符合要求则服务器可能返回此响应。
						response.setStatus(HttpStatus.SC_DANGER_REFERER);
						
						// 获取doType参数
						String doType = request.getParameter("doType");
						LOG.debug("doType:"+ doType);
						
						if(doType!=null && doType.equalsIgnoreCase("query")){
		        			model.getQueryModel().setItems(empty);
		        			invocation.getStack().set(Result.DATA, model.getQueryModel());
		        		}else{
		        			invocation.getStack().set(Result.DATA, model);
		        		}
						return Result.DATA;
					}else{
						List<Object> args = new ArrayList<Object>();
						args.add("Referer");
						String msg = TextProviderHelper.getText("W-N000000-01002", "", args , invocation.getStack());
						invocation.getStack().set(Result.MESSAGE, msg);
						return Result.EX_WARN;
					}
		        }  
			}
			//执行下一个拦截器
			return invocation.invoke();
		}
	}

}
