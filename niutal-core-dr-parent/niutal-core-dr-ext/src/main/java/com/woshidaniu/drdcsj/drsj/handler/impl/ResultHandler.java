/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.common.file.TempFileService;
import com.woshidaniu.drdcsj.drsj.comm.Constants;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 结果handler
 * 如果错误，创建错误汇总的excel文件
 * 如果正确，则不需要错误汇总
 */
public final class ResultHandler implements Handler{
	
	private static final Logger log = LoggerFactory.getLogger(ResultHandler.class);
	
	//处理总数(从Excel中读出的条数)
	private int totalSize = 0;
	//接受的总条数 = 插入成功的条数 + 更新成功的条数
	private int totalAcceptRowsSize = 0;
	//插入成功的条数
	private int successInsertRowsSize = 0;
	//更新成功的条数
	private int successUpdateRowsSize = 0;
	//不接受的总条数(需要导出的条数) = 插入失败的条数 + 更新失败的条数 + 多种验证失败的条数
	private int totalUnAcceptRowSize = 0;
	
	private File resultFile;
	
	private String resultFileId;
	
	private TempFileService tempFileService;
	
	public ResultHandler(TempFileService tempFileService) {
		this.tempFileService = tempFileService;
		this.resultFile = this.tempFileService.createTempFile(Constants.MODULAR_WORK_DIR_NAME, "xls");
		this.resultFileId = FilenameUtils.getBaseName(this.resultFile.getName());
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> accpetRows,final List<ExcelRow> unacceptRows){
		
		List<ExcelRow> successInsertRows = (List<ExcelRow>)context.getAttr(SimpleFlushDatabaseHandler.key_successInsertRows);
		List<ExcelRow> failInsertRows =  (List<ExcelRow>)context.getAttr(SimpleFlushDatabaseHandler.key_failInsertRows);
		List<ExcelRow> successUpdateRows = (List<ExcelRow>)context.getAttr(SimpleFlushDatabaseHandler.key_successUpdateRows);;
		List<ExcelRow> failUpdateRows = (List<ExcelRow>)context.getAttr(SimpleFlushDatabaseHandler.key_failUpdateRows);
		
		this.totalSize = accpetRows.size() + unacceptRows.size();
		
		this.successInsertRowsSize = successInsertRows.size();
		this.successUpdateRowsSize = successUpdateRows.size();
		
		this.totalAcceptRowsSize = accpetRows.size();
		this.totalUnAcceptRowSize = unacceptRows.size();
		
		log.info("结果汇总:插入成功{}条数据,插入失败{}条数据,更新成功{}条数据,更新失败{}条数据",successInsertRows.size(),	failInsertRows.size(),successUpdateRows.size(),failUpdateRows.size());
		log.info("结果汇总:共处理{}条数据,其中接受{}条数据,未接受{}条数据",this.totalSize,this.totalAcceptRowsSize,this.totalUnAcceptRowSize);
		log.info("结果汇总:导出错误类数据{}条",this.totalUnAcceptRowSize);
		
		if(this.totalUnAcceptRowSize > 0) {
			//导出Excel
			try {
				//简单jxl结果Excel
				//SimpleJxlResultExcelCreator.createResultExcel(context,unacceptRows, this.resultFile);
				
				//带有下拉选择的复杂poi结果Excel
				SimplePoiResltExcelCreator spec = new SimplePoiResltExcelCreator();
				spec.createResultExcel(context, unacceptRows, this.resultFile);
				log.info("结果汇总:创建导入错误数据结果汇总xls文件[{}]",this.resultFile);
			} catch (Exception e) {
				log.error("创建错误数据汇总xls文件[{}]异常",this.resultFile,e);
			}
		}
	}
	
	public File getResultFile() {
		return this.resultFile;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getSuccessInsertRowsSize() {
		return successInsertRowsSize;
	}

	public void setSuccessInsertRowsSize(int successInsertRowsSize) {
		this.successInsertRowsSize = successInsertRowsSize;
	}

	public int getSuccessUpdateRowsSize() {
		return successUpdateRowsSize;
	}

	public void setSuccessUpdateRowsSize(int successUpdateRowsSize) {
		this.successUpdateRowsSize = successUpdateRowsSize;
	}

	public String getResultFileId() {
		return resultFileId;
	}

	public int getTotalAcceptRowsSize() {
		return totalAcceptRowsSize;
	}

	public int getTotalUnAcceptRowSize() {
		return totalUnAcceptRowSize;
	}
}
