/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.PlainDatasourceResource;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.IArrayInsert;

/**
 * 利用数据库存储过程进行数据插入
 * @author xiaokang
 */
public class SqlInsert extends PlainDatasourceResource implements IArrayInsert {
	
	private static final Logger log = LoggerFactory.getLogger(SqlInsert.class);
	
	private SqlOperation sqlOperation;
	
	public SqlInsert() {
	}
	public SqlInsert(SqlOperation sqlOperation) {
		this.sqlOperation = sqlOperation;
	}

	@Override
	public Integer[] batchAddOrUpdate(String[][] data,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
		//throw new RuntimeException("暂不支持！");
		return new Integer[]{0,0};
	}

	@Override
	public int batchInsertData(String[][] data,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
 		int insertCount = 0;
		try {
			if(sqlOperation != null && sqlOperation.getInsert() != null || sqlOperation.getInsert().length > 0){
				initConnection();
				getConn().setAutoCommit(false);
				String[] insertSqlArr = sqlOperation.getInsert();
				for (String insertSql : insertSqlArr) {
					List<String> zdList = new ArrayList<String>();
					String realSql = getRealSqlAndZdList(insertSql, zdList);
					statement = getConn().prepareStatement(realSql);
					for (int j = 1; j < data.length; j++) {
						for (int i = 0; i < zdList.size(); i++) {
							String drlmc = getDrlmc(zdList.get(i), drlpzList);
							int zdIndex = ArrayUtils.indexOf(data[0], drlmc);
							statement.setString(i+1, data[j][zdIndex]);
						}
						statement.addBatch();
					}
					int[] executeBatch = statement.executeBatch();
					insertCount = data.length-1;
				}
				getConn().commit();
			}
		} catch (SQLException e) {
			log.error("",e);
			try {
				getConn().rollback();
			} catch (SQLException e1) {
				log.error("",e1);
			}
		} finally{
			close(conn, statement, null, sqlSession);
		}
		return insertCount;
	}

	@Override
	public int batchUpdate(String[][] data, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		int updateCount = 0;
		try {
			if(sqlOperation != null && sqlOperation.getUpdate() != null || sqlOperation.getUpdate().length > 0){
				initConnection();
				getConn().setAutoCommit(false);
				String[] updateSqlArr = sqlOperation.getUpdate();
				for (String updateSql : updateSqlArr) {
					List<String> zdList = new ArrayList<String>();
					String realSql = getRealSqlAndZdList(updateSql, zdList);
					statement = getConn().prepareStatement(realSql);
					for (int j = 1; j < data.length; j++) {
						for (int i = 0; i < zdList.size(); i++) {
							String drlmc = getDrlmc(zdList.get(i), drlpzList);
							int zdIndex = ArrayUtils.indexOf(data[0], drlmc);
							statement.setString(i+1, data[j][zdIndex]);
						}
						statement.addBatch();
					}
					int[] executeBatch = statement.executeBatch();
					updateCount = data.length-1;
				}
				getConn().commit();
			}
		} catch (SQLException e) {
			log.error("",e);
			try {
				getConn().rollback();
			} catch (SQLException e1) {
				log.error("",e1);
			}
		} finally{
			close(conn, statement, null, sqlSession);
		}
		return updateCount;
	}
	
	protected int getZdIndex(String[] zds , String zd){
		return ArrayUtils.indexOf(zds, zd);
	}
	
	/**
	 * 替换#字段#为？
	 * @param sql
	 * @return
	 */
	protected String getRealSqlAndZdList(String sql,List<String> zds){
		String realSql = sql;
		String[] substringsBetween = StringUtils.substringsBetween(sql, "#", "#");
		for (String string : substringsBetween) {
			realSql = StringUtils.replaceOnce(realSql, "#"+string+"#", "?");
			if(null != zds){
				zds.add(string);
			}
		}
		return realSql;
	}
	
	protected String getDrlmc(String drl, List<DrlpzModel> drlpzList){
		for (DrlpzModel drlpzModel : drlpzList) {
			if(StringUtils.equalsIgnoreCase(drlpzModel.getDrl(), drl)){
				return drlpzModel.getDrlmc();
			}
		}
		return null;
	}
	
	public SqlOperation getSqlOperation() {
		return sqlOperation;
	}
	
	public void setSqlOperation(SqlOperation sqlOperation) {
		this.sqlOperation = sqlOperation;
	}
}
