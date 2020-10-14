package com.woshidaniu.struts2.interceptor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.ExceptionUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.core.http.HttpStatus;
import com.woshidaniu.web.utils.WebResponseUtils;

/**
 * 
 * @className: ExceptionInterceptor
 * @description: struts2 异常信息处理拦截器，要配置调用action之前
 * @author : kangzhidong
 * @date : 下午02:24:12 2015-1-22
 * @modify by:
 * @modify date :
 * @modify description :
 */
@SuppressWarnings("serial")
public class BaseExceptionHandleInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	protected MessageFormat format = null;
	protected Map<String,String> messageMap = new HashMap<String,String>();
	
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("BaseExceptionHandleInterceptor..."); 
		
		// 获取request请求对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//判断是否ajax请求
		boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
		LOG.debug("isAjaxRequest:"+ isAjaxRequest);
		
		//执行目标方法 (调用下一个拦截器, 或执行Action)   
		String resultCode = null;
		String keyMessage = null;
		try {
			// 运行被拦截的Action,期间如果发生异常会被catch住
			resultCode = invocation.invoke();
			if(resultCode !=null && (resultCode.startsWith("E-")||resultCode.startsWith("R-")||resultCode.startsWith("C-")||resultCode.startsWith("E-")||resultCode.startsWith("I-"))){
				//从*.properties的配置文件中查询是否有对应key的信息
				keyMessage = MessageUtil.getText(resultCode);
				if(BlankUtils.isBlank(keyMessage)){
					return resultCode;
				}else{
					// 判断是否ajax请求
					if( isAjaxRequest ){ 
						invocation.getStack().set(Result.DATA, keyMessage);
						return Result.DATA;
					}else{
						// ValueStack 设置错误消息，用于返回客户端
			            invocation.getStack().set(Result.MESSAGE, keyMessage);
						//返回error页面
						return Result.EX_ERROR;
					}
				}
			}else{
				return resultCode;
			}
		} catch (Throwable ex) {
			// 日志记录异常信息
			LOG.error(ex.getMessage(),ex);
			//响应的response对象
			HttpServletResponse response  =  ServletActionContext.getResponse();
			
			// 判断是否ajax请求
			if( isAjaxRequest ){ 

				// 获取doType参数
				String doType = request.getParameter("doType");
				LOG.debug("doType:"+ doType);
				
				//HTTP Status 911（HTTP 运行异常）    -> 应用程序运行期间发生错误，则服务器可能返回此响应。
				response.setStatus(HttpStatus.SC_RUNTIME_ERROR);
				
				if(doType!=null && doType.equalsIgnoreCase("query")){
        			model.getQueryModel().setItems(empty);
        			invocation.getStack().set(Result.DATA, model.getQueryModel());
        		}else{
        			invocation.getStack().set(Result.DATA, model);
        		}
				return Result.DATA;
			}else{
				// ValueStack 设置错误消息，用于返回客户端
	            invocation.getStack().set(Result.MESSAGE, ExceptionUtils.getFullHtmlStackTrace(ex));
	            // Set the content type
	            response.setContentType(WebResponseUtils.HTML_TYPE);
				//返回error页面
				return Result.EX_ERROR;
			}
		}
	}

	public void destroy() {
		messageMap = null;
		LOG = null;
		format = null;
	}
	
}
