package com.woshidaniu.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;

/**
 * 公用导出
 * @author Penghui.Qu
 *
 */
public interface IDocExportDao {

	/**
	 * 查询导出配置
	 * @param model
	 * @return
	 */
	public List<ExportConfigModel> getExportConfig(ExportModel model);
	
	
	
	/**
	 * 插入导出配置
	 * @param model
	 * @return
	 */
	public int insertConfig(List<HashMap<String,String>> list);
	
	
	
	/**
	 * 删除用户导出配置
	 * @param model
	 * @return
	 */
	public int deleteConfig(ExportModel model);


	/**
	 *@描述：初始化页面上的配置
	 *@创建人:"huangrz"
	 *@创建时间:2014-12-12上午09:07:35
	 *@param model
	 */
	public void updateCshpz(ExportModel model);
}
