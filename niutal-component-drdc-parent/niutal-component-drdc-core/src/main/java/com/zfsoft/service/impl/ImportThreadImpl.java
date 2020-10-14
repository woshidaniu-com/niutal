package com.woshidaniu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.common.excel.ExcelUtils;
import com.woshidaniu.common.excel.extend.IImportExtend;
import com.woshidaniu.common.excel.extend.imp.ImportExtendImp;
import com.woshidaniu.dao.entities.ImportModel;
import com.woshidaniu.export.service.utils.dbf.DBFReader;
/**
 * 
 * @系统名称: 
 * @模块名称: 
 * @类功能描述: 
 * @作者： zcl  982
 * @时间： 2014-6-26 下午01:45:52 
 * @版本： V1.0
 * @修改记录:
 */
public class ImportThreadImpl extends ImportServiceImpl implements Runnable {
	//扩展模式标志
	public final String  _IMPORT_ISEXTEND_IMPOT="1";
	volatile List<String[]> dataList = null;
	// 获取验证字段列表
	List<ImportModel> validatorColumnList;
	// 获取字段绑定的验证
	List<ImportModel> validatorList;
	ImportModel model;
	// 获取实际导入表的具体列
	List<ImportModel> importColumnList;
	private Map<String, Object> vReslut;
	private int rowStart = 0;;
	private int rowEnd = 0;
	/**
	 * 多少行分一个线程
	 */
	private int threadRows = 100;
	private int nowThread;
	public ImportThreadImpl() {
	}

