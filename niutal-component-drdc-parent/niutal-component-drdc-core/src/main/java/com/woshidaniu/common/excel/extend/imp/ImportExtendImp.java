package com.woshidaniu.common.excel.extend.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.woshidaniu.common.excel.extend.IImportExtend;
import com.woshidaniu.dao.entities.ImportModel;
import com.woshidaniu.service.impl.ImportServiceImpl;

public class ImportExtendImp extends ImportServiceImpl implements IImportExtend {
	/**
	 * 扩展验证唯一key
	 */
	private static final String _DRYZBH_EXTEND = "DR0014";
	/**
	 * 使用'超级'扩展模式（不使用每次从数据库查询）的阀值
	 */
	private static final int _KQ_SUPER=100;
	/**
	 * 表配置验证信息列名称
	 */
	private static final String _YZCS_KEY = "YZCS";

	@SuppressWarnings( { "unused", "unchecked" })
	@Override
	public Map<String, Object> fomartForExtend(ImportModel model,
			Map<String, Object> data) {
		try {
			List<List<ImportModel>> succeedList = (List<List<ImportModel>>) data
					.get("succeedList");
			List<String[]> errorList = (List<String[]>) data.get("errorList");
			// 获取实际导入表的具体列
			List<ImportModel> importColumnList = getImportColumnList(model
					.getDrmkdm(), model.getDrbm());
			// 获取需要特殊验证的列配置
			List<Map<String, String>> pzxxlist = getPzxx(model);
			// 如果为空直接返回，无需特殊验证
			if (null == pzxxlist || pzxxlist.isEmpty()) {
				return data;
			}
			String yzcs = null;
			// 已经扩展字段对象
			Map<String, ImportModel> ykzzd = new HashMap<String, ImportModel>();
			Map<String, String> pzxx = null;
			String yzcss[];
			ImportModel extendModel = null;
			List<List<ImportModel>> fomartSucceedList = new ArrayList<List<ImportModel>>();
			List<ImportModel> fomartList = null;
			String value = null;
			for (List<ImportModel> list : succeedList) {
				ykzzd.clear();// 清空生成的记录
				fomartList = new ArrayList<ImportModel>();
				// 处理显示模式扩展配置
				for (ImportModel im : list) {
					pzxx = getExtendZdXx(pzxxlist, im);
					if (null != pzxx && !pzxx.isEmpty()) {
						if (succeedList.size() > _KQ_SUPER) {
							value = getValueForPzxxSuper(im, list, pzxx
									.get(_YZCS_KEY));
						} else {
							value = getValueForPzxx(im, list, pzxx
									.get(_YZCS_KEY));
						}
						if (null != value) {
							im.setValue(value);
						}
						// 记录已处理扩展
						ykzzd.put(pzxx.get("xssx"), im);
					}
					fomartList.add(im);
				}
				// 处理隐藏模式扩展配置
				for (Map<String, String> map : pzxxlist) {
					// 如果没有符合的列，则认为 是 ‘隐藏 模式’（模板中不显示）
					if (null == ykzzd.get(map.get("xsxx"))) {
						extendModel = new ImportModel();
						extendModel.setZdlx(map.get("ZDLX"));
						extendModel.setZdm(map.get("ZDM"));
						if (succeedList.size() > _KQ_SUPER) {
							value = getValueForPzxxSuper(extendModel, list, map
									.get(_YZCS_KEY));
						} else {
							value = getValueForPzxx(extendModel, list, map
									.get(_YZCS_KEY));
						}
						if (null != value) {
							extendModel.setValue(value);
						}
						fomartList.add(extendModel);
					}
				}
				fomartSucceedList.add(fomartList);
			}
			data.put("succeedList", fomartSucceedList);
			return data;
		} catch (Exception e) {
			throw new RuntimeException("生成隐藏扩展配置信息对象失败！" + e.getMessage(), e);
		}
	}

	/**
	 * 根据配置信息和行数据 获取对应格式化后的数据
	 * 
	 * @param list
	 * @param yzcs
	 * @return
	 */
	private String getValueForPzxx(ImportModel im, List<ImportModel> list,
			String yzcs) {
		yzcs = fomartYzsc(list, yzcs);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("sql", yzcs);
		List<Map<String, String>> v = dao.getPzxxValue(hashMap);
		if (v == null || v.size() <= 0) {
			return null;
		}
		return v.get(0).get(im.getZdm().toUpperCase());
	}

	/**
	 * 根据配置信息和行数据 获取对应格式化后的数据 考虑每次通过sql查询数据库，条数多了，会有很多数据库连接交互，
	 * 这里使用此方法，每个线程只查询一次，条件过滤，使用java来实现。
	 * 
	 * @param im
	 * @param list
	 * @param yzcs
	 * @return
	 */
	// 总数据 小缓存
	private Map<String, List<Map<String, String>>> yzcsHc = new HashMap<String, List<Map<String, String>>>();

