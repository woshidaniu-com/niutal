package com.woshidaniu.common.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 		：zhidong-1571
 * 
 * 基础生命周期MBean对象,继承此类可快速实现MBean注册
 */
public class BaseLifecycleMBean extends BaseLifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(BaseLifecycleMBean.class);
	
	private JmxBeanRegisterHelper helper = new JmxBeanRegisterHelper();

	@Override
	protected void doStart() {
		helper.registerMBean(this);
	}

	@Override
	protected void doStop() {
		helper.unregisterMBean(this);
	}
}