/**
 * 
 */
package com.woshidaniu.common.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @描述 系统操作日志注解
 * 
 * @version 1.0
 * 
 * @author xiaokang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface BusinessLog {
	
	/**
	 * 操作模块
	 */
	public String czmk() default "";
	
	/**
	 * 业务名称
	 */
	public String ywmc() default "";
	
	/**
	 * 操作类型
	 */
	public BusinessType czlx() ;
	
	/**
	 * 操作描述
	 */
	public String czms();
	
	/**
	 * 是否马上处理
	 */
	public boolean immediate() default false;
	
}
