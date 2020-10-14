package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * 标准数据查询（含国标、校标、业务数据）
 * @author Penghui.Qu
 *
 */
public interface ISjbzService extends BaseService<SjbzModel> {

	/**
	 * 标准数据
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getBzsjList(String tableName) throws Exception;
	
	/**
	 * 标准数据  下拉选择需要大写的字段名，DM,MC
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getBzsjListForAutoCom(String tableName) throws Exception;
	
	/**
	 * 业务数据
	 * @param lx
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getYwsjList(String lx) throws Exception;
	
	/**
	 * 业务数据
	 * @param lx 类型
	 * @param zt 状态
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getYwsjList(String lx, String zt) throws Exception;
	
	/**
	 * 根据国标代码查询名称
	 * @return
	 */
	public SjbzModel getMcByDm(String tableName,String dm);
	
	/**
	 * 查询业务数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getJcsjList(String lx);
	/**
	 * 获取基础代码列表根据类型
	 * @param lx基础数据类型代码，配置文件中的key
	 * @return
	 */
	public List<Map<String,Object>> getTjListByLx(String lx);
}
