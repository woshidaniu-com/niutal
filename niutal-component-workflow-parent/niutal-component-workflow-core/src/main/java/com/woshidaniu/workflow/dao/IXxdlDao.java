package com.woshidaniu.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.annotation.BaseAnDao;
import com.woshidaniu.workflow.model.XxdlModel;

/**
 * 
 * 类描述：消息队列DAO类
 *
 * @version: 1.0
 * @author: jyy
 * @since: 2017-8-23 10:42:47
 */
public interface IXxdlDao extends BaseAnDao<XxdlModel,XxdlModel>{
	
	
	List<XxdlModel> selectXxdlxxByxxzH(XxdlModel xxdlModel) throws DataAccessException;
	
	void deleteXxdl(XxdlModel xxdlModel) throws DataAccessException;
	
	void deleteSqrXxdl(XxdlModel xxdlModel) throws DataAccessException;
	
	void deleteXxdlBySqr(XxdlModel xxdlModel) throws DataAccessException;
	
	void updateXxdlclzt(XxdlModel xxdlModel) throws DataAccessException;
}
