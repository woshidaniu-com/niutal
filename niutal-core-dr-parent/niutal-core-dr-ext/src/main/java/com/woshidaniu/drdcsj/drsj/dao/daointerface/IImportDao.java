/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.ColumnDef;
import com.woshidaniu.drdcsj.drsj.dao.entities.CrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrFzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.LyzgzdzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.PluginModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.YzgzModel;

@Repository(value="drdcsjDao")
public interface IImportDao extends BaseDao<DrpzModel> {
	/**
	 * 获取导入配置列表
	 * @param drmkdm 导入模块代码
	 * @return
	 */
	List<DrpzModel> getDrPzxxList(String drmkdm);
	/**
	 * 获取导入配置 
	 * @param drmkdm 导入模块代码
	 * @return
	 */
	List<DrpzModel> getDrPzxx(String drmkdm);
	/**
	 * 获取配置信息
	 * @param drmkdm 导入模块代码
	 * @return List<DrlpzModel>
	 */
	List<DrlpzModel> getLPzxx(String drmkdm);
	/**
	 * 获取导入配置列Model
	 * @param drlpzid 导入列配置id
	 * @return
	 */
	DrlpzModel getDrlpzModel(String drlpzid);
	/**
	 * 获取导入列配置列表
	 * @param ids 列配置id
	 * @return
	 */
	List<DrlpzModel> getDrlpzModelList(List<String> ids);
	/**
	 * 获取验证规则
	 * @param yzgzid 列验证规则id
	 * @return YzgzModel
	 */
	YzgzModel getLPzRule(String yzgzid);
	/**
	 * 获取列验证对照信息
	 * @param drlpzModel 列信息
	 * @return
	 */
	List<LyzgzdzModel> getLyzgzdz(DrlpzModel drlpzModel);
	/**
	 * 执行插入
	 * @return 成功插入条数
	 */
	int excuteInsert(List<String> sqls);
	/**
	 * 获取插入sql
	 * @return 配置的插入sql
	 */
	List<String> getInsertSql(String drmkdm);
	/**
	 * 获取选择导入列model集合
	 * @param list 选择导入列
	 * @return
	 */
	List<DrlpzModel> getSelectColumnModel(List<String> list);
	/**
	 * 获取插入方式
	 * @param drmkdm 导入模块代码
	 * @return
	 */
	String getCrfs(String drmkdm);
	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	int batchInsertData(HashMap<String, Object> hashMap);
	/**
	 * 获取插入配置
	 * @param drmkdm
	 * @return
	 */
	CrpzModel getCrpz(String drmkdm);
	/**
	 * 获取联合组建配置
	 * @param rulerId
	 * @param strings 
	 * @return
	 */
	List<DrlpzModel> getCompositeIds(Map<String , Object> params);
	/**
	 * 获取主键集合
	 * @param primary 主键列
	 * @param tableName 表名称
	 * @return
	 */
	List<String> getPrimary(Map<String, String> param);
	
	List<String> getValues(Map<String, String> param);
	/**
	 * 获取格式化参照数据
	 * @param sql
	 * @return
	 */
	List<Map<String, String>> getFomartData(String sql);
	
	CrpzModel getCrpzModel(String drmkdm);
	/**
	 * 获取导入辅助配置信息列表
	 * @param drmkdm
	 * @return
	 */
	List<DrFzModel> getDrFzModelList(String drmkdm);
	/**
	 * 获取业务扩展验证配置信息
	 * @param drmkdm
	 * @return
	 */
	List<PluginModel> getPluginModelList(String drmkdm);
	/**
	 * 根据表名获取字段定义信息
	 * @param tableName
	 * @return
	 */
	List<ColumnDef> getColumnDefList(String tableName);
}