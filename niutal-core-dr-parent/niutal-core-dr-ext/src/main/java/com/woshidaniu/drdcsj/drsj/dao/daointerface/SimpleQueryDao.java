/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SimpleQueryDao {
	//------------以下是工具方法，提供给验证等组件使用
	/**
	 * @description	： 根据sql获得单一列的List
	 * @param sql
	 * @return
	 */
	List<String> querySingleStringColumnListBySql(@Param("sql") String sql);
	/**
	 * @description ：根据多个参数的sql获得List数据
	 * @param sql
	 * @param params
	 * @return
	 */
	List<String> querySingleStringColumnListByParamsSql(@Param("sql") String sql,@Param("params") List<String> params);
	/**
	 * @description	： 根据多个参数的sql获得多个列的int值List数据
	 * @param executableSql
	 * @param paramsList
	 * @return
	 */
	Integer[] querySingleIntegerColumnListByParamsSql(@Param("sql")String sql, @Param("params")List<String> params);
	
	/**
	 * @description	：根据多个参数的sql获得整数返回值
	 * @param sql
	 * @param params
	 * @return
	 */
	Integer querySingleIntegerByParamsSql(@Param("sql")String sql, @Param("params")List<String> params);
	
	/**
	 * @description	： 更新
	 * @param sql
	 * @param params
	 * @return
	 */
	Integer update(@Param("sql")String sql, @Param("params")List<String> params);
}
