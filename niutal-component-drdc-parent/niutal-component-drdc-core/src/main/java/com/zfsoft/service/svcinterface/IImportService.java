package com.woshidaniu.service.svcinterface;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.progress.ProgressBar;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.ImportModel;

/**
 * 导入数据
 * @author Jiangdong.Yi
 *
 */
public interface IImportService extends BaseService<ImportModel>{
	//导入临时文件目录
	public static final String IMPORT_TEMP_DIRPATH="\\temp\\importTemp\\";
	//导入模板文件前缀
	public static final String IMPORT_TEMPLATE="template";
	//导入错误文件前缀
	public static final String IMPORT_ERROR="error";
	
	//导入验证参数默认值 "无"
	public static final String IMPORT_YZCS_DEFAULT="无";
	
	//导入文件默认后缀
	public static final String IMPORT_SUFFLX="xls";
	
	//导入数据唯一性验证
	public final String RULE_IMPORTUNIQUERULE="ImportUniqueRule";
	
	//用于导入验证项和并标记
	public final String UNITESIGN="##UNITE##";
	
	//是否合并验证: 是
	public final String SFHBYZ_1="1";
	//是否合并验证: 否
	public final String SFHBYZ_0="0";
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
	public List<ImportModel> getValidateColumnList(String drmkdm,String drbm);
	
	/**
	 * 获取导入字段列表 根据导入模块代码和导入表名
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	public List<ImportModel> getImportColumnList(String drmkdm, String drbm);
	
	/**
	 * 获取导入模板   简单的导入模板
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	public File getImportTemplate(ImportModel importModel) throws Exception;
	
	/**
	 * 获取导入错误数据Excel
	 * @param drmkdm 导入模块代码
	 * @param drbm 导入表明
	 * @param errorList 错误列表
	 * @param user 用户信息
	 * @return
	 * @throws Exception
	 */
	public File getImportErrortData(String drmkdm,String drbm,List<String[]> errorList
			,User user) throws Exception;
	
	/**
	 * 导入数据
	 * @param model
	 * @return
	 */
	public Map<String, Object> importData(ImportModel model) throws Exception;
	/**
	 * 导入数据（带进度条）
	 * @param model
	 * @param pb
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> importData(ImportModel model,ProgressBar pb) throws Exception;
	/**
	 * 获取验证数据列表
	 * @param parame {tableName:"",columnName:"XXX,XXX"}
	 * @return List
	 */
	public List<HashMap<String, String>> getValidateValueList(HashMap<String, String> parame);
	
	/**
	 * 获取验证数据列表
	 * @param parame {tableName:"",columnName:"XXX,XXX"}
	 * @return HashMap
	 */
	public HashMap<String, String> getValidateValueMap(HashMap<String, String> parame);
}
