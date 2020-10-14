package com.woshidaniu.zdybd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.format.fastjson.FastJSONObject;
import com.woshidaniu.zdybd.dao.daointerface.IFlszDao;
import com.woshidaniu.zdybd.dao.entities.FlszModel;
import com.woshidaniu.zdybd.dao.entities.GnszModel;
import com.woshidaniu.zdybd.service.svcinterface.IFlszService;
import com.woshidaniu.zdybd.service.svcinterface.IZdybdCommonService;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 分类设置
 * @作者： ligl
 * @时间： 2014-2-18 上午09:36:29
 * @版本： V1.0
 * @修改记录:
 */
public class FlszServiceImpl extends BaseServiceImpl<FlszModel, IFlszDao>
		implements IFlszService {
	private IZdybdCommonService zdybdCommonService;

	public IZdybdCommonService getZdybdCommonService() {
		return zdybdCommonService;
	}

	public void setZdybdCommonService(IZdybdCommonService zdybdCommonService) {
		this.zdybdCommonService = zdybdCommonService;
	}

	@Override
	@Cacheable(value=CacheName.CACHE_ZDYBD,key="'zdybd'+ #gnszModel.pzlx+ '_' + #gnszModel.gndm + '_' + #gnszModel.flszid + 'FlszServiceImpl.getListByGndm'")
	public List<FlszModel> getListByGndm(GnszModel gnszModel)
			throws Exception {
		List<FlszModel> list = null;
		if (gnszModel == null || gnszModel.getGndm() == null) {
			list = new ArrayList<FlszModel>();
		} else {
			list = dao.getListByGndm(gnszModel);
		}
		return list;
	}

	@Override
	@Cacheable(value=CacheName.CACHE_ZDYBD,key="'zdybd' + #gnszModel.gndm + 'FlszServiceImpl.getSrcListByGndm'")
	public List<String> getSrcListByGndm(GnszModel gnszModel) throws Exception {
		List<String> resultList = new ArrayList<String>();
		List<FlszModel> list = getListByGndm(gnszModel);
		for (FlszModel flszModel : list) {
			if (flszModel == null) {
				continue;
			}
			String bdms = flszModel.getBdms();
			if (bdms == null
					|| (!bdms.trim().equals("2") && !bdms.trim().equals("4"))) {
				continue;
			}
			String bdszz = flszModel.getBdszz();
			if (bdszz == null || bdszz.trim().equals("")) {
				continue;
			}
			String src = getSrc(bdszz);
			resultList.add(src);
		}
		return resultList;
	}

	/*
	 * 获取json串中的src
	 */
	private String getSrc(String bdszz) {
		String src = null;
		bdszz = "{data:[{" + bdszz + "}]}";
		try {
			List<Map> list = FastJSONObject.toBeans(bdszz, "data", Map.class);
			if (list != null && list.size() > 0) {
				src = (String) list.get(0).get("src");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}
}
