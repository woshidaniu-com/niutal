package com.woshidaniu.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.ImportModel;

/**
 * 导入dao
 * 
 * @author Jiangdong.Yi
 * 
 */
public interface IDocImportDao extends BaseDao<ImportModel> {
	
	/**
	 * 获取导入表 列表根据导入模块代码
	 * @param drmkdm
	 * @return
	 */
	public List<ImportModel> getImportTableListByDrmkdm(String drmkdm);
	
	/**
	 * 获取验证字段列表 根据导入模块代码和导入表名
	 * @param model
	 * @return
	 */
	public List<ImportModel> getValidateColumnListByDrmkdmAndDrbm(ImportModel model);
	
	/**
	 * 查询获取列验证列表
	 * @param model
	 * @return
	 */
	public List<ImportModel> getColumnValidateListByDrmkdmAndDrbm(ImportModel model);
	
	/**
	 * 获取列列表合并验证规则
	 * @param model
	 * @return
	 
	public List<ImportModel> getColumnListByDrmkdmAndDrbm(ImportModel model);
	*/
	
	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	public int batchInsertData(HashMap<String, Object> hashMap);
	
	/**
	 * 获取验证数据列表
	 * @param parame
	 * @return
	 */
	public List<HashMap<String, String>> getValidateValueList(HashMap<String, String> parame);
	
	
	/**
	 * 获取验证数据Map
	 * @param parame
	 * @return
	 */
	public List<HashMap<String, String>> getValidateValueMap(HashMap<String, String> parame); 
	
	/**
	 * 获取导入字段列表 根据导入模块代码和导入表名
	 * @param model
	 * @return
	 */
	public List<ImportModel> getImportColumnListByDrmkdmAndDrbm(ImportModel model);

	/**
	 * 获取表字段及字段长度等属性
	 * 
	 * @param parame
	 * @return
	 */
	public List<HashMap<String, String>> getTableColumns(String tableName);
	/**
	 * 获取配置信息
	 * @param model
	 * @return
	 */
	public List<Map<String,String>> getPzxx(ImportModel model);
	/**
	 * 获取配置信息返回的实际数据
	 * @param hashMap
	 * @return
	 */
	public List<Map<String,String>> getPzxxValue(HashMap<String, String> hashMap);

}

