package com.woshidaniu.service.common.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.beanutils.reflection.JavaAssistUtils;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.aop.aware.AfterAware;
import com.woshidaniu.common.aop.aware.BeforeAware;
import com.woshidaniu.common.aop.aware.ExceptionAware;
import com.woshidaniu.common.aop.aware.Invocation;
import com.woshidaniu.common.log.Operation;
import com.woshidaniu.common.log.User;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.daointerface.ILogDao;
import com.woshidaniu.entities.RzglModel;
import com.woshidaniu.format.utils.OgnlFormatUtils;
import com.woshidaniu.orm.mybatis.spring.support.MybatisDataSourceDao;
import com.woshidaniu.orm.mybatis.utils.MyBatisSQLUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;
/**
 * 
 * @package com.woshidaniu.service.common.aspect
 * @className: CommonLogAspect
 * @description: 公共的日志记录切面回调
 * @author : kangzhidong
 * @date : 2014-6-11
 * @time : 下午05:25:21
 */
public class CommonLogAspect implements AfterAware,BeforeAware,ExceptionAware {

	protected org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Resource
	private ILogDao dao;
	//成绩数据库操作dao接口
	private MybatisDataSourceDao dataSourceDao;
	@Resource
	private ICommonQueryDao queryDao;
	
	private String[] inserts = new String[]{"zj","plzj","bc","plbc","add","batchadd","insert","batchinsert","inherit"};
	private String[] deletes = new String[]{"sc","plsc","delete","remove","batchdelete","batchremove"};
	private String[] updates = new String[]{"gx","sz","plgx","xg","plxg","update","batchupdate","modify","batchmodify"};
	private String[] querys = new String[]{"cx","query","get","find","select"};
	private StringBuilder build = new StringBuilder();
	//切面调用信息
	private ThreadLocal<Invocation> threadLocal = new ThreadLocal<Invocation>();
	private RzglModel model = null;
	
	private Operation getOperation(String methodName){
		methodName = methodName.toLowerCase();
		Operation opt = Operation.OP_UPDATE;
		for (String prefix : inserts) {
			if(methodName.startsWith(prefix)){
				opt = Operation.OP_INSERT;
				break;
			}
		}
		for (String prefix : deletes) {
			if(methodName.startsWith(prefix)){
				opt = Operation.OP_DELETE;
				break;
			}
		}
		for (String prefix : updates) {
			if(methodName.startsWith(prefix)){
				opt = Operation.OP_UPDATE;
				break;
			}
		}
		for (String prefix : querys) {
			if(methodName.startsWith(prefix)){
				opt = Operation.OP_SELECT;
				break;
			}
		}
		return opt;
	}
	
	/**
	 * 
	 * @description: 在方法执行前自动填充参数
	 * 
	 * @author : kangzhidong
	 * @date : 2014-6-11
	 * @time : 下午05:25:46 
	 * @param inv
	 */
	public void doBefore(Invocation inv) throws Exception {
		//log.info("=====================调用[" + inv.getTarget().getClass() + "] 类 [ " + inv.getMethod().getName() + "]方法的before通知:开始记录日志==================================");  
		//if(BlankUtils.isBlank(threadLocal.get())){
			//清除历史日志组
			threadLocal.remove();
			//查询日志注解
			Comment comment = inv.getComment();
			//Comment不为空;开启记录描述或者开启记录SQL表示进行日志记录
			if(!BlankUtils.isBlank(comment) && ( comment.recordDesc() == true || comment.recordSQL() == true)){
				//缓存当前日志组
				threadLocal.set(inv);
				
				//查询日志注解
				Logger logger = inv.getLogger();
				
				//用户对象
				User user = WebContext.getUser();
				//功能模块
				String modelKey = BlankUtils.isBlank(comment.model())?logger.model():comment.model();
				String modelName = MessageUtil.getLogText(modelKey);
				//业务模块
				String businessKey = BlankUtils.isBlank(comment.business())?logger.business():comment.business();
				String business = MessageUtil.getLogText(businessKey);
				//操作类型
				String methodName = inv.getMethod().getName();
				Operation opt  = getOperation(methodName);
				
				String hostInfo[] = WebContext.getRemoteInfo();
				
				//用户[]在[]模块，进行[]操作，
				model = new RzglModel();
				
				//设置日志组号ID
				model.setRzzh_id(queryDao.getSysGuid());
				//操作人
				model.setCzr(user.getYhm());
				//操作日期：数据库自动获取
				//操作模块
				model.setCzmk(modelName  );
				//业务名称
				model.setYwmc(business);
				//操作类型:根据方法名称判断
				model.setCzlx(opt.toString().split(":")[0]);
				//IP地址
				model.setIpdz(hostInfo[0]);
				//主机名
				model.setZjm(hostInfo[2]);
			}
		//}
	}
	
