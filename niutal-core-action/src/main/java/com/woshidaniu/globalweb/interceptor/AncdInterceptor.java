/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.globalweb.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.dao.entities.AncdModel;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名 AncdInterceptor.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年4月18日 下午3:12:58
 * @功能描述 按钮菜单拦截器,把用户的三级菜单按钮缓存到session中
 * 
 */
public class AncdInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -58568588042535484L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
//		ValueStack vs = ServletActionContext.getValueStack(ServletActionContext.getRequest());
//		String httpPath=ServletActionContext.getRequest().getServletPath();
//		User httpUser = SessionFactory.getUser().
//		HttpSession httpSession = ServletActionContext.getRequest().getSession();
//		List<AncdModel> seesionAncd = getPathAncdFromSession(httpPath, httpSession);
//		if(seesionAncd == null){
//		}else{
//			vs.set("ancdModelList", seesionAncd);
//		}
		
		return invocation.invoke();
	}
	
	/**
	 * 
	 * @param path
	 * @param ancdList
	 * @param httpSession
	 */
	@SuppressWarnings("unchecked")
	protected void savePathAncdToSession(String path, List<AncdModel> ancdList, HttpSession httpSession){
		Map<String,List<AncdModel>> ancdObject = (Map<String,List<AncdModel>>)httpSession.getAttribute(GlobalString.WEB_SESSION_ANCD);
		if(ancdObject == null){
			ancdObject = new HashMap<String, List<AncdModel>>();
			httpSession.setAttribute(GlobalString.WEB_SESSION_ANCD, ancdObject);
		}
		List<AncdModel> list = ancdObject.get(path);
		if(list == null){
			ancdObject.put(path, ancdList);
		}
	}
	
	/**
	 * 
	 * @param path
	 * @param httpSession
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<AncdModel> getPathAncdFromSession(String path, HttpSession httpSession){
		Object ancdObject = httpSession.getAttribute(GlobalString.WEB_SESSION_ANCD);
		return (ancdObject == null) ? null : ((Map<String,List<AncdModel>>)ancdObject).get(path);
	}
}
