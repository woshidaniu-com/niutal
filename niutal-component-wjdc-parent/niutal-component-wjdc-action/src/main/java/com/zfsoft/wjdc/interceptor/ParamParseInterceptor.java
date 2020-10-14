package com.woshidaniu.wjdc.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 解析请求URL,如有参数追加项，截取并set
 * @ClassName: ParamInterceptor 
 * @author jinjj
 * @date 2012-6-13 下午01:18:38 
 *
 */
public class ParamParseInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -3722179973134636629L;
	Log log = LogFactory.getLog(ParamParseInterceptor.class);
	/**
	 *@see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getServletPath();
		String[] values = path.split("_");
		if(values.length==4){//仅处理含一个参数的情况，待扩展
			String paramName = values[2];
			String paramValue = values[3].replace(".html", "");
			Map<String,Object> map = ServletActionContext.getContext().getParameters();
			map.put(paramName, paramValue);
			ServletActionContext.getContext().setParameters(map);
		}
 
	    return invocation.invoke();   
	}  

	
}
