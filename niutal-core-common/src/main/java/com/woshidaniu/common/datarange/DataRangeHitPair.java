/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.datarange;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 康康（1571）
 * 提示注解，用于@DataRangeHit注解上
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface DataRangeHitPair {

	//数据资源ID
	String zyId();
	
	//数据规则名称
	String gzid();
	
	//表名称,真实的表名称,而非表别名
	String table();
	
	//对应字段名称,真实的字段名,而非字段别名
	String column();
	
}
