/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.svcinterface.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.file.TempFileService;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.drdcsj.drsj.comm.Constants;
import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.CrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrFzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.LyzgzdzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.PluginModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.IInsertDb;
import com.woshidaniu.drdcsj.drsj.excel.DrExcelUtils;
import com.woshidaniu.drdcsj.drsj.excel.DrfzQuery;
import com.woshidaniu.drdcsj.drsj.excel.ExcelUtils;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;
import com.woshidaniu.drdcsj.drsj.ruler.imp.Validator;
import com.woshidaniu.drdcsj.drsj.ruler.imp.properties.CommonValidator;
import com.woshidaniu.drdcsj.drsj.ruler.imp.properties.CompositeIdValidator;
import com.woshidaniu.drdcsj.drsj.ruler.imp.properties.FormateValidator;
import com.woshidaniu.drdcsj.drsj.ruler.imp.properties.PluginValidator;
import com.woshidaniu.drdcsj.drsj.svcinterface.IExcelImportService;
import com.woshidaniu.drdcsj.drsj.svcinterface.IImportService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author 982 张昌路
 * @author 1571 康康
 * 1.整理,优化
 */
@Service(value = "drdcsjService")
public class ExcelImportService extends BaseServiceImpl<DrpzModel, IImportDao> implements IExcelImportService {

