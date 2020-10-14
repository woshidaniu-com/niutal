/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * excel重复数据验证
 */
/**public**/ final class PrimaryKeyDuplicateExcelValidatorHandler extends AbstractHandler{
	
	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyDuplicateExcelValidatorHandler.class);
	
	private String errorTemplate = "Excel中的主键列(%s)有重复值(%s)";
	
	private String traceLogTemplate = "第%s行[id:%s]没有主键";

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		List<ExcelCell> primaryKeys = currentRow.getPrimaryKeys();
		if(primaryKeys.isEmpty()) {
			if(log.isTraceEnabled()) {
				String logMsg = String.format(traceLogTemplate, currentRow.getIndex(),currentRow.getId());
				log.trace(logMsg);				
			}
		}else {
			long currentRowId = currentRow.getId();
			//当前行主键
			PrimaryKey currentRowPrimaryKey = new PrimaryKey(primaryKeys);
			
			boolean hasError = false;
			String error = "";
			
			for(ExcelRow r : allRows) {
				long rowId = r.getId();
				if(currentRowId != rowId) {
					
					//目标行主键
					PrimaryKey pk = new PrimaryKey(r.getPrimaryKeys());
					
					//主键相同，不允许
					if(currentRowPrimaryKey.compareTo(pk) == 0) {
						
						String reasonKey = currentRowPrimaryKey.getReasonKey();
						String reasonValue = currentRowPrimaryKey.getReasonValue();
						
						hasError = true;
						error = String.format(errorTemplate, reasonKey,reasonValue);
						
						break;

					}
				}
			}
			
			if(hasError) {
				for(ExcelCell cell : primaryKeys) {
					if(log.isTraceEnabled()) {
						log.trace("Excel行[rowId={},index={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);						
					}
					cell.setError(error);
				}
			}
		}
		return true;
	}
	
	//抽象primayKey对象的验证
	private class PrimaryKey implements Comparable<PrimaryKey>{

		private Map<String,String> keys = new HashMap<String,String>(2);
		
		private String reasonKey = "";
		private String reasonValue = "";
		
		public PrimaryKey(List<ExcelCell> primaryKeys) {
			
			for(ExcelCell cell : primaryKeys) {
				String headerName = cell.getExcelHeaderName();
				String cellValue = cell.getExcelCellValue();
				keys.put(headerName, cellValue);
			}
		}
		
		public String getReasonKey() {
			return this.reasonKey;
		}
		
		public String getReasonValue() {
			return this.reasonValue;		
		}
		
		@Override
		public int compareTo(PrimaryKey o) {
			
			Map<String,String> otherKeys = o.keys;
			
			int size = this.keys.size();
			int equalCount = 0;
			
			Iterator<Entry<String, String>> it = this.keys.entrySet().iterator();
			while(it.hasNext()) {
				
				Entry<String, String> e = it.next();
				String k1 = e.getKey();
				String v1 = e.getValue();
				
				String v2 = otherKeys.get(k1);
				if(v1.equals(v2)) {
					if(StringUtils.isNotEmpty(this.reasonKey)) {
						this.reasonKey = this.reasonKey + "," + k1;						
					}else {
						this.reasonKey = k1;
					}
					if(StringUtils.isNotEmpty(this.reasonValue)) {
						this.reasonValue = this.reasonValue + "," + v1;						
					}else {
						this.reasonValue = v1;
					}
					equalCount ++;
				}
			}
			
			return size == equalCount ? 0 : -1;
		}
	}

	public String getErrorTemplate() {
		return errorTemplate;
	}

	public void setErrorTemplate(String errorTemplate) {
		this.errorTemplate = errorTemplate;
	}

	public String getTraceLogTemplate() {
		return traceLogTemplate;
	}

	public void setTraceLogTemplate(String traceLogTemplate) {
		this.traceLogTemplate = traceLogTemplate;
	}
}
