package com.woshidaniu.tjcx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.tjcx.dao.daointerface.ITjcxBaseDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjcxDao;
import com.woshidaniu.tjcx.dao.daointerface.IYsjDao;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjcxModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public class TjcxServiceImpl extends BaseServiceImpl<TjcxModel, ITjcxDao>
		implements ITjcxService {
	private static final Logger log = LoggerFactory.getLogger(TjcxServiceImpl.class);

	private ITjcxBaseDao tjcxBaseDao;
	private IYsjDao ysjDao;
	private ITjcxCommon tjcxCommon;

	/**
	 * 
	 * @描述:得到某一项目的统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午02:07:54
	 * @修改记录:
	 * @param kzszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> getTjcxsj(QueryModel queryModel, KzszModel kzszModel)
			throws Exception {

		List<ExportConfigModel> exportConfigList = tjcxCommon
				.getExportConfigList(kzszModel);

		String zds = tjcxCommon.getXszd(exportConfigList);
		if (zds == null || zds.equals("")) {
			log.error("统计查询：未配置导出字段！");
			throw new Exception("未配置导出字段！");
		}

		queryModel.setTotalResult(tjcxCommon.getTotalResult(kzszModel));// 总条数
		List<HashMap> list = new ArrayList<HashMap>();

		HashMap<String, String> map = new HashMap<String, String>();
		String sql = null;
		try {
			sql = getSql(queryModel, kzszModel, zds, false);
			map.put("sql", sql);
			list = tjcxBaseDao.getListBySql(map);
		} catch (Exception e) {
			log.error("统计查询-获取统计查询列表报错：sql-" + sql + " " + e.getMessage());
		}

		return list;
	}

	/**
	 * 
	 * @描述:得到某一项目的统计查询列表，不分页
	 * @作者：ligl
	 * @日期：2013-8-26 下午02:07:54
	 * @修改记录:
	 * @param 
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getTjcxsjNoPage(KzszModel kzszModel) throws Exception {

		List<ExportConfigModel> exportConfigList = tjcxCommon.getExportConfigList(kzszModel);

		String zds = tjcxCommon.getXszd(exportConfigList);
		if (zds == null || zds.equals("")) {
			log.error("统计查询：未配置导出字段！");
			throw new Exception("未配置导出字段！");
		}

		List<HashMap> list = new ArrayList<HashMap>();

		HashMap<String, String> map = new HashMap<String, String>();
		String sql = null;
		try {
			
			sql = getSql(null, kzszModel, zds, true);
			
			map.put("sql", sql);
			
			list = tjcxBaseDao.getListBySql(map);
			
			
		} catch (Exception e) {
			log.error("统计查询-获取统计查询列表(不分页)报错：sql-" + sql + " " + e.getMessage());
		}
		return list;
	}

	@Override
	public int getTjcxsjCountNoPage(KzszModel kzszModel) throws Exception {
		
		String countSql = getCountSql(kzszModel);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("sql", countSql);
		int count = tjcxBaseDao.getCount(map);
		return count;
	}

	
	private String getCountSql(KzszModel kzszModel) throws Exception{
		StringBuffer sb = new StringBuffer();
		YsjModel ysjModel = ysjDao.getModel(kzszModel);
		if (ysjModel != null) {
			tjcxCommon.setSjfw(ysjModel);// 设置数据范围
			String ysjSql = ysjModel.getSql();
			if (ysjSql != null && !ysjSql.trim().equals("")) {
				sb.append("select ");
				sb.append(" count(1) ct ");
				sb.append(" from ");
				if (ysjSql.trim().toLowerCase().substring(0, 6)
						.equals("select")) {
					sb.append("(");
					sb.append(ysjSql);
					sb.append(")");
				} else {
					sb.append(ysjSql);
				}
				sb.append(" where 1=1 ");

				String gltj1 = tjcxCommon.setGltj(sb.toString(), kzszModel.getGltj());// 设置过滤条件
				sb = new StringBuffer(gltj1);
				
			} else {
				log.error("统计查询：数据源未配置！");
			}
		}
		return sb.toString();
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
				if (ysjSql.trim().toLowerCase().substring(0, 6)
						.equals("select")) {
					sb.append("(");
					sb.append(ysjSql);
					sb.append(")");
				} else {
					sb.append(ysjSql);
				}
				sb.append(" where 1=1 ");

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

	
}
