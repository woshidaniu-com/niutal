/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.log;

import java.util.List;

/**
 * 日志服务类
 * @author Penghui.Qu[445]
 */
public interface BusinessLogService {

	/**
	 * 添加日志
	 */
	public void log(BusinessLogModel log);
	
	/**
	 * 添加日志
	 */
	public void log(List<BusinessLogModel> logs);
	
	/**
	 * 添加体质
	 * @param log
	 * @param immidiately 是否直接处理
	 */
	public void log(BusinessLogModel log, boolean immidiately);
	
	/**
	 * 批量操作
	 * @param operateLogModels
	 */
	public void batchLog(List<BusinessLogModel> operateLogModels);
	
	/**
	 * 记录什么人，对什么业务 ,作了什么操作
	 * @param user 操作人
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czlx 操作类型
	 * @param czms 操作描述
	 */
	public void log(User user, String ywmc, String mkmc, String czlx, String czms) ;
	
	/**
	 * 日志记录（查询）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(User user, String ywmc, String mkmc, String czms);

	/**
	 * 日志记录（查询）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(String ywmc, String mkmc, String czms) ;
	
	/**
	 * 日志记录（删除）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(User user, String ywmc, String mkmc, String czms);
	
	/**
	 * 日志记录（删除）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(String ywmc, String mkmc, String czms) ;
	
	/**
	 * 日志记录（修改）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(User user, String ywmc, String mkmc, String czms) ;
	
	/**
	 * 日志记录（修改）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(String ywmc, String mkmc, String czms);
	
	/**
	 * 日志记录（增加）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(User user, String ywmc, String mkmc, String czms) ;
	
	/**
	 * 日志记录（增加）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(String ywmc, String mkmc, String czms) ;
	
	/**
	 * 日志记录（登陆）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void login(User user, String ywmc, String mkmc, String czms);

	/**
	 * 日志记录（注销）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void logout(User user, String ywmc, String mkmc, String czms);
}
