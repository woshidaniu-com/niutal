/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;

class SimpleMapperMethodMatcher implements MapperMethodMatcher{

	@Override
	public boolean match(String mapperMethod,String mappedStatementId) {
		if(mapperMethod == null) {
			throw new IllegalArgumentException("mapperMethod can't be null");
		}
		return mapperMethod.equals(mappedStatementId);
	}
}
