/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

import java.util.List;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.imp.MapInsert;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * xiaokang[1036]
 */
public abstract class AbstractExtendMapInsert extends MapInsert{
	
	@Override
	public int batchInsertData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		int batchInsertData = super.batchInsertData(vm, drpzModel, drlpzList);
		doBatchInsertData(vm, drpzModel, drlpzList);
		return batchInsertData;
	}

	@Override
	public int batchUpdateData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		int batchUpdateData = super.batchUpdateData(vm, drpzModel, drlpzList);
		doBatchUpdateData(vm, drpzModel, drlpzList);
		return batchUpdateData;
	}

	@Override
	public Integer[] batchAddOrUpdate(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		Integer[] batchAddOrUpdate = super.batchAddOrUpdate(vm, drpzModel, drlpzList);
		doBatchAddOrUpdate(vm, drpzModel, drlpzList);
		return batchAddOrUpdate;
	}
	
	protected abstract void doBatchInsertData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList);
	
	protected abstract void doBatchUpdateData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList);

	protected abstract void doBatchAddOrUpdate(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList);

}
