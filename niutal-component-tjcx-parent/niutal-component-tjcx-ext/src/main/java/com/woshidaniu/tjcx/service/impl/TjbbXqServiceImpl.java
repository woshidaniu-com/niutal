package com.woshidaniu.tjcx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.tjcx.TjcxThreadLocalUtil;
import com.woshidaniu.tjcx.dao.daointerface.ICxzdDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjbbDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjcxBaseDao;
import com.woshidaniu.tjcx.dao.daointerface.IYsjDao;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjbbXqService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表详情service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2014-05-20 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public class TjbbXqServiceImpl extends BaseServiceImpl<TjxmModel, ITjbbDao>
		implements ITjbbXqService {
	private static final Logger log = LoggerFactory.getLogger(TjbbServiceImpl.class);
	@SuppressWarnings("unused")
	private static String FLAG_QZLX_GDQZ = "1";// 取值类型：1.固定值，格式为：1:男,2:女。显示格式：复选框
	@SuppressWarnings("unused")
	private static String FLAG_QZLX_SJKQZ = "2";// 取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框

	private ITjcxBaseDao tjcxBaseDao;
	private IYsjDao ysjDao;
	private ITjcxCommon tjcxCommon;
	private ICxzdDao cxzdDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> getTjbbXq(QueryModel queryModel, KzszModel kzszModel)
			throws Exception {

		List<ExportConfigModel> exportConfigList = tjcxCommon
				.getExportConfigList(kzszModel);
		if (exportConfigList == null || exportConfigList.size() == 0) {
			exportConfigList = new ArrayList<ExportConfigModel>();
			List<CxzdModel> cxzdList = cxzdDao.getModelList(new CxzdModel(kzszModel.getXmdm()));
			ExportConfigModel exportConfigModel = null;
			for (CxzdModel cxzdModel : cxzdList) {
				exportConfigModel = new ExportConfigModel();
				exportConfigModel.setZd(cxzdModel.getZdmc());
				exportConfigModel.setZdmc(cxzdModel.getZdsm());
				exportConfigList.add(exportConfigModel);
			}
		}	
		
		String zds = tjcxCommon.getXszd(exportConfigList);
		if (zds == null || zds.equals("")) {
			zds = getCxzdCl(kzszModel);
		}

		queryModel.setTotalResult(tjcxCommon.getTotalResult(kzszModel));// 总条数
		List<HashMap> list = new ArrayList<HashMap>();

		HashMap<String, String> map = new HashMap<String, String>();
		String sql = null;
		try {
			sql = getSql(queryModel, kzszModel, zds, false);
			map.put("sql", sql);
			YsjModel ysjModel = ysjDao.getModel(kzszModel);
			
			TjcxThreadLocalUtil.setYsjThreadLocal(ysjModel);
			
			//if (ysjModel != null && ysjModel.getSjfwlx() != null
			//		&& ysjModel.getSjfwlx().equals(Constants.FLAG_SJFWLX_XKJ)) {
			//	list = tjcxBaseDao.getListBySqlByScope(map);
			//} else {
				list = tjcxBaseDao.getListBySql(map);
			//}
		} catch (Exception e) {
			log.error("统计查询-获取统计查询列表报错：sql-" + sql + " " + e.getMessage());
		}

		return list;
	}

	/*
	 * 得到配置的查询字段
	 */
	private String getCxzdCl(KzszModel kzszModel) {
		String zds = "";
		List<CxzdModel> cxzdList = cxzdDao.getModelList(new CxzdModel(kzszModel
				.getXmdm()));
		boolean flag = false;
		for (CxzdModel cxzdModel : cxzdList) {
			if (cxzdModel != null) {
				if (flag) {
					zds += ",";
				} else {
					flag = true;
				}
				zds += cxzdModel.getZdmc();
			}
		}
		return zds;
	}

	/**
	 * 得到配置的查询字段
	 */
	public List<CxzdModel> getCxzdBt(KzszModel kzszModel) {
		List<CxzdModel> cxzdList = cxzdDao.getModelList(new CxzdModel(kzszModel
				.getXmdm()));
		List<CxzdModel> cxzdList2 = new ArrayList<CxzdModel>();
		for (int i = 0; i < cxzdList.size(); i++) {
			CxzdModel cxzdModel = cxzdList.get(i);
			if (cxzdModel == null) {
				continue;
			}
			String qzlx = cxzdModel.getQzlx();
			if (qzlx == null || qzlx.trim().equals("") || (!qzlx.equals("1") && !qzlx.equals("2"))) {
				continue;
			}
			if (qzlx.equals(Constants.FLAG_QZLX_SJKQZ)) {// 取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
				cxzdModel.setQzfw(tjcxCommon.getSjkqz(cxzdModel.getQzfw()));
			}
			cxzdList2.add(cxzdModel);
		}
		return cxzdList2;
	}

	/*
	 * 拼接sql
	 */
	private String getSql(QueryModel queryModel, KzszModel kzszModel,
			String zds, boolean isFy) throws Exception {
		StringBuffer sb = new StringBuffer();
		YsjModel ysjModel = ysjDao.getModel(kzszModel);
		if (ysjModel != null) {
			tjcxCommon.setSjfw(ysjModel);// 设置数据范围
			String ysjSql = ysjModel.getSql();
			if (ysjSql != null && !ysjSql.trim().equals("")) {
				if (!isFy) {
					sb.append("select * from (");
				}
				sb.append("select tt.*,rownum as xyz from (");
				sb.append("select ");
				sb.append(zds);
				sb.append(" from ");
				if (ysjSql.toLowerCase().contains("select")) {
					sb.append("(");
					sb.append(ysjSql);
					sb.append(")");
				} else {
					sb.append(ysjSql);
				}
				sb.append(" a where 1=1 ");

				String gltj1 = tjcxCommon.setGltj(sb.toString(), kzszModel.getGltj());// 设置过滤条件
				sb = new StringBuffer(gltj1);
				
				if (queryModel != null && queryModel.getSortName() != null
						&& !queryModel.getSortName().trim().equals("")) {
					sb.append(" order by ");
					sb.append(queryModel.getSortName());
					sb.append(" ");
					sb.append(queryModel.getSortOrder());
				}
				sb.append(") tt ");
				if (!isFy && queryModel != null) {
					sb.append(")");
					sb.append(" where xyz between ");
					sb.append(queryModel.getCurrentResult() + 1);
					sb.append(" and ");
					sb.append(queryModel.getCurrentResult()
							+ queryModel.getShowCount());
				}
			} else {
				log.error("统计查询：数据源未配置！");
			}
		}
		return sb.toString();
	}

	public ITjcxBaseDao getTjcxBaseDao() {
		return tjcxBaseDao;
	}

	public void setTjcxBaseDao(ITjcxBaseDao tjcxBaseDao) {
		this.tjcxBaseDao = tjcxBaseDao;
	}

	public IYsjDao getYsjDao() {
		return ysjDao;
	}

	public void setYsjDao(IYsjDao ysjDao) {
		this.ysjDao = ysjDao;
	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}

	public ICxzdDao getCxzdDao() {
		return cxzdDao;
	}

	public void setCxzdDao(ICxzdDao cxzdDao) {
		this.cxzdDao = cxzdDao;
	}

}
