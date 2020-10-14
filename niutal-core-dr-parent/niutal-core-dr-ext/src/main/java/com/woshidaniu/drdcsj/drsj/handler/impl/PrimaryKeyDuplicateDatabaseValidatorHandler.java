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

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.sqlBuilder.SelectCountSqlBuilder;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

/**
 * @author 康康（1571）
 * 主键验证,数据库内的所有数据的验证
 */
/**public**/ final class PrimaryKeyDuplicateDatabaseValidatorHandler extends AbstractHandler{
	
	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyDuplicateDatabaseValidatorHandler.class);
	
	public PrimaryKeyDuplicateDatabaseValidatorHandler() {
		super();
	}

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {

		//插入方式
		final String crfs = context.getCrfs();
		//导入表名称
		final String drbmc = context.getDrbmc();
		
		//主键列
		List<ExcelCell> cells = currentRow.getPrimaryKeys();
		//无主键列
		if(cells.isEmpty()) {
			currentRow.setInsert(false);
			currentRow.setUpdate(false);
			if(log.isTraceEnabled()) {
				log.trace("Excel行[rowId={},index={}]无主键列",currentRow.getId(),currentRow.getIndex());				
			}
			return true;
		}
		Map<String/*主键列名称*/,String/*主键列的值*/> keys = new HashMap<String,String>();
		for(ExcelCell cell : cells) {
			String cellValue = cell.getExcelCellValue();
			DrlpzModel drlpz = cell.getDrlpzModel();
			String column = drlpz.getDrl();
			keys.put(column, cellValue);
		}
		
		if(ImportConfig._CRFS_INSERT.equals(crfs)) {//如果是插入
			currentRow.setInsert(true);
			//主键在数据库中必须不存在
			int cnt  = this.validateFromDb(context,drbmc,keys);
			if(cnt > 0) {//数据库中存在
				//数据库中已经存在(学号[xx],姓名[yy])为主键的重复数据
				renderError("数据库中已经存在(",cells,")为主键的重复数据",currentRow);
			}else {
				if(log.isTraceEnabled()) {
					log.trace("用户选择插入操作,Excel数据行[id={},index={}]在数据库不存在,需要插入操作",currentRow.getId(),currentRow.getIndex());					
				}
			}
			return true;
		}
		
		if(ImportConfig._CRFS_UPDATE.equals(crfs)) {//如果是更新
			currentRow.setUpdate(true);
			//主键在数据库中必须已经存在
			int cnt  = this.validateFromDb(context,drbmc,keys);
			if(cnt <= 0) {//数据库中不存在
				//数据库中不存在(学号[xx],姓名[yy])为主键的一行数据,无法更新
				renderError("数据库中不存在(",cells,")为主键的数据一行数据,无法更新",currentRow);
			}else if(cnt > 1) {
				//意外情况,数据库竟然存在2个以上具有相同主键的数据!!!
				if(log.isTraceEnabled()) {
					log.trace("数据库存在重复主键数据!!!");					
				}
			}else {//cnt == 1
				if(log.isTraceEnabled()) {
					log.trace("用户选择更新操作,Excel数据行[id={},index={}]在数据库存在,需要插入操作",currentRow.getId(),currentRow.getIndex());					
				}
			}
			return true;
		}
		
		if(ImportConfig._CRFS_INSERT_UPDATE.equals(crfs)) {//如果是插入和更新
			//主键不能为空
			int cnt  = this.validateFromDb(context,drbmc,keys);
			if(cnt <= 0) {//数据库中不存在
				if(log.isTraceEnabled()) {
					log.trace("用户选择插入或更新操作,Excel数据行[id={},index={}]在数据库不存在,需要插入操作",currentRow.getId(),currentRow.getIndex());					
				}
				currentRow.setInsert(true);
				currentRow.setUpdate(false);
			}else if(cnt == 1){//数据库中已经存在
				if(log.isTraceEnabled()) {
					log.trace("用户选择插入或更新操作,Excel数据行[id={},index={}]在数据库已存在,需要更新操作",currentRow.getId(),currentRow.getIndex());					
				}
				currentRow.setInsert(false);
				currentRow.setUpdate(true);
			}else {
				//意外情况,数据库竟然存在2个以上具有相同主键的数据!!!
				if(log.isTraceEnabled()) {
					log.trace("数据库存在重复主键数据!!!");					
				}
			}
			return true;
		}
		
		log.error("插入方式未定义[{}]",crfs);
		return false;
	}

	/**
	 * @description	： 渲染错误
	 * @param cells
	 */
	private void renderError(String tipsPrefix,List<ExcelCell> cells,String tipsSuffix,ExcelRow currentRow) {
		StringBuilder sb = new StringBuilder(tipsPrefix);
		boolean first = true;
		for(ExcelCell cell : cells) {
			String headerName = cell.getExcelHeaderName();
			String cellValue = cell.getExcelCellValue();
			if(!first) {
				sb.append(",");
			}
			sb.append(headerName).append("[").append(cellValue).append("]");
			first = false;
		}
		sb.append(tipsSuffix);
		
		String error = sb.toString();
		//设置错误
		for(ExcelCell cell : cells) {
			
			log.warn("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);
			cell.setError(error);
		}
	}

	private int validateFromDb(final HandlerContext context,final String drbmc,final Map<String,String> keys) {
		
		SimpleQueryDao sqd =  context.getSqlSession().getMapper(SimpleQueryDao.class);
		
		SelectCountSqlBuilder selectCountSqlBuilder = new SelectCountSqlBuilder();
		selectCountSqlBuilder.selectCount(drbmc);
		
		Iterator<Entry<String, String>>  it = keys.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> e = it.next();
			String column = e.getKey();
			String columnValue = e.getValue();
			
			selectCountSqlBuilder.where(column, columnValue);
		}
		String querySql = MybatisSqlBuildUtils.buildQueryParamSql(selectCountSqlBuilder.getSql());
		int cnt = sqd.querySingleIntegerByParamsSql(querySql,selectCountSqlBuilder.getParams());
		return cnt;
	}
}
