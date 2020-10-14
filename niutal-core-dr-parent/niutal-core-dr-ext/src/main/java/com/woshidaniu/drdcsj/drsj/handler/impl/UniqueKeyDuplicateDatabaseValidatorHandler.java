/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.sqlBuilder.SelectCountSqlBuilder;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

/**
 * @author 康康（1571）
 * 验证数据库内唯一键数据
 */
/**public**/ final class UniqueKeyDuplicateDatabaseValidatorHandler extends AbstractHandler{

	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyDuplicateExcelValidatorHandler.class);
	
	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		//导入表名称
		final String drbmc = context.getDrbmc();
		//唯一键列
		List<ExcelCell> uniqueKeys = currentRow.getUniqueKeys();
		//无唯一键
		if(uniqueKeys.isEmpty()) {
			if(log.isTraceEnabled()) {
				log.trace("Excel行[rowId={},index={}]无唯一键列",currentRow.getId(),currentRow.getIndex());				
			}
			return true;
		}
		if(currentRow.isInsert()) {
			//唯一键在数据库中必须不存在
			for(ExcelCell cell : uniqueKeys) {
				SimpleQueryDao sqd =  context.getSqlSession().getMapper(SimpleQueryDao.class);
				
				SelectCountSqlBuilder selectCountSqlBuilder = new SelectCountSqlBuilder();
				selectCountSqlBuilder.selectCount(drbmc);
				
				String column = cell.getDrlpzModel().getDrl();
				String columnValue = cell.getExcelCellValue();
				selectCountSqlBuilder.where(column, columnValue);
				
				String querySql = MybatisSqlBuildUtils.buildQueryParamSql(selectCountSqlBuilder.getSql());
				int cnt = sqd.querySingleIntegerByParamsSql(querySql,selectCountSqlBuilder.getParams());
				if(cnt > 0) {//数据库中存在
					//设置错误
					String error = String.format("数据库中已经存在字段为%s为唯一值的重复数据[%s],无法再次插入", cell.getExcelHeaderName(),cell.getExcelOriginValue());
					log.warn("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);
					cell.setError(error);
				}
			}
		}else if(currentRow.isUpdate()) {
			List<ExcelCell> primaryKeys = currentRow.getPrimaryKeys();
			if(primaryKeys.isEmpty()) {
				//如果没有主键列,无法更新,交给后面的handler处理
				return true;
			}else {
				SimpleQueryDao sqd =  context.getSqlSession().getMapper(SimpleQueryDao.class);
				//如果有主键列,则查看是否有其他的数据库行数据中存在目标唯一键数据的
				//形如 select count(*) from t_user  where xh = ?  and name <> ?
				//这里xh是唯一键,name是主键,注意,这里的查询走得是唯一键的索引
				for(ExcelCell cell : uniqueKeys) {
					
					String drl = cell.getDrlpzModel().getDrl();
					String drlValue = cell.getExcelCellValue();
					
					SelectCountSqlBuilder selectCountSqlBuilder = new SelectCountSqlBuilder();
					selectCountSqlBuilder.selectCount(drbmc);
					selectCountSqlBuilder.where(drl, drlValue);
					
					for(ExcelCell pk : primaryKeys) {
						
						String pkColumn = pk.getDrlpzModel().getDrl();
						String pkValue = pk.getExcelCellValue();
						selectCountSqlBuilder.whereNot(pkColumn, pkValue);
					}
					String querySql = MybatisSqlBuildUtils.buildQueryParamSql(selectCountSqlBuilder.getSql());
					int cnt = sqd.querySingleIntegerByParamsSql(querySql,selectCountSqlBuilder.getParams());
					if(cnt > 0) {//数据库中存在主键是name <> 某值但 xh = 某值的行
						String error = String.format("数据库中已经存在字段为%s为唯一值的重复数据[%s],无法更新", cell.getExcelHeaderName(),cell.getExcelOriginValue());
						log.warn("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);
						cell.setError(error);
					}
				}
			}
		}else {
			//nothing to do
		}
		return true;
	}
}