	/**
	 * 
	 * @description: 在方法执行后记录SQL
	 * @author : kangzhidong
	 * @date : 2014-6-11
	 * @time : 下午05:25:46 
	 * @param inv
	 */
	public void doAfter(Invocation inv) throws Exception{
		try {
			
			//查询日志注解
			Logger logger = inv.getLogger();
			Comment comment = inv.getComment();
			//Comment不为空;开启记录描述进行描述日志记录
			if(!BlankUtils.isBlank(comment) && comment.recordDesc() == true ){
				
				//用户对象
				User user = WebContext.getUser();
				//功能模块
				String modelKey = BlankUtils.isBlank(comment.model())?logger.model():comment.model();
				String modelName = MessageUtil.getLogText(modelKey);
				//业务模块
				String businessKey = BlankUtils.isBlank(comment.business())?logger.business():comment.business();
				String business = MessageUtil.getLogText(businessKey);
				//操作类型
				String methodName = inv.getMethod().getName();
				Operation opt  = getOperation(methodName);
				
				//基本操作描述：系统自动装配
				build.delete(0, build.length());
				
				build.append("用户[").append(user.getXm()).append("(").append(user.getYhm()).append(");<br/>");
				build.append("在[").append(modelName).append("]功能模块;<br/>");
				build.append("[").append(business).append("]业务模块;<br/>");
				build.append("进行[").append(opt.toString().split(":")[1]).append("]操作;<br/>");
				
				//详细操作描述：开发者配置
				String operateDesc = MessageUtil.getLogText(businessKey +"-"+ methodName);
				if(!BlankUtils.isBlank(operateDesc)){
					
					try {
						String[] paramters = JavaAssistUtils.getMethodParamNames(inv.getTarget().getClass(),methodName);
						//存储参数： key 要与Mapper中的参数名一致
						Map<String,Object> parameterContext = new HashMap<String,Object>(); 
						for (int i = 0; i < inv.getArgs().length ; i++) {
							Object argment =  inv.getArgs()[i];
							parameterContext.put(paramters[i],argment); 
						}
						parameterContext.put("user", WebContext.getUser());
						build.append(OgnlFormatUtils.format(operateDesc, parameterContext));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//操作描述
				model.setCzms(build.toString());
				
				//记录日志到主数据库
				dao.insert(model);
				
				threadLocal.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//log.info("=====================调用[" + inv.getTarget().getClass() + "] 类 [ " + inv.getMethod().getName() + "]方法的after通知:开始记录完成==================================");  
	}
	
	/**
	 * 
	 * @description: 在方法执行发生异常后被调用
	 * @author : kangzhidong
	 * @date : 2014-6-11
	 * @time : 下午05:25:46 
	 * @param inv
	 */
	public void doException(Invocation inv) throws Exception{
		
		/*异常处理：如发生Email*/
		
		
	}
	
	
	public void doAfterStatement(MappedStatement mappedStatement,BoundSql boundSql){
		if(!BlankUtils.isBlank(dataSourceDao)){
			//判断此次方法调用是否记录SQL
			Invocation inv = threadLocal.get();
			if(!BlankUtils.isBlank(inv)){
				Comment comment = inv.getComment();
				//Comment不为空;开启记录SQL表示进行SQL日志记录;并且筛选掉 getSysGuid方法的处理
				boolean isRecord = isRecord(mappedStatement.getId(),inv);
				
				if(!BlankUtils.isBlank(comment) && comment.recordSQL() == true && isRecord){
				
					//log.info("=====================是否记录SQL：isRecord =" +isRecord +" ==================================");
					//log.info("=====================在[" + mappedStatement.getId() + "]SQL 执行完成后记录SQL语句 ==================================");
					
					//insert into niutal_xtgl_czrzb ( czbh, yhm, czmk, ywmc, czlx, czms, ipdz, zjm ) values (sys_guid() , ?, ?,?, ?, ?, ?, ?) 
					try {
						//操作SQL
						model.setCzms(MyBatisSQLUtils.getRunSQL(mappedStatement, boundSql.getParameterObject(),true));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//获得statementID
					String statementID = MyBatisSQLUtils.getStatementID(ILogDao.class, "insert");
					try {
						//保存SQL
						dataSourceDao.insert(statementID, model);
					} catch (Exception e) {
						LOG.error(e.getMessage(), e.getCause());
					}
				}
			}
		}
	}
	//判断是否记录
	private boolean isRecord(String statementID,Invocation inv){
		Comment comment = inv.getComment();
		Logger logger = inv.getLogger();
		String[] unstatements = ArrayUtils.addAll(comment.unstatements(), logger.unstatements());
				 //过滤掉审核流程和日志记录方法本身
				 unstatements = ArrayUtils.addAll(unstatements, new String[]{".*getSysGuid*.*",".*ILogDao.*",".*IPendingAffairInfoDao.*",".*ISpAuditingLogDao.*",".*ISpBusinessDao.*",".*ISpLineDao.*",".*ISpNodeDao.*",".*ISpNodeTaskDao.*",".*ISpProcedureDao.*",".*ISpTaskDao.*",".*ISpWorkLineDao.*",".*ISpWorkNodeDao.*",".*ISpWorkProcedureDao.*",".*ISpWorkTaskDao.*"});
		
		String[] statements = ArrayUtils.addAll(comment.statements(), logger.statements());
		
		boolean isRecord = true;
		if((statementID.indexOf("getSysGuid") > -1 || statementID.matches(".*getSysGuid*.*"))||statementID.indexOf("ILogDao") > -1 || statementID.matches(".*ILogDao.*")){ 
			isRecord = false;
		}else if(!BlankUtils.isBlank(unstatements)){
			for (String unstatement : unstatements) {
				if(statementID.indexOf(unstatement) > -1 || statementID.matches(unstatement)){ 
					isRecord = false;
				}
			}
		}else if(!BlankUtils.isBlank(statements)){
			isRecord = false;
			for (String statement : statements) {
				if(statementID.indexOf(statement) > -1 || statementID.matches(statement)){ 
					isRecord = true;
				}
			}
		}
		if(isRecord){
			//log.info("=====================unstatements:[" + StringUtil.join(unstatements) + "]==================================");
			//log.info("=====================statements:[" + StringUtil.join(statements)  + "] ==================================");
		}
		return isRecord;
	}
	
	public ThreadLocal<Invocation> getThreadLocal() {
		return threadLocal;
	}

	public ILogDao getDao() {
		return dao;
	}

	public void setDao(ILogDao dao) {
		this.dao = dao;
	}

	public MybatisDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(MybatisDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}
	
}
