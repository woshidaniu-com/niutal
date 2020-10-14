/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;
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

import com.woshidaniu.common.datarange.DataRangeHitInfo;
import com.woshidaniu.common.datarange.DataRangeHitPairInfo;
import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.entities.SjzygzModel;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.service.common.sqlExpr.ConditionSqlExprBuilder;
import com.woshidaniu.service.common.sqlExpr.DataRangeHitSqlBuilder;
import com.woshidaniu.service.common.sqlExpr.WhereInSqlExprBuilder;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

/**
 * 
 数据范围拦截器,我是大牛最强数据范围,无mapper类侵入,无mapper.xml文件侵入
 
 
 
 @see com.woshidaniu.common.datarange.DataRangeHitInfo
 @see com.woshidaniu.common.datarange.DataRangeHitPairInfo
 
 
 
以学工某一系统的统计查询为例,在spring配置文件中可加入如下配置:


 	<!-- 部门代码表限制条件，老师，所在部门 -->
	<bean id="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_bmdmb" class="com.woshidaniu.common.datarange.DataRangeHitPairInfo">
		<!-- 资源id -->
		<property name="zyId" value="teacher"/>
		<!-- 规则id -->
		<property name="gzid" value="TEACHER_SZBM"/>
		<!-- 需要限制数据范围的表 -->
		<property name="table" value="niutal_xtgl_bmdmb"/>
		<!-- 需要限制数据范围的表的字段-->
		<property name="column" value="BMDM_ID"/>
	</bean>
	
	<!-- 学生信息表限制条件 -->
	<bean id="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_xsxxb" class="com.woshidaniu.common.datarange.DataRangeHitPairInfo">
		<property name="zyId" value="teacher"/>
		<property name="gzid" value="TEACHER_SZBM"/>
		<property name="table" value="niutal_xtgl_xsxxb"/>
		<property name="column" value="BMDM_ID"/>
	</bean>
	
	<!-- 班级代码表限制条件 -->
	<bean id="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_bjdmb" class="com.woshidaniu.common.datarange.DataRangeHitPairInfo">
		<property name="zyId" value="teacher"/>
		<property name="gzid" value="TEACHER_SZBM"/>
		<property name="table" value="niutal_xtgl_bjdmb"/>
		<property name="column" value="BMDM_ID"/>
	</bean>
	
	<bean id="DataRangeHitInfo" class="com.woshidaniu.common.datarange.DataRangeHitInfo">
		<!-- 要拦截的mybatis的mapper类的方法，这里是类名加方法名 -->
		<property name="mapperMethod" value="com.woshidaniu.tjcx.dao.daointerface.ITjcxBaseDao.getListBySql"/>
		<property name="dataRangeHitPairInfos">
			<util:set>
				<ref bean="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_bmdmb"/>
				<ref bean="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_xsxxb"/>
				<ref bean="DataRangeHitPairInfo_teacher_TEACHER_SZBM_niutal_xtgl_bjdmb"/>
			</util:set>
		</property>
	</bean>
 
 
 
 @author 	康康（1571）
 *
 */
@Intercepts(//
		{
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }), 
		}
)
public class SpringBeanConfigDataRangeHitInfoInterceptor extends AbstractInterceptorAdapter implements SpringBeanConfigDataRangeHitInfoInterceptorMBean{
	
	private static final Logger log = LoggerFactory.getLogger(SpringBeanConfigDataRangeHitInfoInterceptor.class);
	
	private MapperMethodMatcher mapperMethodMatcher = new SimpleMapperMethodMatcher();
	
	private volatile boolean enable = true;
	
	private volatile boolean registed = false;
	
	private AtomicLong hitCount = new AtomicLong(0);
	
	private AtomicLong interceptCount = new AtomicLong(0);
	
	//是否显示权限标签提示
	private volatile boolean showTag = false;
	
	{
		String val = MessageUtil.getText("niutal.dataRangeHitInfo.showTag");
		this.showTag = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.showTag;
	}
	
	@Override
	public Object doStatementIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		
		this.interceptCount.incrementAndGet();
		
		this.registeJmx();
		
		if(!this.enable) {
			return invocation.proceed();
		}
		
		Map<String, DataRangeHitInfo> map = ServiceFactory.getSpringContext().getBeansOfType(DataRangeHitInfo.class);
		if(map.isEmpty()) {
			return invocation.proceed();
		}
		
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		String mappedStatementId = mappedStatement.getId();

		Set<DataRangeHitPairInfo> matchPairs = new HashSet<DataRangeHitPairInfo>();
		
