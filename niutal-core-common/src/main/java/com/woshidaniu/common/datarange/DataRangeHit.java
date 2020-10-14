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
 * 数据范围提示,绑定数据范围条件到具体的某个表,某个字段上,就是说指哪打哪 :) 
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataRangeHit {

	/**
	 *  提示pair
	 * 如下原始sql
	 * 	select * from t_student
	 * 则会处理成
	 *  select * from t_student where BMDM in( .... )
	 * @return
	 */
	DataRangeHitPair[] hits();
}
