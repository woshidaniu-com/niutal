/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.svcinterface;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigVoModel;

import java.util.List;

/**
 * @description	： 导出授权服务
 * @author 		：康康（1571）
 */
public interface IExportAuthService {

	/**
	 * @description	：获得分页的ExportConfigVoModel
	 * @return
	 */
	List<ExportConfigVoModel> getPagedExportConfigVoModelList(ExportConfigModel model);
	
	/**
	 * @description	： 插入配置Model
	 * @param exportConfigModel
	 * @return
	 */
	int insertExportConfigModel(ExportConfigModel exportConfigModel);
	
	/**
	 * @description	： 删除配置Model
	 * @param exportConfigModel
	 * @return
	 */
	int deleteExportConfigModel(String dcclbh, String zd);

	/**
	 * @description	： 更新配置Model
	 * @param exportConfigModel
	 * @return
	 */
	int updateExportConfigModel(ExportConfigModel exportConfigModel);

	/**
	 * 获得ExportConfigVoModle
	 * @param dcclbh
	 * @param zd
	 * @return
	 */
	ExportConfigVoModel getExportConfigVoModel(String dcclbh, String zd);

	/**
	 * 更新导出权限
	 * @param dcclbh
	 * @param zd
	 * @param jsdm
	 * @return
	 */
	int updateExportAuth(String dcclbh, String zd, String[] sqzs);
}
