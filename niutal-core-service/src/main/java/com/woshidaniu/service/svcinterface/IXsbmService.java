package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.dao.entities.XsbmModel;

/**
 * 学院、专业、班级公用加载实现
 * @author Penghui.Qu
 *
 */
public interface IXsbmService extends BaseService<XsbmModel> {
	
	/**
	 * 加载全部学院
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getXyList() throws Exception;
	
	
	/**
	 * 加载专业
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getZyList(XsbmModel t) throws Exception;
	
	
	
	/**
	 * 加载层次列表
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getCcList() throws Exception;
	
	
	
	/**
	 * 加载班级列表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getBjList(XsbmModel t) throws Exception;
	
	
	
	/**
	 * 加载年级 列表
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getNjList() throws Exception;
	
	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	public List<XsbmModel> getBmListByXlcc(XsbmModel t)throws Exception;
	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> likeQueryBmListByXlcc(Map<String,Object>params)throws Exception;
	/**
	 * 按学院加载专业 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> likeQueryZyList(Map<String,Object> params) throws Exception;
	/**
	 * 模糊查询加载班级列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> likeQueryBjList(Map<String,Object> params) throws Exception;
}
