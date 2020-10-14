package com.woshidaniu.common.datarange;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据范围拦截器通过检测该注解决定如何进行处理
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月23日下午2:58:03
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataRange {

	
	/**
	 * 
	 * <p>方法说明：数据资源ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月23日下午1:31:33<p>
	 * @return String[]
	 */
	String[] dataIds();
	
	/**
	 * 
	 * <p>
	 * 	方法说明：数据资源与规则关联信息;<br>
	 * <pre>
	 * 示例一：{STUDENT_SZXY:'BMDM'} <br>
	 * 说明：数据源支持规则 STUDENT_SZXY ，对应字段 XYDM
	 * </pre>
	 * <pre>
	 * 示例二：{STUDENT_SZXY:'BMDM',STUDENT_SDBJ:'BJDM_ID'} <br>
	 * 说明：数据源支持规则 
	 * 1、 STUDENT_SZXY ，对应字段 XYDM
	 * 2、 STUDENT_SDBJ ，对应字段 BJDM
	 * </pre>
	 * STUDENT_SZXY:数据资源规则ID<br>
	 * STUDENT_SDBJ:数据资源规则ID<br>
	 * XYDM:查询过滤字段<br>
	 * BJDM:查询过滤字段<br>
	 * </p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月22日下午3:35:59<p>
	 * @return java.lang.String
	 */
	String info();
	
	
	
	/**
	 * 
	 * <p>方法说明：数据范围关联查询方式<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月23日下午2:31:56<p>
	 * @return QueryType
	 */
	QueryType type() default QueryType.IN;
}
