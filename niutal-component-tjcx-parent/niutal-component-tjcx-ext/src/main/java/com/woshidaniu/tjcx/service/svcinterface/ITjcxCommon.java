package com.woshidaniu.tjcx.service.svcinterface;

import java.util.List;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 通用方法接口
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface ITjcxCommon {

	/**
	 * 
	 * @描述:取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:45:11
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getSjkqz(String qzfw);

	/**
	 * 
	 * @描述:取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:44:56
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getFfqz(String qzfw);

	/**
	 * 
	 * @描述:取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:45:11
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getSjkqz(CxzdModel cxzdModel);

	/**
	 * 
	 * @描述:取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:44:56
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	public String getFfqz(CxzdModel cxzdModel);

	/**
	 * 
	 * @描述:设置数据源数据范围
	 * @作者：ligl
	 * @日期：2013-9-18 下午03:26:31
	 * @修改记录:
	 * @param ysjModel
	 *            void 返回类型
	 * @throws
	 */
	public void setSjfw(YsjModel ysjModel);

	/**
	 * 
	 * @描述:得到配置的字段
	 * @作者：ligl
	 * @日期：2014-05-21 上午09:14:37
	 * @修改记录:
	 * @param kzszModel
	 * @return
	 * @throws Exception
	 *             List<ExportConfigModel> 返回类型
	 * @throws
	 */
	public List<ExportConfigModel> getExportConfigList(KzszModel kzszModel)
			throws Exception;

	/**
	 * 获取已配置的导出字段
	 * 
	 * @param exportConfigList
	 * @return
	 * @throws Exception
	 */
	public String getXszd(List<ExportConfigModel> exportConfigList)
			throws Exception;

	/**
	 * 
	 * @描述:返回记录总数
	 * @作者：ligl
	 * @日期：2013-8-27 下午01:28:27
	 * @修改记录:
	 * @param kzszModel
	 * @return int 返回类型
	 * @throws
	 */
	public int getTotalResult(KzszModel kzszModel) throws Exception;

	/**
	 * 
	 * @描述:设置过滤条件
	 * @作者：ligl
	 * @日期：2014-8-12 上午09:17:02
	 * @修改记录: 
	 * @param sql
	 * @param gltj
	 * @return
	 * @throws Exception
	 * String 返回类型 
	 * @throws
	 */
	public String setGltj(String sql, String gltj) throws Exception;
}
