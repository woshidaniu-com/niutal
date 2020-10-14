/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

import java.util.List;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

public interface IInsertDb {
	
	public static final int BATCH_NUMBER = 100;
	
	/**
	 * 批量插入业务数据
	 * @param hashMap
	 * @return
	 */
	public int batchInsertData(ValidatorModel vm, DrpzModel drpzModel, List<DrlpzModel> drlpzList);

	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	public int batchUpdateData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList);

	/**
	 * 批量增加并更新
	 * @param data
	 * @param drpzModel
	 * @param drlpzList
	 * @return
	 */
	public Integer[] batchAddOrUpdate(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drlpzList);
}
