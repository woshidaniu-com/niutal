package com.woshidaniu.globalweb.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.entities.TimeSettingModel;
import com.woshidaniu.service.common.ICommonTimeSettingService;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;


/**
 * 
 *@类名称: TimeSettingAccessInterceptor.java
 *@类描述：  功能访问时间控制拦截器
 *@创建人： kangzhidong
 *@创建时间：2015-2-9 下午06:27:50
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings({ "serial" })
public class FunctionalTimeSettingInterceptor extends BaseAbstractInterceptor {
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	//时间设置service
	protected ICommonTimeSettingService service;
	
	@Override
	public void init() {
		service = (ICommonTimeSettingService) (service==null?ServiceFactory.getService("timeSettingService"):service);
	}
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("FunctionalTimeSettingInterceptor..."); 
    	//请求的request对象
        HttpServletRequest request = ServletActionContext.getRequest();
        //从Action 请求方法中解析得到操作代码
        String czdm  = this.getOptCode(invocation);
        /* //获取请求基本路基
		String path = request.getServletPath();
		//从Action url中解析得到操作代码
        String czdm  = WebRequestUtils.getOptCode(path,"html");
        */
        //获取功能模块代码
		String gnmkdm = WebUtils.getFuncCode(request);
		//判断功能模块不为空
		if(BlankUtils.isBlank(gnmkdm)){
			return interceptRequired(invocation,"W-N000000-01001",new String[]{ "gnmkdm" });
		}
		//开启了时间控制
		if(service.isOnTimeControl(gnmkdm, czdm)){
			//在开放时间内
			if(service.isInTimeSetting(gnmkdm, czdm)){
				return invocation.invoke();
			}else{
				TimeSettingModel model = service.getTimeSetting(gnmkdm, czdm);
				//获取提示信息
				if(BlankUtils.isBlank(model.getTsxx())){
					List<Object> args = new ArrayList<Object>();
					args.add(model.getKssj());
					args.add(model.getJssj());
					invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01003", "", args , invocation.getStack()) );
					//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01003",new Object[]{model.getKssj(),model.getJssj()})  );
				}
				invocation.getStack().set("model", model );
				return Result.EX_NON_OPEN;
				/*
				//判断是否ajax请求
				if( WebRequestUtils.isAjaxRequest(request)){
					invocation.getStack().set(Result.DATA, message);
					return Result.DATA;
				}else{  
					
				}*/
			}
		}else{
			return invocation.invoke();
		}
    }
    
    
    
    
    
    
}