	public ImportThreadImpl(List<String[]> dataList,
			List<ImportModel> validatorColumnList,
			List<ImportModel> validatorList, ImportModel model,
			List<ImportModel> importColumnList,int nowThread, int rowStart, int rowEnd) {
		this.nowThread=nowThread;
		this.validatorColumnList = validatorColumnList;
		this.validatorList = validatorList;
		this.model = model;
		this.importColumnList = importColumnList;
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
		if (rowEnd >= dataList.size()) {
			rowEnd = dataList.size();
		}
		this.dataList = dataList.subList(rowStart, rowEnd);
		log.debug("----第["+nowThread+"]新线程开启，执行范围从[" + rowStart + "]到[" + rowEnd + "]----");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			log.debug("----第["+nowThread+"]新线程执行----");
			// 格式化导入数据源 待优化
			dataList = formatImportData(dataList, validatorColumnList);

			// 验证结果前保留验证数据至全局
			initVDataSource(dataList, validatorList);

			// 验证结果
			vReslut = validateData(model, dataList,
					validatorList, importColumnList);

			// 统计验证结果
			ImportModel importModel = new ImportModel();
			importModel.setDrzs(String.valueOf(dataList.size()));
			//扩展规则验证
			ImportExtendImp iei=new ImportExtendImp();
			iei.setDao(dao);
			IImportExtend iie=iei;
			vReslut=iie.fomartForExtend(model, vReslut);
			// 插入正确的数据
			List<List<ImportModel>> succeedList = (List<List<ImportModel>>) vReslut
					.get("succeedList");
			////
			if (succeedList != null && succeedList.size() > 0) {
				// 成功数
				importModel.setCgs(String.valueOf(succeedList.size()));

				if (drms != null && drms.equals("1")) {
					updateImportData(succeedList, model.getDrbm(),
							validatorList);
				} else {
					insertImportData(succeedList, model.getDrbm(),
							importColumnList);
				}
			} else {
				// 成功数
				importModel.setCgs("0");
			}

			List<String[]> errorList = (List<String[]>) vReslut
					.get("errorList");
			if (errorList != null && errorList.size() > 0) {
				// 错误数
				importModel.setCws(String.valueOf(errorList.size()));
			} else {
				// 错误数
				importModel.setCws("0");
			}

			// 加入导入结果
			vReslut.put("importModel", importModel);
			//hbReslut(vReslut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 合并线程处理结果
	 * 
	 * @param vReslu
	 *            当前线程处理的结果
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> hbReslut(Map<String, Object> vReslu,Map<String, Object> basevReslu) {
		ImportModel im = (ImportModel) vReslu.get("importModel");
		if (basevReslu == null) {
			basevReslu = vReslu;
			//vReslut.putAll(vReslu);
		} else {
			// 获取当前线程的成功数、错误数，计算累加。
			ImportModel importModel = (ImportModel) basevReslu
					.get("importModel");
			synchronized (importModel) {
				// 成功数
				Integer cgs = Integer.parseInt(im.getCgs())
						+ Integer.parseInt(importModel.getCgs());
				importModel.setCgs(cgs.toString());
				// 错误数
				Integer cws = Integer.parseInt(im.getCws())
						+ Integer.parseInt(importModel.getCws());
				importModel.setCws(cws.toString());
				// 总条数
				Integer drzs = Integer.parseInt(im.getDrzs())
						+ Integer.parseInt(importModel.getDrzs());
				importModel.setDrzs(drzs.toString());
				basevReslu.put("importModel", importModel);

				// 合并线程 成功数据和失败数据
				// 验证通过数据列表
				List<List<ImportModel>> succeedList = (List<List<ImportModel>>) vReslu
						.get("succeedList");
				// 验证不通过数据列表
				List<String[]> errorList = (List<String[]>) vReslu
						.get("errorList");

				List<List<ImportModel>> baseSucceedList = (List<List<ImportModel>>) basevReslu
						.get("succeedList");
				List<String[]> baseErrorList = (List<String[]>) basevReslu
						.get("errorList");
				baseSucceedList.addAll(succeedList);
				baseErrorList.addAll(errorList);
				basevReslu.put("succeedList", baseSucceedList);
				basevReslu.put("errorList", baseErrorList);
			}
			/*
			 * importModel.setCgs(String.valueOf(baseSucceedList.size()));
			 * importModel.setCws(String.valueOf(baseErrorList.size()));
			 * importModel
			 * .setDrzs(String.valueOf(baseSucceedList.size()+baseErrorList
			 * .size())); basevReslu.put("importModel",
			 * importModel);
			 */
		}
		return basevReslu;
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
		importModel.setIsThreadImport(_IMPORT_ISEXTEND_IMPOT);
		return dao.getValidateColumnListByDrmkdmAndDrbm(importModel);
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
		importModel.setIsThreadImport(_IMPORT_ISEXTEND_IMPOT);
		return uniteValidateInfo(dao
				.getValidateColumnListByDrmkdmAndDrbm(importModel), dao
				.getColumnValidateListByDrmkdmAndDrbm(importModel));
	}
	/**
	 * 导入数据
	 * 
	 * @param model
	 * @return
	 */
	public Map<String, Object> importData(ImportModel model) throws Exception {
		long nowTime=System.currentTimeMillis();
		if (model == null || model.getImportFile() == null
				|| StringUtils.isEmpty(model.getDrmkdm())
				|| StringUtils.isEmpty(model.getDrbm())) {
			return null;
		}
		drms = model.getDrms();
		// 生成新的验证参数控制
		createValidator();
		File file = null;
		if (drms != null && drms.equals("1")) {
			file = model.getUpdateFile();
		} else {
			file = model.getImportFile();
		}

		// 获取导入数据
		List<String[]> dataList = null;
		String drwjgs = model.getDrwjgs();
		if (drwjgs != null && drwjgs.equals("dbf")) {// dbf文件读入
			DBFReader dbfreader = new DBFReader(new FileInputStream(file));
			dataList = dbfreader.getDataList();
		} else {
			dataList = ExcelUtils.getDataList(file);
		}
		if (dataList == null || dataList.size() <= 1) {
			pb.autoFinish();
			return null;
		}
		autoSetProgress(dataList.size());
		// 获取验证字段列表
		List<ImportModel> validatorColumnList = getValidateColumnList(model
				.getDrmkdm(), model.getDrbm());

		// 获取字段绑定的验证
		List<ImportModel> validatorList = getColumnListUniteValidate(model
				.getDrmkdm(), model.getDrbm());

		// 获取实际导入表的具体列
		List<ImportModel> importColumnList = getImportColumnList(model
				.getDrmkdm(), model.getDrbm());
		// 删除列头
		dataList.remove(0);
		autoSetThreadRow(dataList.size());
		int cs = dataList.size() / threadRows
				+ (dataList.size() % threadRows > 0 ? 1 : 0);
		Thread importT[] = new Thread[cs];
		ImportThreadImpl itis[]=new ImportThreadImpl[cs];
		ImportThreadImpl iti;
		for (int i = 0; i < cs; i++) {
			setThreadStart();
			iti = new ImportThreadImpl(dataList,
					validatorColumnList, validatorList, model,
					importColumnList,i,this.getRowStart(), this.getRowEnd());
			iti.setValidatorParame(validatorParame);
			iti.setvDataSource(vDataSource);
			iti.setUniteValidatorList(uniteValidatorList);
			iti.setUniteValidatorValue(uniteValidatorValue);
			iti.setDrms(drms);
			iti.setDao(dao);
			//保存之线程对象
			itis[i]=iti;
			Thread importThread = new Thread(iti);
			importThread.setName("import-thread-"+i);
			importT[i] = importThread;
			importThread.start();
		}
		// 设置主线程等待子线程结束
		for (Thread t : importT) {
			t.join();
		}
		//计算线程执行结果
		for(ImportThreadImpl importTT:itis){
			this.vReslut=hbReslut(importTT.getvReslutt(), this.vReslut);
		}
		long endTime=System.currentTimeMillis();
		log.info("############################导入执行完成#############################");
		log.info("      共执行了["+cs+"]个线程，["+dataList.size()+"]条数据");
		log.info("      共花费时间:"+(endTime-nowTime)/1000+"秒");
		log.info("####################################################################");
		return vReslut;
		
	}
	/**
	 * 自动设置进度条更新速度
	 * @param size
	 */
	private void autoSetProgress(int size){
		Double alltime= new Integer(size).doubleValue()/new Integer(100).doubleValue();
		Double pg=1000*alltime/100;
		//最慢不能超过3秒更新一次进度
		if(pg>3000){
			pg=3000d;
		}
		pb.setProgress(pg.longValue());
	}
	/**
	 * 自动根据 导入总数据，设置分线程行数
	 * @param allRow
	 */
	private void autoSetThreadRow(int allRow){
		//如果大于5000条，则每1000条一个线程
		if(allRow>=5000){
			threadRows = 1000;
		}
	}
	/**
	 * 设置线程执行数据源开始信息
	 */
	private void setThreadStart() {
		if (this.getRowEnd() == 0) {
			this.setRowEnd(this.getThreadRows());
		} else {
			this.setRowStart(this.getRowEnd());
			this.setRowEnd(this.getRowStart() + this.getThreadRows());
		}
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
		// 去除多余列空白
		List<String[]> impDataList = new ArrayList<String[]>();
		String[] data = null;
		String[] dtjlStrs = null;
		for (int i = 0; i < dataList.size(); i++) {
			dtjlStrs = dataList.get(i);
			data = new String[columnList.size()];
			for (int j = 0; j < columnList.size(); j++) {
				if (dtjlStrs.length > j) {
					data[j] = dtjlStrs[j];
				}
			}
			impDataList.add(data);
		}
		return impDataList;
	}
	/**
	 * 初始化验证数据源 <li>现只用于唯一验证</li>
	 */
	protected void initVDataSource(List<String[]> dataSource,
			List<ImportModel> importModelList) {
		synchronized (dataSource) {
			if (dataSource == null || importModelList == null) {
				return;
			}
			if (this.vDataSource == null) {
				this.vDataSource = new HashMap<String, Object>();
			}
			String[] columnValue = null;
			// 将横向的数据源列表 转成 纵项列表保存
			AtomicInteger i = new AtomicInteger(0);
			for ( ;i.get()< importModelList.size(); i.getAndIncrement()) {
				ImportModel im=getUniteValidatorImportModel(importModelList.get(i.get()));
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
				AtomicInteger j = new AtomicInteger(0);
				for (; j.get() < dataSource.size();j.getAndIncrement()) {
					if (columnValue[j.get()] == null || "".equals(columnValue[j.get()])) {
						columnValue[j.get()] = dataSource.get(j.get())[i.get()];
					} else {
						columnValue[j.get()] = columnValue[j.get()] + dataSource.get(j.get())[i.get()];
					}
				}
				// 使用验证参数做key 只用于唯一验证
				this.vDataSource.put(im.getYzcs(), columnValue);
			}
		}
	}
	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public int getThreadRows() {
		return threadRows;
	}

	public void setThreadRows(int threadRows) {
		this.threadRows = threadRows;
	}

	public Map<String, Object> getvReslutt() {
		return vReslut;
	}

	public void setvReslutt(Map<String, Object> vReslutt) {
		this.vReslut = vReslutt;
	}
}
