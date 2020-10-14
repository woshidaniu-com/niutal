/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.utils;

/**
 * @author 		：康康（1571）
 * @description	： mybatis查询构建器
 */
public class MybatisSqlBuildUtils {
	
	private static String buildQueryParamSql(String originSql,String paramsName) {
		int param_index = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<originSql.length();i++) {
			char c = originSql.charAt(i);
			if(c == '?') {
				sb.append("#{"+ paramsName +"["+ param_index +"]}");
				param_index++;
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String buildQueryParamSql(String originSql) {
		return buildQueryParamSql(originSql, "params");
	}
}
