/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

public abstract class ValidatorDbValidator extends ValidatorAbstract{
	
	protected SqlSession sqlSession;

	public ValidatorDbValidator(DrpzModel drpzModel, List<DrlpzModel> drlpzList, ValidatorModel validatorModel,SqlSession sqlSession) {
		super(drpzModel, drlpzList, validatorModel);
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 批量查询 author 1036 xiaokang
	 * @param sql
	 * @param paramsList
	 * @return
	 * @throws SQLException
	 */
	protected int[] batchQuery(String sql, List<String[]> paramsList){
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
	protected List<String> query(String sql, String param) {
		String executableSql = MybatisSqlBuildUtils.buildQueryParamSql(sql);
		SimpleQueryDao simpleQueryDao = this.sqlSession.getMapper(SimpleQueryDao.class);
		List<String> result = simpleQueryDao.querySingleStringColumnListByParamsSql(executableSql,Arrays.asList(param));
		return result;
	}
}