	private static final Logger log = LoggerFactory.getLogger(ExcelImportService.class);
	//导入验证模式配置key
	private static final String ValidateModelKey = "niutal.dr.validate.mode";
	@Autowired
	@Qualifier("sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	@Qualifier("insertAdapter")
	private IInsertDb insertDao;
	@Autowired
	@Qualifier("drdcsjDao")
	private IImportDao dao;
	//验证模式
	private String validateMode = "";
	
	@Autowired
	private TempFileService tempFileService;

	@PostConstruct
	public void init() {
		this.validateMode = MessageUtil.getText(ValidateModelKey);
		log.info("导入模块,导入验证key:[{}],导入验证value:[{}]",ValidateModelKey,this.validateMode);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}

	@Override
	public Map<String,String> importData(String drmkdm,String crfs,File importFile,Map<String, List<String>> data,List<String> selectImportColumn,String yhm) throws Exception{
		final SqlSession sqlSession =  this.sqlSessionFactory.openSession();
		try{
			Map<String,String> result = this.doImportData(drmkdm, crfs, importFile, data, selectImportColumn, yhm, sqlSession);
			
			sqlSession.commit();
			
			return result;
			
		}catch (Exception e) {
			
			String msg = String.format("导入数据发生异常[drmkdm=%s,crfs=%s,importFile=%s,yhm=%s,selectImportColumn=%s],origin cause:%s",drmkdm,crfs,importFile,yhm,selectImportColumn,e.getMessage());
			
			log.error(msg,e);
			
			sqlSession.rollback();		

			throw e;
			
		}finally{
			sqlSession.close();
		}
	}
	
	public Map<String,String> doImportData(String drmkdm,String crfs,File importFile,Map<String, List<String>> data,List<String> selectImportColumn,String yhm,SqlSession sqlSession){
		//导入列配置
		List<DrlpzModel> drlpzList = this.dao.getDrlpzModelList(selectImportColumn);
		
		log.debug("导入数据:导入文件[{}],导入模块代码[{}],操作方式[{}],导入用户[{}]",importFile,drmkdm,crfs,yhm);

		DrpzModel drpzModel = this.dao.getDrPzxx(drmkdm).get(0);
		drpzModel.setCrfs(crfs);

		//主键id集合
		List<String> compositeIdList = new ArrayList<String>();
		for (DrlpzModel drlpzModel : drlpzList) {
			if (ImportConfig._SFZJ_YES.equals(drlpzModel.getSfzj())) {
				String drl = drlpzModel.getDrl();
				compositeIdList.add(drl);
				log.debug("主键列[{}]",drl);
			}
		}
		//是联合主键
		if (compositeIdList.size() > 1) {
			drpzModel.setCompositeId(true);
		}
		drpzModel.setCompositeIds(compositeIdList.toArray(new String[compositeIdList.size()]));

		// 获取业务验证配置
		List<PluginModel> pluginModelList = this.dao.getPluginModelList(drmkdm);
		if(!pluginModelList.isEmpty()) {
			for (PluginModel pluginModel : pluginModelList) {
				log.debug("导入配置[{}]配置插件[{}]",drmkdm,pluginModel);
				pluginModel.doConfig(drlpzList);
			}
		}else {
			log.debug("导入配置[{}]没有配置插件",drmkdm);
		}
		drpzModel.setPluginModelList(pluginModelList);

		// map深度复制
		Map<String, List<String>> fomartData = new HashMap<String, List<String>>();
		Set<String> keySet = data.keySet();
		for (String string : keySet) {
			List<String> list = data.get(string);
			List<String> copy = new ArrayList<String>(list);
			fomartData.put(string, copy);
		}
		//导入错误信息
		ImportErrorMessage errorMessages = new ImportErrorMessage();
		int totalCount = this.getImportTs(data); 

		// 验证传递Model对象（封装错误信息，格式化后的值）
		ValidatorModel vm = new ValidatorModel(errorMessages, fomartData);
		
		//1.格式化数据
		//列传行
		
		Validator formateValidator = new FormateValidator(drpzModel,drlpzList,vm,sqlSession);
		formateValidator.validate();
		
		//格式化验证有问题直接返回 
		if (!errorMessages.isEmpty() && firstErrorReturn(drpzModel)) {
			String errorFileId = this.createErrorExcel(drpzModel,data,errorMessages, yhm);
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put(IImportService.VersionKey, IImportService.Version_v1);
			resultMap.put(IImportService.ResultKey, "0");
			resultMap.put(IImportService.ResultFileIdKey, errorFileId);
			resultMap.put(IImportService.TotalSizeKey, String.valueOf(totalCount));
			resultMap.put(IImportService.SuccessInsertRowsSizeKey, "0");
			resultMap.put(IImportService.SuccessUpdateRowsSizeKey, "0");
			resultMap.put(IImportService.TotalUnAcceptRowSizeKey, String.valueOf(errorMessages.size()));
			
			return resultMap;
		}

		//2. 常规验证，即：可根据配置直接验证的校验
		//常规验证: 必填，长度，唯一主键，唯一性验证
		Validator commonValidator = new CommonValidator(drpzModel,drlpzList,vm,sqlSession);
		commonValidator.validate();
		
		//常规验证有问题直接返回
		if (!errorMessages.isEmpty() && firstErrorReturn(drpzModel)) {
			String errorFileId = createErrorExcel(drpzModel, data, errorMessages, yhm);
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put(IImportService.VersionKey, IImportService.Version_v1);
			resultMap.put(IImportService.ResultKey, "0");
			resultMap.put(IImportService.ResultFileIdKey,errorFileId);
			resultMap.put(IImportService.TotalSizeKey,String.valueOf(totalCount));
			resultMap.put(IImportService.SuccessInsertRowsSizeKey, "0");
			resultMap.put(IImportService.SuccessUpdateRowsSizeKey, "0");
			resultMap.put(IImportService.TotalUnAcceptRowSizeKey,String.valueOf(errorMessages.size()));
			
			return resultMap;
		}
		
		//3. 联合主键验证
		if (drpzModel.isCompositeId()) {
			// 联合主键字段数组
			String[] compositeIds = drpzModel.getCompositeIds();
			Validator compositeIdValidator = new CompositeIdValidator(drpzModel,drlpzList,vm,sqlSession,compositeIds);
			compositeIdValidator.validate();
		}

		//4.业务验证
		if (!drpzModel.getPluginModelList().isEmpty()) {
			PluginValidator pluginValidator = new PluginValidator(drpzModel,drlpzList,vm,drpzModel.getPluginModelList());
			pluginValidator.validate();
		}

		int insertRow = 0;
		int updateRow = 0;
		String resultFileId = "";
		if (!errorMessages.isEmpty()) {
			resultFileId = this.createErrorExcel(drpzModel, data,errorMessages, yhm);
		} else {
			//5.数据库操作
			StopWatch stopWatch5 = new StopWatch();
			stopWatch5.start();
			String crfsString = drpzModel.getCrfs();
			//数据库实际操作
			if (ImportConfig._CRFS_INSERT.equals(crfsString)) {
				insertRow = insertDao.batchInsertData(vm, drpzModel, drlpzList);
			} else if (ImportConfig._CRFS_UPDATE.equals(crfsString)) {
				updateRow = insertDao.batchUpdateData(vm, drpzModel, drlpzList);
			} else if (ImportConfig._CRFS_INSERT_UPDATE.equals(crfsString)) {
				Integer insertOrUpdate[] = insertDao.batchAddOrUpdate(vm, drpzModel, drlpzList);
				insertRow = insertOrUpdate[0];
				updateRow = insertOrUpdate[1];
			}else {
				log.error("未定义的类型:{}",drpzModel.getCrfs());
			}
			
			//打印日志
			stopWatch5.stop();
			if (ImportConfig._CRFS_INSERT.equals(crfsString)) {
				log.info("批量插入数据库,耗时:{}ms", stopWatch5.getTime());
			} else if (ImportConfig._CRFS_UPDATE.equals(crfsString)) {
				log.info("批量更新数据库,耗时:{}ms", stopWatch5.getTime());
			} else if (ImportConfig._CRFS_INSERT_UPDATE.equals(crfsString)) {
				log.info("批量插入或更新数据库,耗时:{}ms", stopWatch5.getTime());
			}else {
				log.error("未定义的类型:{}",drpzModel.getCrfs());
			}
		}
		//old
		//ValidationResult.create(errorMessages, totalCount, insertRow, updateRow);
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put(IImportService.VersionKey, IImportService.Version_v1);
		if(errorMessages.size() > 0) {
			resultMap.put(IImportService.ResultKey, "0");//错误
		}else {
			resultMap.put(IImportService.ResultKey, "1");//成功
		}
		resultMap.put(IImportService.ResultFileIdKey, resultFileId);
		resultMap.put(IImportService.TotalSizeKey, totalCount+"");
		resultMap.put(IImportService.SuccessInsertRowsSizeKey, insertRow+"");
		resultMap.put(IImportService.SuccessUpdateRowsSizeKey, updateRow+"");
		resultMap.put(IImportService.TotalUnAcceptRowSizeKey, errorMessages.size()+"");
		return resultMap;
	}
	
	private int getImportTs(Map<String, List<String>> data) {
		if (null == data || data.isEmpty()) {
			return 0;
		}
		for (Iterator<String> it = data.keySet().iterator(); it.hasNext();) {
			return data.get(it.next()).size() - 1;
		}
		return 0;
	}
	
	/**
	 * 判断导入的验证模式
	 * @param drpzModel
	 * @return
	 */
	private boolean firstErrorReturn(DrpzModel drpzModel) {
		if (StringUtils.isBlank(drpzModel.getDryz())) {
			String mode = StringUtils.trimToNull(this.validateMode);
			return mode== null || "0".equals(mode);
		}
		return "0".equals(drpzModel.getDryz());
	}

	@Override
	public List<DrlpzModel> getRulers(DrpzModel drpzModel) {
		List<DrlpzModel> drpzList = null;
		drpzList = dao.getLPzxx(drpzModel.getDrmkdm());
		for (DrlpzModel dm : drpzList) {
			dm = fomarterDrlpz(dm);
		}
		return drpzList;
	}

	@Override
	public List<DrlpzModel> getRulers(List<String> drlidList, DrpzModel drpzModel) {
		if (null == drlidList) {
			return getRulers(drpzModel);
		}
		List<DrlpzModel> list = new ArrayList<DrlpzModel>();
		for (String drlid : drlidList) {
			list.add(fomarterDrlpz(dao.getDrlpzModel(drlid)));
		}
		return list;
	}
	
	/**
	 * 格式化导入列配置model
	 * 设置对应验证规则对照信息和验证规则
	 * @param dm
	 * @return
	 */
	private DrlpzModel fomarterDrlpz(DrlpzModel dm) {
		// 获取对应验证规则对照信息
		List<LyzgzdzModel> lyzgzdzList = dao.getLyzgzdz(dm);
		dm.setLyzgzdzList(lyzgzdzList);
		// 获取对应验证规则信息
		for (LyzgzdzModel lyzgzdzModel : lyzgzdzList) {
			lyzgzdzModel.setYzgzModel(dao.getLPzRule(lyzgzdzModel.getYzgzid()));
		}
		return dm;
	}

	@Override
	public File getTemplateFile(String drmkdm) {
		
		List<DrpzModel> list = this.dao.getDrPzxx(drmkdm);
		
		DrpzModel drpzModel = list.get(0);
		List<DrFzModel> drfzModelList = dao.getDrFzModelList(drmkdm);
		List<DrlpzModel> drlpzModelList = dao.getLPzxx(drmkdm);
		// 获取列名称
		Map<String, List<Map<String, String>>> fzData = new HashMap<String, List<Map<String, String>>>();
		Map<String, List<String>> fzDropDownList = new HashMap<String, List<String>>();
		
		// 辅助信息
		for (DrFzModel drfzModel : drfzModelList) {
			String drl = drfzModel.getDrl();
			// 名称
			String fzmc = drfzModel.getFzmc();
			// 数据抓取配置
			String pz = drfzModel.getPz();
			String[] array = StringUtils.split(pz, ":", 2);
			// 方式
			String type = array[0];
			// 目标
			String source = array[1];
			
			if (StringUtils.isBlank(drl)) {
				DrfzQuery fzQuery = new DrfzQuery(type, source);
				fzData.put(fzmc, fzQuery.queryFzData());
			} else {
				DrfzQuery fzQuery = new DrfzQuery(type, source);
				fzDropDownList.put(drl, fzQuery.queryFzDrlData());
			}
		}

		for (DrlpzModel model : drlpzModelList) {
			String constraintMessage = getComments(drpzModel,model);
			model.setConstraintMessage(constraintMessage);
			
			List<String> dropdownValues = fzDropDownList.get(model.getDrl());
			model.setDropdownValues(dropdownValues);
		}

		// 获取模板路径
		File resultExcelFile =  this.tempFileService.createTempFile(Constants.MODULAR_WORK_DIR_NAME, "xls");
		DrExcelUtils.renderTemplate(resultExcelFile,drpzModel,drlpzModelList, fzData);
		return resultExcelFile;
	}
	/**
	 * 给字段标题添加提示信息
	 * @param dm
	 * @return
	 */
	private String getComments(DrpzModel drpzModel,DrlpzModel dm) {
		String sfzj = dm.getSfzj();
		String zdcd = dm.getZdcd();
		//FIXME 不得已用这个AtomicInteger，方便递增
		final AtomicInteger index = new AtomicInteger(1);
		final StringBuffer comments = new StringBuffer();
		if (ImportConfig._SFZJ_YES.equals(sfzj)) {
			comments.append(index.getAndIncrement()).append(". ").append("不能重复; \n");
		}
		final String crfs = drpzModel.getCrfs();
		int sfbtColumnFlag = dm.getSfbtFlag();
		final String notNullComment = ". 不可为空; \n";
		// 验证当前字段是否必填
		SfbtColumnSwitchProcessor switchProcessor = new SfbtColumnSwitchProcessor() {
			
			private boolean appended = false; 
			@Override
			protected void ifBtOnInsert() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
			@Override
			protected void ifBtOnUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
			@Override
			protected void ifBtOnInsertAndUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);					
					appended = true;
				}
			}
		};
		switchProcessor.process(crfs, sfbtColumnFlag);
		
		if (StringUtils.isNotBlank(zdcd)) {
			comments.append(index.getAndIncrement()).append(". ").append("最大长度为" + zdcd + ";");
		}
		return comments.toString();
	}

	@Override
	public List<DrlpzModel> getImportColumn(DrpzModel drpzModel) {
		return dao.getLPzxx(drpzModel.getDrmkdm());
	}
	/**
	 * 生成错误模板
	 * @param model
	 * @param user
	 */
	protected String createErrorExcel(DrpzModel model,Map<String, List<String>> data, ImportErrorMessage importMap, String yhm) {
		File errorFile = this.tempFileService.createTempFile(Constants.MODULAR_WORK_DIR_NAME, "xls");
		String errorFileId = FilenameUtils.getBaseName(errorFile.getName());
		ExcelUtils.createErrorExcel(data, errorFile, importMap);
		return errorFileId;
	}

	@Override
	public List<DrpzModel> getDrPzxx(String gnmk) {
		return dao.getDrPzxx(gnmk);
	}

	@Override
	public CrpzModel getCrpz(String drmkdm) {
		return dao.getCrpz(drmkdm);
	}
}
