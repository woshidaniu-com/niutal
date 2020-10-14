package com.woshidaniu.jcsj.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.jcsj.dao.daointerface.IBjdmDao;
import com.woshidaniu.jcsj.dao.entities.BjdmModel;
import com.woshidaniu.jcsj.service.svcinterface.IBjdmService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 班级代码维护
 * @author Administrator
 *
 */
@Service(value="zfxgBjdmService")
public class BjdmServiceImpl extends BaseServiceImpl<BjdmModel, IBjdmDao> implements
		IBjdmService {
	
	private static final String SPLIT_CHAR = ",";

	@Resource
	private IBjdmDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.jcsj.service.svcinterface.IBjdmService#scBjdm(java.lang.String)
	 */
	public String scBjdm(String ids) {
		if (StringUtils.isNull(ids)){
			throw new NullPointerException();
		}
		
		String[] bjdmArr = ids.split(SPLIT_CHAR);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bjdmArr", bjdmArr);
		
		int delCount = dao.batchDelete(map);
		
		if (delCount == bjdmArr.length){
			return MessageUtil.getText("I99005");
		}
		
		if (delCount == 0){
			return MessageUtil.getText("I99006");
		}
		
		return MessageUtil.getText("I88001",new Object[]{delCount,bjdmArr.length-delCount});
	}


	

}
