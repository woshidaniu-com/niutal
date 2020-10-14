package com.woshidaniu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 *@类名称:RefererAccess.java
 *@类描述：防止盗链注解：标注哪个Action或Method需防盗链
 *@创建人：kangzhidong
 *@创建时间：Oct 19, 2015 5:03:59 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Documented
@Inherited
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RefererAccess {
	
	public abstract String value() default "";
	
}
