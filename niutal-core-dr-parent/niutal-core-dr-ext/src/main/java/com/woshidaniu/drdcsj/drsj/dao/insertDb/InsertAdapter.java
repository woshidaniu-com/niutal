/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.woshidaniu.drdcsj.drsj.comm.ImportUtil;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.imp.ConfigInsert;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

/**
 * @author 982 张昌路
 * 插入适配器， 核心代码使用Map<列名,List<列值>>格式，  需要提供String[][]（列，行）格式， 
 * 故这里做了适配器，根据需要依赖注入对应接口实现  
 * mapInsert：Map格式
 * arrayInsert：String[][]格式
 */
@Component("insertAdapter")
public class InsertAdapter implements IInsertDb {
	
	@Autowired
	@Qualifier("mapInsert")
	private IInsertDb mapInsert;
	@Autowired
	private IImportDao importDao;
	
	private IArrayInsert arrayInsert;
	
	@Override
	public Integer[] batchAddOrUpdate(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
		// 执行配置插入，如果存在着自己执行后返回
		String drmkdm = drpzModel.getDrmkdm();
		ConfigInsert configInsert = new ConfigInsert(drmkdm,this.importDao);
	    Object excutor = configInsert.getExecutor();
		if(excutor != null){
			return configInsert.batchAddOrUpdate(vm, drpzModel,drlpzList);
		}else{
			// 默认使用map类型格式插入，如果不存在则使用数组格式执行
			// 对应格式通过spring注入
			if (this.mapInsert != null) {
				return this.mapInsert.batchAddOrUpdate(vm, drpzModel, drlpzList);
			}else if(this.arrayInsert != null){
				String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
				return this.arrayInsert.batchAddOrUpdate(list, drpzModel, drlpzList);
			}else{
				return new Integer[]{0,0};
			}
		}
	}

	@Override
	public int batchInsertData(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
		// 执行配置插入，如果存在着自己执行后返回
		// 如果存在自定义插入配置，则执行自定义代码
		String drmkdm = drpzModel.getDrmkdm();
		ConfigInsert configInsert = new ConfigInsert(drmkdm,this.importDao);
		Object excutor = configInsert.getExecutor();
		if(excutor != null){
			return configInsert.batchInsertData(vm, drpzModel, drlpzList);
		}else{
			// 默认使用map类型格式插入，如果不存在则使用数组格式执行
			// 对应格式通过spring注入
			if (this.mapInsert != null) {
				return this.mapInsert.batchInsertData(vm, drpzModel, drlpzList);
			}else if(this.arrayInsert != null){
				String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
				return this.arrayInsert.batchInsertData(list, drpzModel, drlpzList);
			}else{
				return 0;
			}
		}
	}

	@Override
	public int batchUpdateData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		// 执行配置插入，如果存在着自己执行后返回
		String drmkdm = drpzModel.getDrmkdm();
		ConfigInsert configInsert = new ConfigInsert(drmkdm,this.importDao);
		Object excutor = configInsert.getExecutor();
		if(null != excutor){
			return configInsert.batchUpdateData(vm, drpzModel, drlpzList);
		}else{
			// 默认使用map类型格式插入，如果不存在则使用数组格式执行
			// 对应格式通过spring注入
			if (this.mapInsert != null) {
				return this.mapInsert.batchUpdateData(vm, drpzModel, drlpzList);
			}else if(this.arrayInsert != null){
				String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
				return this.arrayInsert.batchUpdate(list, drpzModel, drlpzList);
			}else{
				return 0;
			}
		}
	}
}
