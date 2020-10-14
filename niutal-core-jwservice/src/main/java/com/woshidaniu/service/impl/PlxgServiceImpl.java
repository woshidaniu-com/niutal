package com.woshidaniu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.daointerface.IPlxgDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.PlxgModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IPlxgService;
import com.woshidaniu.util.local.LocaleUtils;
/**
 * 
 *@类名称:PlxgServiceImpl.java
 *@类描述：数据批量修改service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-10 下午12:38:03
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Service
public class PlxgServiceImpl extends CommonBaseServiceImpl<PlxgModel,IPlxgDao> implements IPlxgService {
	
	@Resource
	private IPlxgDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}

	public List<PairModel> getGnmkdmPairList() {
		return dao.getGnmkdmPairList(LocaleUtils.getLocaleKey());
	}
	
	public List<PlxgModel> getBatchModelList(PlxgModel model){
		return dao.getBatchModelList(model);
	}
	
	@Override
	public boolean batchUpdate(List<PlxgModel> list) {
		Map<String,Object> map = new  HashMap<String, Object>();
		map.put("list", list);
		getDao().batchUpdate(map);
		return true;
	}

}
