package com.woshidaniu.jcsj.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.jcsj.dao.daointerface.IZydmDao;
import com.woshidaniu.jcsj.dao.entities.ZydmModel;
import com.woshidaniu.jcsj.service.svcinterface.IZydmService;
import com.woshidaniu.util.base.MessageUtil;


@Service(value="zfxgZydmService")
public class ZydmServiceImpl extends BaseServiceImpl<ZydmModel, IZydmDao> implements
		IZydmService {

	private static final String SPLIT_CHAR = ",";
	
	@Resource
	private IZydmDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	public boolean zjZydm(ZydmModel model) {
		int result = dao.insert(model);
		return result > 0;
	}

	public String scZydm(String ids) {
		if (StringUtils.isNull(ids)){
			throw new NullPointerException();
		}
		String[] bmdmidArr = ids.split(SPLIT_CHAR);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("zydm", bmdmidArr);
		
		int delCount = dao.batchDelete(map);
		
		if (delCount == bmdmidArr.length){
			return MessageUtil.getText("I99005");
		}
		
		if (delCount == 0){
			return MessageUtil.getText("I99006");
		}
		
		return MessageUtil.getText("I88001",new Object[]{delCount,bmdmidArr.length-delCount});
	}

	public List<ZydmModel> cxZydmList(String xydm) {
		return dao.cxZydmList(xydm);
	}

	
	public ZydmModel valideZydm(ZydmModel t) {
		return dao.valideZydm(t);
	}

	
	public List<ZydmModel> cxZydm() {
		return dao.cxZydm();
	}

	

}
