/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.svcinterface;

import java.util.Map;

/**
 * @类名 ICsszService.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月18日 下午5:30:42
 * @功能描述 在线咨询-参数设置
 * 
 */
public interface ICsszService {
	/**
	 * 查询配置信息
	 * @return
	 */
	Map<String,String> getConfig();
	
	/**
	 * 更新配置信息
	 * @param condig
	 * @return
	 */
	boolean updateConfig(Map<String,String> config);
	
	/**
	 * 在线咨询是否可用
	 * @param config
	 * @return
	 */
	Map<String,Object>  isOpen(boolean isLogin);
	
}
