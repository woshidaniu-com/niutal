/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.daointerface;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigVoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @description	： 导出字段选择Dao
 * @author 		：康康（1571）
 */
@Repository("exportAuthDao")
public interface IExportAuthDao {

	/**
	 * @description	：获得分页的ExportConfigVoModel
	 * @param ExportConfigModel
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
	int deleteExportConfigModel(@Param("dcclbh") String dcclbh, @Param("zd") String zd);

	/**
	 * @description	： 更新配置Model
	 * @param exportConfigModel
	 * @return
	 */
	int updateExportConfigModel(ExportConfigModel exportConfigModel);

	/**
	 * 查询可展示字段
	 * @param dcclbh 导出编号
	 * @param sqz 权授权
	 * @return
	 */
	Set<String> getExportAuthZdList(@Param("dcclbh") String dcclbh, @Param("sqz") String sqz);

	/**
	 * 查询ExportConfigVoModel
	 * @param dcclbh
	 * @param zd
	 * @return
	 */
	ExportConfigVoModel getExportConfigVoModel(@Param("dcclbh") String dcclbh, @Param("zd") String zd);

	/**
	 * 查询授权值
	 * @param dcclbh 导出编号
	 * @param zd 字段
	 * @return
	 */
	Set<String> getExportAuthSqzList(@Param("dcclbh") String dcclbh, @Param("zd") String zd);

	/**
	 * 删除导出权限配置
	 * @param exportModel
	 * @return
	 */
	int deleteExportAuth(@Param("dcclbh") String dcclbh, @Param("zd") String zd);

	/**
	 * 插入导出权限配置
	 * @param exportModel
	 * @return
	 */
	int insertExportAuth(@Param("dcclbh") String dcclbh, @Param("zd") String zd, @Param("sqz") String sqz);

}
