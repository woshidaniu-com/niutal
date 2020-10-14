package com.woshidaniu.globalweb.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;


/**
 * 
 *@类名称:FunctionalSessionIdenticalInterceptor.java
 *@类描述：会话用户与指定参数值一致性校验拦截器
 *<pre>
 *	1、Ajax请求或者直接打开的连接需要传递参数sessionUserKey参数为当前登录用户ID
 *	2、添加拦截器，拦截器中实现获取请求的Action和具体方法逻辑，获取方法是否有标记sessionUserKey参数要与会话用户ID一致的注解；
 *		如果有此注解，表示要求一致，则验证是否一致，不一致则进行异常提醒
 *</pre>
 *@创建人：kangzhidong
 *@创建时间：Oct 16, 2015 5:03:20 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 *
 */
@SuppressWarnings("serial")
public class FunctionalSessionIdenticalInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		//执行下一个拦截器
		return invocation.invoke();
				
		/*
		LOG.debug("FunctionalSessionIdenticalInterceptor..."); 
		
		//action 将要调用的Action对象的类型
		Class<?> actionClass  = invocation.getProxy().getAction().getClass();
		//action 将要执行的方法名称
		String methodName = invocation.getProxy().getMethod();
		//action 将要执行的方法对象
		Method method = ReflectionUtils.getMethod(actionClass, methodName);
		
		//如果忽略一致参数检查
		if(AnnotationUtils.hasAnnotation(method, IgnoreIdentical.class)){
			//执行下一个拦截器
			return invocation.invoke();
		}
		
		//请求的request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//响应的response对象
		HttpServletResponse response  =  ServletActionContext.getResponse();
		
		//判断是否ajax请求
		boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
		LOG.debug("isAjaxRequest:"+ isAjaxRequest);
		
		// 获取doType参数
		String doType = request.getParameter("doType");
		LOG.debug("doType:"+ doType);
		
		// 获取ignore参数
		String ignore = request.getParameter("ignore");
		LOG.debug("ignore:"+ ignore);
		
		//当前登录账户
		User user     = WebSessionFactory.getUser();
		
		if(user.isStudent()){
			String xh = request.getParameter("xh");
			String xh_id = request.getParameter("xh_id");

			if( (!BlankUtils.isBlank(xh) && !(  xh.equals(user.getYhm()) 
											|| xh.equals(user.getYhlybid()))   
											
				) || ( !BlankUtils.isBlank(xh_id) && ! ( xh_id.equals(user.getYhm()) 
											|| xh_id.equals(user.getYhlybid()) 
											))  
											
				){
				
				//判断是否ajax请求
				if( isAjaxRequest){ 
					
					//HTTP Status 905（HTTP 不一致）    -> 会话用户与指定参数值一致性校验结果不一致，服务器可能返回此响应。
					response.setStatus(HttpStatus.SC_NOT_IDENTICAL);
					
					if(doType!=null && doType.equalsIgnoreCase("query")){
	        			model.getQueryModel().setItems(empty);
	        			invocation.getStack().set(Result.DATA, model.getQueryModel());
	        		}else{
	        			invocation.getStack().set(Result.DATA, model);
	        		}
					return Result.DATA;
		        }else{ 
		        	List<Object> args = new ArrayList<Object>();
					args.add(!BlankUtils.isBlank(xh_id) ? "xh_id" :"xh" );
					invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01006", "", args , invocation.getStack()) );
		        	//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01006",new Object[]{!BlankUtils.isBlank(xh_id) ? "xh_id" :"xh" }));
					return Result.EX_WARN;
		        }
				
			}
		}else if(user.isTeacher() && !"1".equals(ignore)){
			
			String jgh = request.getParameter("jgh");
			String jgh_id = request.getParameter("jgh_id");
			if( (!BlankUtils.isBlank(jgh) && !(jgh.equals(user.getYhm()) || jgh.equals(user.getYhlybid())) ) || 
				(!BlankUtils.isBlank(jgh_id) && ! (jgh_id.equals(user.getYhm()) || jgh_id.equals(user.getYhlybid()) )) ){
				
				//判断是否ajax请求
				if( isAjaxRequest){ 
					
					//HTTP Status 905（HTTP 不一致）    -> 会话用户与指定参数值一致性校验结果不一致，服务器可能返回此响应。
					response.setStatus(HttpStatus.SC_NOT_IDENTICAL);
					
					if(doType!=null && doType.equalsIgnoreCase("query")){
	        			model.getQueryModel().setItems(empty);
	        			invocation.getStack().set(Result.DATA, model.getQueryModel());
	        		}else{
	        			invocation.getStack().set(Result.DATA, model);
	        		}
					return Result.DATA;
		        }else{ 
		        	List<Object> args = new ArrayList<Object>();
		        	args.add(!BlankUtils.isBlank(jgh_id) ? "jgh_id" :"jgh" );
					invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01006", "", args , invocation.getStack()) );
		        	//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01006",new Object[]{!BlankUtils.isBlank(jgh_id) ? "jgh_id" :"jgh" }));
					return Result.EX_WARN;
		        }
				
			}
		}
		//执行下一个拦截器
		return invocation.invoke();*/
	}
	
}
