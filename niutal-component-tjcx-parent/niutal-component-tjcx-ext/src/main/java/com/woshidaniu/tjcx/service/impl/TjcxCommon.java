package com.woshidaniu.tjcx.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.dao.daointerface.IExportDao;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.tjcx.TjcxThreadLocalUtil;
//import com.woshidaniu.export.service.utils.ExportComparator;
//import com.woshidaniu.service.common.sqlplugin.SjfwSqlUtils;
import com.woshidaniu.tjcx.dao.daointerface.ICxzdDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjcxBaseDao;
import com.woshidaniu.tjcx.dao.daointerface.IYsjDao;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.DmmcModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;

public class TjcxCommon implements ITjcxCommon {
	protected static final Logger log = LoggerFactory.getLogger(TjcxCommon.class);
	protected static String PUBLIC_FLAG = "public";
	protected static String DCQZ = "tjcx_";// 导出前缀
	protected ITjcxBaseDao tjcxBaseDao;
	protected IExportDao exportDao;
	protected IYsjDao ysjDao;
	protected ICxzdDao cxzdDao;

	/**
	 * 
	 * @描述:取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:45:11
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public String getSjkqz(CxzdModel cxzdModel) {
		String qzfw = cxzdModel.getQzfw();
		String flzdmc = cxzdModel.getFlzdmc();
		String flwjzdmc = cxzdModel.getFlwjzdmc();
		String flzdz = cxzdModel.getFlzdz();
		String cxtjdm = cxzdModel.getCxtjdm();
		String result = "";
		String bm = null;// 表名
		String dm = null;// 代码
		String mc = null;// 名称
		String param = null;// 参数

		List<DmmcModel> dmMcList = new ArrayList<DmmcModel>();
		List<HashMap> dmMcMapList = null;
		if (qzfw != null) {
			StringBuffer sb = new StringBuffer();
			try {
				String[] szzs1 = qzfw.split(Constants.CHAR_SX_REG);
				qzfw = szzs1[0];
				if (szzs1.length > 1) {
					param = szzs1[1];
				}

				String[] gxzs = qzfw.split(Constants.CHAR_MH);
				if (gxzs != null && gxzs.length > 1) {
					bm = gxzs[0];
					dm = gxzs[1].split(Constants.CHAR_DH)[0];
					mc = gxzs[1].split(Constants.CHAR_DH)[1];
					HashMap<String, String> sqlMap = new HashMap<String, String>();
					sb.append("select distinct ");
					sb.append(dm);
					sb.append(" DM,");
					sb.append(mc);
					sb.append(" MC ");
					sb.append(" from  ");
					sb.append(bm);
					sb.append(" where ");
					sb.append(dm);
					sb.append(" is not null ");
					if (param != null) {
						sb.append(" and ");
						sb.append(param);
					}
					if (flzdmc != null && !flzdmc.equals("")) {// 包含父类字段

						if (flzdz == null) {
							sb.append(" and 1=2 ");
						} else if (flzdz.trim().equals("")) {

						} else {
							if (flwjzdmc == null || flwjzdmc.trim().equals("")) {
								flwjzdmc = flzdmc;
							}
							sb.append(" and ");
							sb.append(flwjzdmc);
							sb.append(" in(");
							flzdz = flzdz.replaceAll(",", "','");
							flzdz = "'" + flzdz + "'";
							sb.append(flzdz);
							sb.append(")");
						}
					}

					if (!StringUtils.isNull(cxtjdm)) {
						sb.append(" and ");
						sb.append(dm);
						sb.append(" in(");
						cxtjdm = cxtjdm.replaceAll(",", "','");
						cxtjdm = "'" + cxtjdm + "'";
						sb.append(cxtjdm);
						sb.append(")");
					}

					sb.append(" order by  ");
					sb.append(dm);
					sqlMap.put("sql", sb.toString());
					log.info("数据库取值:配置[" + qzfw + "] sql-" + sb.toString());
					dmMcMapList = tjcxBaseDao.getListBySql(sqlMap);
					DmmcModel dmmcModel = null;
					for (HashMap map : dmMcMapList) {
						if (map != null) {
							dmmcModel = new DmmcModel();
							dmmcModel.setDm(map.get("DM") + "");
							Object curMc = map.get("MC");
							if (curMc != null) {
								dmmcModel.setMc(map.get("MC") + "");
							}
							dmMcList.add(dmmcModel);
						}
					}
					result = dmMcGsh(dmMcList);
				}
			} catch (Exception e) {
				log.error("数据库取值报错：配置[" + qzfw + "] sql-" + sb.toString() + " "
						+ e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 
	 * @描述:取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	 * @作者：ligl
	 * @日期：2013-7-25 上午09:44:56
	 * @修改记录:
	 * @param qzfw
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getFfqz(CxzdModel cxzdModel) {
		String qzfw = cxzdModel.getQzfw();
		String flzdmc = cxzdModel.getFlzdmc();
		String flzdz = cxzdModel.getFlzdz();
		String result = "";
		String lm = null;// 类名
		String ffms = null;// 方法名|参数
		String ffm = null;// 方法名
		String param = null;// 参数
		if (qzfw != null) {
			try {
				String[] gxzs = qzfw.split(Constants.CHAR_MH);
				if (gxzs != null) {
					lm = gxzs[0].split(Constants.CHAR_JH)[0];// 类名
					ffms = gxzs[0].split(Constants.CHAR_JH)[1];// 方法名|参数
					ffm = ffms.split(Constants.CHAR_SX_REG)[0];
					if (ffms.split(Constants.CHAR_SX_REG).length > 1) {
						param = ffms.split(Constants.CHAR_SX_REG)[1];
					}
					Class t = Class.forName(lm);
					Object o = t.newInstance();
					Method method = null;
					if (param == null) {
						method = t.getMethod(ffm);
						if (!StringUtils.isNull(flzdmc)) {// 包含父类字段参数值
							method = t.getMethod(ffm, String.class);
						} else {
							method = t.getMethod(ffm);
						}
					} else {
						if (!StringUtils.isNull(flzdmc)) {// 包含父类字段参数值
							method = t.getMethod(ffm, String.class,
									String.class);
						} else {
							method = t.getMethod(ffm, String.class);
						}
					}
					if (gxzs.length == 1) {// 仅包含 类名全称#方法名，方法无参，返回为竖线分割的字符串
						result = (String) method.invoke(o);
					} else if (gxzs.length > 1) {// 类名全称#方法名:代码,名称，方法无参，返回为List<hashMap<String,String>>格式
						List<HashMap<String, String>> list = null;
						String dm = gxzs[1].split(Constants.CHAR_DH)[0];
						String mc = gxzs[1].split(Constants.CHAR_DH)[1];
						if (param == null) {
							if (!StringUtils.isNull(flzdmc)) {// 包含父类字段参数值
								list = (List) method.invoke(o, flzdz);
							} else {
								list = (List) method.invoke(o);
							}
						} else {
							if (!StringUtils.isNull(flzdmc)) {// 包含父类字段参数值
								list = (List) method.invoke(o, param, flzdz);
							} else {
								list = (List) method.invoke(o, param);
							}
						}

						result = dmMcGsh(list, dm, mc);
					}
				}
			} catch (Exception e) {
				log.error("方法取值报错：配置[" + qzfw + "]" + " " + e.getMessage());
			}
		}
		return result;
	}

	/*
	 * 将list中代码名称格式化,转化成1:男,2:女
	 */
	@SuppressWarnings("rawtypes")
	protected String dmMcGsh(List<HashMap<String, String>> list, String dm,
			String mc) {
		String result = "";
		boolean flag = false;
		if (list != null) {
			for (HashMap map : list) {
				String key = map.get(dm).toString();
				String value = map.get(mc).toString();
				if (value != null) {
					if (flag) {
						result += Constants.CHAR_DH;
					} else {
						flag = true;
					}
					result += key + Constants.CHAR_MH + value;
				}
			}
		}
		return result;
	}

