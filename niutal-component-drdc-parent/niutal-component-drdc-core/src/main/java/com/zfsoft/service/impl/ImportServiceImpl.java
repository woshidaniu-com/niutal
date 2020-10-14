package com.woshidaniu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.excel.ExcelUtils;
import com.woshidaniu.common.excel.extend.IImportExtend;
import com.woshidaniu.common.excel.extend.imp.ImportExtendImp;
import com.woshidaniu.common.excel.template.ATemplateBuilder;
import com.woshidaniu.common.excel.template.ImportTemplateBuilder;
import com.woshidaniu.common.imp.ImportInitParameter;
import com.woshidaniu.common.imp.ImportJdbcTypeFormat;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.progress.ProgressBar;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.common.validator.ValidatorUtils;
import com.woshidaniu.common.validator.rules.NotEmptyRule;
import com.woshidaniu.dao.daointerface.IDocImportDao;
import com.woshidaniu.dao.entities.ImportModel;
import com.woshidaniu.export.service.utils.dbf.DBFReader;
import com.woshidaniu.export.service.utils.dbf.DBFWriter;
import com.woshidaniu.export.service.utils.dbf.JDBField;
import com.woshidaniu.service.svcinterface.IImportService;
import com.woshidaniu.util.base.StringUtil;

/**
 * 导入数据
 * 
 * @author Jiangdong.Yi
 * 
 */
