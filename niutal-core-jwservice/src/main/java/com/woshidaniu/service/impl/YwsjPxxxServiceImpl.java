package com.woshidaniu.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.daointerface.IYwsjPxxxDao;
import com.woshidaniu.entities.YwsjPxxxModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IYwsjPxxxService;

/**
 * 
 *@类名称:YwsjPxxxServiceImpl.java
 *@类描述：业务数据排序service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-2-4 下午05:52:37
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Service
public class YwsjPxxxServiceImpl extends CommonBaseServiceImpl<YwsjPxxxModel, IYwsjPxxxDao> implements IYwsjPxxxService{
	@Resource
	private IYwsjPxxxDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	public boolean update(YwsjPxxxModel model) {
		List<YwsjPxxxModel> modelList = model.getList();
		if(!BlankUtils.isBlank(modelList)){
			if(dao.getCount(model) == 0){
				for (YwsjPxxxModel ywsjPxxxModel : modelList) {				
					//插入排序数据
					dao.insert(ywsjPxxxModel);									
				}
			}else{
				for (YwsjPxxxModel ywsjPxxxModel : modelList) {				
					//更新排序信息
					dao.update(ywsjPxxxModel);
				}
			}
		}
		return true;
	}
	
	public List<YwsjPxxxModel> getModelList(YwsjPxxxModel model) {
		return dao.getModelList(model);
	}
}
