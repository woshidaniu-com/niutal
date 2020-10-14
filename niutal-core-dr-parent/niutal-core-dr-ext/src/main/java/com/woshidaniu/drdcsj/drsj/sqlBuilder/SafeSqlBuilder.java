/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.sqlBuilder;

import java.util.List;

/**
 * @author 康康（1571）
 * 安全的sql构建器,防止被excel内的恶意内容攻击,导致数据库被干掉这个严肃的问题
 */
interface SafeSqlBuilder {
	
	/**
	 * @description	： 得到构建的sql
	 * @return
	 */
	public String getSql();
	
	/**
	 * @description	： 得到参数
	 * @return
	 */
	public List<String> getParams();
	
	/**
	 * @description	： 检查参数
	 * @param sql
	 */
	public void checkSql(String sql);
}
