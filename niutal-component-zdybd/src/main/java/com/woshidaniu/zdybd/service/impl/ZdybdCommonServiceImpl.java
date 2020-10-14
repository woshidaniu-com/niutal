package com.woshidaniu.zdybd.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.zdybd.dao.daointerface.IZdybdBaseDao;
import com.woshidaniu.zdybd.dao.entities.DmmcModel;
import com.woshidaniu.zdybd.service.svcinterface.IZdybdCommonService;

public class ZdybdCommonServiceImpl extends
		BaseServiceImpl<DmmcModel, IZdybdBaseDao> implements
		IZdybdCommonService {
	private static final transient Logger log = LoggerFactory
			.getLogger(ZdybdCommonServiceImpl.class);

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
	public String getSjkqz(String szz) {
		String result = "";
		String bm = null;// 表名
		String dm = null;// 代码
		String mc = null;// 名称
		String param = null;//参数
		List<DmmcModel> dmmcList = null;
		HashMap<String, String> sqlMap = new HashMap<String, String>();

		if (szz != null) {
			try {
				String[] szzs1 = szz.split("[|]");
				szz = szzs1[0];
				if(szzs1.length > 1){
					param = szzs1[1];
				}
				String[] szzs = szz.split(":");
				if (szzs != null && szzs.length > 1) {
					bm = szzs[0];
					dm = szzs[1].split(",")[0];
					mc = szzs[1].split(",")[1];
					StringBuffer sb = new StringBuffer();
					sb.append("select distinct ");
					sb.append(dm);
					sb.append(" dm,");
					sb.append(mc);
					sb.append(" mc ");
					sb.append(" from  ");
					sb.append(bm);
					sb.append(" where ");
					sb.append(dm);
					sb.append(" is not null ");
					if(param != null){
						sb.append(" and ");
						sb.append(param);
					}
					sb.append(" order by  ");
					sb.append(dm);
					sqlMap.put("sql", sb.toString());
					dmmcList = dao.getDmmcListBySql(sqlMap);
					result = dmMcGsh(dmmcList);
				}
			} catch (Exception e) {
				log.error("数据库取值报错：" + sqlMap.get("sql"), e);
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
	public String getFfqz(String szz) {
		String result = "";
		String lm = null;// 类名
		String ffms = null;// 方法名|参数
		String ffm = null;// 方法名
		String param = null;// 参数
		if (szz != null) {
			try {
				String[] szzs = szz.split(":");
				if (szzs != null) {
					lm = szzs[0].split("#")[0];// 类名
					ffms = szzs[0].split("#")[1];// 方法名|参数
					ffm = ffms.split("[|]")[0];
					if (ffms.split("[|]").length > 1) {
						param = ffms.split("[|]")[1];
					}
					Class t = Class.forName(lm);
					Object o = t.newInstance();
					Method method = null;
					if (param == null) {
						method = t.getMethod(ffm);
						method = t.getMethod(ffm);
					} else {
						method = t.getMethod(ffm, String.class);
					}
					if (szzs.length == 1) {// 仅包含 类名全称#方法名，方法无参，返回为竖线分割的字符串
						result = (String) method.invoke(o);
					} else if (szzs.length > 1) {// 类名全称#方法名:代码,名称，方法无参，返回为List<hashMap<String,String>>格式
						List<HashMap<String, String>> list = null;
						String dm = szzs[1].split(",")[0];
						String mc = szzs[1].split(",")[1];
						if (param == null) {
							list = (List) method.invoke(o);
						} else {
							list = (List) method.invoke(o, param);
						}

						result = dmMcGsh(list, dm, mc);
					}
				}
			} catch (Exception e) {
				log.error("方法取值报错：", e);
			}
		}
		return result;
	}

	/*
	 * 将list中代码名称格式化,转化成1:男,2:女
	 */
	private String dmMcGsh(List<HashMap<String, String>> list, String dm,
			String mc) {
		String result = "";
		boolean flag = false;
		if (list != null) {
			for (HashMap map : list) {
				String key = map.get(dm).toString();
				String value = map.get(mc).toString();
				if (value != null) {
					if (flag) {
						result += ",";
					} else {
						flag = true;
					}
					result += "{dm:'" + key + "',mc:'" + value + "'}";
				}
			}
		}
		result = "[" + result + "]";
		return result;
	}

	/*
	 * 将list中代码名称格式化,转化成1:男,2:女
	 */
	private String dmMcGsh(List<DmmcModel> list) {
		String result = "";
		boolean flag = false;
		if (list != null) {
			for (DmmcModel model : list) {
				if (model != null && model.getDm() != null) {
					String key = model.getDm();
					String value = model.getMc();
					if (value != null) {
						if (flag) {
							result += ",";
						} else {
							flag = true;
						}
						result += "{dm:'" + key + "',mc:'" + value + "'}";
					}
				}
			}
		}
		result = "[" + result + "]";
		return result;
	}

	/**
	 * 
	 * @描述:将1:男,2:女 转化成json格式
	 * @作者：ligl
	 * @日期：2013-11-28 下午07:16:05
	 * @修改记录:
	 * @param dmmc
	 * @return String 返回类型
	 * @throws
	 */
	public String dmmcToJson(String dmmc) {
		String dm = null;
		String mc = null;
		String result = "";
		boolean flag = false;

		if (dmmc != null && !dmmc.trim().equals("")) {
			String[] dmmcs = dmmc.split(",");
			for (String str : dmmcs) {
				if (str == null || str.trim().equals("")) {
					continue;
				}
				String[] strs = str.split(":");
				if (strs != null) {
					if (strs.length == 0) {
						dm = "";
					} else {
						dm = strs[0];
					}
					if (strs.length > 1) {
						mc = strs[1];
					} else {
						mc = dm;
					}
				}
				if (flag) {
					result += ",";
				} else {
					flag = true;
				}
				result += "{dm:'" + dm + "',mc:'" + mc + "'}";
			}
		}
		result = "[" + result + "]";
		return result;
	}

	@Override
	public String getSsxJson() throws Exception {
		String shSql = "select DM,MC from DM_GB_B_ZHRMGHGXZQHDM where DM like '__0000' and ZT='1' order by DM";
		String qxSql = "select DM,MC from DM_GB_B_ZHRMGHGXZQHDM where DM not like '__0000' and ZT='1' order by DM";
		String jsonStr = "";
		List<HashMap<String, String>> shList = dao.getShList(shSql);
		List<HashMap<String, String>> qxList = dao.getQxList(qxSql);

		List<HashMap> list = new ArrayList<HashMap>();
		List<HashMap> resultShiList = null;
		List<HashMap> resultXiList = null;
		HashMap resultShMap = null;
		HashMap resultShiMap = null;
		HashMap resultXiMap = null;
		for (HashMap<String, String> shMap : shList) {
			String shdm = shMap.get("DM");
			String shmc = shMap.get("MC");

			resultShMap = new HashMap();
			resultShiList = new ArrayList<HashMap>();
			resultShMap.put("treeNode", shmc);
			resultShMap.put("value", shdm);
			resultShMap.put("childNode", resultShiList);
			if(qxList == null || qxList.size() == 0){
				continue;
			}
			for (HashMap<String, String> qxMap : qxList) {
				String qxdm = qxMap.get("DM");
				String qxmc = qxMap.get("MC");
				if (qxdm == null || qxdm.length() < 6 || !qxdm.substring(0, 2).equals(shdm.substring(0, 2)) || qxdm.equals(shdm)) {
					continue;
				}
				if (qxdm.substring(4, 6).equals("00")) {
					resultXiList = new ArrayList<HashMap>();
					resultShiMap = new HashMap();
					resultShiMap.put("treeNode", qxmc);
					resultShiMap.put("value", qxdm);					
					resultShiMap.put("childNode", resultXiList);
					resultShiList.add(resultShiMap);
				} else {
					resultXiMap = new HashMap();
					resultXiMap.put("treeNode", qxmc);
					resultXiMap.put("value", qxdm);
					resultXiList.add(resultXiMap);
				}
			}
			list.add(resultShMap);
		}
		jsonStr = JSONArray.fromObject(list).toString();
		return jsonStr;

	}

}
