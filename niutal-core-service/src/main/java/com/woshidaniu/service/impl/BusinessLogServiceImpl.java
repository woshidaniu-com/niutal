package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.log.BusinessLogCache;
import com.woshidaniu.common.log.BusinessLogModel;
import com.woshidaniu.common.log.BusinessLogService;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.ILogDao;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.utils.WebRequestUtils;

@Service
public class BusinessLogServiceImpl implements BusinessLogService {

	@Autowired
	private ILogDao dao;

	private static final Logger log = LoggerFactory.getLogger(BusinessLogServiceImpl.class);
	/**
	 * 操作缓存
	 */
	private BusinessLogCache<BusinessLogModel> logCache = new BusinessLogCache<BusinessLogModel>(
			MessageUtil.getInt("log.cache.size")) {
		@Override
		protected boolean persistent() throws Exception {
			try {
				List<BusinessLogModel> opeartionCache = getOpeartionCache();
				if (opeartionCache.size() == 0)
					return true;
				List<BusinessLogModel> tem = new ArrayList<BusinessLogModel>(opeartionCache);
				clear();
				dao.batchInsert(tem);
				return true;
			} catch (Exception e) {
				log.error("---------操作日志批持久化发生错误！-----------", e);
			}
			return false;
		}
	};
	
	/**
	 * 添加日志
	 */
	public void log(BusinessLogModel log) {
		if (log != null) {
			logCache.add(log);
		}
	}

	/**
	 * 添加日志
	 */
	public void log(List<BusinessLogModel> logs) {
		if (logs != null && logs.size() > 0) {
			logCache.addAll(logs);
		}
	}

	/**
	 * 添加体质
	 * 
	 * @param log
	 * @param immidiately
	 *            是否直接处理
	 */
	public void log(BusinessLogModel log, boolean immidiately) {
		// 如果是立即处理，马上持久化
		log(log);
		if (immidiately) {
			logCache.triggerManual();
		}
	}

	/**
	 * 批量操作
	 * 
	 * @param operateLogModels
	 */
	public void batchLog(List<BusinessLogModel> operateLogModels) {
		if (operateLogModels != null && operateLogModels.size() > 0) {
			log(operateLogModels);
		}
	}

	/**
	 * 记录什么人，对什么业务 ,作了什么操作
	 * 
	 * @param user
	 *            操作人
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czlx
	 *            操作类型
	 * @param czms
	 *            操作描述
	 */
	public void log(User user, String ywmc, String mkmc, String czlx, String czms) {
		try {
			BusinessLogModel model = new BusinessLogModel();
			model.setCzlx(czlx);
			model.setCzmk(mkmc);
			model.setCzms(czms);
			model.setCzr(String.format("%s【职工号：%s】", user.getXm(), user.getYhm()));
			model.setYwmc(ywmc);
			HttpServletRequest request = (HttpServletRequest) WebContext.getRequest();
			model.setIpdz(WebRequestUtils.getRemoteAddr(request));
			model.setZjm(request.getRemoteHost());
			log(model);
		} catch (Exception e) {
			log.error("记日志出错!", e);
		}
	}

	/**
	 * 日志记录（查询）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void select(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.SELECT.getKey(), czms);
	}

	/**
	 * 日志记录（查询）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void select(String ywmc, String mkmc, String czms) {
		User user = WebContext.getUser();
		select(user, ywmc, mkmc, czms);
	}

	/**
	 * 日志记录（删除）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void delete(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.DELETE.getKey(), czms);
	}

	/**
	 * 日志记录（删除）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void delete(String ywmc, String mkmc, String czms) {
		User user = WebContext.getUser();
		delete(user, ywmc, mkmc, czms);
	}

	/**
	 * 日志记录（修改）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void update(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.UPDATE.getKey(), czms);
	}

	/**
	 * 日志记录（修改）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void update(String ywmc, String mkmc, String czms) {
		User user = WebContext.getUser();
		update(user, ywmc, mkmc, czms);
	}

	/**
	 * 日志记录（增加）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void insert(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.INSERT.getKey(), czms);
	}

	/**
	 * 日志记录（增加）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void insert(String ywmc, String mkmc, String czms) {
		User user = WebContext.getUser();
		insert(user, ywmc, mkmc, czms);
	}

	/**
	 * 日志记录（登陆）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void login(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.LOGIN.getKey(), czms);
	}

	/**
	 * 日志记录（注销）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void logout(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, BusinessType.LOGOUT.getKey(), czms);
	}

}
