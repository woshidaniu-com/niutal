package com.woshidaniu.plugins.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.woshidaniu.common.action.BaseAction;

/**
 * 缓存管理类
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class CacheAction extends BaseAction{

	private static final long serialVersionUID = -2501362324302188782L;
	
	/**
	 * 缓存更新
	 * @return
	 */
	public String cacheUpdate(){
		return "cacheUpdate";
	}
	
//	/**
//	 * 高级查询缓存更新
//	 * @return
//	 */
//	public String gjcxUpdate(){
//		String result = "success";
//		try {
//			CacheManagerLoader.getInstance().clearElement(BaseGBDataEnum.GJCX_HTML.getValue());
//			CacheLoader.addGjcxCache();
//		} catch (Exception e) {
//			log.error("高级查询缓存更新异常:",e);
//			result = e.toString();
//		}
//	    getValueStack().set(DATA,result);
//		return DATA;
//	}
}


