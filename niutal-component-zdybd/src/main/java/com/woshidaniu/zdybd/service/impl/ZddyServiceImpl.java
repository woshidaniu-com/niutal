package com.woshidaniu.zdybd.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.format.fastjson.FastJSONObject;
import com.woshidaniu.zdybd.dao.daointerface.IZddyDao;
import com.woshidaniu.zdybd.dao.entities.GnszModel;
import com.woshidaniu.zdybd.dao.entities.ZddyModel;
import com.woshidaniu.zdybd.service.svcinterface.IZddyService;
import com.woshidaniu.zdybd.service.svcinterface.IZdybdCommonService;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 字段定义
 * @作者： ligl
 * @时间： 2014-2-18 上午09:36:29
 * @版本： V1.0
 * @修改记录:
 */
public class ZddyServiceImpl extends BaseServiceImpl<ZddyModel, IZddyDao>
		implements IZddyService {
	private static final transient Logger log = LoggerFactory
			.getLogger(ZddyServiceImpl.class);
	private IZdybdCommonService zdybdCommonService;

	@Override
	public void getListByGndm(List<ZddyModel> gnszList) throws Exception {
		/*
		 * 1.固定值，格式为：1:男,2:女 2:数据库取值,“表名:代码,名称”,
		 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
		 */
		String szlx = null;
		String szz = null;
		String zdlx = null;
		String hccl = null;
		String ssxJson = null;
		for (ZddyModel model : gnszList) {
			szlx = model.getSzlx();
			szz = model.getSzz();
			zdlx = model.getZdlx();
			hccl = model.getHccl();
			if (szlx == null) {
				continue;
			}
			if (hccl == null || hccl.equals("0")) {
				if (szlx.trim().equals("2") || szlx.trim().equals("20")) {
					model.setSzz(zdybdCommonService.getSjkqz(szz));
				} else if (szlx.trim().equals("3") || szlx.trim().equals("30")) {
					model.setSzz(zdybdCommonService.getFfqz(szz));
				}
			}
		}
	}

	@Cacheable(value = CacheName.CACHE_ZDYBD, key = "'zdybd'+ #gnszModel.pzlx+ '_' + #gnszModel.gndm + '_' + #gnszModel.flszid + 'ZddyServiceImpl.getListByGndmCache'")
	public List<ZddyModel> getListByGndmCache(GnszModel gnszModel)
			throws Exception {
		List<ZddyModel> list = null;
		if (gnszModel == null || gnszModel.getGndm() == null) {
			list = new ArrayList<ZddyModel>();
		} else {
			list = dao.getListByGndm(gnszModel);
			/*
			 * 1.固定值，格式为：1:男,2:女 2:数据库取值,“表名:代码,名称”,
			 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
			 */
			String szlx = null;
			String szz = null;
			String zdlx = null;
			String hccl = null;
			String pzlx = gnszModel.getPzlx();
			ZddyModel model = null;
			for (int i = list.size() - 1; i >= 0; i--) {
				model = list.get(i);
				if (pzlx != null && !pzlx.trim().equals("")) {
					resetModelByPzlx(pzlx, model);
					if (model.getSfqy() != null && model.getSfqy().equals("0")) {
						list.remove(i);
						continue;
					}
				}
				szlx = model.getSzlx();
				szz = model.getSzz();
				zdlx = model.getZdlx();
				hccl = model.getHccl();
				//如果是省市县类型，需要查询数据
//				if("22".equals(zdlx)){
//					String ssxJson = zdybdCommonService.getSsxJson();
//					if(StringUtil.isNoneBlank(ssxJson)){
//						model.setSzz(ssxJson);
//					}
//				}
				
				if (szlx == null) {
					continue;
				}
				if (szlx.trim().equals("1") || szlx.trim().equals("10")) {
					model.setSzz(zdybdCommonService.dmmcToJson(szz));
				}
				if (hccl != null && hccl.equals("1")) {
					if (szlx.trim().equals("2") || szlx.trim().equals("20")) {
						model.setSzz(zdybdCommonService.getSjkqz(szz));
					} else if (szlx.trim().equals("3")
							|| szlx.trim().equals("30")) {
						model.setSzz(zdybdCommonService.getFfqz(szz));
					}
				}
			}
		}
		return list;
	}

	/*
	 * 根据配置类型，重置model
	 */
	private void resetModelByPzlx(String pzlx, ZddyModel model) {
		String methodName = null;
		try {
			if(pzlx.contains("ckpz")){//查看配置，默认全部为仅显示状态
				model.setYxxg("0");
				model.setYxwk("1");
			}
			
			methodName = "get" + pzlx.substring(0, 1).toUpperCase()
					+ pzlx.substring(1);
			Class clazz = model.getClass();
			Method m1 = clazz.getMethod(methodName);
			String value = (String) m1.invoke(model);
			ZddyModel zddyModelTmp = null;
			if (value != null) {
				value = "{" + value + "}";
				ZddyModel obj = FastJSONObject.toBean(value, ZddyModel.class);
				if (obj != null) {
					zddyModelTmp = (ZddyModel) obj;
					resetModelImpl(model, zddyModelTmp);
				}
			}
		} catch (Exception e) {
			log.warn("ZddyModel中方法执行失败：" + methodName);
		}
	}

	private void resetModelImpl(ZddyModel model, ZddyModel zddyModelTmp) {
		String[] repeatZddy = ZddyModel.REPEAT_ZDDY;
		Class clazz = model.getClass();
		Method m1 = null;
		Object value = null;
		String methodSuffix = null;
		for (int i = 0; i < repeatZddy.length; i++) {
			String repeatZddy1 = repeatZddy[i];
			try {
				methodSuffix = repeatZddy1.substring(0, 1).toUpperCase()
						+ repeatZddy1.substring(1);
				m1 = clazz.getMethod("get" + methodSuffix);
				value = m1.invoke(zddyModelTmp);
				if (value != null) {
					m1 = clazz.getMethod("set" + methodSuffix, String.class);
					m1.invoke(model, (String) value);
				}
			} catch (Exception e) {
				log.warn("ZddyModel中设置属性执行失败：" + repeatZddy1);
			}
		}
	}

	@Override
	public List<ZddyModel> getListByGndmOfXs(GnszModel gnszModel)
			throws Exception {
		List<ZddyModel> list = dao.getListByGndm(gnszModel);
		for (ZddyModel model : list) {
			model.setYxwk("1");
			model.setYxxg("0");
		}
		return list;
	}

	public IZdybdCommonService getZdybdCommonService() {
		return zdybdCommonService;
	}

	public void setZdybdCommonService(IZdybdCommonService zdybdCommonService) {
		this.zdybdCommonService = zdybdCommonService;
	}

}
