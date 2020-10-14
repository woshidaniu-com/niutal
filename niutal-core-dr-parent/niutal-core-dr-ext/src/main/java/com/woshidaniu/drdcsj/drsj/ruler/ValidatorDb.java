/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

/**
 * @author 982 张昌路
 * 
 * @author 1571 康康
 * 1.改用sqlSession，复用sqlSession，提高性能
 * 
 */
public abstract class ValidatorDb {
	
	protected static final Logger log = LoggerFactory.getLogger(ValidatorDb.class);
	
	protected SqlSession sqlSession;
	
	public ValidatorDb(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	/**
	 * 批量查询 author 1036 xiaokang
	 * @param sql
	 * @param paramsList
	 * @return
	 * @throws SQLException
	 */
	public int[] batchQuery(String sql, List<String[]> paramsList){
		///构建参数
		List<String> params = new ArrayList<String>();
		for(int i=0;i<paramsList.size();i++) {
			String[] arr = paramsList.get(i);
			for(int k=0;k<arr.length;k++) {
				String v = arr[k];
				params.add(v);
			}
		}
		//转换成 select count(1) from t_user where a = ? and b = ? 
		//	  union all
		//	  select count(1) from t_user where a = ? and b = ?
		int unionTime = paramsList.size();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<unionTime;i++) {
			sb.append(sql);
			if( i != unionTime - 1) {
				sb.append(" union all ");
			}
		}
		//构建mybatis执行的sql
		String executableSql = MybatisSqlBuildUtils.buildQueryParamSql(sb.toString());
		
		SimpleQueryDao simpleQueryDao = this.sqlSession.getMapper(SimpleQueryDao.class);
		Integer[] tempResult = simpleQueryDao.querySingleIntegerColumnListByParamsSql(executableSql,params);
		int[] result = new int[tempResult.length];
		for(int i=0;i<tempResult.length;i++) {
			Integer temp = tempResult[i];
			result[i] = temp.intValue();
		}
		return result;
	}
	/**
	 * 列查询操作
	 * @param sql 查询语句
	 * @param param 唯一参数
	 * @return
	 * @throws SQLException
	 */
	public List<String> query(String sql, String param) {
		String executableSql = MybatisSqlBuildUtils.buildQueryParamSql(sql);
		SimpleQueryDao simpleQueryDao = this.sqlSession.getMapper(SimpleQueryDao.class);
		List<String> result = simpleQueryDao.querySingleStringColumnListByParamsSql(executableSql,Arrays.asList(param));
		return result;
	}
}
