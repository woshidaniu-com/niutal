package com.woshidaniu.globalweb.interceptor;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.WebContext;


/**
 * 
 *@类名称:FunctionalAuthorizatedInterceptor.java
 *@类描述：功能模块操作权限拦截器
 *@创建人：kangzhidong
 *@创建时间：Oct 19, 2015 3:08:32 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FunctionalAuthorizatedInterceptor extends BaseAbstractInterceptor {
    
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	//系统操作描述service
		protected IYhglService service;
		
		@Override
		public void init() {
			service = (IYhglService) (service==null?ServiceFactory.getService("yhglService"):service);
		}
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("FunctionalAuthorizatedInterceptor..."); 
		//请求的request对象
        HttpServletRequest request = ServletActionContext.getRequest();
        //获取请求基本路基
		String path = request.getServletPath();
		//当前登录账户
	    User user 		   = WebContext.getUser();
        //从Action 请求方法中解析得到操作代码
        String czdm  = this.getOptCode(invocation);
        /* 
		//从Action url中解析得到操作代码
        String czdm  = WebRequestUtils.getOptCode(path,"html");
        */
        //获取功能模块代码
		/*String gnmkdm = WebUtils.getFuncCode(request);
		if(!BlankUtils.isBlank(user)&&!BlankUtils.isBlank(user.getYhm())&&
		   !BlankUtils.isBlank(gnmkdm)&&gnmkdm.startsWith("N")){
			gnmkdm = gnmkdm.split(",")[0];
			if(!service.checkPrivilege(user.getYhm(),gnmkdm)){
	        	// 判断是否ajax请求
				if( WebRequestUtils.isAjaxRequest(request)){ 
					invocation.getStack().set(Result.DATA, "没有访问权限!");
					return Result.DATA;
				}else{
					return Result.EX_NON_ACCESS;
				}
	        }
		}*/
        
        
        //解决URL中不能带参数问题  开始
    	String method = request.getMethod();
		if(method.equalsIgnoreCase("get")){
			StringBuilder builder  = new StringBuilder(path);
			Enumeration paramNames = request.getParameterNames();
			int i = 0 ;
			//解决原来菜单URL不能带参数问题
			while (paramNames.hasMoreElements()) {
				String paramName   = (String) paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						if(i==0){
							builder.append("?");
							builder.append(paramName).append("=").append(paramValue);
						}else{
							builder.append("&");
							builder.append(paramName).append("=").append(paramValue);
						}
						i++;
					}
				}
			}
			path = builder.toString();
		}
		//解决URL中不能带参数问题 结束
       
        if(path.indexOf("_")==-1){
            return invocation.invoke();
        }
        //查询功能模块相关信息(一、二 级菜单),根据url查询
        String actionPath        = path.substring(0,path.indexOf("_"));
        //路径为/cygn/jcdm开头不验证。
        if(!"/cygn/jcdm".equals(actionPath)){
            /**
             * 访问功能查询功能按钮时，系统会把功能操作代码放入session中，
             * 所以通过下面方法直接取session即可，
             * 如果有记录说明需要操作权限控制，反之不需要操作权限控制。
             */
        	String sessionKey = WebUtils.getFuncSessionKey(actionPath, user.getJsdm());
            List<String> list  = (List<String>) SessionFactory.getSession().getAttribute(sessionKey);
            if(list!=null){
                /***
                 * 通过URl解析如果操作代码为cx，不拦截
                 */
                if(("cx").equals(czdm)){
                    return invocation.invoke();
                }else{
                	boolean isczqx    = false;//判断是否有操作权限
            		/**
                	 * 循环从session取出的功能操作代码List，与当前功能操作代码比较如果true说明验证通过。
                	 */
            		for(String gnczdm:list){
            			if(czdm.equals(gnczdm)){
                			isczqx  = true;
                			break;
                		}
            		}
                	if(isczqx){
                		//直接跳转
                    	return invocation.invoke();
                	}else{
                		// 判断是否ajax请求
            			if( WebRequestUtils.isAjaxRequest(request)){ 
            				invocation.getStack().set(Result.DATA, "没有访问权限!");
            				return Result.DATA;
            			}else{
            				return Result.EX_NON_ACCESS;
            			}
                	}
                }
            }else{
            	//直接跳转
            	return invocation.invoke();
            }
        }else{
        	 return invocation.invoke();
        }
    }
    
     
}
