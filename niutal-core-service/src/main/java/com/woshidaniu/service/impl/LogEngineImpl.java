package com.woshidaniu.service.impl;

import java.util.List;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.BusinessLogModel;
import com.woshidaniu.common.log.BusinessLogService;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;


/**
* 
* @类名称：OperateLogEngineImpl
* @类描述：日志记录入口
* @创建人：qph
* @创建时间：2012-4-20 
* @修改备注： modify by caozf
* 
* @修改人：xiaokang
* @修改内容：加入缓存列表，异步批量处理操作日志持久化
* @修改时间：2015-10-27
*/
public class LogEngineImpl extends BaseLog{
	
	private LogEngineImpl() {
	}
	
	
	private static class LogFactory{
		private static LogEngineImpl instance = new LogEngineImpl(); 
	}
	
	public static LogEngineImpl getInstance() {
		return LogFactory.instance;
	}
	
	/**
	 * 添加日志
	 */
	public void log(BusinessLogModel log){
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.log(log);
	}
	/**
	 * 添加日志
	 */
	public void log(List<BusinessLogModel> logs){
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.log(logs);
	}
	/**
	 * 添加日志
	 * @param log
	 * @param immidiately 是否直接处理
	 */
	public void log(BusinessLogModel log, boolean immidiately){
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.log(log, immidiately);
	}
	
	/**
	 * 批量操作
	 * 
	 * @param operateLogModels
	 */
	public void batchLog(List<BusinessLogModel> operateLogModels){
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.batchLog(operateLogModels);
	}
	
	/**
	 * 记录什么人，对什么业务 ,作了什么操作
	 * @param user 操作人
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czlx 操作类型
	 * @param czms 操作描述
	 */
	public void log(User user, String ywmc, String mkmc, String czlx,
			String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.log(user, ywmc, mkmc, czlx, czms);
	}

	
	/**
	 * 日志记录（查询）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.select(user, ywmc, mkmc, czms);
	}

	
	/**
	 * 日志记录（查询）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.select(ywmc, mkmc, czms);
	}
	
	
	
	/**
	 * 日志记录（删除）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.delete(user, ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（删除）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.delete(ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.update(user, ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.update(ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.insert(user, ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.insert(ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（登陆）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void login(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.login(user, ywmc, mkmc, czms);
	}

	
	/**
	 * 日志记录（注销）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void logout(User user, String ywmc, String mkmc, String czms) {
		BusinessLogService businessLog = ServiceFactory.getService(BusinessLogService.class);
		businessLog.logout(user, ywmc, mkmc, czms);
	}

	
}