	private String getValueForPzxxSuper(ImportModel im, List<ImportModel> list,
			String yzcs) {
		Map<String, String> param = getParams(list, yzcs);
		yzcs = yzcs.substring(0, yzcs.indexOf("where"));
		yzcs="select * from "+yzcs.substring(yzcs.indexOf("from")+5);
		List<Map<String, String>> v = null;
		// 如果已经存在此配置sql的总数据，直接获取
		if (null != yzcsHc.get(yzcs)) {
			v = yzcsHc.get(yzcs);
		} else {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("sql", yzcs);
			v = dao.getPzxxValue(hashMap);
		}
		if (v == null || v.size() <= 0) {
			return null;
		}
		Map<String, String> oracleMap = new HashMap<String, String>();
		for (Map<String, String> map : v) {
			boolean ischeck = true;
			Iterator<Entry<String, String>> it = param.entrySet().iterator();
			// 通过配置的sql过滤条件，过滤总数据中的数据
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				// 如果此列的值在当前行中存在
				if (ischeck
						&& map.get(entry.getKey().toUpperCase()).equals(
								entry.getValue())) {
					ischeck = true;
				} else {
					ischeck = false;
				}
			}
			// 全部验证通过
			if (ischeck) {
				oracleMap = map;
				break;
			}
		}
		yzcsHc.put(yzcs, v);
		return oracleMap.get(im.getZdm().toUpperCase());
	}

	private Map<String, String> getParams(List<ImportModel> list, String yzcs) {
		Map<String, String> param = new HashMap<String, String>();
		String[] yzcss = yzcs.split("#");
		StringBuffer yzcsNew = new StringBuffer();
		int start = -1;
		int end = -1;
		String key;
		String value;
		for (String str : yzcss) {
			start = str.indexOf("{");
			end = str.indexOf("}");
			if (start > -1 && end > -1) {
				key = str.substring(start + 1, end);
				value = getValue(list, key);
				yzcsNew.append(value);
				param.put(key, value);
			} else {
				yzcsNew.append(str);
			}
			if (end != -1 && end != str.length()) {
				yzcsNew.append(str.subSequence(end + 1, str.length()));
			}
		}
		return param;
	}

	/**
	 * 获取具体扩展验证参数配置信息
	 * 
	 * @param yzcs
	 *            原配置参数信息</br>data[select bmmc from niutal_xtgl_bmdmb where
	 *            bmdm_id=#{bmdm_id}]
	 * @return
	 */
	private String getYzcsDataPzxx(String yzcs) {
		return yzcs.substring(yzcs.indexOf("[") + 1, yzcs.indexOf("]"));
	}

	/**
	 * 获取字段扩展配置信息
	 * 
	 * @param pzxxlist
	 *            扩展配置信息集合
	 * @param im
	 *            当前的字段
	 * @return
	 */
	private Map<String, String> getExtendZdXx(
			List<Map<String, String>> pzxxlist, ImportModel im) {
		for (Map<String, String> map : pzxxlist) {
			if (map.get("ZDM").equals(im.getZdm())) {
				return map;
			}
		}
		return null;
	}

	/**
	 * 根据行数据设置格式化参数
	 * 
	 * @param list
	 *            行数据
	 * @param yzcs
	 *            配置的格式化参数
	 */
	public String fomartYzsc(List<ImportModel> list, String yzcs) {
		// yzcs = "select * from table where a=#{b} and b=#{c}";
		String[] yzcss = yzcs.split("#");
		StringBuffer yzcsNew = new StringBuffer();
		int start = -1;
		int end = -1;
		for (String str : yzcss) {
			start = str.indexOf("{");
			end = str.indexOf("}");
			if (start > -1 && end > -1) {
				yzcsNew.append(getValue(list, str.substring(start + 1, end)));
			} else {
				yzcsNew.append(str);
			}
			if (end != -1 && end != str.length()) {
				yzcsNew.append(str.subSequence(end + 1, str.length()));
			}
		}
		return yzcsNew.toString();
	}

	/**
	 * 获取对应的导入值
	 * 
	 * @param list
	 *            当前行数据
	 * @param bl
	 *            变量值
	 * @return
	 */
	public String getValue(List<ImportModel> list, String bl) {
		for (ImportModel im : list) {
			if (bl.equals(im.getZdm())) {
				if (null == im.getValue()) {
					return null;
				}
				return im.getValue().toString();
			}
		}
		return null;
	}

	private List<Map<String, String>> getPzxx(ImportModel model) {
		model.setDryzbh(_DRYZBH_EXTEND);
		// select zdm,yzcs from zfxg_dr_drbmpzb where dryzbh=${dryzbh} and
		// drmkdm=${drmkdm} and drbm=${drbm}
		return dao.getPzxx(model);
	}

	public static void main(String[] args) {
		String a = "select * from table where a=#{b} and b=#{c}";
		String[] yzcss = a.split("#");
		StringBuffer yzcs = new StringBuffer();
		int start = -1;
		int end = -1;
		for (String str : yzcss) {
			start = str.indexOf("{");
			end = str.indexOf("}");
			if (start > -1 && end > -1) {
				System.out.println(str.substring(start + 1, end));
			} else {
				yzcs.append(str);
			}
		}
	}
}