	/*
	 * 将list中代码名称格式化,转化成1:男,2:女
	 */
	protected String dmMcGsh(List<DmmcModel> list) {
		String result = "";
		boolean flag = false;
		if (list != null) {
			for (DmmcModel model : list) {
				if (model != null && model.getDm() != null) {
					String key = model.getDm();
					String value = model.getMc();
					if (value != null) {
						if (flag) {
							result += Constants.CHAR_DH;
						} else {
							flag = true;
						}
						result += key + Constants.CHAR_MH + value;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 设置数据源数据范围，
	 */
	@SuppressWarnings("deprecation")
	public void setSjfw(YsjModel ysjModel) {
		if (ysjModel != null) {
			TjcxThreadLocalUtil.setYsjThreadLocal(ysjModel);
			String ysjSql = ysjModel.getSql();
			String sjfwlx = ysjModel.getSjfwlx();
			
			if (ysjSql != null && !ysjSql.trim().equals("") && sjfwlx != null
					&& !sjfwlx.trim().equals("")) {
				if (!ysjSql.toLowerCase().contains("select")) {
					ysjSql = "select * from " + ysjSql + " a where 1=1 ";
				}else{
					ysjSql = "select * from (" + ysjSql +") a where 1=1 ";
				}
				//if (sjfwlx.equals(Constants.FLAG_SJFWLX_XKJ)) {// 新框架方式调用
//					ysjSql = SjfwSqlUtils.getFuncDataRangeSql(ysjSql);
					//TODO 待完善
				//} else if (sjfwlx.equals(Constants.FLAG_SJFWLX_XG)) {// 调用接口					
				//	HttpSession session = WebContext.getSession();
				//	Object sqlQxXgObj = session.getAttribute(Constants.TJCX_SQL_QX_XG);
				//	if(sqlQxXgObj != null){
				//		String sqlQxXg = (String)sqlQxXgObj;
				//		ysjSql += sqlQxXg;
				//	}
				//}
			}
			ysjModel.setSql(ysjSql);
		}
	}

	/**
	 * 
	 * @描述:得到配置的字段
	 * @作者：ligl
	 * @日期：2013-8-27 上午09:14:37
	 * @修改记录:
	 * @param kzszModel
	 * @return
	 * @throws Exception
	 *             List<ExportConfigModel> 返回类型
	 * @throws
	 */
	public List<ExportConfigModel> getExportConfigList(KzszModel kzszModel)
			throws Exception {

		ExportModel exportModel = new ExportModel();
		exportModel.setDcclbh(DCQZ + kzszModel.getXmdm());
		exportModel.setZgh(kzszModel.getCzy());
		List<ExportConfigModel> exportConfigList = exportDao
				.getExportConfig(exportModel);

		for (int i = exportConfigList.size() - 1; i >= 0; i--) {
			ExportConfigModel o = exportConfigList.get(i);
			if (o == null || o.getZt() == null || o.getZt().equals("0")) {
				exportConfigList.remove(i);
			}
		}

		if (exportConfigList == null || exportConfigList.size() == 0) {
			exportModel.setZgh(PUBLIC_FLAG);
			exportConfigList = exportDao.getExportConfig(exportModel);
		}

		// 根据显示顺序排序
		Collections.sort(exportConfigList, new Comparator<ExportConfigModel>(){

			@Override
			public int compare(ExportConfigModel oldModel, ExportConfigModel newModel) {
				try{
					return Integer.valueOf(oldModel.getXssx()) > Integer.valueOf(newModel.getXssx()) ? 1 : -1;
				}catch (Exception e) {
					return 0;
				}
			}});
		
		return exportConfigList;
	}

	/**
	 * 获取已配置的导出字段
	 * 
	 * @param exportConfigList
	 * @return
	 * @throws Exception
	 */
	public String getXszd(List<ExportConfigModel> exportConfigList)
			throws Exception {
		String zds = "";
		if (exportConfigList == null) {
			return zds;
		}
		boolean flag = false;
		for (ExportConfigModel exportConfigModel : exportConfigList) {
			if (exportConfigModel != null) {
				if (flag) {
					zds += ",";
				} else {
					flag = true;
				}
				zds += exportConfigModel.getZd();
			}
		}
		return zds;
	}

	/**
	 * 
	 * @描述:返回记录总数
	 * @作者：ligl
	 * @日期：2013-8-27 下午01:28:27
	 * @修改记录:
	 * @param kzszModel
	 * @return int 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTotalResult(KzszModel kzszModel) throws Exception {
		String sql = getSqlForTotalResult(kzszModel);
		HashMap map = new HashMap();
		map.put("sql", sql);
		int iNum = 0;
		try {
			//YsjModel ysjModel = ysjDao.getModel(kzszModel);
			//if (ysjModel != null && ysjModel.getSjfwlx() != null
			//		&& ysjModel.getSjfwlx().equals(Constants.FLAG_SJFWLX_XKJ)) {
			//	map = tjcxBaseDao.getMapBySqlByScope(map);
			//} else {
				map = tjcxBaseDao.getMapBySql(map);
			//}
			String num = ((BigDecimal) map.get("NUM")).toString();
			iNum = Integer.parseInt(num);
		} catch (Exception e) {
			log.error("获取记录总数报错：sql-" + sql + " " + e.getMessage());
		}
		return iNum;
	}

	/*
	 * 拼接sql
	 */
	protected String getSqlForTotalResult(KzszModel kzszModel) throws Exception {
		StringBuffer sb = new StringBuffer();
		YsjModel ysjModel = ysjDao.getModel(kzszModel);
		if (ysjModel != null) {
			setSjfw(ysjModel);// 设置数据范围

			String ysjSql = ysjModel.getSql();
			if (ysjSql != null && !ysjSql.trim().equals("")) {
				sb.append("select ");
				sb.append("count(*) num");
				sb.append(" from ");
				if (ysjSql.toLowerCase().contains("select")) {
					sb.append("(");
					sb.append(ysjSql);
					sb.append(")");
				} else {
					sb.append(ysjSql);
				}
				sb.append(" where 1=1 ");
				String gltj1 = setGltj(sb.toString(), kzszModel.getGltj());// 设置过滤条件
				sb = new StringBuffer(gltj1);

			} else {
				log.error("统计查询：数据源未配置！");
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @描述:设置过滤条件
	 * @作者：ligl
	 * @日期：2014-8-12 上午09:17:02
	 * @修改记录:
	 * @param sql
	 * @param gltj
	 * @return
	 * @throws Exception
	 *             String 返回类型
	 * @throws
	 */
	public String setGltj(String sql, String gltj) {
		String sql1 = null;
		if (!StringUtils.isEmpty(gltj)) {
			if (sql.contains(Constants.GLTJ_DEFAULT_BS)) {
				String gltj1 = " and (" + gltj + ")";
				sql1 = StringUtils.replace(sql, Constants.GLTJ_DEFAULT_BS,
						gltj1);
			} else {
				sql1 = sql;
				sql1 += " and (" + gltj + ")";
			}
		} else {
			sql1 = StringUtils.replace(sql, Constants.GLTJ_DEFAULT_BS, "");
		}
		return sql1;
	}
	
	@Override
	public String getSjkqz(String qzfw) {
		CxzdModel cxzdModel = new CxzdModel();
		cxzdModel.setQzfw(qzfw);
		return getSjkqz(cxzdModel);
	}

	@Override
	public String getFfqz(String qzfw) {
		CxzdModel cxzdModel = new CxzdModel();
		cxzdModel.setQzfw(qzfw);
		return getFfqz(cxzdModel);
	}

	public ITjcxBaseDao getTjcxBaseDao() {
		return tjcxBaseDao;
	}

	public void setTjcxBaseDao(ITjcxBaseDao tjcxBaseDao) {
		this.tjcxBaseDao = tjcxBaseDao;
	}

	public IExportDao getExportDao() {
		return exportDao;
	}

	public void setExportDao(IExportDao exportDao) {
		this.exportDao = exportDao;
	}

	public IYsjDao getYsjDao() {
		return ysjDao;
	}

	public void setYsjDao(IYsjDao ysjDao) {
		this.ysjDao = ysjDao;
	}

	public ICxzdDao getCxzdDao() {
		return cxzdDao;
	}

	public void setCxzdDao(ICxzdDao cxzdDao) {
		this.cxzdDao = cxzdDao;
	}

}
