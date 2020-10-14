/**
 * 
 */
package com.woshidaniu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kzd
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface HTMLEncode {
	/**
	 * HTML 编码类型
	 * 
	 * HTML CONTENT
	 * HTML ATTRIBUTE
	 * CSS
	 * JAVASCRIPT
	 * @return
	 */
	String type() default "HTML";
	
}
