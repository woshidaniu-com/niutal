/**
 * 
 */
package com.woshidaniu.globalweb.interceptor;

import java.lang.annotation.*;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月9日下午5:47:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface POST {

	String value() default "post";
}
