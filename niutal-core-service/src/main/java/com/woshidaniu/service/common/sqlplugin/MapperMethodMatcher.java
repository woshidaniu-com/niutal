/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;

/**
 * Mapper方法匹配器
 * @author 		：康康（1571）
 */
interface MapperMethodMatcher {

	boolean match(String mapperMethod,String mappedStatementId);
}
