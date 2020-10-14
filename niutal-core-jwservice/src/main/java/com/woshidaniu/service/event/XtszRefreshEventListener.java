package com.woshidaniu.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.woshidaniu.service.svcinterface.IXtszService;

/**
 * 
 *@类名称	: XtszRefreshEventListener.java
 *@类描述	：应用启动完成后主动刷新本地系统参数缓存，防止不同步问题
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月3日 上午10:52:23
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component("xtszRefreshEventListener")
public class XtszRefreshEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	protected IXtszService xtszService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//应用启动完成后主动刷新本地系统参数缓存，防止不同步问题
		getXtszService().refreshCache();
		
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}
	
}
