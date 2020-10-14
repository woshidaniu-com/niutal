package com.woshidaniu.common.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 *@类名称:Logger.java
 *@类描述：Aop环绕通知注解：有此注解的class,方法上如果有Comment则会进行日志记录
 *@创建人：kangzhidong
 *@创建时间：2014-8-5 下午08:31:49
 *@版本号:v1.0
 */
@Target({ ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Logger {
	
	/**
	 * spring 上下文中的Name
	 */
	public abstract String name() default "commonLogAspect";
	
	/**
	 * 功能模块代码
	 */
	public abstract String model() default "";
	
	/**
	 * 业务模块代码
	 */
	public abstract String business() default "";
	
	/**
	 * bean对象中要调用的方法
	 */
	public abstract String method() default "doProceeding";
	
	/**
	 * 不需要记录日志的SQL ID;默认为空 不进行处理
	 */
	public abstract String[] unstatements() default {};
	
	/**
	 * 需要记录日志的SQL ID;默认为空 记录全部
	 */
	public abstract String[] statements() default {};
	
}