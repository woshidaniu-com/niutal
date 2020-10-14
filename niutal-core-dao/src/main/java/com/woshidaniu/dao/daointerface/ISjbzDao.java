package com.woshidaniu.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * 数据标准
 * @author Penghui.Qu
 *
 */
@Repository("sjbzDao")
public interface ISjbzDao extends BaseDao<SjbzModel> {

	/**
	 * 查询标准数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getBzsjList(SjbzModel model) ;
	
	/**
	 * 查询标准数据  下拉选择需要大写的字段名，DM,MC
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getBzsjListForAutoCom(SjbzModel model) throws Exception;
	
	
	/**
	 * 查询业务数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getYwsjList(String lx) throws Exception;
	
	/**
	 * 查询业务数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getYwsjListByLxAndZt(SjbzModel model) throws Exception;
	
	/**
	 * 
	 * @param sjbzModel
	 * @return
	 */
	public SjbzModel getMcByDm(SjbzModel sjbzModel);
	
	/**
	 * 查询业务数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getJcsjList(String lx);
}
