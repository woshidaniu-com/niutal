package com.woshidaniu.globalweb.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.web.WebContext;


/**
 * 
 *@类名称:SystemDetailsInterceptor.java
 *@类描述：系统操作详情（System details）拦截器；<br/>
 *<pre>
 *		  当doType=details且gnmkdm和czdm都不为空，表示点击的功能要启用操作功能详情确认功能
 *</pre>
 *@创建人：kangzhidong
 *@创建时间：2015-3-9 下午01:22:59
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings({ "serial" })
public class FunctionalDetailsInterceptor extends BaseAbstractInterceptor {
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	//系统操作描述service
	protected IXtczmsService service;
	
	@Override
	public void init() {
		service = (IXtczmsService) (service==null?ServiceFactory.getService("xtczmsService"):service);
	}
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("FunctionalDetailsInterceptor..."); 
    	//请求的request对象
        HttpServletRequest request = ServletActionContext.getRequest();
		
        //获取功能模块代码
		String gnmkdm = WebUtils.getFuncCode(request);
		//判断功能模块不为空
		if(BlankUtils.isBlank(gnmkdm) ){
			return interceptRequired(invocation,"W-N000000-01001",new String[]{ "gnmkdm" });
		}

		//从Action 请求方法中解析得到操作代码
        String czdm  = this.getOptCode(invocation);
        	   czdm  = "cx".equalsIgnoreCase(czdm)?"00":czdm;
		// 获取offDetails参数:表示是否关闭提示，关闭则不进入提示页面
		String offDetails = request.getParameter("offDetails");
		// 获取doType参数
		String doType = request.getParameter("doType");
		User user = WebContext.getUser();
		//使用了系统操作描述功能
		if(!("N211205".equals(gnmkdm)&&user.isStudent())&&!"1".equalsIgnoreCase(offDetails) && "details".equalsIgnoreCase(doType) && service.isHasXtczms(gnmkdm, czdm)){
			XtczmsModel model = service.getXtczms(gnmkdm, czdm);
			//操作描述信息不为空
			if(!BlankUtils.isBlank(model)){
				invocation.getStack().set("model", model );
				return Result.DETAILS;
			}else{
				return invocation.invoke();
			}
		}else{
			return invocation.invoke();
		}
    }
   
}
