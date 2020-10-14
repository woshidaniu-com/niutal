/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

/**
 * @author 1036 小康
 * sql格式化
 */
public class SqlFormatter implements ImportFormatter{
	
	private static final Logger LOG = LoggerFactory.getLogger(SqlFormatter.class);
	
	private SqlSession sqlSession;
	//原始sql
	private String sql;
	
	public SqlFormatter(SqlSession sqlSession, String sql) {
		this.sqlSession = sqlSession;
		this.sql = sql;
	}

	@Override
	public FormatResult format(DrlpzModel drlpzModel, String value) {
		// 空不做处理
		if (StringUtils.isBlank(value)) {
			return new DefaultFormatResult(value);
		}
		String executeSql = MybatisSqlBuildUtils.buildQueryParamSql(sql);
		SimpleQueryDao simpleQueryDao = this.sqlSession.getMapper(SimpleQueryDao.class);
		List<String> queryValues = simpleQueryDao.querySingleStringColumnListByParamsSql(executeSql, Arrays.asList(value));
		
		if(LOG.isTraceEnabled()) {
			LOG.trace("待导入列：{}:格式化语句:{},参数：{}", drlpzModel.getDrl(), sql, value);			
		}
		
		//如果查询不到数据获取查询存在多条，该验证不通过
		if(queryValues.isEmpty()){
			
			String error = getErrorMessage(drlpzModel, value);
			return new DefaultFormatResult(value,error);
			
		}else if(queryValues.size() == 1){
			//取第一条数据
			String result = queryValues.get(0);
			return new DefaultFormatResult(result);
			
		}else if(queryValues.size() > 1){
			
			String error = getDuplicateValueErrorMessage(drlpzModel, value);
			return new DefaultFormatResult(value,error);
			
		}else {
			//can't happen
			return new DefaultFormatResult(value);
		}
	}
	
	/**
	 * 获取错误信息
	 * @param drlpzModel 导入列信息
	 * @param value 列值
	 * @param json JSONObject对象
	 * @return String 错误信息
	 */
	private String getErrorMessage(DrlpzModel drlpzModel, String value) {
		if (StringUtils.isNotBlank(drlpzModel.getGshxx())) {
			return "[" + drlpzModel.getDrlmc() + "]" + drlpzModel.getGshxx();
		}else {
			return "[" + drlpzModel.getDrlmc() + "]的值必须在系统中存在";			
		}
	}

	private String getDuplicateValueErrorMessage(DrlpzModel drlpzModel, String value){
		return "[" + drlpzModel.getDrlmc() + "{value= " + value + "}]的值在数据库中存在多条记录";
	}

	@Override
	public String toString() {
		return "SqlFormatter [sql=" + sql + "]@"+this.hashCode();
	}
}