public class ImportServiceImpl extends BaseServiceImpl<ImportModel, IDocImportDao>
		implements IImportService {
	protected static final transient Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
	protected ProgressBar pb=null;
	/**
	 * 验证数据源 <li>数据结构 hashMap String[]</li>
	 */
	protected Map<String, Object> vDataSource = null;
	/**
	 * 验证参数
	 */
	protected Map<String, Object> validatorParame = null;
	/**
	 * 合并验证列表
	 */
	protected List<ImportModel> uniteValidatorList = null;
	/**
	 * 合并验证值
	 */
	protected Map<String, Object> uniteValidatorValue = null;
	protected String drms = null;//导入模式，1为更新操作

	public Object getValidatorParame(String vRuleName) {
		if (StringUtil.isEmpty(vRuleName)) {
			return null;
		}
		return validatorParame.get(vRuleName);
	}

	public void setValidatorParame(String vRuleName, Object objParame) {
		this.validatorParame.put(vRuleName, objParame);
	}

	public Map<String, Object> getvDataSource() {
		return vDataSource;
	}

	public void setvDataSource(Map<String, Object> vDataSource) {
		this.vDataSource = vDataSource;
	}

	public Map<String, Object> getValidatorParame() {
		return validatorParame;
	}

	public void setValidatorParame(Map<String, Object> validatorParame) {
		this.validatorParame = validatorParame;
	}

	/**
	 * 创建验证
	 */
	protected void createValidator() {
		// 生成新的参数控制
		this.validatorParame = new HashMap<String, Object>();
		this.vDataSource = new HashMap<String, Object>();
		this.uniteValidatorList = new ArrayList<ImportModel>();
		this.uniteValidatorValue = new HashMap<String, Object>();
	}

	/**
	 * 清除
	 */
	private void cleanValidator() {
		// 注销新的参数控制
		//this.validatorParame = null;
		//this.vDataSource = null;
		//this.uniteValidatorList = null;
		//this.uniteValidatorValue = null;
	}

	/**
	 * 初始化验证数据源 <li>现只用于唯一验证</li>
	 */
	protected void initVDataSource(List<String[]> dataSource,
			List<ImportModel> importModelList) {
		if (dataSource == null || importModelList == null) {
			return;
		}
		if (this.vDataSource == null) {
			this.vDataSource = new HashMap<String, Object>();
		}
		String[] columnValue = null;
		// 将横向的数据源列表 转成 纵项列表保存
		for (int i = 0; i < importModelList.size(); i++) {
			ImportModel im=getUniteValidatorImportModel(importModelList.get(i));
			// 过滤不是唯一验证
			if (im == null) {
				continue;
			}

			// 如果是合并验证， 则参数相同
			if (this.vDataSource.get(im.getYzcs()) != null) {
				columnValue = (String[]) this.vDataSource.get(im.getYzcs());
			} else {
				columnValue = new String[dataSource.size()];
			}

			// 获取列数据
			for (int j = 0; j < dataSource.size(); j++) {
				if (columnValue[j] == null || "".equals(columnValue[j])) {
					columnValue[j] = dataSource.get(j)[i];
				} else {
					columnValue[j] = columnValue[j] + dataSource.get(j)[i];
				}
			}
			// 使用验证参数做key 只用于唯一验证
			this.vDataSource.put(im.getYzcs(), columnValue);
		}
	}
	
	/**
	 * 获取验证数据  
	 * @param importModelList
	 * @return
	 */
	protected ImportModel getUniteValidatorImportModel(ImportModel importModel){
		if(importModel == null){
			return null;
		}
		
		ImportModel im=null;
		//如果是合并验证
		im=getUniteItem(importModel);
		if(im != null){
			return im;
		}
		
		if(!StringUtil.isEmpty(importModel.getYzlmc()) &&  importModel.getYzlmc().indexOf(RULE_IMPORTUNIQUERULE) > 0){
			// TODO 此处可以优化，因为每一列的验证多是一样的，可以考虑内存中
			
			String[] yzlmcs = importModel.getYzlmc().split(",");
			String[] yzlbms = importModel.getYzlbm().split(",");
			String[] yzcss = importModel.getYzcs().split(",");
			String[] yzmcs = importModel.getYzmc().split(",");
			String[] yzsms = importModel.getYzsm().split(",");
			
			//拷贝当前验证规则
			im = new ImportModel();
			copyImportModelProperties(im, importModel);
			//替换参数
			for (int i = 0; i < yzlmcs.length; i++) {
				if(RULE_IMPORTUNIQUERULE.equals(yzlmcs[i])){
					im.setYzlmc(yzlmcs[i]);
					im.setYzlbm(yzlbms[i]);
					im.setYzcs(yzcss[i]);
					im.setYzmc(yzmcs[i]);
					im.setYzsm(yzsms[i]);
					break;
				}
			}
			return im;
		}
		
		return im;
	}

	/**
	 * 获取导入表 列表根据导入模块代码
	 * 
	 * @param drmkdm
	 * @return
	 */
	public List<ImportModel> getImportTableListByDrmkdm(String drmkdm) {
		if (StringUtils.isEmpty(drmkdm)) {
			return null;
		}
		return dao.getImportTableListByDrmkdm(drmkdm);
	}

	/**
	 * 获取验证字段列表 根据导入模块代码和导入表名
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	public List<ImportModel> getValidateColumnList(String drmkdm, String drbm) {
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		ImportModel importModel = new ImportModel();
		importModel.setDrmkdm(drmkdm);
		importModel.setDrbm(drbm);
		return dao.getValidateColumnListByDrmkdmAndDrbm(importModel);
	}

	/**
	 * 获取导入字段列表 根据导入模块代码和导入表名
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	public List<ImportModel> getImportColumnList(String drmkdm, String drbm) {
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		ImportModel importModel = new ImportModel();
		importModel.setDrmkdm(drmkdm);
		importModel.setDrbm(drbm);
		return dao.getImportColumnListByDrmkdmAndDrbm(importModel);
	}

	/**
	 * 获取导入模板
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	public File getImportTemplate(ImportModel importModel) throws Exception {
		String drmkdm=null;
		String drbm = null;
		String drwjgs = null;
		if(importModel != null){
			drmkdm = importModel.getDrmkdm();
			drbm = importModel.getDrbm();
			drwjgs = importModel.getDrwjgs();
		}
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		// 生成新的验证参数控制
		createValidator();
		
		// 获取验证字段列表
		List<ImportModel> list = getValidateColumnList(drmkdm, drbm);
		List<String[]> strList = importModelListToStringArrayList(list);
		List<String[]> postilList = getValidatePostil(drmkdm, drbm);

		// 获取模板路径
		StringBuffer path = new StringBuffer(ServletActionContext
				.getServletContext().getRealPath(IMPORT_TEMP_DIRPATH));
		path.append("\\");
		path.append(IMPORT_TEMPLATE);
		path.append("_");
		path.append(drmkdm);
		path.append(".");
		if(StringUtil.isNotEmpty(drwjgs)){
			path.append(drwjgs);
		}else{
			path.append(IMPORT_SUFFLX);
		}
		// 设置生成模板
		ATemplateBuilder templateBuilder = new ImportTemplateBuilder(postilList);
		File file = null;
		if(drwjgs != null && drwjgs.equals("dbf")){//dbf文件读入
			file = createDbf(list, path.toString());
		}else{
			file = ExcelUtils.createExcel(strList, path.toString(),
					templateBuilder);
		}

		return file;
	}
	
	/*
	 * 生成dbf文件
	 */
	private File createDbf(List<ImportModel> list, String path)
			throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		FileOutputStream os = new FileOutputStream(file);

		JDBField[] ajdbfield = new JDBField[list.size()];
		
		ImportModel importModel = null;

		List<String> tableNameList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			importModel = list.get(i);
			if(!tableNameList.contains(importModel.getDrbm())){
				tableNameList.add(importModel.getDrbm());
			}
		}
		List<HashMap<String, String>> tableColumnsList = new ArrayList<HashMap<String,String>>();
		for (String tableNameTemp : tableNameList) {
			tableColumnsList.addAll(dao.getTableColumns(tableNameTemp));
		}
		for (int i = 0; i < list.size(); i++) {
			importModel = list.get(i);
			String dataLength = null;
			for (HashMap<String, String> hashMap : tableColumnsList) {
				String columnName = hashMap.get("column_name");
				if(importModel.getZdm() != null && importModel.getZdm().trim().equalsIgnoreCase(columnName)){
					dataLength = hashMap.get("data_length");
					break;
				}
			}
			int iDataLength = 20;
			if(dataLength != null){
				try {
					iDataLength = Integer.parseInt(dataLength);
				} catch (Exception e) {
				}
			}
			ajdbfield[i] = new JDBField(importModel.getDrmbzdmc(), 'C', iDataLength, 0);
		}
		DBFWriter dbfwriter = new DBFWriter(os, ajdbfield);

		dbfwriter.close();
		return file;
	}
	
	private void makeDir(File file) {
		if (!file.getParentFile().exists()) {
			makeDir(file.getParentFile());
		}
		file.mkdir();
	}


	/**
	 * 导入数据
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> importData(ImportModel model)
			throws Exception {
		if (model == null || model.getImportFile() == null
				|| StringUtils.isEmpty(model.getDrmkdm())
				|| StringUtils.isEmpty(model.getDrbm())) {
			return null;
		}
		drms = model.getDrms();
		// 生成新的验证参数控制
		createValidator();
		File file = null;
		if(drms != null && drms.equals("1")){
			file = model.getUpdateFile();
		}else{
			file = model.getImportFile();			
		}
		
		// 获取导入数据
		List<String[]> dataList = null;
		String drwjgs = model.getDrwjgs();
		if(drwjgs != null && drwjgs.equals("dbf")){//dbf文件读入
			DBFReader dbfreader = new DBFReader(new FileInputStream(file));
			dataList = dbfreader.getDataList();
		}else{
			dataList = ExcelUtils.getDataList(file);
		}

		if (dataList == null || dataList.size() <= 1) {
			return null;
		}

		// 获取验证字段列表
		List<ImportModel> validatorColumnList = getValidateColumnList(model
				.getDrmkdm(), model.getDrbm());

		// 获取字段绑定的验证
		List<ImportModel> validatorList = getColumnListUniteValidate(model
				.getDrmkdm(), model.getDrbm());

		// 获取实际导入表的具体列
		List<ImportModel> importColumnList = getImportColumnList(model
				.getDrmkdm(), model.getDrbm());
		// 格式化导入数据源 待优化
		dataList = formatImportData(dataList, validatorColumnList);

		// 验证结果前保留验证数据至全局
		initVDataSource(dataList, validatorList);

		// 验证结果
		HashMap<String, Object> vReslut = validateData(model,dataList, validatorList,
				importColumnList);
		//扩展规则验证
		ImportExtendImp iei=new ImportExtendImp();
		iei.setDao(dao);
		IImportExtend iie=iei;
		iie.fomartForExtend(model, vReslut);
		// 统计验证结果
		ImportModel importModel = new ImportModel();
		importModel.setDrzs(String.valueOf(dataList.size()));

		// 插入正确的数据
		List<List<ImportModel>> succeedList = (List<List<ImportModel>>) vReslut
				.get("succeedList");
		if (succeedList != null && succeedList.size() > 0) {
			// 成功数
			importModel.setCgs(String.valueOf(succeedList.size()));

			if(drms != null && drms.equals("1")){
				updateImportData(succeedList, model.getDrbm(), validatorList);
			}else{
				insertImportData(succeedList, model.getDrbm(), importColumnList);
			}
		} else {
			// 成功数
			importModel.setCgs("0");
		}

		List<String[]> errorList = (List<String[]>) vReslut.get("errorList");
		if (errorList != null && errorList.size() > 0) {
			// 错误数
			importModel.setCws(String.valueOf(errorList.size()));
		} else {
			// 错误数
			importModel.setCws("0");
		}

		// 加入导入结果
		vReslut.put("importModel", importModel);
		return vReslut;
	}

	/**
	 * 获取导入错误数据Excel
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @param errorList
	 * @return
	 * @throws Exception
	 */
	public File getImportErrortData(String drmkdm, String drbm,
			List<String[]> errorList, User user) throws Exception {
		log.info("##################生成错误提示信息#####################");
		long now=System.currentTimeMillis();
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)
				|| errorList == null || errorList.size() == 0) {
			return null;
		}
		// 生成新的验证参数控制
		createValidator();
		
		// 获取错误模板
		String[] importTempletStr = getErrorTemplet(drmkdm, drbm);
		// 设置标注
		List<String[]> postilList = getValidatePostil(drmkdm, drbm);
		// 设置生成模板
		ATemplateBuilder templateBuilder = new ImportTemplateBuilder(postilList);
		// 格式化错误数据， 增加模板表头
		if (errorList != null && errorList.size() > 0) {
			errorList.add(0, importTempletStr);
		}

		// 创建错误文件路径
		StringBuffer filePath = new StringBuffer(ServletActionContext
				.getServletContext().getRealPath(
						IImportService.IMPORT_TEMP_DIRPATH));
		filePath.append("\\");
		filePath.append(IImportService.IMPORT_ERROR);
		filePath.append("_");
		filePath.append(drmkdm);
		// 导入错误数据文件名称加入用户名
		if (!StringUtils.isEmpty(user.getYhm())) {
			filePath.append("_");
			filePath.append(user.getYhm());
		}
		filePath.append(".");
		filePath.append(IMPORT_SUFFLX);
		ExcelUtils.createExcel(errorList, filePath.toString(), templateBuilder);
		log.info("      花费时间为:"+(System.currentTimeMillis()-now)/1000+"秒");
		log.info("######################################################");
		return null;
	}

	/**
	 * 导入模型list转化 字符传数据列表, 只转换导入名称到String[]
	 * 
	 * @param modelList
	 * @return
	 */
	private List<String[]> importModelListToStringArrayList(
			List<ImportModel> modelList) {
		if (modelList == null || modelList.size() == 0) {
			return null;
		}
		List<String[]> strList = new ArrayList<String[]>();
		String[] str = new String[modelList.size()];
		for (int i = 0; i < modelList.size(); i++) {
			str[i] = modelList.get(i).getDrmbzdmc();
		}
		strList.add(str);
		return strList;
	}

	/**
	 * 获取错误的模板头
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	private String[] getErrorTemplet(String drmkdm, String drbm) {
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		// 获取验证字段列表
		List<ImportModel> importTemplet = getValidateColumnList(drmkdm, drbm);
		List<String[]> importTempletStr = importModelListToStringArrayList(importTemplet);
		if (importTempletStr != null && importTempletStr.size() > 0) {
			String[] templet = new String[importTempletStr.get(0).length + 1];
			for (int i = 0; i < importTempletStr.get(0).length; i++) {
				templet[i] = importTempletStr.get(0)[i];
			}
			templet[importTempletStr.get(0).length] = "错误备注";
			return templet;
		} else {
			return null;
		}
	}

	/**
	 * 验证数据
	 * 
	 * @param dataSource
	 *            数据源
	 * @param validateRule
	 *            验证列和验证规则
	 * @return
	 */
	protected HashMap<String, Object> validateData(ImportModel model,List<String[]> dataSource,
			List<ImportModel> validateRule, List<ImportModel> importColumnList)
			throws Exception {
		if (dataSource == null || validateRule == null
				|| importColumnList == null) {
			return null;
		}
		// 验证结果
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		// 验证结果
		String vResult = "";
		// 验证通过数据列表
		List<List<ImportModel>> succeedList = new ArrayList<List<ImportModel>>();
		// 验证不通过数据列表
		List<String[]> errorList = new ArrayList<String[]>();
		// 临时错误数据
		String[] tempErrorData = null;
		// 临时正确数据集合
		List<ImportModel> tempSucceedData = null;
		// 临时正确数据
		ImportModel importModel = null;
		// 验证规则列表
		String[] vRule = null;
		// 验证包名
		String[] vPackage = null;
		// 验证行标记 true：通过，false：不通过
		boolean rowSign = true;
		// 验证错误的信息
		StringBuffer errorMessage = null;
		// 合并列数
		int uniteNum = 0;
		// 成功数据计数器
		int succeedTally = -1;
		for (int i = 0; i < dataSource.size(); i++) {
			// 遍历得到行
			tempErrorData = new String[dataSource.get(i).length + 1];
			tempSucceedData = new ArrayList<ImportModel>();
			// 重置标记
			rowSign = true;
			// 清空验证信息
			errorMessage = new StringBuffer();
			// 重设合并列数
			uniteNum = 0;
			// 重设成功数据计数器
			succeedTally = 0;
			// 验证value
			StringBuffer valueB = null;
			//初始化新一行的合并验证数据
			this.uniteValidatorValue = new HashMap<String, Object>();
			// 遍历得到单元格
			for (int j = 0; j < dataSource.get(i).length; j++) {
				// 获取验证规则
				vRule = validateRule.get(j).getYzlmc() != null ? validateRule
						.get(j).getYzlmc().split(",") : new String[0];
				vPackage = validateRule.get(j).getYzlbm() != null ? validateRule
						.get(j).getYzlbm().split(",")
						: new String[0];

				// 将参数封装引用类型
				if(dataSource.get(i)[j] != null){
					valueB = new StringBuffer(dataSource.get(i)[j]);
				}else{
					valueB = new StringBuffer();
				}
				// 将验证过的数据储存起来
				tempErrorData[j] = valueB.toString();

				// 验证该项是否需要合并验证
				if (getUniteItem(validateRule.get(j)) != null) {
					setUniteValidatorValue(validateRule.get(j), valueB
							.toString());
				}

				ImportModel validateImportModel = new ImportModel();
				validateImportModel = validateRule.get(j);
				// 绑定验证
				vResult = validateValue(validateImportModel, valueB, vPackage,
						vRule);
				if (!StringUtils.isEmpty(vResult)) {
					// 验证不通过
					errorMessage.append(validateRule.get(j).getDrmbzdmc());
					errorMessage.append(":");
					errorMessage.append(vResult);
					errorMessage.append("\n");
					rowSign = false;
				} else {
					// 验证通过
				}

				// 计算验证成功的数据计数器
				if (uniteNum == 0) {
					uniteNum = Integer.valueOf(
							importColumnList.get(succeedTally).getHbdrs())
							.intValue();
				}
				// 计数器运算完成减少合并列
				uniteNum--;
				
				//加入正确的值tempSucceedData
				addSucceedData(tempSucceedData, validateRule.get(j), valueB.toString());
				
				// 计算验证成功的数据计数器 计数器自增长
				if (uniteNum == 0) {
					succeedTally++;
				}
			}
			// 合并数据验证
			String vUniteMessage = validateUniteValue();
			// 验证结果不为空 错误结果 并记录错误状态
			if (!StringUtil.isEmpty(vUniteMessage)) {
				errorMessage.append(vUniteMessage);
				rowSign = false;
			}

			// 该行数据验证
			if (rowSign) {
				// 通过
				succeedList.add(tempSucceedData);
			} else {
				// 不通过
				tempErrorData[dataSource.get(i).length] = errorMessage
						.toString();
				errorList.add(tempErrorData);
			}
		}
		hashMap.put("succeedList", succeedList);
		hashMap.put("errorList", errorList);
		return hashMap;
	}
	
	/**
	 * 增加正确数据
	 * @param tempSucceedData
	 * @param importModel
	 */
	private void addSucceedData(List<ImportModel> tempSucceedData,
			ImportModel importModel, Object value)throws Exception {
		if (tempSucceedData == null || importModel == null) {
			return;
		}
		StringBuffer val = new StringBuffer("");
		
		for (int i = 0; i < tempSucceedData.size(); i++) {
			if (tempSucceedData.get(i).getZdm().equals(importModel.getZdm())) {
				//凭借数值
				val.append(tempSucceedData.get(i).getValue().toString());
				val.append(value);
				//设置数值
				tempSucceedData.get(i).setValue(
						ImportJdbcTypeFormat.parameterFormat(val.toString(),
								importModel.getZdlx()));
				return ;
			}
		}
		
		//如果金结果集中，则新增
		ImportModel im = new ImportModel();
		im.setZdlx(importModel.getZdlx());
		im.setZdm(importModel.getZdm());
		im.setValue(ImportJdbcTypeFormat.parameterFormat(value.toString(), importModel.getZdlx()));
		tempSucceedData.add(im);
	}

	/**
	 * 验证值
	 * 
	 * @param importModel
	 *            导入对象
	 * @param value
	 *            验证数据
	 * @param vRuleObj
	 *            验证规则
	 * @return
	 * 
	 *         private String validateValue(ImportModel importModel, String
	 *         value, String[] vRule)throws Exception { if(importModel == null
	 *         || vRule == null || vRule.length == 0){ return null; } // 验证规则包名
	 *         StringBuffer vPackage = null; // 验证规则object对象 Object vRuleObj =
	 *         null; // 验证类型 String[] yzcs=importModel.getYzcs().split(",");
	 * 
	 *         //验证结果信息 StringBuffer resultMessage=new StringBuffer();
	 * 
	 *         //导入参数初始化 ImportInitParameter iip=new ImportInitParameter(); for
	 *         (int i = 0; i < vRule.length; i++) { // 验证规则包名 vPackage = new
	 *         StringBuffer("com.woshidaniu.common.validator.rules.");
	 *         vPackage.append(vRule[i]); vRuleObj =
	 *         Class.forName(vPackage.toString()); //执行验证类型 当前是带参数和不带参数 if
	 *         (!StringUtil.isEmpty(yzcs[i]) &&
	 *         !IMPORT_YZCS_DEFAULT.equals(yzcs[i])) { Object[]
	 *         parameters=iip.getInitParameterArray(vRule[i],yzcs[i]);
	 * 
	 *         
	 *         resultMessage.append(ValidatorUtils.validateValueByParameter(value
	 *         , parameters, vRuleObj)); }else{
	 *         resultMessage.append(ValidatorUtils.validateValue(value,
	 *         vRuleObj)); } } return resultMessage.toString(); }
	 */

	/**
	 * 验证值
	 * 
	 * @param importModel
	 *            导入对象
	 * @param value
	 *            验证数据
	 * @param vPackage
	 *            验证包名
	 * @param vRuleObj
	 *            验证规则
	 * @return
	 */
	private String validateValue(ImportModel importModel, Object value,
			String[] vPackage, String[] vRule) throws Exception {
		if (importModel == null || vRule == null || vRule.length == 0) {
			return null;
		}
		// 验证规则包名
		StringBuffer vPackages = null;
		// 验证规则object对象
		Object vRuleObj = null;
		// 验证类型
		String[] yzcs = importModel.getYzcs().split(",");

		// 验证结果信息
		StringBuffer resultMessage = new StringBuffer();

		// 导入参数初始化
		ImportInitParameter iip = new ImportInitParameter(vDataSource);

		// 验证参数key
		String vParameKey = "";

		// 验证该参数验证是否存在
		Object[] parameters = null;
		for (int i = 0; i < vRule.length; i++) {
			// 验证规则包名
			vPackages = new StringBuffer(vPackage[i]);
			vPackages.append(".");
			vPackages.append(vRule[i]);
			vRuleObj = Class.forName(vPackages.toString());

			// 如果不是非空验证，并且验证数值是空的时候直接通过不做验证
			if (!NotEmptyRule.class.equals(vRuleObj)
					&& (value == null || StringUtil.isEmpty(value.toString()))) {
				continue;
			}
			// 执行验证类型 当前是带参数和不带参数
			boolean yzFlag = false;
			if(drms != null && drms.equals("1")){//仅更新操作
				if (yzcs[i] != null
						&& !IMPORT_YZCS_DEFAULT.equals(yzcs[i])&& !RULE_IMPORTUNIQUERULE.equals(vRule[i])) {
					yzFlag = true;
				}
			}else{
				if (yzcs[i] != null
						&& !IMPORT_YZCS_DEFAULT.equals(yzcs[i]) ) {
					yzFlag = true;
				}
			}
			if (yzFlag) {
				// 验证该参数验证是否存在 此处使用了中文 key
				vParameKey = importModel.getDrmbzdmc() + "_" + vRule[i];
				parameters = (Object[]) getValidatorParame(vParameKey);
				if (parameters == null) {
					parameters = iip.getInitParameterArray(vRule[i], yzcs[i]);
					// 设置参数至全局变量中
					setValidatorParame(vParameKey, parameters);
				}

				resultMessage.append(ValidatorUtils.validateValueByParameter(
						value, parameters, vRuleObj));
			} else {
				resultMessage.append(ValidatorUtils.validateValue(value,
						vRuleObj));
			}
		}
		return resultMessage.toString();
	}

	/**
	 * * 插入导入数据
	 * 
	 * @param dataList
	 *            数据列表
	 * @param tableName
	 *            表名称
	 * @param columnList
	 *            列名称列表
	 * @return
	 */
	protected boolean insertImportData(List<List<ImportModel>> dataList,
			String tableName, List<ImportModel> columnList) {
		if (dataList == null || dataList.size() == 0
				|| StringUtils.isEmpty(tableName) || columnList == null
				|| columnList.size() == 0) {
			return false;
		}
		HashMap<String, Object> insertData = new HashMap<String, Object>();
		// 表名
		insertData.put("tableName", tableName);

		// 字段列表
		insertData.put("columnList", columnList);

		// 值列表
		insertData.put("dataList", dataList);

		return dao.batchInsertData(insertData) > 0;
	}

	/*
	 * 更新导入的数据
	 */
	protected boolean updateImportData(List<List<ImportModel>> dataList,
			String tableName, List<ImportModel> columnList) {
		if (dataList == null || dataList.size() == 0
				|| tableName == null || columnList == null
				|| columnList.size() == 0) {
			return false;
		}
		HashMap<String, Object> insertData = new HashMap<String, Object>();
		// 表名
		insertData.put("tableName", tableName);
		
		String yzlmc = null;
		String zdm = null;
		List<String> uniqueZdmList = new ArrayList<String>();
		for (ImportModel importModel : columnList) {
			zdm = importModel.getZdm();
			yzlmc = importModel.getYzlmc();
			if(yzlmc != null && yzlmc.contains(IImportService.RULE_IMPORTUNIQUERULE)){
				uniqueZdmList.add(zdm);
			}
		}
		
		if(uniteValidatorList != null && uniteValidatorList.size() > 0){
			for (ImportModel uniteValidator : uniteValidatorList) {
				uniqueZdmList.add(uniteValidator.getZdm());
			}
		}
		
		boolean result = false;
		if(uniqueZdmList.size() > 0){
			List<ImportModel> uniqueValueList = null;
			for (List<ImportModel> columnValueList : dataList) {
				uniqueValueList = uniqueValueList(columnValueList,uniqueZdmList);
				insertData.put("columnValueList", columnValueList);
				insertData.put("uniqueValueList", uniqueValueList);
				dao.batchUpdate(insertData);
			}
			result = true;
		}
		return result;
	}
	
	/*
	 * 取唯一验证的字段及值
	 */
	private List<ImportModel> uniqueValueList(List<ImportModel> columnValueList,List<String> uniqueZdmList){
		List<ImportModel> uniqueValueList = new ArrayList<ImportModel>();
		for (ImportModel importModel : columnValueList) {
			for (String uniqueZdm : uniqueZdmList) {
				if(uniqueZdm != null && uniqueZdm.equals(importModel.getZdm())){
					uniqueValueList.add(importModel);
				}
			}
		}
		
		return uniqueValueList;
	}

	/**
	 * 格式化导入数据（原因是用户操作导入模板，使导入模板出现多余列）
	 * 
	 * @param dataList
	 * @param columnList
	 * @return
	 */
	protected List<String[]> formatImportData(List<String[]> dataList,
			List<ImportModel> columnList) {
		if (dataList == null || dataList.size() == 0 || columnList == null
				|| columnList.size() == 0) {
			return null;
		}
		// 删除第一行标题
		if (dataList.size() > 0) {
			dataList.remove(0);
		}
		// 去除多余列空白
		List<String[]> impDataList = new ArrayList<String[]>();
		String[] data = null;
		String[] dtjlStrs = null;
		for (int i = 0; i < dataList.size(); i++) {
			dtjlStrs = dataList.get(i);
			data = new String[columnList.size()];
			for (int j = 0; j < columnList.size(); j++) {
				if(dtjlStrs.length > j){
					data[j] = dtjlStrs[j];
				}
			}
			impDataList.add(data);
		}
		return impDataList;
	}

	/**
	 * 获取字段列表 合并验证规则(以','分割)
	 * 
	 * @param model
	 * @return
	 */
	protected List<ImportModel> getColumnListUniteValidate(String drmkdm,
			String drbm) throws Exception {
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		ImportModel importModel = new ImportModel();
		importModel.setDrmkdm(drmkdm);
		importModel.setDrbm(drbm);
		return uniteValidateInfo(dao
				.getValidateColumnListByDrmkdmAndDrbm(importModel), dao
				.getColumnValidateListByDrmkdmAndDrbm(importModel));
	}

	/**
	 * 合并验证规则
	 * 
	 * @param columnList
	 * @param vRuleList
	 * @return
	 */
	private List<ImportModel> uniteValidateRule(List<ImportModel> columnList,
			List<ImportModel> vRuleList) {
		if (columnList == null || vRuleList == null) {
			return columnList;
		}
		// 合并项
		String yzlmc = "";
		String yzcs = "";
		String yzmc = "";
		String yzsm = "";
		String yzlbm = "";

		for (int i = 0; i < columnList.size(); i++) {
			yzlmc = "";
			yzcs = "";
			yzmc = "";
			yzsm = "";
			yzlbm = "";

			for (int j = 0; j < vRuleList.size(); j++) {
				if (columnList.get(i).getDrmbzdmc().equals(
						vRuleList.get(j).getDrmbzdmc())) {
					if (StringUtils.isEmpty(yzlmc)) {
						yzlmc = vRuleList.get(j).getYzlmc();
						yzcs = vRuleList.get(j).getYzcs();
						yzmc = vRuleList.get(j).getYzmc();
						// 验证说明文字替换
						if (StringUtil.isEmpty(vRuleList.get(j).getDrmkyzsm())) {
							yzsm = vRuleList.get(j).getYzsm();
						} else {
							yzsm = vRuleList.get(j).getDrmkyzsm();
						}
						yzlbm = vRuleList.get(j).getYzlbm();
					} else {
						yzlmc = yzlmc + "," + vRuleList.get(j).getYzlmc();
						yzcs = yzcs + "," + vRuleList.get(j).getYzcs();
						yzmc = yzmc + "," + vRuleList.get(j).getYzmc();

						// 验证说明文字替换
						if (StringUtil.isEmpty(vRuleList.get(j).getDrmkyzsm())) {
							yzsm = yzsm + "," + vRuleList.get(j).getYzsm();
						} else {
							yzsm = yzsm + "," + vRuleList.get(j).getDrmkyzsm();
						}
						yzlbm = yzlbm + "," + vRuleList.get(j).getYzlbm();
					}
				}
			}

			columnList.get(i).setYzlmc(yzlmc);
			columnList.get(i).setYzcs(yzcs);
			columnList.get(i).setYzmc(yzmc);
			columnList.get(i).setYzsm(yzsm);
			columnList.get(i).setYzlbm(yzlbm);
		}
		return columnList;
	}

	/**
	 * 合并验证信息
	 * 
	 * @param columnList
	 * @param vRuleList
	 * @return
	 */
	protected List<ImportModel> uniteValidateInfo(List<ImportModel> columnList,
			List<ImportModel> vRuleList) throws Exception {
		if (columnList == null || vRuleList == null) {
			return columnList;
		}
		// 合并项
		String yzlmc = "";
		String yzcs = "";
		String yzmc = "";
		String yzsm = "";
		String yzlbm = "";

		for (int i = 0; i < columnList.size(); i++) {
			yzlmc = "";
			yzcs = "";
			yzmc = "";
			yzsm = "";
			yzlbm = "";

			for (int j = 0; j < vRuleList.size(); j++) {
				// 是否是当前列
				if (columnList.get(i).getDrmbzdmc().equals(
						vRuleList.get(j).getDrmbzdmc())) {
					// 合并项验证 若为合并验证，则不加入普通验证规则
					if (SFHBYZ_1.equals(vRuleList.get(j).getSfhbyz())) {
						setUniteValidatorList(vRuleList.get(j));
						continue;
					}

					// 未绑定验证类
					if (StringUtils.isEmpty(yzlmc)) {
						yzlmc = vRuleList.get(j).getYzlmc();
						yzcs = vRuleList.get(j).getYzcs();
						yzmc = vRuleList.get(j).getYzmc();
						// 验证说明文字替换
						if (StringUtil.isEmpty(vRuleList.get(j).getDrmkyzsm())) {
							yzsm = vRuleList.get(j).getYzsm();
						} else {
							yzsm = vRuleList.get(j).getDrmkyzsm();
						}
						yzlbm = vRuleList.get(j).getYzlbm();
					} else {
						yzlmc = yzlmc + "," + vRuleList.get(j).getYzlmc();
						yzcs = yzcs + "," + vRuleList.get(j).getYzcs();
						yzmc = yzmc + "," + vRuleList.get(j).getYzmc();

						// 验证说明文字替换
						if (StringUtil.isEmpty(vRuleList.get(j).getDrmkyzsm())) {
							yzsm = yzsm + "," + vRuleList.get(j).getYzsm();
						} else {
							yzsm = yzsm + "," + vRuleList.get(j).getDrmkyzsm();
						}
						yzlbm = yzlbm + "," + vRuleList.get(j).getYzlbm();
					}
				}
			}

			columnList.get(i).setYzlmc(yzlmc);
			columnList.get(i).setYzcs(yzcs);
			columnList.get(i).setYzmc(yzmc);
			columnList.get(i).setYzsm(yzsm);
			columnList.get(i).setYzlbm(yzlbm);
		}
		return columnList;
	}

	/**
	 * 获取验证备注
	 * 
	 * @param drmkdm
	 * @param drbm
	 * @return
	 */
	private List<String[]> getValidatePostil(String drmkdm, String drbm)
			throws Exception {
		if (StringUtils.isEmpty(drmkdm) || StringUtils.isEmpty(drbm)) {
			return null;
		}
		List<ImportModel> impList = getColumnListUniteValidate(drmkdm, drbm);
		if (impList != null && impList.size() == 0) {
			return null;
		}
		List<String[]> list = new ArrayList<String[]>();
		String[] postil = null;
		StringBuffer pos = null;
		// 验证说明
		String[] yzsms = null;
		postil = new String[impList.size()];
		for (int i = 0; i < impList.size(); i++) {

			pos = new StringBuffer();
			if (!StringUtils.isEmpty(impList.get(i).getYzmc())) {
				yzsms = impList.get(i).getYzsm().split(",");
				for (int j = 0; j < yzsms.length; j++) {
					pos.append(impList.get(i).getDrmbzdmc());
					pos.append(":");
					pos.append(yzsms[j]);
					pos.append(";");
					pos.append("\n");
				}
			}
			postil[i] = pos.toString();
		}
		list.add(postil);
		return list;
	}

	/**
	 * 获取验证数据列表
	 * 
	 * @param parame
	 *            {tableName:"",columnName:"XXX,XXX"}
	 * @return
	 */
	public List<HashMap<String, String>> getValidateValueList(
			HashMap<String, String> parame) {
		if (parame == null) {
			return null;
		}
		return dao.getValidateValueList(parame);
	}

	/**
	 * 获取验证数据单列数据
	 * 
	 * @param parame
	 *            {tableName:"",columnName:"XXX"}
	 * @return HashMap
	 */
	public HashMap<String, String> getValidateValueMap(
			HashMap<String, String> parame) {
		if (parame == null) {
			return null;
		}
		List<HashMap<String, String>> vData = dao.getValidateValueMap(parame);
		return listHashMapvalueToHashMapKey(vData, parame.get("columnName"));
	}

	/**
	 * 将list中hashMap的Value 转行成 hashMap的key
	 * 
	 * @return
	 */
	private HashMap<String, String> listHashMapvalueToHashMapKey(
			List<HashMap<String, String>> dataList, String parame) {
		if (dataList == null) {
			return null;
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < dataList.size(); i++) {
			hashMap.put(dataList.get(i).get("COLUMNNAME"), dataList.get(i).get(
					"COLUMNNAME"));
		}
		return hashMap;
	}

	/**
	 * 获取合并验证项
	 * 
	 * @param importModel
	 *            验证model
	 * @return 如果存在：model，不存在：null
	 */
	private ImportModel getUniteItem(ImportModel importModel) {
		if (importModel == null || uniteValidatorList.size() == 0) {
			return null;
		}
		ImportModel uniteModel = null;
		for (int i = 0; i < uniteValidatorList.size(); i++) {
			if (uniteValidatorList.get(i).getDrmbzdmc().indexOf(
					UNITESIGN + importModel.getDrmbzdmc() + UNITESIGN) > -1) {
				uniteModel = uniteValidatorList.get(i);
			}
		}
		return uniteModel;
	}

	/**
	 * 设置为合并验证项
	 * 
	 * @param importModel
	 */
	private void setUniteValidatorList(ImportModel importModel)
			throws Exception {
		if (importModel == null) {
			return;
		}
		// 是否已添加合并项
		if (uniteValidatorList.size() != 0) {
			for (int i = 0; i < uniteValidatorList.size(); i++) {
				// 过滤是否是同一个验证规则
				if (uniteValidatorList.get(i).getYzcs().equals(
						importModel.getYzcs())) {
					uniteValidatorList.get(i).setDrmbzdmc(
							uniteValidatorList.get(i).getDrmbzdmc()
									+ importModel.getDrmbzdmc() + UNITESIGN);
					return;
				}
			}
		}

		ImportModel uniteItem = new ImportModel();
		// 简单复制属性
		copyImportModelProperties(uniteItem, importModel);

		// 标记合并项
		uniteItem.setDrmbzdmc(UNITESIGN + uniteItem.getDrmbzdmc() + UNITESIGN);
		uniteValidatorList.add(uniteItem);
	}

	/**
	 * 设置合并验证的值
	 * 
	 * @param importModel
	 * @throws Exception
	 */
	private void setUniteValidatorValue(ImportModel importModel, String value)
			throws Exception {
		if (importModel == null) {
			return;
		}
		//祝贺验证
		String uniteDrmbzdmc=UNITESIGN+importModel.getDrmbzdmc()+UNITESIGN;
		
		// 合并验证值
		if (uniteValidatorList.size() != 0) {
			for (int i = 0; i < uniteValidatorList.size(); i++) {
				//判断当前字段是否是合并项        如果导入的"字段名称"，和合并导入模板字段名称是 存在的   
				if(uniteValidatorList.get(i).getDrmbzdmc().indexOf(uniteDrmbzdmc) > -1){
					//判断是否已存在uniteValidatorValue  ，如果存在则合并内容
					if (uniteValidatorValue.containsKey(uniteValidatorList.get(i).getDrmbzdmc())) {
						uniteValidatorValue.put(uniteValidatorList.get(i).getDrmbzdmc(), uniteValidatorValue
								.get(uniteValidatorList.get(i).getDrmbzdmc())
								+ value);
						return ;
					} else {
						uniteValidatorValue.put(uniteValidatorList.get(i).getDrmbzdmc(), value);
						return ;
					}
				}
			}
		}
	}

	/**
	 * 验证合并值
	 * 
	 * @return
	 */
	private String validateUniteValue() throws Exception {
		if (uniteValidatorList == null || uniteValidatorList.size() == 0) {
			return "";
		}

		// 验证规则
		String[] vRule = null;
		// 验证包名
		String[] vPackage = null;
		// 验证结果
		String vResult = "";
		// 错误信息
		StringBuffer vMessage = new StringBuffer("");
		//导入模板字段名称
		String drmbzdmc="";

		for (int i = 0; i < uniteValidatorList.size(); i++) {
			if (uniteValidatorValue.containsKey(uniteValidatorList.get(i)
					.getDrmbzdmc())) {
				// 获取验证规则
				vRule = uniteValidatorList.get(i).getYzlmc() != null ? uniteValidatorList
						.get(i).getYzlmc().split(",")
						: new String[0];
				vPackage = uniteValidatorList.get(i).getYzlbm() != null ? uniteValidatorList
						.get(i).getYzlbm().split(",")
						: new String[0];
			}
			// 绑定验证
			vResult = validateValue(uniteValidatorList.get(i),
					this.uniteValidatorValue.get(uniteValidatorList.get(i)
							.getDrmbzdmc()), vPackage, vRule);
			if (!StringUtils.isEmpty(vResult)) {
				drmbzdmc=uniteValidatorList.get(i).getDrmbzdmc();
				
				// 验证不通过
				// 截取 UNITESIGN 分割符
				vMessage.append(drmbzdmc.substring(drmbzdmc.indexOf(UNITESIGN)+UNITESIGN.length(), 
						drmbzdmc.lastIndexOf(UNITESIGN)).replaceAll(UNITESIGN, "+"));
				vMessage.append(":");
				vMessage.append(vResult);
				vMessage.append("\n");
			}
		}

		return vMessage.toString();
	}

	/**
	 * 复制导入对象属性
	 * 
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            源对象
	 */
	private void copyImportModelProperties(ImportModel dest, ImportModel orig){
		if(dest == null || orig == null){
			return ;
		}
		//如果importModel 新增数据可以在此处增加 ，
		dest.setDryzbh(orig.getDryzbh());
		dest.setDrmkdm(orig.getDrmkdm());
		dest.setDrmkmc(orig.getDrmkmc());
		dest.setDrmkyzsm(orig.getDrmkyzsm());
		dest.setDrbm(orig.getDrbm());
		dest.setDrbzwmc(orig.getDrbzwmc());
		dest.setZdm(orig.getZdm());
		dest.setZdmc(orig.getZdmc());
		dest.setZdlx(orig.getZdlx());
		dest.setXssx(orig.getXssx());
		dest.setYzcs(orig.getYzcs());
		dest.setDrmbzdmc(orig.getDrmbzdmc());
		dest.setSfhbyz(orig.getSfhbyz());
		dest.setYzmc(orig.getYzmc());
		dest.setYzlmc(orig.getYzlmc());
		dest.setYzsm(orig.getYzsm());
		dest.setCshcssy(orig.getCshcssy());
		dest.setYzlbm(orig.getYzlbm());
	}

	public List<ImportModel> getUniteValidatorList() {
		return uniteValidatorList;
	}

	public void setUniteValidatorList(List<ImportModel> uniteValidatorList) {
		this.uniteValidatorList = uniteValidatorList;
	}

	public Map<String, Object> getUniteValidatorValue() {
		return uniteValidatorValue;
	}

	public void setUniteValidatorValue(Map<String, Object> uniteValidatorValue) {
		this.uniteValidatorValue = uniteValidatorValue;
	}

	public String getDrms() {
		return drms;
	}

	public void setDrms(String drms) {
		this.drms = drms;
	}

	@Override
	public Map<String, Object> importData(ImportModel model, ProgressBar pb)
			throws Exception {
		this.pb=pb;
		return importData(model);
	}
}
