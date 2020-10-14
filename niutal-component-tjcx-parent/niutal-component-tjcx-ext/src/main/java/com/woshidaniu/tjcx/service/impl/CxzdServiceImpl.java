package com.woshidaniu.tjcx.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.tjcx.dao.daointerface.ICxzdDao;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.YsfModel;
import com.woshidaniu.tjcx.service.svcinterface.ICxzdService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public class CxzdServiceImpl extends BaseServiceImpl<CxzdModel, ICxzdDao>
		implements ICxzdService {
	private static final transient Logger log = LoggerFactory.getLogger(CxzdServiceImpl.class);

	private ITjcxCommon tjcxCommon;
	@SuppressWarnings("unused")
	private static String FLAG_QZLX_GDQZ = "1";// 取值类型：1.固定值，格式为：1:男,2:女。显示格式：复选框
	private static String FLAG_QZLX_SJKQZ = "2";// 取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	private static String FLAG_QZLX_FFQZ = "3";// 取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框

	private static String FLAG_QZLX_WBK = "11";// 11,普通文本框;
	private static String FLAG_QZLX_SZK = "12";// 12,数字文本框;
	private static String FLAG_QZLX_RQK = "13";// 13,日期选择框;
	private static String FLAG_QZLX_RQK_SJKSJ = "14";// 14.日期选择框，数据库字段为日期格式；
	private static String FLAG_QZLX_RQK_EJZGS = "15";// 15.存储为二进制格式；

	@SuppressWarnings("unused")
	private static String FLAG_SJFWLX_XKJ = "1";// 1新框架方式 

	/**
	 * @throws Exception 
	 * 
	 * @描述:获取单条记录
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param gnbh
	 * @return List<TjxmModel> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public CxzdModel getModel(CxzdModel model)  {
		CxzdModel cxzdModel = dao.getModel(model);
		if(cxzdModel == null){
			cxzdModel = new CxzdModel();
		}
		cxzdModel.setCxtjdm(model.getCxtjdm());
		String flzdmc = cxzdModel.getFlzdmc();
		if(flzdmc != null && !flzdmc.trim().equals("")){
			String flzdz = null;
			String flzdzJson = model.getFlzdz();
			if(flzdzJson == null){
				cxzdModel.setFlzdz("");
			}
			try {
				List list = JsonUtil.jsonToList(flzdzJson);
				for (Object object : list) {
					net.sf.ezmorph.bean.MorphDynaBean bean = (net.sf.ezmorph.bean.MorphDynaBean)object;
					if(flzdmc.equals((String)bean.get("zdmc"))){
						flzdz = (String) bean.get("cxzdViewDms");
						cxzdModel.setFlzdz(flzdz);//设置父类字段值
						break;
					}
				}
			} catch (Exception e) {
				log.warn("父类字段值，json格式解析错误:" + flzdzJson + " " + e.getMessage());
			}
		}
		
		if (cxzdModel != null) {
			if (cxzdModel.getQzlx() != null) {// 设置取值范围
				if (cxzdModel.getQzlx().equals(FLAG_QZLX_SJKQZ)||cxzdModel.getQzlx().equals(FLAG_QZLX_RQK_EJZGS)) {// 取值类型：2或者15:数据库取值,“表名:代码,名称”,。显示格式：复选框
					cxzdModel.setQzfw(tjcxCommon.getSjkqz(cxzdModel));
				} else if (cxzdModel.getQzlx().equals(FLAG_QZLX_FFQZ)) {// 取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
					cxzdModel.setQzfw(tjcxCommon.getFfqz(cxzdModel));
				} 

				if ((cxzdModel.getQzlx().equals(FLAG_QZLX_WBK)
						|| cxzdModel.getQzlx().equals(FLAG_QZLX_SZK)
						|| cxzdModel.getQzlx().equals(FLAG_QZLX_RQK) || cxzdModel
						.getQzlx().equals(FLAG_QZLX_RQK_SJKSJ))
						&& cxzdModel.getYsfzdm() != null) {// 设置运算符

					cxzdModel.setYsfList(dao.getYsf(cxzdModel));
				}
			}
		}

		return cxzdModel;
	}

	/**
	 * 
	 * @描述:取所有运算符
	 * @作者：ligl
	 * @日期：2013-8-28 上午10:24:41
	 * @修改记录:
	 * @return List<YsfModel> 返回类型
	 * @throws
	 */
	public List<YsfModel> getAllYsfList()  throws Exception {
		return dao.getAllYsf();
	}
	
	/**
	 * 
	 * @描述:取所有子类
	 * @作者：ligl
	 * @日期：2013-9-24 下午02:43:33
	 * @修改记录: 
	 * @param model
	 * @return
	 * List<CxzdModel> 返回类型 
	 * @throws
	 */
	public List<CxzdModel> getChildrenList(CxzdModel model){
		List<CxzdModel> cxzdModelList = dao.getChildrenList(model);
		return cxzdModelList;
	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}


}
