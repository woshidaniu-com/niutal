/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

import java.util.List;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;

public interface IArrayInsert {
	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	public int batchInsertData(String[][] data, DrpzModel drpzModel,List<DrlpzModel> drlpzList);

	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	public int batchUpdate(String[][] data, DrpzModel drpzModel,List<DrlpzModel> drlpzList);

	/**
	 * 批量增加并更新
	 * @param data
	 * @param drpzModel
	 * @param drlpzList
	 * @return
	 */
	public Integer[] batchAddOrUpdate(String[][] data, DrpzModel drpzModel,List<DrlpzModel> drlpzList);
}
