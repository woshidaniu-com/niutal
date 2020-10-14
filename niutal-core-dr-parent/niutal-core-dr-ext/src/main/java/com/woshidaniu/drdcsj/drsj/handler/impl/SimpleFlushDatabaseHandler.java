/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.SimpleQueryDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.FlushDatabaseListener;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.sqlBuilder.InsertSqlBuilder;
import com.woshidaniu.drdcsj.drsj.sqlBuilder.UpdateSqlBuilder;
import com.woshidaniu.drdcsj.drsj.utils.MybatisSqlBuildUtils;

/**
 * @author 康康（1571）
 * 刷新数据库到数据库的handler,逐行插入或更新数据并commit,性能未必会比批量差,原因如下：
 * 1.excel内用户导入的数据并非海量数据,个人估测最多也就是5000,如果每次数据库操作耗时2ms,总耗时才10s,感觉用户应该可以接受.
 *   因为是递进的导入,所以花费随着导入的次数的增加,每次导入花费的时间将越来越少
 * 2.公司系统并非高并发系统
 * 3.逐行插入或更新更符合常规思维
 * 4.防止业务开发人员调用commit,rollback对框架代码的影响
 */
/**public**/ final class SimpleFlushDatabaseHandler extends FlushDatabaseHandler{
	
	//通过构造函数传递进来真正的sqlSession是为了防止业务开发人员在自己的插件里面commit或rollback
	private SqlSession sqlSession;
	private List<FlushDatabaseListener> listeners = Collections.emptyList();
	
	public SimpleFlushDatabaseHandler(SqlSession sqlSession,List<FlushDatabaseListener> listeners) {
		this.sqlSession = sqlSession;
		this.listeners = listeners;
	}

	@Override
	public boolean doAccept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows){
		final SimpleQueryDao simpleQueryDao = this.sqlSession.getMapper(SimpleQueryDao.class);
		if(
				(currentRow.isInsert()&&!currentRow.isUpdate())//是插入，但不是更新
				||
				(!currentRow.isInsert()&&currentRow.isUpdate())//是更新，但不是插入
				) {
			//逻辑正确,执行数据库操作
			boolean accept = this.handleRow(context,currentRow,simpleQueryDao);
			return accept;
		}else {
			//代码逻辑问题
			if(log.isTraceEnabled()) {
				log.trace("第{}行数据,插入和更新状态异常:[{}]",currentRow.getIndex(),currentRow);				
			}
			return false;
		}
	}

	private boolean handleRow(final HandlerContext context,final ExcelRow currentRow,final SimpleQueryDao simpleQueryDao) {
		
		boolean success = false;
		//是插入
		if(currentRow.isInsert()) {
			success = this.doInsert(context,currentRow,simpleQueryDao);
		}
		//是更新
		if(currentRow.isUpdate()) {
			success = this.doUpdate(context,currentRow,simpleQueryDao);
		}
		//最终提交
		if(success) {
			this.sqlSession.commit();
		}else {
			this.sqlSession.rollback();
		}
		return success;
	}

	private boolean doUpdate(HandlerContext context, ExcelRow currentRow, SimpleQueryDao simpleQueryDao) {
		String drbmc = context.getDrbmc();
		//执行监听器
		for(FlushDatabaseListener listener: listeners) {
			try {
				listener.beforeUpdate(context, currentRow);
			}catch (Exception e) {
				log.error("插件[{}]更新Excel行[rowId={},rowIndex={}]到数据库异常",listener,currentRow.getId(),currentRow.getIndex(),e);
				currentRow.getCells().get(0).setError("服务器内部错误,请重试");
				return false;
			}
		}
		//是否跳过原始的数据库刷新操作
		Boolean skipDatabaseFlush = (Boolean)context.getAttr(FlushDatabaseHandler.CONFIG_KEY_SIKP_ORIGINAL_UPDATE);
		try {

			//更新数据
			if(skipDatabaseFlush == null || !skipDatabaseFlush){
				this.updateToDatabase(drbmc,currentRow,simpleQueryDao);
			}
		}catch(Exception e) {
			log.error("更新Excel行[rowId={},rowIndex={}]到数据库异常",currentRow.getId(),currentRow.getIndex(),e);
			currentRow.getCells().get(0).setError("服务器内部错误,请重试");
			return false;
		}
		
		//执行监听器
		for(FlushDatabaseListener listener: listeners) {
			try {
				listener.afterUpdate(context, currentRow);
			}catch (Exception e) {
				log.error("插件[{}]更新Excel行[rowId={},rowIndex={}]到数据库异常",listener,currentRow.getId(),currentRow.getIndex(),e);
				currentRow.getCells().get(0).setError("服务器内部错误,请重试");
				return false;
			}
		}
		return true;
	}

	private boolean doInsert(final HandlerContext context,final ExcelRow currentRow,final SimpleQueryDao simpleQueryDao) {
		String drbmc = context.getDrbmc();
		//执行监听器
		for(FlushDatabaseListener listener: listeners) {
			try {
				listener.beforeInsert(context, currentRow);
			}catch (Exception e) {
				log.error("插件[{}]插入Excel行[rowId={},rowIndex={}]到数据库异常",listener,currentRow.getId(),currentRow.getIndex(),e);
				currentRow.getCells().get(0).setError("服务器内部错误,请重试");
				return false;
			}
		}
		//是否跳过原始的数据库刷新操作
		Boolean skipDatabaseFlush = (Boolean)context.getAttr(FlushDatabaseHandler.CONFIG_KEY_SIKP_ORIGINAL_INSERT);
		try {
			//插入数据
			if(skipDatabaseFlush == null || !skipDatabaseFlush){
				this.insertToDatabase(drbmc, currentRow, simpleQueryDao);
			}
		}catch (Exception e) {
			log.error("插入Excel行[rowId={},rowIndex={}]到数据库异常",currentRow.getId(),currentRow.getIndex(),e);
			currentRow.getCells().get(0).setError("服务器内部错误,请重试");
			return false;
		}
		
		//执行监听器
		for(FlushDatabaseListener listener: listeners) {
			try {
				listener.afterInsert(context, currentRow);
			}catch (Exception e) {
				log.error("插件[{}]插入Excel行[rowId={},rowIndex={}]到数据库异常",listener,currentRow.getId(),currentRow.getIndex(),e);
				currentRow.getCells().get(0).setError("服务器内部错误,请重试");
				return false;
			}
		}
		return true;
	}

	private void updateToDatabase(final String drbmc,final ExcelRow row,final SimpleQueryDao simpleQueryDao) {
		
		List<ExcelCell> primaryKeys = row.getPrimaryKeys();
		int notPrimaryKeySize = row.getCells().size() - primaryKeys.size();
		
		List<ExcelCell> notPrimaryKeyColumns = new ArrayList<ExcelCell>(notPrimaryKeySize);
		//非主键
		for(ExcelCell cell : row.getCells()) {
			if(cell.isIgnore()){
				continue;
			}
			DrlpzModel drlpz = cell.getDrlpzModel();
			String sfzj = drlpz.getSfzj();
			
			if(!ImportConfig._SFZJ_YES.equals(sfzj)) {
				notPrimaryKeyColumns.add(cell);
			}
		}

		UpdateSqlBuilder updateSqlBuilder = new UpdateSqlBuilder();
		updateSqlBuilder.update(drbmc);
		
		//set
		for(int i=0;i<notPrimaryKeyColumns.size();i++) {
			
			ExcelCell ec = notPrimaryKeyColumns.get(i);
			DrlpzModel drlpz = ec.getDrlpzModel();
			
			String column = drlpz.getDrl();
			String columnValue = ec.getExcelCellValue();
			
			updateSqlBuilder.set(column, columnValue);
		}
		
		//where条件
		for(int i=0;i<primaryKeys.size();i++) {
			ExcelCell ec = primaryKeys.get(i);
			DrlpzModel drlpz = ec.getDrlpzModel();
			
			String column = drlpz.getDrl();
			String columnValue = ec.getExcelCellValue();
			
			updateSqlBuilder.where(column, columnValue);
		}
		
		String querySql = MybatisSqlBuildUtils.buildQueryParamSql(updateSqlBuilder.getSql());
		simpleQueryDao.update(querySql, updateSqlBuilder.getParams());
	}

	private void insertToDatabase(final String drbmc,final ExcelRow row,final SimpleQueryDao simpleQueryDao) {

		InsertSqlBuilder insertSqlBuilder = new InsertSqlBuilder();
		insertSqlBuilder.insert(drbmc);
		
		for(ExcelCell cell : row.getCells()) {
			if(cell.isIgnore()){
				continue;
			}
			String column = cell.getDrlpzModel().getDrl();
			String columnValue = cell.getExcelCellValue();
			
			insertSqlBuilder.value(column, columnValue);
		}
		
		String querySql = MybatisSqlBuildUtils.buildQueryParamSql(insertSqlBuilder.getSql());
		simpleQueryDao.update(querySql, insertSqlBuilder.getParams());
	}
}
