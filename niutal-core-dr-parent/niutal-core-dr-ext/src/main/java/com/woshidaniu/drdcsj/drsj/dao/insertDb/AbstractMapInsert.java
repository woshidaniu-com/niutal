/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author xiaokang[1036]
 * map insert
 */
public abstract class AbstractMapInsert implements IInsertDb{

	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	public int batchInsertData(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		HashMap<String, Object> insertData = convertForInsert(vm.getDataSource(), drpzModel, drpzList);
		return batchInsertData(insertData);
	}

	private int batchInsertData(HashMap<String, Object> insertData) {
		HashMap<String, Object> insertData2 = new HashMap<String, Object>();
		int column = 0;
		// 值列表
		List<String[]> dataList = (List<String[]>) insertData.get("dataList");
		for (int i = 0; i < dataList.size();) {
			insertData2.put("tableName", insertData.get("tableName"));
			insertData2.put("columnList", insertData.get("columnList"));
			i += BATCH_NUMBER;
			if (i >= dataList.size()) {
				insertData2.put("dataList", dataList.subList(i - BATCH_NUMBER,dataList.size()));
				column += doBatchInsertData(insertData2);
				break;
			}
			insertData2.put("dataList", dataList.subList(i - BATCH_NUMBER, i));
			column += doBatchInsertData(insertData2);
		}
		return column;
	}

	/**
	 * 必须返回执行条数
	 */
	protected abstract int doBatchInsertData(HashMap<String, Object> insertData);
	
	private HashMap<String, Object> convertForInsert(Map<String, List<String>> data,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		HashMap<String, Object> dbData = new HashMap<String, Object>();
		List<String[]> dataList = new ArrayList<String[]>();
		List<String> columnList = new ArrayList<String>();
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
		return dbData;
	}
	

	private String[] getDrlmc(List<DrlpzModel> drlpzList,String[] compositeIds2){
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

	private HashMap<String, Object> convertForUpdate(Map<String, List<String>> data,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
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
	public int batchUpdateData(ValidatorModel vm,  DrpzModel drpzModel,List<DrlpzModel> drpzList) {
		HashMap<String, Object> updateData = convertForUpdate(vm.getDataSource(), drpzModel, drpzList);
		return batchUpdate(updateData);
	}

	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	private int batchUpdate(HashMap<String, Object> updateData) {
		HashMap<String, Object> updateData2 = new HashMap<String, Object>();
		updateData2.put("tableName", updateData.get("tableName"));
		updateData2.put("columnList", updateData.get("columnList"));
		updateData2.put("pk", updateData.get("pk"));
		int column = 0;
		// 值列表
		List<String[]> dataList = (List<String[]>) updateData.get("dataList");
		List<String[]> pkvList = (List<String[]>) updateData.get("pkv");
		for (int i = 0; i < dataList.size();) {
			i += BATCH_NUMBER;
			if (i >= dataList.size()) {
				updateData2.put("dataList", dataList.subList(i - BATCH_NUMBER, dataList.size()));
				updateData2.put("pkv", pkvList.subList(i - BATCH_NUMBER, pkvList.size()));
				column += doBatchUpdate(updateData2);
				break;
			}
			updateData2.put("pkv", pkvList.subList(i - BATCH_NUMBER, i));
			updateData2.put("dataList", dataList.subList(i - BATCH_NUMBER, i));
			column += doBatchUpdate(updateData2);
			
		}
		return column;
	}

	/**
	 * 必须返回执行条数
	 */
	protected abstract int doBatchUpdate(HashMap<String, Object> updateData);
	
	/**
	 * 批量增加并更新
	 * @param data
	 * @param drpzModel
	 * @param drpzList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer[] batchAddOrUpdate(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drpzList) {
		HashMap<String, Object> dbDataInsert = convertForInsert(vm.getDataForInsert(), drpzModel, drpzList);
		HashMap<String, Object> dbDataUpdate = convertForUpdate(vm.getDataForUpdate(), drpzModel, drpzList);
		List<String[]> insertList = (List<String[]>) dbDataInsert.get("dataList");
		List<String[]> updateList = (List<String[]>) dbDataUpdate.get("dataList");
		int insert = 0;
		int update = 0;
		if (!insertList.isEmpty()) {
			insert = batchInsertData(dbDataInsert);
		}
		if (!updateList.isEmpty()) {
			update = batchUpdate(dbDataUpdate);
		}
		return new Integer[] { insert, update };
	}
}
