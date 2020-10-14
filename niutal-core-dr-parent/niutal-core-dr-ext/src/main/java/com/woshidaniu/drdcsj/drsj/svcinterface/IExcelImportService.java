/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.svcinterface;

import java.io.File;
import java.util.List;

import com.woshidaniu.drdcsj.drsj.dao.entities.CrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;

/**
 * @author 982 张昌路
 */
public interface IExcelImportService extends IImportService {
	/**
	 * 获取导入模板文件
	 * @param drpzModel
	 * @return
	 */
	File getTemplateFile(String drmkdm);
	/**
	 * 获取导入列配置
	 * @param drpzModel
	 * @return
	 */
	List<DrlpzModel> getImportColumn(DrpzModel drpzModel);
	/**
	 * 获取导入列配置信息
	 * @param drpzModel
	 * @return
	 */
	List<DrlpzModel> getRulers(DrpzModel drpzModel);
	/**
	 * 获取设置导入列的验证信息
	 * @param drlidList 导入列id列表
	 * @return
	 */
	List<DrlpzModel> getRulers(List<String> drlidList, DrpzModel drpzModel);

	/**
	 * 获取导入信息
	 * @param drmkdm 导入模块代码
	 * @return
	 */
	List<DrpzModel> getDrPzxx(String drmkdm);

	CrpzModel getCrpz(String drmkdm);
}
