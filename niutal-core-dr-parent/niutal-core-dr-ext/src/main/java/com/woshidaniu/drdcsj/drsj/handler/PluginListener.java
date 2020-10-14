/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 插件Listener,在spring中标识是handler的bean
 * @author 		：康康（1571）
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PluginListener {

	/**
	 * @description	： 导入模块代码
	 * @return
	 */
	String[] drmkdm();
	
	/**
	 * @description	： 是否启用
	 * @return
	 */
	boolean enable() default true;
	
	/**
	 * @description	： 针对处理此drmkdm的顺序
	 * @return
	 */
	int order() default Integer.MAX_VALUE;
}
