package com.woshidaniu.globalweb.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

/**
 * 页面跳转拦截器，用于修改result返回页面。
 * 如：学校访问该学校代码下页面，若找不到则访问通用页面
 * @author Penghui.Qu
 *
 */
public class PageForwardInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	public static final String KEY = "xxdm";
	public static final String COMMON_CODE = "";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//采用struts2监听器，在返回视图前调用其beforeResult
		invocation.addPreResultListener(new PreResultListener() {
			@Override
			public void beforeResult(ActionInvocation invocation, String resultCode) {
				//获取配置
				Map<String,ResultConfig> resultMap = invocation.getProxy().getConfig().getResults();
				//根据返回结果字符查找对应的配置
				ResultConfig resultConfig = resultMap.get(resultCode);
				
				if (resultConfig != null){
					Map<String,String> map = resultConfig.getParams();
					String location = map.get("location");
					
					//如果返回页面中使用的了对应的表达式则判断返回视图页面是否存在
					if (!StringUtils.isEmpty(location) && location.indexOf("${"+KEY+"}") > -1){
						//获取学校代码
						ServletContext application = ServletActionContext.getServletContext();
						String xxdm = String.valueOf(application.getAttribute(KEY));
						String realLocation = location.replace("${"+KEY+"}", xxdm);
						
						String viewPath = ServletActionContext.getServletContext().getRealPath(realLocation);
						File file = new File(viewPath);
						if (!file.exists()){
							xxdm=COMMON_CODE;
						}
						ValueStack stack = ActionContext.getContext().getValueStack();
						stack.set(KEY, xxdm);
					}
				}
			}
		});
		
		return  invocation.invoke();
	}

}

