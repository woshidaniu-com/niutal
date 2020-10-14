package com.woshidaniu.tjcx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.tjcx.TjcxException;
import com.woshidaniu.tjcx.dao.daointerface.IBbldyDao;
import com.woshidaniu.tjcx.dao.daointerface.ICxzdDao;
import com.woshidaniu.tjcx.dao.daointerface.IKzszDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjbbDao;
import com.woshidaniu.tjcx.dao.daointerface.ITjcxBaseDao;
import com.woshidaniu.tjcx.dao.daointerface.ITsxdyDao;
import com.woshidaniu.tjcx.dao.daointerface.IYsjDao;
import com.woshidaniu.tjcx.dao.entites.BbldyModel;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjbbModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.dao.entites.TsxdyModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjbbService;
import com.woshidaniu.tjcx.service.svcinterface.ITjcxCommon;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public class TjbbServiceImpl extends BaseServiceImpl<TjxmModel, ITjbbDao>
		implements ITjbbService {
	private static final Logger log = LoggerFactory.getLogger(TjbbServiceImpl.class);

	private ITjcxBaseDao tjcxBaseDao;
	private IYsjDao ysjDao;
	private ICxzdDao cxzdDao;
	private IKzszDao kzszDao;
	private IBbldyDao bbldyDao;
	private ITsxdyDao tsxdyDao;
	private ITjcxCommon tjcxCommon;

	
	
	/**
	 * 
	 * @描述:根据项目代码查询相关数据
	 * @作者：ligl
	 * @日期：2013-7-23 下午02:22:57
	 * @修改记录:
	 * @param KzszModel
	 * @return TjbbModel 返回类型
	 * @throws
	 */
	@Override
	public TjbbModel getSjByXmdm(KzszModel kzszModel) {
		TjbbModel tjbbModel = new TjbbModel();
		tjbbModel.setKzszList(kzszDao.getModelList(kzszModel));
		tjbbModel.setCxzdList(cxzdDao.getModelList(new CxzdModel(kzszModel
				.getXmdm(),kzszModel.getZdxsms())));
		tjbbModel.setBblList(bbldyDao.getModelList(new BbldyModel(kzszModel
				.getXmdm())));
		tjbbModel.setTsxList(tsxdyDao.getModelList(new TsxdyModel(kzszModel
				.getXmdm())));
		tjbbModel.setAllYsfList(cxzdDao.getAllYsf());
		return tjbbModel;
	}

	/**
	 * 
	 * @描述:根据设置内容返回报表数据
	 * @作者：ligl
	 * @日期：2013-7-23 下午02:22:57
	 * @修改记录:
	 * @param kzszModel
	 * @return TjbbModel 返回类型
	 * @throws
	 */
	@Override
	public TjbbModel getTjbb(KzszModel kzszModel) throws Exception {
		TjbbModel tjbbModel = new TjbbModel();
		tjbbModel.setCxtj(kzszModel);

		List<String> tsxJsZdList = new ArrayList<String>();

		String tsxSelect = getTsxSelect(kzszModel, tsxJsZdList);// 特殊项经过拼接后的字符串
		tjbbModel.setTsxJsZdList(tsxJsZdList);
		List<BbldyModel> bblList = getBblList(kzszModel);// 报表列集合
		tjbbModel.setBblList(bblList);
		tjbbModel.setTsxList(getTsxList(kzszModel));// 特殊项集合

		tjbbModel.setTjsjList(getTjsjList(kzszModel, tsxSelect, bblList, kzszModel.getLimit()));// 统计数据
		dealData(tjbbModel);
		return tjbbModel;
	}

	/*
	 * 对数据进行处理
	 */
	private void dealData(TjbbModel tjbbModel) {

		// 根据查询条件过滤报表列
		dealDataCxtj(tjbbModel);

		// 设置报表列取值范围
		dealDataBblqzfw(tjbbModel);

		// 对统计数据不合法的参数进行处理
		dealDataTjsj(tjbbModel);
	}

	/*
	 * 设置报表列取值范围
	 */
	private void dealDataBblqzfw(TjbbModel tjbbModel) {
		KzszModel cxtj = tjbbModel.getCxtj();
		String bbzxl = cxtj.getBbzxl();
		String bbhxl = cxtj.getBbhxl();
		HashMap<String, String> bblqzfwMap = new HashMap<String, String>();

		String[] bbzxls = null;
		String[] bbhxls = null;
		if (bbzxl != null && !bbzxl.trim().equals("")) {
			bbzxls = bbzxl.split(Constants.CHAR_DH);
		}
		if (bbhxl != null && !bbhxl.trim().equals("")) {
			bbhxls = bbhxl.split(Constants.CHAR_DH);
		}
		String qzfw = null;
		if (bbzxls != null) {
			for (int i = 0; i < bbzxls.length; i++) {
				qzfw = getQzfwByZdmc(bbzxls[i], tjbbModel);
				if (bbzxls[i] != null && qzfw != null) {
					bblqzfwMap.put(bbzxls[i], qzfw);
				}
			}
		}

		if (bbhxls != null) {
			for (int i = 0; i < bbhxls.length; i++) {
				qzfw = getQzfwByZdmc(bbhxls[i], tjbbModel);
				if (bbhxls[i] != null && qzfw != null) {
					bblqzfwMap.put(bbhxls[i], qzfw);
				}
			}
		}
		tjbbModel.setBblqzfwMap(bblqzfwMap);

	}

	/*
	 * 对统计数据不合法的参数进行处理
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealDataTjsj(TjbbModel tjbbModel) {
		KzszModel cxtj = tjbbModel.getCxtj();
		String bbzxl = cxtj.getBbzxl();
		String bbhxl = cxtj.getBbhxl();

		HashMap<String, String> bblMap = new HashMap<String, String>();

		String[] bbzxls = null;
		String[] bbhxls = null;
		if (bbzxl != null && !bbzxl.trim().equals("")) {
			bbzxls = bbzxl.split(Constants.CHAR_DH);
		}
		if (bbhxl != null && !bbhxl.trim().equals("")) {
			bbhxls = bbhxl.split(Constants.CHAR_DH);
		}
		String qzfwCode = null;
		String qzfw = null;
		if (bbzxls != null) {
			for (int i = 0; i < bbzxls.length; i++) {
				qzfw = getQzfwByZdmc(bbzxls[i], tjbbModel);
				qzfwCode = getCodeFromQzfw(qzfw);
				if (bbzxls[i] != null && !bbzxls[i].trim().equals("")) {
					bblMap.put(bbzxls[i], qzfwCode);
				}
			}
		}

		List<HashMap> tjsjList = tjbbModel.getTjsjList();
		String value = null;
		String[] codes = null;
		for (HashMap tjsjMap : tjsjList) {
			if (bbzxls != null) {
				for (int i = 0; i < bbzxls.length; i++) {
					value = tjsjMap.get(StringUtils.upperCase(bbzxls[i] + ""))
							+ "";
					String codesStr = bblMap.get(bbzxls[i]);
					if(codesStr != null){
						codes = codesStr.split(",");
					}
					if(log.isTraceEnabled()){
						log.trace("codes = {}", codes);
					}
					
					//BUG,太严重了。。。。
					if (!ArrayUtils.contains(codes, value)) {
						if(log.isDebugEnabled()){
							log.debug("纵向列：{}={} 不在代码表中，被标记为未知（WZ）", new Object[]{bbzxls[i], value});
						}

						tjsjMap.put(StringUtils.upperCase(bbzxls[i]),
								Constants.WZ_EN);
						//如果未知，则需要保存未知的值，查询未知详情的时候需要用到
						tjsjMap.put(Constants.WZ_VALUE, value);
					}
				}
			}

			if (bbhxls != null) {
				for (int i = 0; i < bbhxls.length; i++) {
					value = tjsjMap.get(StringUtils.upperCase(bbhxls[i] + ""))
							+ "";
					if (value == null || value.trim().equals("")) {
						tjsjMap.put(StringUtils.upperCase(bbhxls[i]),
								Constants.WZ_CN);
					}
				}
			}
		}
	}

	/*
	 * 根据查询条件过滤报表列
	 */
	private void dealDataCxtj(TjbbModel tjbbModel) {
		KzszModel cxtj = tjbbModel.getCxtj();
		if (cxtj != null) {
			String oldGltj = cxtj.getOldGltj();// 过滤条件
			String newGltj = cxtj.getNewGltj();// 过滤条件
			String bbzxl = cxtj.getBbzxl();// 报表纵向列
			if (bbzxl != null && !bbzxl.trim().equals("")) {
				String[] bbzxls = bbzxl.split(Constants.CHAR_DH);
				if (bbzxls != null) {
					for (int i = 0; i < bbzxls.length; i++) {
						if (StringUtils.contains(oldGltj, bbzxls[i])) {
							String qzfw = getQzfwByZdmc(bbzxls[i], tjbbModel);
							if (qzfw == null) {
								continue;
							}
							// /过滤取值范围
							if (oldGltj != null && !oldGltj.trim().equals("")) {
								qzfw = glQzfw(oldGltj, bbzxls[i], qzfw);
							}
								// /修改后的取值范围保存
							reloadQzfw(bbzxls[i], qzfw, tjbbModel);
						}
						
						if (StringUtils.contains(newGltj, bbzxls[i])) {
							String qzfw = getQzfwByZdmc(bbzxls[i], tjbbModel);
							if (qzfw == null) {
								continue;
							}	
							if (newGltj != null && !newGltj.trim().equals("")) {
								qzfw = glQzfw(newGltj, bbzxls[i], qzfw);
							}
							// /修改后的取值范围保存
							reloadQzfw(bbzxls[i], qzfw, tjbbModel);

						}
					}
				}

			}

		}
	}

	/*
	 * 修改后的取值范围保存
	 */
	private void reloadQzfw(String zdmc, String qzfw, TjbbModel tjbbModel) {
		List<BbldyModel> bblList = tjbbModel.getBblList();
		for (BbldyModel bbldyModel : bblList) {
			if (bbldyModel.getZdmc() != null
					&& bbldyModel.getZdmc().equalsIgnoreCase(zdmc)) {
				bbldyModel.setQzfw(qzfw);
			}
		}
	}

	/*
	 * 根据字段名称获取取值范围
	 */
	private String getQzfwByZdmc(String zdmc, TjbbModel tjbbModel) {
		String qzfw = null;
		List<BbldyModel> bblList = tjbbModel.getBblList();
		for (BbldyModel bbldyModel : bblList) {
			if (bbldyModel.getZdmc() != null
					&& bbldyModel.getZdmc().equalsIgnoreCase(zdmc)) {
				qzfw = bbldyModel.getQzfw();
			}
		}
		return qzfw;
	}

	/*
	 * 根据字段名称获取取值范围
	 */
	private String getCodeFromQzfw(String qzfw) {
		StringBuffer codes = new StringBuffer();
		String[] qzfws = qzfw.split(Constants.CHAR_DH);
		for (int i = 0; i < qzfws.length; i++) {
			if (qzfws[i] != null) {
				String[] qzfwDgs = qzfws[i].split("\\:");
				if (qzfwDgs != null && qzfwDgs.length > 0) {
					codes.append(qzfwDgs[0]);
					codes.append(Constants.CHAR_DH);
				}
			}
		}
		return codes.toString();
	}

	/*
	 * 过滤取值范围,简化处理，....主要针对"等于"情况
	 */
	private String glQzfw(String gltj, String zdmc, String qzfw) {
		List<String> qzfwList = new ArrayList<String>();
		String qzfwReturn = "";
		String[] qzfws = qzfw.split(Constants.CHAR_DH);
		gltj = gltj.replaceAll("or", "and");
		String[] gltjs = gltj.split("and");
		String gltjsTemp = "";
		for (int i = 0; i < gltjs.length; i++) {
			if (gltjs[i].contains(zdmc)) {
				gltjsTemp += gltjs[i];
			}
		}
		for (int i = 0; i < qzfws.length; i++) {
			if (qzfws[i] != null) {
				String[] qzfwDgs = qzfws[i].split("\\:");
				if (qzfwDgs != null && qzfwDgs.length > 1) {
					
					if (!StringUtils.contains(gltjsTemp, "<>")){
						String re6="(\\'(.*)\\')";	// Single Quote String 1
					    Pattern p = Pattern.compile(re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					    
					    Matcher m = p.matcher(gltjsTemp);
					    
					    if (m.find())
					    {
					        String var1=m.group(1);
					        if(StringUtils.isNoneBlank(var1)){
					        	String[] split = StringUtils.split(var1, Constants.CHAR_DH);
					        	
					        	 for (String string : split) {
					        		 if (StringUtils.equals(string, "'" + qzfwDgs[0] + "'")) {
											qzfwList.add(qzfws[i]);
										}
								}
					        	
					        }
					    }			
						
					} else {
						String re6="\\'(.*)\\'";	// Single Quote String 1
					    Pattern p = Pattern.compile(re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					    
					    Matcher m = p.matcher(gltjsTemp);
					    if (m.find())
					    {
					        String var1=m.group(1);
				        	String trim = StringUtils.trim(var1);
			        		if (!StringUtils.equals(trim , "'" + qzfwDgs[0] + "'")) {
			        				qzfwList.add(qzfws[i]);
								}
					    }
					}
					
				}
			}

		}
		boolean flag = false;
		for (String qzfwDg : qzfwList) {
			if (flag) {
				qzfwReturn += Constants.CHAR_DH;
			} else {
				flag = true;
			}
			qzfwReturn += qzfwDg;
		}
		return qzfwReturn;
	}

	/*
	 * 获取报表列统计数据
	 */
	private List<BbldyModel> getBblList(KzszModel kzszModel) {
		List<BbldyModel> list = new ArrayList<BbldyModel>();
		HashMap<String, String> map = new HashMap<String, String>();
		String bbls = hxzxpj(kzszModel);
		if (StringUtils.isNotEmpty(bbls)) {
			bbls = bbls.replaceAll(Constants.CHAR_DH, Constants.CHAR_DYH
					+ Constants.CHAR_DH + Constants.CHAR_DYH);
			bbls = Constants.CHAR_DYH + bbls + Constants.CHAR_DYH;
			map.put("xmdm", kzszModel.getXmdm());
			map.put("bbls", bbls);
			list = bbldyDao.getListByTj(map);
			setQzfw(list, kzszModel);// 设置报表列取值范围
		}
		return list;
	}

	/*
	 * 设置报表列取值范围
	 * TODO
	 * 需要根据联动关系，筛选数据
	 */
	private void setQzfw(List<BbldyModel> list, KzszModel kzszModel) {
		BbldyModel bbldyModel = null;
		for (int i = 0; i < list.size(); i++) {
			bbldyModel = list.get(i);
			if (StringUtils.isNotEmpty(bbldyModel.getQzlx())) {
				if (bbldyModel.getQzlx().equals(Constants.FLAG_QZLX_SJKQZ)) {// 取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
					bbldyModel.setQzfw(tjcxCommon
							.getSjkqz(bbldyModel.getQzfw()));
				} else if (bbldyModel.getQzlx()
						.equals(Constants.FLAG_QZLX_FFQZ)) {// 取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
					bbldyModel
							.setQzfw(tjcxCommon.getFfqz(bbldyModel.getQzfw()));
				}
				list.set(i, bbldyModel);
			}
		}
	}

	/*
	 * 获取特殊项统计数据
	 */
	private List<TsxdyModel> getTsxList(KzszModel kzszModel) {

		List<TsxdyModel> list = new ArrayList<TsxdyModel>();
		if (StringUtils.isNotEmpty(kzszModel.getTsx())) {
			HashMap<String, String> map = new HashMap<String, String>();
			String tsxmcs = kzszModel.getTsx()
					.replaceAll(
							Constants.CHAR_DH,
							Constants.CHAR_DYH + Constants.CHAR_DH
									+ Constants.CHAR_DYH);
			tsxmcs = Constants.CHAR_DYH + tsxmcs + Constants.CHAR_DYH;
			map.put("xmdm", kzszModel.getXmdm());
			map.put("tsxmcs", tsxmcs);
			list = tsxdyDao.getListByTj(map);
		}
		return list;
	}

	/*
	 * 获取统计数据
	 */
	@SuppressWarnings("rawtypes")
	private List<HashMap> getTjsjList(KzszModel kzszModel, String tsxSelect,
			List<BbldyModel> bblList, int limit) throws Exception {
		List<HashMap> list = new ArrayList<HashMap>();
		StringBuffer sb = new StringBuffer();
		YsjModel ysjModel = ysjDao.getModel(kzszModel);
		if (ysjModel != null) {
			tjcxCommon.setSjfw(ysjModel);//设置数据范围
			String ysjSql = ysjModel.getSql();
			if (ysjSql != null && !ysjSql.trim().equals("")) {
				String hxzxpj = hxzxpj(kzszModel);
				sb.append("select ");
				sb.append(hxzxpj);
				sb.append(",count(1) zs ");
				sb.append(tsxSelect);
				sb.append(" from ");
				ysjSql = qjkSet(hxzxpj, bblList, ysjSql);
				sb.append(ysjSql);
				sb.append(" where 1=1 ");

				String gltj1 = tjcxCommon.setGltj(sb.toString(), kzszModel
						.getGltj());// 设置过滤条件
				sb = new StringBuffer(gltj1);

				sb.append(" group by ");
				sb.append(hxzxpj);
				sb.append(" order by ");
				sb.append(hxzxpj);

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("sql", sb.toString());
				if(limit >= 0){
					//先统计一下数据量，如果查过配置数据，直接抛异常，提示用户数据量过大
					String countSql = "select count(*) from ( " + sb.toString() + " )";
					HashMap<String,String> countParam = new HashMap<String, String>();
					countParam.put("sql", countSql);
					int dataCount = tjcxBaseDao.getDataCount(countParam);
					if(dataCount > limit){
						throw new TjcxException("统计数据量超多阈值.");
					}
				}
				
				try {
					//if (ysjModel.getSjfwlx() != null
					//		&& ysjModel.getSjfwlx().equals(
					//				Constants.FLAG_SJFWLX_XKJ)) {// 新框架
					//	list = tjcxBaseDao.getListBySqlByScope(map);
					//} else {
						list = tjcxBaseDao.getListBySql(map);
					//}
				} catch (Exception e) {
					log.error("统计报表-获取统计数据报错：sql-" + sb.toString() + " "
							+ e.getMessage());
				}
			} else {
				log.error("统计报表-源数据未配置！");
			}
		}
		return list;
	}

	/*
	 * 针对区间块类型，转化源数据sql
	 */
	private String qjkSet(String hxzxpj, List<BbldyModel> bblList, String ysjSql) {
		String qjlx = null;// 区间类型
		String qzfw = null;// 区间范围
		String zdmc = null;// 字段名称 tjcxqjk_nl
		String sjkzdmc = null;// 数据库实际字段名称，如：年龄nl

		StringBuffer caseSb = new StringBuffer();

		String qjkdm = null;// 区间块代码
		String qjkValue = null;// 区间块值

		String[] qzfws = null;// 取值范围逗号分割
		String[] dmmc = null;// 取值范围，每一个具体项冒号分割，dm:mc

		StringBuffer sb = new StringBuffer();// 拼接后
		if (ysjSql.trim().toLowerCase().substring(0, 6).equals("select")) {
			ysjSql = "(" + ysjSql + ")";
		}
		ysjSql += " ysj";

		if (bblList != null && bblList.size() > 0 && hxzxpj != null) {
			for (BbldyModel bbldyModel : bblList) {
				qjlx = bbldyModel.getQzlx();
				if (qjlx == null || !qjlx.equals(Constants.FLAG_QZLX_QJK)) {// 非区间块类型
					continue;
				}
				zdmc = bbldyModel.getZdmc();
				if (!StringUtils.contains(hxzxpj, zdmc)) {// 界面未选该报表列字段
					continue;
				}

				qzfw = bbldyModel.getQzfw();
				if (qzfw == null || qzfw.trim().equals("")) {// 取值范围为空
					continue;
				}

				if (zdmc.length() < Constants.TJCXQJK.length()) {// 区间块字段配置不合法
					continue;
				}
				sjkzdmc = zdmc.substring(Constants.TJCXQJK.length());

				qzfws = qzfw.split(Constants.CHAR_DH);// 取值范围逗号分割

				for (int i = 0; i < qzfws.length; i++) {
					dmmc = qzfws[i].split(Constants.CHAR_MH_REG);// 取值范围，每一个具体项冒号分割，dm:mc
					if (dmmc == null || dmmc.length == 0) {// 不合法配置，忽略
						continue;
					}
					String qjkMinValue = null;// 区间块值，最小值
					String qjkMaxValue = null;// 区间块值，最大值
					qjkdm = dmmc[0];
					qjkValue = qjkdm.substring(sjkzdmc.length()).trim();// 去掉开头的数据库字段名称，nlto20->to20
					String[] qjkValues = qjkValue.split(Constants.TJCXQJK_TO);
					if (qjkValues != null) {
						qjkMinValue = qjkValue.split(Constants.TJCXQJK_TO)[0];
						if (qjkValues.length > 1) {
							qjkMaxValue = qjkValue.split(Constants.TJCXQJK_TO)[1];
						}
					}
					caseSb.append(" when ");
					boolean isInMinValue = false;// 是否包含最小值
					if (qjkMinValue != null && !qjkMinValue.trim().equals("")) {
						caseSb.append(" to_number(");
						caseSb.append(sjkzdmc);
						caseSb.append(")>=");
						caseSb.append(qjkMinValue);
						isInMinValue = true;
					}
					if (qjkMaxValue != null && !qjkMaxValue.trim().equals("")) {
						if (isInMinValue) {
							caseSb.append(" and ");
						}
						caseSb.append(" to_number(");
						caseSb.append(sjkzdmc);
						caseSb.append(")<");
						caseSb.append(qjkMaxValue);
					}
					caseSb.append(" then '");
					caseSb.append(qjkdm);
					caseSb.append("'");
				}

				if (caseSb.length() > 0) {// 包含区间块
					caseSb.insert(0, ",(case  ");
					caseSb.append(" end ) ");
					caseSb.append(zdmc);
				}
			}
		}
		if (caseSb.length() > 0) {// 包含区间块
			sb.insert(0, "(select ysj.*");
			sb.append(caseSb.toString());
			sb.append(" from ");
			sb.append(ysjSql);
			sb.append(")");
		} else {
			sb.append(ysjSql);
		}
		// ysjSql = "("
		// +
		// "select ysj.*,(case   when to_number(nl) < 20 then  'lt20'  when to_number(nl) >= 20 and to_number(nl) < 30 then  '20to30' when to_number(nl) >= 30 and to_number(nl) < 40 then  '30to40'  when to_number(nl) >= 40 then 'gt40'  end  ) tjcxqjk_nl from "
		// + ysjSql + ")";

		return sb.toString();
	}

	/*
	 * 得到特殊项select
	 */
	private String getTsxSelect(KzszModel kzszModel, List<String> tsxJsZdList) {
		String tsxSelect = "";
		List<String> tsxZdList = new ArrayList<String>();// 特殊项字段
		List<String> tsxJsList = new ArrayList<String>();// 特殊项计算项max,min...
		if (kzszModel != null) {
			String tsx = kzszModel.getTsx();
			if (tsx != null) {
				String[] tsxs = tsx.split(Constants.CHAR_DH);
				if (tsxs != null) {
					for (int i = 0; i < tsxs.length; i++) {
						if (!StringUtils.contains(Constants.GD_TSX, tsxs[i])) {
							if (StringUtils.contains(Constants.GD_TSX_JS,
									tsxs[i])) {
								tsxJsList.add(tsxs[i]);
							} else {
								tsxZdList.add(tsxs[i]);
							}
						}
					}
				}
			}
		}
		for (String tsxZd : tsxZdList) {
			for (String tsxJs : tsxJsList) {
				if(StringUtils.equals(tsxJs, Constants.GD_TSX_JS_PTG)){
					tsxSelect += ", 0 " + tsxJs + Constants.CHAR_FGF_BS + tsxZd;
				}else{
					tsxSelect += "," + tsxJs + "(to_number(" + tsxZd + ")) "
					+ tsxJs + Constants.CHAR_FGF_BS + tsxZd;
				}
				tsxJsZdList.add(tsxJs + Constants.CHAR_FGF_BS + tsxZd);
			}
		}
		return tsxSelect;
	}

	/*
	 * 横向列与纵向列拼接
	 */
	private String hxzxpj(KzszModel kzszModel) {
		StringBuffer sb = new StringBuffer();
		if (kzszModel != null) {
			if (kzszModel.getBbhxl() != null
					&& !kzszModel.getBbhxl().trim().equals("")) {
				sb.append(kzszModel.getBbhxl());
			}
			if (kzszModel.getBbzxl() != null
					&& !kzszModel.getBbzxl().trim().equals("")) {
				if (kzszModel.getBbhxl() != null
						&& !kzszModel.getBbhxl().trim().equals("")) {
					sb.append(",");
				}
				sb.append(kzszModel.getBbzxl());
			}
		}

		return sb.toString();
	}

	public IYsjDao getYsjDao() {
		return ysjDao;
	}

	public void setYsjDao(IYsjDao ysjDao) {
		this.ysjDao = ysjDao;
	}

	public ITjcxBaseDao getTjcxBaseDao() {
		return tjcxBaseDao;
	}

	public void setTjcxBaseDao(ITjcxBaseDao tjcxBaseDao) {
		this.tjcxBaseDao = tjcxBaseDao;
	}

	public ICxzdDao getCxzdDao() {
		return cxzdDao;
	}

	public void setCxzdDao(ICxzdDao cxzdDao) {
		this.cxzdDao = cxzdDao;
	}

	public IKzszDao getKzszDao() {
		return kzszDao;
	}

	public void setKzszDao(IKzszDao kzszDao) {
		this.kzszDao = kzszDao;
	}

	public ITsxdyDao getTsxdyDao() {
		return tsxdyDao;
	}

	public void setTsxdyDao(ITsxdyDao tsxdyDao) {
		this.tsxdyDao = tsxdyDao;
	}

	public IBbldyDao getBbldyDao() {
		return bbldyDao;
	}

	public void setBbldyDao(IBbldyDao bbldyDao) {
		this.bbldyDao = bbldyDao;
	}

	public ITjcxCommon getTjcxCommon() {
		return tjcxCommon;
	}

	public void setTjcxCommon(ITjcxCommon tjcxCommon) {
		this.tjcxCommon = tjcxCommon;
	}

}
