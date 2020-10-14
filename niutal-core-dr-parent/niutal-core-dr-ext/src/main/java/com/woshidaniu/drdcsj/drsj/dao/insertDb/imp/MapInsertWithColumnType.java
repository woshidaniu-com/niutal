/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb.imp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.drdcsj.drsj.comm.PlainDatasourceResource;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.ColumnDef;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.DBOperationConstant;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.IInsertDb;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author xiaokang
 * 根据数据库字段类型插入数据库(仅限插入使用，并且只支持oracle date数据格式，转为收费开发)
 */
@Component("mapInsertWithColumnType")
public class MapInsertWithColumnType  extends PlainDatasourceResource implements IInsertDb {
	
	private static final Logger log = LoggerFactory.getLogger(MapInsertWithColumnType.class);
	@Resource
	@Qualifier("drdcsjDao")
	private IImportDao dao;
	
	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	@Override
	public int batchInsertData(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		List<ColumnDef> columnDefList = dao.getColumnDefList(drpzModel.getDrbmc());	
		HashMap<String, Object> insertData = convertForInsert(vm.getDataSource(), drpzModel, drpzList);
		String tableName = (String) insertData.get("tableName");
		StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " ");
		List<String> columnList = (List<String>) insertData.get("columnList");
		sql.append("("+StringUtils.join(columnList, ",")+")");
		sql.append(" VALUES ");
		sql.append(" (" + StringUtils.repeat("?", ",", columnList.size()) + " )");
		return batchInsertData(insertData , sql.toString(), columnDefList);
	}

	protected int batchInsertData(HashMap<String, Object> insertData , String sql , List<ColumnDef> columnDefList) {
		List<String> columnList = (List<String>) insertData.get("columnList");
		int column = 0;
		// 值列表
		List<String[]> dataList = (List<String[]>) insertData.get("dataList");	
		initConnection();
		Connection connection = getConn();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			for (int i = 0; i < dataList.size(); i++) {
				String[] values = dataList.get(i);
				for (int j = 0; j < values.length; j++) {
					String columnName = columnList.get(j);
					String columnType = getColumnDef(columnName, columnDefList).getDataType();
					String value = values[j];
					//目前只支持到日期类型
					if(StringUtils.equalsIgnoreCase(DBOperationConstant.DBColumnType.VARCHAR2, columnType)){
						prepareStatement.setString(j+1, value);
					}else if(StringUtils.equalsIgnoreCase(DBOperationConstant.DBColumnType.DATE, columnType)){
						prepareStatement.setDate(j+1, new Date(DateUtils.parse(value).getTime()));
					}else{
						prepareStatement.setString(j+1, value);
					}
				}
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
			connection.commit();
			column = dataList.size();
		} catch (Exception e) {
			try {
				column = 0;
				connection.rollback();
			} catch (SQLException e1) {
				log.error("",e1);
			}
		}finally{
			close(connection, statement, null, sqlSession);
		}
		
		return column;
	}
	
	protected HashMap<String, Object> convertForInsert(Map<String, List<String>> data,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		HashMap<String, Object> dbData = new HashMap<String, Object>();
		List<String[]> dataList = new ArrayList<String[]>();
		List<String> columnList = new ArrayList<String>();
		for (int i = 0; i < drpzList.size(); i++) {
			columnList.add(drpzList.get(i).getDrl());
		}
		for (int i = 0; i < data.get(drpzList.get(0).getDrlmc()).size() - 1; i++) {
			List<String> row = new ArrayList<String>();
			for (int j = 0; j < drpzList.size(); j++) {
				String drlmc = drpzList.get(j).getDrlmc();
				row.add(data.get(drlmc).get(i + 1));
			}
			dataList.add(row.toArray(new String[] {}));
		}
		// 表名
		dbData.put("tableName", drpzModel.getDrbmc());
		// 字段列表
		dbData.put("columnList", columnList);
		// 值列表
		dbData.put("dataList", dataList);
		return dbData;
	}

	protected String[] getDrlmc(List<DrlpzModel> drlpzList,String[] compositeIds2){
		String[] drlmc = new String[compositeIds2.length];
		for (int i = 0; i < compositeIds2.length; i++) {
			for (DrlpzModel drlpzModel : drlpzList) {
				String drlmc2 = drlpzModel.getDrlmc();
				String drl = drlpzModel.getDrl();
				if(StringUtils.equals(drl, compositeIds2[i])){
					drlmc[i] = drlmc2;
					break;
				}
			}
		}
		return drlmc;
	}

	protected ColumnDef getColumnDef(String columnName , List<ColumnDef> ColumnDefList){
		for (ColumnDef columnDef : ColumnDefList) {
			if(StringUtils.equalsIgnoreCase(columnName, columnDef.getColumnName())){
				return columnDef;
			}
		}
		return null;
	}
	
	protected HashMap<String, Object> convertForUpdate(Map<String, List<String>> data,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		//主键字段
		String[] compositeIds = drpzModel.getCompositeIds();
		HashMap<String, Object> dbData = new HashMap<String, Object>();
		List<String[]> dataList = new ArrayList<String[]>();
		List<String> columnList = new ArrayList<String>();
		List<String> pk = new ArrayList<String>(Arrays.asList(compositeIds));
		// 记录主键列对应值 list
		String[] drlmc = getDrlmc(drpzList, compositeIds);
		List<String[]> pkv = new ArrayList<String[]>();
		for (int i = 1; i < data.get(drlmc[0]).size(); i++) {
			String[] item = new String[drlmc.length];
			for (int j = 0; j < drlmc.length; j++) {
				List<String> list = data.get(drlmc[j]);
				item[j] = list.get(i);
			}
			pkv.add(item);
		}
		for (int i = 0; i < drpzList.size(); i++) {
			columnList.add(drpzList.get(i).getDrl());
		}
		for (int i = 0; i < data.get(drpzList.get(0).getDrlmc()).size() - 1; i++) {
			List<String> row = new ArrayList<String>();
			for (int j = 0; j < drpzList.size(); j++) {
				row.add(data.get(drpzList.get(j).getDrlmc()).get(i + 1));
			}
			dataList.add(row.toArray(new String[] {}));
		}
		// 表名
		dbData.put("tableName", drpzModel.getDrbmc());
		// 字段列表
		dbData.put("columnList", columnList);
		// 值列表
		dbData.put("dataList", dataList);
		dbData.put("pk", pk);
		dbData.put("pkv", pkv);
		return dbData;
	}
	
	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	@Override
	public int batchUpdateData(ValidatorModel vm,  DrpzModel drpzModel,List<DrlpzModel> drpzList) {
		//HashMap<String, Object> updateData = convertForUpdate(vm.getDataSource(), drpzModel, drpzList);
		//List<ColumnDef> columnDefList = dao.getColumnDefList(drpzModel.getDrbmc());	
		//return batchUpdate(updateData,columnDefList);
		return 0;
	}

	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	protected int batchUpdate(HashMap<String, Object> updateData,List<ColumnDef> columnDefList) {
		HashMap<String, Object> updateData2 = new HashMap<String, Object>();
		updateData2.put("tableName", updateData.get("tableName"));
		updateData2.put("columnList", updateData.get("columnList"));
		updateData2.put("pk", updateData.get("pk"));
		int column = 0;
		// 值列表
		List<String[]> dataList = (List<String[]>) updateData.get("dataList");
		List<String[]> pkvList = (List<String[]>) updateData.get("pkv");
		for (int i = 0; i < dataList.size();) {
			i += DBOperationConstant.DEFAULT_BATCH_NUMBER;
			if (i >= dataList.size()) {
				updateData2.put("dataList", dataList.subList(i - DBOperationConstant.DEFAULT_BATCH_NUMBER, dataList.size()));
				updateData2.put("pkv", pkvList.subList(i - DBOperationConstant.DEFAULT_BATCH_NUMBER, pkvList.size()));
				column += dao.batchUpdate(updateData2);
				break;
			}
			updateData2.put("pkv", pkvList.subList(i - DBOperationConstant.DEFAULT_BATCH_NUMBER, i));
			updateData2.put("dataList", dataList.subList(i - DBOperationConstant.DEFAULT_BATCH_NUMBER, i));
			column += dao.batchUpdate(updateData2);
		}
		return column;
	}

	/**
	 * 批量增加并更新
	 * @param data
	 * @param drpzModel
	 * @param drpzList
	 * @return
	 */
	@Override
	public Integer[] batchAddOrUpdate(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		//List<ColumnDef> columnDefList = dao.getColumnDefList(drpzModel.getDrbmc());
		//HashMap<String, Object> dbDataInsert = convertForInsert(vm.getDataForInsert(), drpzModel, drpzList);
		//HashMap<String, Object> dbDataUpdate = convertForUpdate(vm.getDataForUpdate(), drpzModel, drpzList);
		//List<String[]> insertList = (List<String[]>) dbDataInsert.get("dataList");
		//List<String[]> updateList = (List<String[]>) dbDataUpdate.get("dataList");
		int insert = 0;
		int update = 0;
		//if (!insertList.isEmpty()) {
		//	insert = batchInsertData(dbDataInsert);
		//}
		//if (!updateList.isEmpty()) {
		//	update = batchUpdate(dbDataUpdate);
		//}
		return new Integer[] { insert, update };
	}
	
	static class DataWithType{
		private ColumnDef type;
		
		private String value;

		public ColumnDef getType() {
			return type;
		}

		public void setType(ColumnDef type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
