/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.daointerface;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Penghui.Qu
 * 公用导出
 */
@Repository("exportDao")
public interface IExportDao {

	/**
	 * 查询导出公共配置
	 * @param dcclbh
	 * @return
	 */
	public List<ExportConfigModel> getExportPublicConfig(String dcclbh);
	
	/**
	 * 查询导出配置
	 * @param model
	 * @return
	 */
	public List<ExportConfigModel> getExportConfig(ExportModel model);
	
	/**
	 * 查询导出偏好设置列表
	 * @param dcclbh
	 * @param zgh
	 * @return
	 */
	public List<ExportConfigPhModel> getExportConfigPh(ExportModel model);
	
	/**
	 * 查询偏好配置的字段列表
	 * @param args
	 * @return
	 */
	public List<ExportConfigModel> getExportConfigPhList(Map<String, String> args);

	/**
	 * 插入导出配置
	 * @param model
	 * @return
	 */
	public int insertConfig(List<HashMap<String, String>> list);
	
	/**
	 * 插入导出偏好设置
	 * @param list
	 * @return
	 */
	public int insertConfigPh(List<ExportConfigPhModel> list);
	
	
	/**
	 * 删除用户导出配置
	 * @param model
	 * @return
	 */
	public int deleteConfig(ExportModel model);
	
	/**
	 * 根据ID删除导出偏好设置
	 * @param id
	 * @return
	 */
	public int deleteConfigPh(String id);
}