		for(DataRangeHitInfo dataRangeHitInfo : map.values()) {
			
			String mapperMethod = dataRangeHitInfo.getMapperMethod();
			boolean match = this.mapperMethodMatcher.match(mapperMethod, mappedStatementId);
			
			if(match) {
				Set<DataRangeHitPairInfo> pairs = dataRangeHitInfo.getDataRangeHitPairInfos();
				if(!pairs.isEmpty()) {
					this.check(mappedStatementId,pairs);
					matchPairs.addAll(pairs);
				}
			}
		}
		if(!matchPairs.isEmpty()) {
			
			BoundSql boundSql = statementHandler.getBoundSql();
			MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
			String sql = boundSql.getSql();
			
			boolean append = this.maybeAppendCondition(matchPairs,sql,metaBoundSql);
			if(append) {
				this.hitCount.incrementAndGet();
			}
		}
		return invocation.proceed();
	}
	
	private boolean maybeAppendCondition(Set<DataRangeHitPairInfo> pairs,String sql, MetaObject metaBoundSql) {
		
		List<ConditionSqlExprBuilder> conditionSqlExprBuilders = new ArrayList<ConditionSqlExprBuilder>(pairs.size());
		for (DataRangeHitPairInfo pair: pairs) {
			ConditionSqlExprBuilder builder = this.createSqlExprBuilder(pair,sql,metaBoundSql);
			if (builder != null) {
				conditionSqlExprBuilders.add(builder);
			}
		}
		if(conditionSqlExprBuilders.isEmpty()) {
			return false;
		}
		DataRangeHitSqlBuilder builder = new DataRangeHitSqlBuilder(sql, conditionSqlExprBuilders,this.showTag);
		String newSql = builder.buildSql();
		metaBoundSql.setValue("sql", newSql);
		return true;
	}
	
	//检查注解有效性
	private void check(String mapperMethod,Set<DataRangeHitPairInfo> pairs) {
		for(DataRangeHitPairInfo pair: pairs) {
			
			String gzid = pair.getGzid();
			String zyid = pair.getZyId();
			String table = pair.getTable();
			String column = pair.getColumn();
			
			if(StringUtils.isBlank(gzid) || StringUtils.isBlank(zyid) || StringUtils.isBlank(table) ||StringUtils.isBlank(column)) {
				throw new IllegalArgumentException(mapperMethod +"匹配的DataRangeHitInfo["+ pair +"]的gzid,zyid,table,column都不能为空");
			}
		}
	}

	private ConditionSqlExprBuilder createSqlExprBuilder(DataRangeHitPairInfo pair, String sql, MetaObject metaBoundSql) {

		// 通过当前用户，取出角色数据范围
		String gzid = pair.getGzid();
		String zyid = pair.getZyId();
		String table = pair.getTable();
		String column = pair.getColumn();
		
		User user = WebContext.getUser();
		String jsdm = user.getJsdm();
		
		IJsglDao jsglDao = ServiceFactory.getService(IJsglDao.class);
		List<SjzygzModel> sjzyList = jsglDao.getSjzyList(jsdm, new String[] {zyid});// 角色规则
		if(sjzyList.isEmpty()) {
			return null;
		}else {
			// 数据范围SQL解析
			for (SjzygzModel model : sjzyList) {
				String gzid_database = model.getGzid();
				if (gzid.equals(gzid_database)) {// 拦截方法所支持的规则
					
					DataRangeService service = (DataRangeService) ServiceFactory.getService(model.getGztgz());
					List<String> sjfwList = service.getDataRangeList(user);
					if(sjfwList.isEmpty()) {
						return null;
					}else {
						String[] values = new String[sjfwList.size()];
						values = sjfwList.toArray(values);
						ConditionSqlExprBuilder conditionSqlExprBuilder = new WhereInSqlExprBuilder(table,column, values, false);
						return conditionSqlExprBuilder;
					}
				}
			}
			return null;
		}
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}
	
	private void registeJmx() {
		if(!this.registed) {
			this.doRegisteJmx();
			
		}
	}

	private synchronized void doRegisteJmx() {
		if(this.registed) {
			return;
		}
		try {
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("com.woshidaniu.niutal.v5:type=SpringBeanConfigDataRangeHitInfoInterceptor");
			if(server.isRegistered(name)) {
				server.unregisterMBean(name);
			}
			server.registerMBean(this, name);
		}catch (Exception e) {
			log.error("",e);
		}finally {
			this.registed = true;
		}
	}

	@Override
	public void setInterceptProperties(Properties properties) {
	}

	@Override
	public boolean isEnable() {
		return this.enable;
	}

	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public AtomicLong getHitCount() {
		return hitCount;
	}

	public void setHitCount(AtomicLong hitCount) {
		this.hitCount = hitCount;
	}

	public AtomicLong getInterceptCount() {
		return interceptCount;
	}

	public void setInterceptCount(AtomicLong interceptCount) {
		this.interceptCount = interceptCount;
	}

	public boolean isShowTag() {
		return showTag;
	}

	public void setShowTag(boolean showTag) {
		this.showTag = showTag;
	}
}
