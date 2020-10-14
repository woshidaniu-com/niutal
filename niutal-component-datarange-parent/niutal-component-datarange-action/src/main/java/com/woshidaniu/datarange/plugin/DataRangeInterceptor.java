package com.woshidaniu.datarange.plugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.daointerface.ICommonSqlDao;
import com.woshidaniu.daointerface.IJsglDao;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.dao.entities.SjfwzModel;
import com.woshidaniu.datarange.service.svcinterface.ISjfwdxService;
import com.woshidaniu.datarange.service.svcinterface.ISjfwzService;
import com.woshidaniu.datarange.utils.DataRangeSQLUtils;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
/**
 * 
 *@类名称		：DataRangeInterceptor.java
 *@类描述		：通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，
 * 				  重写mybatis的SQL语句，数据范围过滤，物理分页
 *@创建人		：kangzhidong
 *@创建时间	：Aug 24, 2016 4:38:41 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class DataRangeInterceptor extends AbstractInterceptorAdapter {

	protected static Logger LOG = LoggerFactory.getLogger(DataRangeInterceptor.class);
	/* 默认拦截分页方法的正则表达式 */
	protected final String DEFAULT_DATARANGE_METHOD_REGEXP = ".*ByScope*.*"; 
	protected String limitId = "jg_id";    //默认数据范围字段Id,在用户没有设置过数据范围时，对此字段指定的值进行解析：如：jg_id
	protected Pattern pattern_find = Pattern.compile("(?:(?:\\{)(?:[^\\{\\}]*?)(?:\\}))+");
	protected List<SjfwzModel> allCollegeDataRanges = null;
	protected List<String> dataRangeFieldList = null;
	
	//数据范围对象service接口
	protected ISjfwdxService sjfwdxService = null;
	//数据范围组service接口
	protected ISjfwzService sjfwzService = null;
	//角色管理dao接口
	protected IJsglDao jsglDao;
	//公共查询dao接口
	protected ICommonSqlDao commonSqlDao = null;
	
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
			//拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象 
			String originalSQL = boundSql.getSql();
			// 匹配每段数据范围规则内容中的func
			Matcher matcher2 = pattern_find.matcher(originalSQL);
			if (matcher2.find()) {
	        	
	            //解决webservice未登陆情况用户数据权限问题
				User user =  SessionFactory.getUser();
	            if(BlankUtils.isBlank(user)){
	            	
	            	user = new User();
	            	
	            	String sso_user_key = Parameters.getGlobalString(Parameter.SESSION_SSO_USER_KEY);
	            	String sso_role_key = Parameters.getGlobalString(Parameter.SESSION_SSO_ROLE_KEY);
	            	
	            	String yhm = (String) SessionFactory.getSession().getAttribute(sso_user_key);
	            	String jsdm = (String) SessionFactory.getSession().getAttribute(sso_role_key);
	            	
	            	LOG.error("webService 访问 ：组织访问用户信息对象！用户名：" + yhm + "，角色代码：" +jsdm );
	            	user.setYhm(yhm);
	            	
	            	if(BlankUtils.isBlank(jsdm)){
	            		
	            		//查询用户信息
	            		jsglDao = BlankUtils.isBlank(jsglDao) ? (IJsglDao)ServiceFactory.getService("jsglDao") : jsglDao;
		            	//若多种角色则采用角色切换的方式,这里按角色级别取最大级别
		    			List<JsglModel> jsglModels = jsglDao.cxJsxxListByYhm(yhm);
		    			
		    			if(!BlankUtils.isBlank(jsglModels)){
		    				jsdm = jsglModels.get(0).getJsdm();
		    			}else{
		    				//设置虚拟角色，防止报错
		    				jsdm = "null";
		    			}
	            	}
	    			user.setJsdm(jsdm);
	    			
	    			SessionFactory.getSession().removeAttribute(sso_user_key);
	    			SessionFactory.getSession().removeAttribute(sso_role_key);
	    			
	            }
	            
	            //查询数据范围对象
	            if(BlankUtils.isBlank(dataRangeFieldList)){
	            	dataRangeFieldList = new ArrayList<String>();
	            	//获得Service接口
	            	sjfwdxService = BlankUtils.isBlank(sjfwdxService) ? (ISjfwdxService) ServiceFactory.getRealService("sjfwdxService", BaseConstant.XXDM) : sjfwdxService; 
		    		//查询数据范围对象
		    		List<SjfwdxModel> sjfwdxList = sjfwdxService.cxSjfwdx(new SjfwdxModel());
		    		if(!BlankUtils.isBlank(sjfwdxList)){
		    			for (SjfwdxModel sjfwdxModel : sjfwdxList) {
		    				if(!BlankUtils.isBlank(sjfwdxModel.getZddm())){
		    					dataRangeFieldList.add(sjfwdxModel.getZddm());
		    				}
		    			}
		    		}
	            }
	            
	    		//得得用户的数据范围集合
	            Map<String,Object> map = new HashMap<String,Object>();
	            map.put("yh_id", user.getYhm());
	            map.put("jsdm", user.getJsdm());
	            //map.put("list", user.getJsdms());
	            //获得Service接口
	            sjfwzService =  BlankUtils.isBlank(sjfwzService) ? (ISjfwzService) ServiceFactory.getService("sjfwzService") : sjfwzService;
	            List<SjfwzModel> dataRanges = sjfwzService.cxSjfwzYhjs(map);
            	 //如果是全学院数据范围，则查询学院信息
	            if( DataRangeSQLUtils.isAllCollege(dataRanges)){
	            	//获得Service接口
	            	commonSqlDao = BlankUtils.isBlank(commonSqlDao) ?  (ICommonSqlDao) ServiceFactory.getService("commonSqlDao") : commonSqlDao;
	            	List<BmdmModel> jgList = commonSqlDao.getAllJgxxList("1");
	            	if(!BlankUtils.isBlank(jgList)){
	            		allCollegeDataRanges = new ArrayList<SjfwzModel>();
	            		for (BmdmModel bmdmModel : jgList) {
							allCollegeDataRanges.add(new SjfwzModel(bmdmModel.getJgmc(), "jg_id="+bmdmModel.getJg_id()));
						}
	            	}
	            }
	            //对原始SQL进行数据范围限制条件的处理
	            String dataRangeSQL = DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL, dataRangeFieldList , dataRanges, allCollegeDataRanges ,limitId);
				//ReflectionUtils.setValueByFieldName(boundSql, "sql", originalSQL);
            	//将处理后的SQL重新写入作为执行SQL
	            metaBoundSql.setValue("sql", dataRangeSQL);
				if (LOG.isDebugEnabled()) {
					// 对原始SQL进行分页SQL和总数SQL的处理
					LOG.debug(" DataRange SQL : " + statementHandler.getBoundSql().getSql());
				}
			}
	        
		}
		// 将执行权交给下一个拦截器  
		return invocation.proceed();
	}

	@Override
	public void setInterceptProperties(Properties properties) {
		
		if(BlankUtils.isBlank(getMethodRegexp())){
			setMethodRegexp(DEFAULT_DATARANGE_METHOD_REGEXP);
			LOG.warn("Property methodRegexp is not setted,use default '{0}' !", DEFAULT_DATARANGE_METHOD_REGEXP);
		}
		
		String limitID = properties.getProperty("limitID");
		if (StringUtils.isNotEmpty(limitID)) {
			this.setLimitId(limitID);
		}else{
			this.setLimitId(null);
			LOG.warn("Property limitID is not setted,use default null !");
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

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public ISjfwdxService getSjfwdxService() {
		return sjfwdxService;
	}

	public void setSjfwdxService(ISjfwdxService sjfwdxService) {
		this.sjfwdxService = sjfwdxService;
	}

	public ISjfwzService getSjfwzService() {
		return sjfwzService;
	}

	public void setSjfwzService(ISjfwzService sjfwzService) {
		this.sjfwzService = sjfwzService;
	}

	public IJsglDao getJsglDao() {
		return jsglDao;
	}

	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}

	public ICommonSqlDao getCommonSqlDao() {
		return commonSqlDao;
	}

	public void setCommonSqlDao(ICommonSqlDao commonSqlDao) {
		this.commonSqlDao = commonSqlDao;
	}
	
}
