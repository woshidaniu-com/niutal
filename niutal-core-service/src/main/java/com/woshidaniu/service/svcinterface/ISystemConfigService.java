package com.woshidaniu.service.svcinterface;

/**
 * 系统配置服务
 * 
 * 编写这个类的初衷是让开发人员能够在运行期通过jmx方式修改配置值
 * 
 * 这样比较方便开发人员自测业务系统时，能够随时切换配置值，比较在不同配置状况下的运行轨迹情况
 * 
 * 这样的方式避免了为了修改某一个配置而频繁重启系统，减少无所谓的时间耗损
 * 
 * @see com.woshidaniu.service.impl.SystemConfigServiceImplMBean
 * @see com.woshidaniu.service.impl.SystemConfigServiceImpl
 * 
 * @author 1571
 *
 */
public interface ISystemConfigService {

	/**
	 * 获得配置值
	 * @param key
	 * @return
	 */
	String getConfigValue(String key);
	
	/**
	 * 是否启用了多角色上下文
	 * @return
	 */
	boolean isEnableMutileRoleContext();
}
