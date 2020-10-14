package com.woshidaniu.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.log.BusinessLogModel;
import com.woshidaniu.common.log.BusinessLogService;
import com.woshidaniu.common.log.User;

/**
 * for stress test
 * @author 1571
 */
public class NoopBusinessLogServiceImpl implements BusinessLogService{
	
	private static final Logger log = LoggerFactory.getLogger(NoopBusinessLogServiceImpl.class);

	@Override
	public void log(BusinessLogModel log) {
		
	}

	@Override
	public void log(List<BusinessLogModel> logs) {
		
	}

	@Override
	public void log(BusinessLogModel log, boolean immidiately) {
		
	}

	@Override
	public void batchLog(List<BusinessLogModel> operateLogModels) {
		
	}

	@Override
	public void log(User user, String ywmc, String mkmc, String czlx, String czms) {
		
	}

	@Override
	public void select(User user, String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void select(String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void delete(User user, String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void delete(String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void update(User user, String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void update(String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void insert(User user, String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void insert(String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void login(User user, String ywmc, String mkmc, String czms) {
		
	}

	@Override
	public void logout(User user, String ywmc, String mkmc, String czms) {
		
	}

}
