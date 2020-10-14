package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.daointerface.ILogDao;
import com.woshidaniu.entities.RzglModel;
import com.woshidaniu.orm.mybatis.spring.support.MybatisDataSourceDao;
import com.woshidaniu.orm.mybatis.utils.MyBatisSQLUtils;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.ILogService;
/**
 * 
 *@类名称:LogServiceImpl.java
 *@类描述：操作日志记录service实现
 *@创建人：kangzhidong
 *@创建时间：2014-10-13 上午09:30:17
 *@版本号:v1.0
 */
@Service("logService")
public class LogServiceImpl extends CommonBaseServiceImpl<RzglModel,ILogDao>  implements ILogService {
	
	@Resource
	private ILogDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	//成绩数据库操作dao接口
	@Resource(name="securityDataSourceDao")
	private MybatisDataSourceDao dataSourceDao;

	public void insertSQL(RzglModel model){
		//获得statementID
		String statementID = MyBatisSQLUtils.getStatementID(ILogDao.class, "insert");
		//保存SQL
		dataSourceDao.insert(statementID, model);
	}
	
	public RzglModel getSQLModel(RzglModel model){
		//获得statementID
		String statementID = MyBatisSQLUtils.getStatementID(ILogDao.class, "getModel");
		//保存SQL
		return dataSourceDao.selectOne(statementID, model);
	}

	public MybatisDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(MybatisDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	
}
