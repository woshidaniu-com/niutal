package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.ZydmModel;

public interface IZydmService extends BaseService<ZydmModel> {
	
	public List<ZydmModel> queryModel(Map<String,String> map) ;
}
