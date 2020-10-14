/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.svcinterface;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;

import java.io.File;
import java.util.List;

/**
 * @author Penghui.Qu
 * 公用导出接口
 */
public interface IExportService {

	public static final String SHEET_NAME = "sheet1";
	public static final String PUBLIC_CONFIG = "public";
	/**
	 *导出选中状态 
	 */
	public static final String SELECT_ZT = "1";
	/**
	 *导出未选中状态 
	 */
	public static final String UNSELECT_ZT = "0";
	/**
	 * 获取导出文件
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public File getExportFile(ExportModel model)  throws Exception;
	
	/**
	 * 查询导出公共配置
	 * @param dcclbh
	 * @return
	 */
	public List<ExportConfigModel> getExportPublicConfig(ExportModel model);
	
	/**
	 * 查询导出配置
	 * @param model
	 * @return
	 */
	public List<ExportConfigModel> getConfigList(ExportModel model);
	
	/**
	 * 查询导出偏好设置
	 * @param model
	 * @return
	 */
	public List<ExportConfigPhModel> getConfigPhList(ExportModel model);
	
	/**
	 * 查询导出编号设置字段列表
	 * @param dcclbh
	 * @param zgh
	 * @return
	 */
	public List<ExportConfigModel> getConfigPhZdList(String dcclbh, String zgh);
	
	/**
	 * 保存导出配置
	 * @param model
	 * @return boolean
	 */
	public boolean saveExportConfig(ExportModel model);
	
	/**
	 * 保存导出配置
	 * @param model
	 * @return boolean
	 */
	public ExportModel saveExportConfig2(ExportModel model);
	
	/**
	 * 删除导出配置
	 * @param model
	 * @return
	 */
	public boolean deleteExportConfig(ExportModel model);
}