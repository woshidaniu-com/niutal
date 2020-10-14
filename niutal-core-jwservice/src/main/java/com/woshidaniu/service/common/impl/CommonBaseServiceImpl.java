/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.impl;

import javax.annotation.Resource;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.daointerface.ICommonSqlDao;

/**
 * 
 *@类名称		： CommonBaseServiceImpl.java
 *@类描述		：系统基础Service接口
 *@创建人		：kangzhidong
 *@创建时间	：Oct 15, 2016 2:42:58 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 *@param <T>
 *@param <E>
 */
public class CommonBaseServiceImpl<T extends ModelBase, E extends BaseDao<T>>  extends BaseServiceImpl<T, E> {

	/**
	 * 业务框架通用结构数据查询接口；用于指定表名，列名称等进行查询
	 */
	@Resource
	private ICommonQueryDao queryDao;
	/**
	 * 业务框架通用业务数据查询接口
	 */
	@Resource
	private ICommonSqlDao sqlDao;
	
	/**
	 * @return the queryDao
	 */
	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}
	
	/**
	 * @param queryDao the queryDao to set
	 */
	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}
	
	/**
	 * @return the sqlDao
	 */
	public ICommonSqlDao getSqlDao() {
		return sqlDao;
	}
	
	/**
	 * @param sqlDao the sqlDao to set
	 */
	public void setSqlDao(ICommonSqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}
	
}
