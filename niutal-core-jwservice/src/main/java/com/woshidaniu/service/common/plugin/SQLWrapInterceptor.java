package com.woshidaniu.service.common.plugin;


import java.sql.Connection;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.daointerface.IPlxgDao;
import com.woshidaniu.entities.PlxgModel;
import com.woshidaniu.db.core.utils.SQLInjectionUtils;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.security.algorithm.DesBase64Codec;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.context.WebContext;

/**
 * 
 *@类名称		： SQLWrapInterceptor.java
 *@类描述		：通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，
 *               重写mybatis的SQL语句，实现物SQL封装
 *@创建人		：kangzhidong
 *@创建时间	：Aug 24, 2016 4:51:26 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Intercepts( { 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class , Integer.class }) 
})
public class SQLWrapInterceptor extends AbstractInterceptorAdapter {

	protected static Logger LOG = LoggerFactory.getLogger(SQLWrapInterceptor.class);
	protected static DesBase64Codec dbencrypt = new DesBase64Codec();
	/* 默认拦截动态SQL方法的正则表达式 */
	protected String DEFAULT_WRAP_METHOD_REGEXP = ".*Wrap*.*";
	protected IPlxgDao plxgDao;
	
	@Override
	protected boolean isRequireIntercept(Invocation invocation, StatementHandler statementHandler, MetaStatementHandler metaStatementHandler) {
		// 通过反射获取到当前MappedStatement
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		//拦截需要限制数据范围的SQL语句。通过MappedStatement的ID匹配
        return (mappedStatement.getId().matches(this.getMethodRegexp()));
	}
	
	@Override
	public Object doStatementIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		
		//检查是否需要进行拦截处理
		if( isRequireIntercept(invocation, statementHandler, metaStatementHandler)){
		
			// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
			BoundSql boundSql = metaStatementHandler.getBoundSql();
			MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
			// 分页参数作为参数对象parameterObject的一个属性  
			Object parameterObject = boundSql.getParameterObject();
			// 获取到我们自己写在Mapper映射语句中对应的Sql语句
			String originalSQL = (String) metaBoundSql.getValue("sql");
			// 对原始SQL进行分页SQL和总数SQL的处理
			String finalSQL = this.getWrapSQL(parameterObject,originalSQL);
			// 将处理后的物理分页sql重新写入作为执行SQL
			metaBoundSql.setValue("sql", finalSQL);
			if (LOG.isDebugEnabled()) {
				LOG.debug(" Pagination SQL : "+ statementHandler.getBoundSql().getSql());
			}
		}
		// 将执行权交给下一个拦截器  
		return invocation.proceed();
	}

	public String getWrapSQL(Object parameterObject,String originalSQL){
        return getBatchUpdateWrapSQL(parameterObject,originalSQL);
	}

	public String getBatchUpdateWrapSQL(Object parameterObject,String originalSQL){
		if(!BlankUtils.isBlank(originalSQL)){
			//请求的request对象
	        HttpServletRequest request = (HttpServletRequest) WebContext.getRequest();
			//参数为某个实体，该实体拥有queryModel属性或者包含QueryModel类型对象
			MetaObject metaObject = MetaObjectUtils.forObject(parameterObject);
			//获取功能模块代码参数
			Object 	gnmkdm = metaObject.getValue("gnmkdmKey");
					gnmkdm = BlankUtils.isBlank(gnmkdm) ?  metaObject.getValue("gnmkdm") : gnmkdm;
					gnmkdm = WebUtils.getFuncCode(request);
			String zddm = request.getParameter("zddm");
			String zdz 	= request.getParameter("zdz");	
			String xgfs = request.getParameter("xgfs");
			if(!BlankUtils.isBlank(gnmkdm) && !BlankUtils.isBlank(zddm) && !BlankUtils.isBlank(zdz) && !BlankUtils.isBlank(xgfs)){
				
				plxgDao = BlankUtils.isBlank(plxgDao) ? (IPlxgDao)ServiceFactory.getService("plxgDao") : plxgDao;
				if(plxgDao != null){
					zddm = SQLInjectionUtils.transactSQLInjection(zddm).trim();
					zdz = SQLInjectionUtils.transactSQLInjection(zdz).trim();
					xgfs = xgfs.trim();
					PlxgModel model = new PlxgModel();
					//功能模块代码
					model.setGnmkdm(gnmkdm.toString());
					//字段代码
					model.setZddm(zddm);
					//后台查询数据
					PlxgModel TmpModel = plxgDao.getModel(model);
					//判断是否存在；
					if(TmpModel != null && !BlankUtils.isBlank(TmpModel.getZdcd())){
						//字段值字符串长度是否符合要求，一个汉字等于两个字符
						if(StringUtils.getStringLength(zdz) <= Integer.valueOf(TmpModel.getZdcd())){
							
							StringBuffer wrapSQL = new StringBuffer();
							//代码块
							wrapSQL.append("declare ");
								wrapSQL.append(" tab_id varchar(50); ");
							wrapSQL.append("begin ");
								//获取主数据库用户名称
								String zd_owner = dbencrypt.decrypt(MessageUtil.getText("db.jdbc.user").getBytes()).trim();
								//查询主键名称
								wrapSQL.append("select wm_concat(column_name) into tab_id ");
								wrapSQL.append("  from all_tab_cols ");
								wrapSQL.append(" where table_name = upper('").append(TmpModel.getZdbm().trim()).append("') ");
								wrapSQL.append("   and owner = upper('").append(zd_owner).append("') ");
								wrapSQL.append("   and nullable = 'N';");
								
								//组织更新语句
								wrapSQL.append(" update ").append(TmpModel.getZdbm()).append("  t ");
								if("1".equalsIgnoreCase(xgfs)){
									wrapSQL.append(" set t.").append(TmpModel.getZddm()).append(" = '").append(zdz).append("' ");
								}else if("2".equalsIgnoreCase(xgfs)){
									wrapSQL.append(" set t.").append(TmpModel.getZddm()).append(" = '").append(zdz).append("' || t.").append(TmpModel.getZddm());
								}else if("3".equalsIgnoreCase(xgfs)){
									wrapSQL.append(" set t.").append(TmpModel.getZddm()).append(" = t.").append(TmpModel.getZddm()).append("|| '").append(zdz).append("' ");
								}else {
									wrapSQL.append(" set t.").append(TmpModel.getZddm()).append(" = t.").append(TmpModel.getZddm());
								}
								wrapSQL.append(" where (tab_id) in ( select tab_id from (");
								wrapSQL.append(originalSQL);
								wrapSQL.append("));");
							wrapSQL.append("end;");
							 
							originalSQL = wrapSQL.toString();
						}else{
							LOG.warn("参数zdz长度超出数据库字段要求长度" + TmpModel.getZdcd() + "!");
						}
					}
				}
			}
		}
		return originalSQL;
	}
	
	@Override
	public void setInterceptProperties(Properties properties) {
		if(BlankUtils.isBlank(getMethodRegexp())){
			setMethodRegexp(DEFAULT_WRAP_METHOD_REGEXP);
			LOG.warn("Property methodRegexp is not setted,use default '{0}' !",DEFAULT_WRAP_METHOD_REGEXP);
		}
	}
 
	@Override
	public Object plugin(Object target) {
		//当前拦截器逻辑仅为动态改变SQL，所以仅对StatementHandler代理对象进行包装
		if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
	}

	public IPlxgDao getPlxgDao() {
		return plxgDao;
	}

	public void setPlxgDao(IPlxgDao plxgDao) {
		this.plxgDao = plxgDao;
	}
	
}
