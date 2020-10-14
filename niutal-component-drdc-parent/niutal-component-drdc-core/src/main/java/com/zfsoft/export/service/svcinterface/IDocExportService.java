package com.woshidaniu.export.service.svcinterface;

import java.io.File;
import java.util.List;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;



/**
 * 公用导出接口
 * @author Penghui.Qu
 *
 */
public interface IDocExportService {

	static final String SHEET_NAME = "sheet1";
	static final String PUBLIC_CONFIG = "public";
	static final String SELECT_ZT = "1";//导出选中状态
	static final String UNSELECT_ZT = "0";//导出未选中状态
	
	/**
	 * 获取导出文件
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	public File getExportFile(ExportModel model) throws Exception;
	
	
	/**
	 * 查询导出配置
	 * @param model
	 * @return
	 */
	public List<ExportConfigModel> getConfigList(ExportModel model);
	
	
	
	/**
	 * 保存导出配置
	 * @param model
	 * @return boolean
	 */
	public boolean saveExportConfig(ExportModel model);

	
	/**
	 *@描述：初始化页面上的配置
	 *@创建人:"huangrz"
	 *@创建时间:2014-12-12上午08:45:14
	 *@param model
	 */
	public void exportInitDcpz(ExportModel model);	
}
