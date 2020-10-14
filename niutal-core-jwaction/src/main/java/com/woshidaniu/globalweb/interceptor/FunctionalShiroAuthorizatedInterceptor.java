package com.woshidaniu.globalweb.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.AnnotationUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.core.AntPathMatcher;
import com.woshidaniu.web.core.PathMatcher;


/**
 * 
 *@类名称:FunctionalShiroAuthorizatedInterceptor.java
 *@类描述：功能模块操作权限拦截器
 *@创建人：kangzhidong
 *@创建时间：Oct 19, 2015 3:08:32 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FunctionalShiroAuthorizatedInterceptor extends BaseAbstractInterceptor {
    
	private Logger LOG = LoggerFactory.getLogger(getClass());
	//系统操作描述service
	private IYhglService service;
	private PathMatcher matcher = new AntPathMatcher();
	
	@Inject(value="struts.permission.excludePattern", required = false)
	protected String excludeURLPattern = null;
	
	@Override
	public void init() {
		service = (IYhglService) (service==null?ServiceFactory.getService("yhglService"):service);
	}
	 
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("FunctionalShiroAuthorizatedInterceptor..."); 
        
        //请求的request对象
        HttpServletRequest request = ServletActionContext.getRequest();
        //当前登录账户
	    //User user = WebContext.getUser();	
	    
		/* ===================== 权限判断第一步：优先判断Shiro注解权限 =====================*/
		
		//action 将要执行的方法名称
		String methodName = invocation.getProxy().getMethod();
		//action 将要执行的方法对象
		Method method = ReflectionUtils.getMethod(invocation.getProxy().getAction().getClass(), methodName);
		//角色权限注解
		RequiresRoles requiresRoles = AnnotationUtils.findAnnotation(method, RequiresRoles.class);
		boolean isRolePermission = false;
		if(requiresRoles != null){
			if(Logical.AND.compareTo(requiresRoles.logical()) == 0 ){
				isRolePermission = SubjectUtils.getSubject().hasAllRoles(CollectionUtils.arrayToList(requiresRoles.value()));
			} 
			else if(Logical.OR.compareTo(requiresRoles.logical()) == 0 ){
				boolean[] hasRoles = SubjectUtils.getSubject().hasRoles(CollectionUtils.arrayToList(requiresRoles.value()));
				for (boolean b : hasRoles) {
					if (b) {
						isRolePermission = b;	
					}
				}
			}
		}
		//字符标记权限注解
		RequiresPermissions requiresPermissions = AnnotationUtils.findAnnotation(method, RequiresPermissions.class);
		boolean isStrPermission = false;
		if(requiresPermissions != null){
			if(Logical.AND.compareTo(requiresPermissions.logical()) == 0 ){
				isStrPermission = SubjectUtils.getSubject().isPermittedAll(requiresPermissions.value());
			} 
			else if(Logical.OR.compareTo(requiresPermissions.logical()) == 0 ){
				boolean[] permitted = SubjectUtils.getSubject().isPermitted(requiresPermissions.value());
				for (boolean b : permitted) {
					if (b) {
						isStrPermission = b;	
					}
				}
			}
		}
		// 方法同时具有 RequiresRoles 和 RequiresPermissions 注解，且通过权限判断
		if((requiresRoles != null && requiresPermissions != null) && isRolePermission && isStrPermission){
			// 有功能权限，允许继续执行
	    	return invocation.invoke();
		}
		// 方法具有 RequiresRoles 和 RequiresPermissions 注解之一，且通过权限判断
		if((requiresRoles != null && isRolePermission) || (requiresPermissions != null && isStrPermission)){
			// 有功能权限，允许继续执行
	    	return invocation.invoke();
		}
		//如果没有注解，则按照请求的方法进行权限判断
		if(requiresRoles == null && requiresPermissions == null){
			//获取请求基本路基
			String path = request.getServletPath();
			// 特殊URL: /xxgl/xxx.html 
	        if(path.indexOf("_")==-1){
	            return invocation.invoke();
	        }
	        // 支持功能白名单：跳过不需要判断权限的地址
	        String[] notcheckURLs = StringUtils.tokenizeToStringArray(excludeURLPattern);
	        for (String notcheckURL : notcheckURLs) {
				if(matcher.match(notcheckURL, path)){
					// 免权限检查，允许继续执行
			    	return invocation.invoke();
				}
			}
	        //从Action 请求方法中解析得到操作代码
	        String czdm  = this.getOptCode(invocation);
	        //获取功能模块代码
			String gnmkdm = WebUtils.getFuncCode(request);
			/* ===================== 权限判断第二步：如果有gnmkdm，则判断功能模块代码与操作代码组合 =====================*/
			if(!BlankUtils.isBlank(gnmkdm)&&gnmkdm.startsWith("N")&&gnmkdm.split(",").length==1){      
				gnmkdm = gnmkdm.split(",")[0];
				if(SubjectUtils.getSubject().isPermitted(gnmkdm + ":" + czdm)){
					// 有功能权限，允许继续执行
			    	return invocation.invoke();
				}
			}else{
				/* ===================== 权限判断第三步：如果没有gnmkdm，则判断请求地址与操作代码组合 =====================*/
				// 拼接权限字符标记： 如 /xtgl/yhgl:cx
				String permissionCzdm = path.substring(0, path.indexOf("_")) + ":" + czdm;
				if(SubjectUtils.getSubject().isPermitted(permissionCzdm)){
					// 有功能权限，允许继续执行
			    	return invocation.invoke();
				}
			}
		}
        
        // 代码到这表示没有通过权限判断；判断是否ajax请求
		if( WebRequestUtils.isAjaxRequest(request)){ 
			invocation.getStack().set(Result.DATA, "没有访问权限!");
			return Result.DATA;
		}else{
			return Result.EX_NON_ACCESS;
		}
    }
	
	public IYhglService getService() {
		return service;
	}
	public void setService(IYhglService service) {
		this.service = service;
	}
     
}
