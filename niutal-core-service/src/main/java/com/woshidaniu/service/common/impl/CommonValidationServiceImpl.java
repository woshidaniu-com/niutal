package com.woshidaniu.service.common.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.dao.daointerface.ICommonValidationDao;
import com.woshidaniu.dao.entities.ValidationModel;
import com.woshidaniu.service.common.ICommonValidationService;

@Service
public class CommonValidationServiceImpl implements ICommonValidationService {

	@Resource
	private ICommonValidationDao commonValidationDao;
	
	//系统表映射
	@Resource
	private Map<String,String> tableMapper;
	
	
	@Override
	public boolean unique(ValidationModel model) {
		String table = tableMapper.get(model.getTable());
		model.setTable(table);
			if(BlankUtils.isBlank(model.getFiled_value())){
				int count = commonValidationDao.getMultiCount(model);
			return count==0;
		}else{
			int count = commonValidationDao.getCount(model);
			return count==0;
		}
	}


	public ICommonValidationDao getCommonValidationDao() {
		return commonValidationDao;
	}

	public void setCommonValidationDao(ICommonValidationDao commonValidationDao) {
		this.commonValidationDao = commonValidationDao;
	}

	public Map<String, String> getTableMapper() {
		return tableMapper;
	}

	public void setTableMapper(Map<String, String> tableMapper) {
		this.tableMapper = tableMapper;
	}

	
	

}
