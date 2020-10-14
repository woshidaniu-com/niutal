package com.woshidaniu.common.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.woshidaniu.common.log.Operation;
/**
 * 
 * @className: Comment
 * @description: 日志记录注解：有此注解的方法才会记录日志
 * @author : kangzhidong
 * @date : 下午06:27:47 2014-6-7
 * @modify by:
 * @modify date :
 * @modify description :
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Comment {
	
	/**
	 * 功能模块代码
	 */
	public abstract String model() default "";
	
	/**
	 * 业务模块代码
	 */
	public abstract String business() default "";
	
	/**
	 * 不需要记录日志的SQL ID;默认过滤掉查询的日子记录
	 */
	public abstract String[] unstatements() default {".*cx.*",".*get.*",".*query.*",".*count.*"};
	
	/**
	 * 需要记录日志的SQL ID;默认为空 记录全部
	 */
	public abstract String[] statements() default {};
	
	/**
	 * 是否记录操作描述:默认true
	 */
	public abstract boolean recordDesc() default true;
	
	/**
	 * 是否记录SQL;默认true
	 */
	public abstract boolean recordSQL() default true;
	
	/**
	 * 操作类型
	 */
	public abstract Operation opt() default Operation.OP_INSERT;

}