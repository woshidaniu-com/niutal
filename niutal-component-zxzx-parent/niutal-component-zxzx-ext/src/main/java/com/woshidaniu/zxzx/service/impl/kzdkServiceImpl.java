package com.woshidaniu.zxzx.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.zxzx.dao.daointerface.IkzdkDao;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
/**
 * 
 * @类名 kzdkServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 下午4:52:22
 * @功能描述 在线在线-板块信息
 *
 */
@Service("zxkzdkxxService")
public class kzdkServiceImpl extends BaseServiceImpl<kzdkModel, IkzdkDao> implements IkzdkService{

	@Override
	@Autowired
	@Qualifier("zxkzdkxxDao")
	public void setDao(IkzdkDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 查询是否存在于常见问题表中和问题咨询表中
	 * @param model
	 * @return
	 */
	public Integer checkCanDelete(String bkdm){
		return dao.checkCanDelete(bkdm);
	}
	
	/**
	 * 顺序设置保存
	 * @param data
	 * @return
	 */
	public boolean kzdkXssz(Map<String,String> data){
		Set<String> keySet = data.keySet();
		for (String key : keySet) {
			String value = data.get(key);
			kzdkModel insertModel = new kzdkModel();
			insertModel.setBkdm(key);
			insertModel.setXsxs(value);
			dao.update(insertModel);
		}
		return true;
	}
	
	public Integer getMaxXsxs(){
		return dao.getMaxXsxs();
	}
	
	/**
	 * 需要借助缓存
	 */
	public List<kzdkModel> getModelListWeb(kzdkModel model){
		return dao.getModelListWeb(model);
	}

	@Override
	public List<kzdkModel> getkzdkModelListWeb() {
		return dao.getkzdkModelListWeb();
	}
}
