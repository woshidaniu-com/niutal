package com.woshidaniu.service.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.db.core.utils.SQLInjectionUtils;
import com.woshidaniu.safety.utils.AntiSamyScanUtils;
import com.woshidaniu.safety.xss.factory.AntiSamyBoundFactory;


public final class MybatisUtils extends com.woshidaniu.orm.mybatis.utils.MybatisUtils{
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param originalSQL
	 * @param page
	 * @return
	 */
	public static String buildPageSQL(MappedStatement mappedStatement,BoundSql boundSql,String originalSQL,QueryModel page,boolean pageable,String dialect){
		if(page!=null && !StringUtils.isEmpty(dialect)){
			//获取XSS扫描器工厂
			AntiSamyBoundFactory antiSamyFactory = AntiSamyBoundFactory.getInstance();
			StringBuffer pageSql = new StringBuffer();
			if("oracle".equals(dialect)){
				String orderby = "";
				if (!StringUtils.isEmpty(page.getSortName()) ){
					//org.apache.commons.lang.StringEscapeUtils.escapeSql(str)
					//对参数进行扫描
					String sortName  = AntiSamyScanUtils.xssClean(antiSamyFactory.getDefaultAntiSamy(), page.getSortName());
					String sortOrder =  AntiSamyScanUtils.xssClean(antiSamyFactory.getDefaultAntiSamy(), StringUtils.isEmpty(page.getSortOrder()) ? " asc " : page.getSortOrder());
					orderby = sortName + " " + sortOrder;
				}
				if(pageable){
					pageSql.append(" with tmp_tb2 as (");
					pageSql.append(originalSQL);
					pageSql.append(" ),");
					pageSql.append("  tmp_tb3 as (select count(1) zs from  (");
					pageSql.append(originalSQL);
					pageSql.append(" ))");
					
					/*pageSql.append(" select t.* from (select tmp_tb1.*, ROWNUM row_id");
					pageSql.append(" from (select tmp_tb2.* ,(select zs from tmp_tb3 ) totalResult");
					pageSql.append(" from  tmp_tb2 ");
					if (!StringUtils.isEmpty(orderby)){
						pageSql.append(" order by ").append(orderby);
					}
					pageSql.append(" ) tmp_tb1");
					if(page.getShowCount()!=0){
						pageSql.append(" where ROWNUM <=").append(page.getCurrentResult()+page.getShowCount());
					}
					pageSql.append(") t where t.row_id > ").append(page.getCurrentResult());*/
					
					pageSql.append(" select t.* from (select tmp_tb1.*, ROWNUM row_id");
					pageSql.append(" from (select tmp_tb2.* ,(select zs from tmp_tb3 ) totalResult");
					pageSql.append(" from  tmp_tb2 ");
					if (!StringUtils.isEmpty(orderby)){
						pageSql.append(" order by ").append(orderby);
						pageSql.append(",rownum");
					}
					pageSql.append(" ) tmp_tb1 where rownum <=").append(page.getCurrentResult()+page.getShowCount()+") t ");
					pageSql.append(" where t.row_id > ").append(page.getCurrentResult());
					
				}else{
					//不分页，但进行排序
					if (!StringUtils.isEmpty(orderby)){
						pageSql.append(" select tmp_tb2.*,ROWNUM row_id  from (").append(originalSQL).append(" ) tmp_tb2 ");
						pageSql.append(" order by ").append(orderby);
					}else{
						pageSql.append(originalSQL);
					}
				}
			}
			return pageSql.toString();
		}else{
			return originalSQL;
		}
	}
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	public static String generatePageSql(MappedStatement mappedStatement,BoundSql boundSql,String sql,QueryModel page,boolean pageable){
		if(page != null){
			StringBuffer pageSql = new StringBuffer();
			String orderby = "";
			if (!StringUtils.isEmpty(page.getSortName()) ){
				orderby = SQLInjectionUtils.transactSQLInjection(page.getSortName()) + " " + SQLInjectionUtils.transactSQLInjection(StringUtils.isEmpty(page.getSortOrder()) ? " asc " : page.getSortOrder());
			}
			if(pageable){
				pageSql.append(" with tmp_tb2 as (");
				pageSql.append(sql);
				pageSql.append(" ),");
				pageSql.append("  tmp_tb3 as (select count(1) zs from  (");
				pageSql.append(sql);
				pageSql.append(" ))");
				pageSql.append(" select t.* from (select tmp_tb1.*, ROWNUM row_id");
				pageSql.append(" from (select tmp_tb2.* ,(select zs from tmp_tb3 ) totalResult");
				pageSql.append(" from  tmp_tb2 ");
				if (!StringUtils.isEmpty(orderby)){
					pageSql.append(" order by ");
					pageSql.append(SQLInjectionUtils.transactSQLInjection(orderby));
					pageSql.append(",rownum");
				}
				pageSql.append(" ) tmp_tb1");
				if(page.getShowCount()!=0){
					pageSql.append(" where ROWNUM <=").append(page.getCurrentResult()+page.getShowCount());
				}
				pageSql.append(") t where t.row_id > ").append(page.getCurrentResult());
				
		////	返回分页SQL String sqlss = 	MyBatisSqlUtils.getRunSQL(mappedStatement, boundSql.getParameterObject(),false);
				
				/*pageSql.append("select t.* from (select tmp_tb1.*,ROWNUM row_id from (");
				pageSql.append(" select tmp_tb2.*,count(0) over (partition by 1) totalResult from (").append(sql).append(" ) tmp_tb2 ");
				if (!StringUtils.isEmpty(orderby)){
					pageSql.append(" order by ");
					pageSql.append(SQLInjectionUtils.transactSQLInjection(orderby));
				}
				pageSql.append(") tmp_tb1 ");    
				if(page.getShowCount()!=0){
					pageSql.append(" where ROWNUM <= ").append(page.getCurrentResult()+page.getShowCount());
				}        
				pageSql.append(" ) t where t.row_id > ").append(page.getCurrentResult());*/
			}else{
				//不分页，但进行排序
				if (!StringUtils.isEmpty(orderby)){
					pageSql.append(" select tmp_tb2.*,ROWNUM row_id  from (").append(sql).append(" ) tmp_tb2 ");
					pageSql.append(" order by ");
					pageSql.append(SQLInjectionUtils.transactSQLInjection(orderby));
				}else{
					pageSql.append(sql);
				}
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		/*ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);  
		parameterHandler.setParameters(ps);
		*/
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			ParameterMapping parameterMapping = null;
			Object value = null;
			String propertyName = null;
			for (int i = 0; i < parameterMappings.size(); i++) {
				parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
}



