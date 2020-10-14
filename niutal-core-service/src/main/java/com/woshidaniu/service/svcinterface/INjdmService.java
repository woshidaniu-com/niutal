package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.NjdmModel;


public interface INjdmService extends BaseService<NjdmModel>{
 
	List<NjdmModel> cxNjdmList();
	
}
