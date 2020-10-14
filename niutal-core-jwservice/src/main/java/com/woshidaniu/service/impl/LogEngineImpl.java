package com.woshidaniu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.Operation;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.entities.RzglModel;
import com.woshidaniu.service.svcinterface.ILogService;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

/**
* 
* 类名称：OperateLogEngineImpl
* 类描述：日志记录入口
* 创建人：qph
* 创建时间：2012-4-20 
* 修改备注： modify by caozf
*
*/
@Service
public class LogEngineImpl extends BaseLog{
	
	protected static Logger LOG = LoggerFactory.getLogger(LogEngineImpl.class);

	private static LogEngineImpl instance = new LogEngineImpl(); 
	
	private LogEngineImpl() {
		
	}
	public static LogEngineImpl getInstance() {
		return instance;
	}
	
	
	// ------------------------------

	
	/**
	 * 记录什么人，对什么业务 ,作了什么操作
	 * @param user 			操作人
	 * @param modelKey 		模块名称KEY
	 * @param businessKey	 业务名称KEY
	 * @param czlx 			操作类型
	 * @param czms 			操作描述
	 */
	public void log(User user,String modelKey, String businessKey,Operation czlx, String czms) {
		try {
			StringBuilder build = new StringBuilder();
			RzglModel model = new RzglModel();
			//功能模块
			String modelName = MessageUtil.getText(modelKey);
				   modelName = StringUtils.hasText(modelName)?modelName:modelKey;
			//业务模块
			String business = MessageUtil.getText(businessKey);
				   business = StringUtils.hasText(business)?business:businessKey;
			//操作描述
			//build.delete(0, build.length());
			build.append("用户[").append(user.getYhm()).append("(").append(user.getXm()).append(")]<br/>");
			build.append("在[").append(modelName).append("]功能模块;<br/>[");
			build.append(business).append("]业务模块;<br/>进行[");
			build.append(czlx.toString().split(":")[1]).append("]操作;<br/>");
			if(!BlankUtils.isBlank(czms)){
				build.append(czms);
			}
			//操作人
			model.setCzr(user.getYhm());
			//操作日期：数据库自动获取
			//操作模块
			model.setCzmk(modelName);
			//业务名称
			model.setYwmc(business);
			//操作类型:根据方法名称判断
			model.setCzlx(czlx.toString().split(":")[0]);
			//操作描述
			model.setCzms(build.toString());
			//获得请求信息接口
			String hostInfo[] = WebContext.getRemoteInfo();
			//IP地址
			model.setIpdz(hostInfo[0]);
			//主机名
			model.setZjm(hostInfo[2]);
			//获取日志记录接口
			ILogService sv = (ILogService) ServiceFactory.getService("logService");
			//记录日志
			sv.insert(model);
		} catch (Exception e) {
			LOG.error("记日志出错!", e);
		}
	}

	
	/**
	 * 日志记录（查询）
	 * @param user 			用户对象
	 * @param modelKey 		模块名称KEY
	 * @param businessKey 	业务名称KEY
	 * @param czms 			操作描述
	 */
	public void select(User user,String modelKey, String businessKey,String czms) {
		log(user,modelKey, businessKey,  Operation.OP_SELECT, czms);
	}
	
	/**
	 * 日志记录（查询）
	 * @param businessKey	 	业务名称
	 * @param modelKey	 		模块名称
	 * @param czms 				操作描述
	 */
	public void select(String modelKey, String businessKey, String czms) {
		User user = WebContext.getUser();
		select(user, modelKey, businessKey,czms);
	}
	
	
	/**
	 * 日志记录（删除）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param modelKey 模块名称
	 * @param czms 操作描述
	 */
	public void delete(User user, String modelKey, String businessKey, String czms) {
		log(user, modelKey, businessKey, Operation.OP_DELETE, czms);
	}
	
	
	/**
	 * 日志记录（删除）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(String modelKey, String businessKey, String czms) {
		User user = WebContext.getUser();
		delete(user, modelKey, businessKey, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param user 			用户对象
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void update(User user, String modelKey, String businessKey, String czms) {
		log(user, modelKey, businessKey, Operation.OP_UPDATE, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void update(String modelKey, String businessKey, String czms) {
		User user = WebContext.getUser();
		update(user, modelKey, businessKey, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param user 			用户对象
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void insert(User user, String modelKey, String businessKey, String czms) {
		log(user, modelKey, businessKey, Operation.OP_INSERT, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void insert(String modelKey, String businessKey, String czms) {
		User user = WebContext.getUser();
		insert(user, modelKey, businessKey, czms);
	}
	
	
	/**
	 * 日志记录（登陆）
	 * @param user 			用户对象
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void login(User user, String modelKey, String businessKey, String czms) {
		log(user, modelKey, businessKey, Operation.OP_LOGIN, czms);
	}

	
	/**
	 * 日志记录（注销）
	 * @param user 			用户对象
	 * @param modelKey 		模块名称
	 * @param businessKey 	业务名称
	 * @param czms 			操作描述
	 */
	public void logout(User user, String modelKey, String businessKey, String czms) {
		log(user, modelKey, businessKey, Operation.OP_LOGOUT, czms);
	}
	
}
