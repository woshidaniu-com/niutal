package com.woshidaniu.search.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.search.dao.daointerface.ISearchDao;
import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.dao.entities.SearchModel;
import com.woshidaniu.search.service.SearchSqlBuilder;
import com.woshidaniu.search.service.SearchTypeEnum;
import com.woshidaniu.search.service.svcinterface.ISearchService;
import com.woshidaniu.util.base.StringUtil;
import com.woshidaniu.util.reflect.ReflectHelper;

import net.sf.json.JSONObject;

/**
 * 高级查询实现
 * 
 * @author Penghui.Qu
 * 
 */
@Service(value="superSearchService")
public class SearchServiceImpl implements ISearchService {

	@Resource
	private ISearchDao searchDao;
	private static final String SCOPE_ALL = "admin";
	private static final String P_METHOD = "method_";
	private static final String P_TABLE = "table_";

	public void setSearchDao(ISearchDao searchDao) {
		this.searchDao = searchDao;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.search.service.svcinterface.ISearchService#buildLinkageScript
	 * (java.lang.String)
	 */
	//@Cacheable(value=BASIC_CACHE,key="#sign + 'buildLinkageScript'")
	public String buildLinkageScript(String sign) {

		SearchLinkageModel model = new SearchLinkageModel();
		model.setSearchSign(sign);

		List<SearchLinkageModel> linkageList = searchDao.getLinkageList(model);

		StringBuilder initLinkage = new StringBuilder();
		initLinkage.append("function initLinkage(){\r\n");

		StringBuilder script = new StringBuilder();
		script.append("var linkageMap = {};\r\n");
		script.append("linkageMap[\"searchSign\"]='").append(sign).append(
				"';\r\n");

		for (SearchLinkageModel linkageModel : linkageList) {
			String selectKey = linkageModel.getLinkageName();
			String linkageFunName = new StringBuilder(selectKey).append("_")
					.append(linkageModel.getNextName()).toString();
			// 动态创建联动函数
			script.append("function ").append(linkageFunName).append("(){");
			script.append("		var options = jQuery(\"dd[name=select_").append(
					selectKey).append("]\");\r\n");
			script.append("		var values = [];\r\n");
			script.append("		for (var i = 0 ; i < options.length ; i++){\r\n");
			script.append("			values.push(options.eq(i).attr(\"value\"));\r\n");
			script.append("		}\r\n");
			script.append("		linkageMap[\"").append(selectKey).append(
					"\"]=values.join('!search!');\r\n");
			script.append("		linkageMap[\"nextName\"]='").append(
					linkageModel.getNextName()).append("';");
			;
			script.append(" 	linkage(linkageMap,'").append(
					linkageModel.getNextName()).append("');\r\n");
			script.append("}\r\n ");

			// 初始化选择项联动函数（为有联动关系的选择项绑定联动函数）
			initLinkage.append("jQuery(\"dd[name=select_").append(selectKey)
					.append("]\").unbind(\"click\",").append(linkageFunName)
					.append(");");
			initLinkage.append("jQuery(\"dd[name=select_").append(selectKey)
					.append("]\").bind(\"click\",").append(linkageFunName)
					.append(");");

			initLinkage.append("jQuery(\"#dd_").append(selectKey).append(
					" a[name!=a_more]\").unbind(\"click\",").append(
					linkageFunName).append(");");
			initLinkage.append("jQuery(\"#dd_").append(selectKey).append(
					" a[name!=a_more]\").bind(\"click\",").append(
					linkageFunName).append(");");
		}

		initLinkage.append("}");

		script.append(initLinkage);
		return script.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.search.service.svcinterface.ISearchService#buildLinkageContent
	 * (com.woshidaniu.search.dao.entities.SearchConfigModel, java.util.List)
	 */
	public String buildLinkageContent(SearchConfigModel config,
			List<SearchLinkageModel> linkageList,int pageNumber) throws Exception {

		if (linkageList == null || linkageList.size() == 0) {
			throw new Exception("不存在联动关系！");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder();
		SearchConfigModel searchConfig = searchDao.getSearchConfig(config);
		String source = searchConfig.getValueSource();

		if (source != null && source.startsWith(P_TABLE)) {
			DataSourceModel dsm = searchDao.getDataSourceInfo(source.replace(P_TABLE, ""));
			map.put("datasource", dsm);
		}

		for (int j = 0; j < linkageList.size(); j++) {
			String relatingName = linkageList.get(j).getRelatingName();
			String linkageValue = linkageList.get(j).getLinkageValue();

			if (StringUtil.isEmpty(linkageValue)) {
				continue;
			}

			String[] valueArray = linkageValue.split("!search!");

			query.append(" and (");
			
			for (int i = 0; i < valueArray.length; i++) {

				String paramKey = "linkage_param_" + j + "_" + i;
				map.put(paramKey, valueArray[i]);

				query.append(relatingName);
				query.append("=#{").append(paramKey).append("}");

				if (i != valueArray.length - 1) {
					query.append(" or ");
				}
			}
			query.append(")");
		}

		map.put("linkage_sql", query.toString());
		map.put("pageNumber", pageNumber);
		map.put("pageSize", searchConfig.getMaxRows());

		List<DataSourceModel> linkageDataList = null;
		User user = SessionFactory.getUser();
		
		if (SCOPE_ALL.equals(user.getYhm())){
			linkageDataList = searchDao.getLinkageData(map);
		} else {
			linkageDataList = searchDao.getLinkageDataByScope(map);
		}
		
		if (pageNumber > 0){
			return createSimpleHtml(searchConfig, linkageDataList,pageNumber);
		}
		
		StringBuilder html = new StringBuilder();
		html.append("<dl><dt>").append(searchConfig.getSearchLabel());
		html.append("：</dt><dd id=\"dd_");
		
		if (StringUtil.isNotEmpty(searchConfig.getSearchByname())){
			html.append(searchConfig.getSearchByname());
		} else {
			html.append(searchConfig.getSearchName());
		}
			
		html.append("\"><ul>");
		html.append(createSimpleHtml(searchConfig, linkageDataList,0));
		html.append("</ul>");
		html.append("</dd>");
		html.append("</dl>");
		
		return html.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.search.service.svcinterface.ISearchService#buildSelectHtml
	 * (java.lang.String)
	 */
//	@Cacheable(value=BASIC_CACHE,key="#sign + 'buildSelectHtml'")
	public String buildSelectHtml(String sign) throws Exception {

		StringBuilder html = new StringBuilder();

		SearchConfigModel config = new SearchConfigModel();
		config.setSearchSign(sign);
		config.setSearchType(SearchTypeEnum.TJCX.toString());

		// 查询所配置的查询条件
		List<SearchConfigModel> searchList = searchDao.getSearchConfigList(config);

		for (int i = 0; searchList != null && i < searchList.size(); i++) {
			SearchConfigModel searchConfigModel = searchList.get(i);
			html.append(getOptionsHtml(searchConfigModel,0));
		}

		html.append(buildSelectOtherHtml(sign));// 创建其他类型的条件,20131111
		return html.toString();
	}


	/*获取选项*/
	private String getOptionsHtml(SearchConfigModel searchConfigModel,int pageNumber) throws Exception {
		List<DataSourceModel> optionsList = null;
		String dataSource = searchConfigModel.getValueSource();
		// 如果数据源表达式为空，或者不满足table_开始且不满足method_开始
		if (StringUtil.isEmpty(dataSource) || (!dataSource.startsWith(P_TABLE) && !dataSource.startsWith(P_METHOD))) {
			throw new RuntimeException();
		}
		int start = 0;
		if (dataSource.startsWith(P_TABLE)) {
			String tableName = dataSource.replace(P_TABLE, "");
			DataSourceModel model = searchDao.getDataSourceInfo(tableName);
			//model.setPageSize(Integer.valueOf(searchConfigModel.getMaxRows()));
			//model.setPageNumber(pageNumber);
			
			User user = SessionFactory.getUser();
			
			if (SCOPE_ALL.equals(user.getYhm())){
				optionsList = searchDao.getInitialList(model);
			} else {
				optionsList = searchDao.getInitialListByScope(model);
			}
		} else {
			optionsList = getOptionsListByMethod(dataSource);
			start = -1;
		}
		if (pageNumber > 0){
			return createSimpleHtml(searchConfigModel, optionsList,pageNumber);
		}
		StringBuilder html = new StringBuilder();
		html.append("<dl><dt>").append(searchConfigModel.getSearchLabel());
		html.append("：</dt><dd id=\"dd_");
		
		if (StringUtil.isNotEmpty(searchConfigModel.getSearchByname())){
			html.append(searchConfigModel.getSearchByname());
		} else {
			html.append(searchConfigModel.getSearchName());
		}
		html.append("\"><ul>");
		html.append(createSimpleHtml(searchConfigModel, optionsList,start));
		html.append("</ul>");
		html.append("</dd>");
		html.append("</dl>");
		return html.toString();
	}

	@SuppressWarnings("unchecked")
	private List<DataSourceModel> getOptionsListByMethod(String dataSource)
			throws Exception {
		String methodInfo = dataSource.replace("method_", "");
		String[] infoArray = methodInfo.split("#");
		if (infoArray.length < 2) {
			throw new Exception("非法参数");
		}
		String beanId = infoArray[0];
		String methodName = infoArray[1];
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();

			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(
						methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(
						methodName, new Class[] { String.class }).invoke(o,
						param);
			}
			return convertMap(list);
		} catch (Exception e) {
			throw e;
		}
	}

	// 转换数据格式
	private List<DataSourceModel> convertMap(List<HashMap<String, String>> list) {
		List<DataSourceModel> dataList = new ArrayList<DataSourceModel>();
		return dataList;
	}


	/*
	 * 创建简单（不包含拼音首字母的查询条件 ）
	 */
	private String createSimpleHtml(SearchConfigModel config,
			List<DataSourceModel> optionsList,int pageNumber) throws Exception {
		StringBuilder html = new StringBuilder();
		// 更多按钮
		if (optionsList!=null && optionsList.size() >= Integer.valueOf(config.getMaxRows()) && pageNumber != -1) {
			html.append("<li name='li_inline'>")
				.append("<a name='a_more' href='javascript:void(0)' class='moreValue_click' onclick=\"getMoreOption('")
				.append(StringUtil.isEmpty(config.getSearchByname()) ? config.getSearchName() : config.getSearchByname())
				.append("',"+pageNumber+");return false;\">")
				.append("<font>更多</font></a></li>");
		}
		return html.toString();
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#buildBlurHtml(java.lang.String)
	 */
	//@Cacheable(value=BASIC_CACHE,key="#sign + 'buildBlurHtml'")
	public String buildBlurHtml(String sign) throws Exception {
		StringBuilder html = new StringBuilder();
		SearchConfigModel config = new SearchConfigModel();
		config.setSearchSign(sign);
		config.setSearchType(SearchTypeEnum.MHCX.toString());
		List<SearchConfigModel> searchList = searchDao.getSearchConfigList(config);
		for (int i = 0; searchList != null && i < searchList.size(); i++) {
			SearchConfigModel searchConfigModel = searchList.get(i);
			html.append("<div name=\"blurType\" value=\"")
				.append(searchConfigModel.getSearchName())
				.append("\">");
			html.append(searchConfigModel.getSearchLabel());
			html.append("</div>");
		}
		return html.toString();
	}

	/*
	 * 创建其他类型的条件,20131111
	 */
	private String buildSelectOtherHtml(String sign) throws Exception {
		StringBuilder html = new StringBuilder();
		SearchConfigModel config = new SearchConfigModel();
		config.setSearchSign(sign);
		// 数字区间类型
		config.setSearchType(SearchTypeEnum.SZQJCX.toString());
		// 查询所配置的查询条件
		List<SearchConfigModel> searchList = searchDao
				.getSearchConfigList(config);
		for (int i = 0; searchList != null && i < searchList.size(); i++) {
			SearchConfigModel searchConfigModel = searchList.get(i);
			String sfqy = searchConfigModel.getOnOrOff();
			if (sfqy == null || !sfqy.equals("1")) {
				continue;
			}
			html.append("<dl><dt>");
			html.append(searchConfigModel.getSearchLabel());
			html.append("：");
			html.append("</dt>");
			html.append("<dd><ul name='szqjcx'>");
			html
					.append("<li><input type='text' name='start' style='width:100px;' maxlength='10'  onblur='szqjcxCheck(this)' onkeyup='szqjcxCheck(this)'></li>");
			html
					.append("<li>至<input type='text' name='end' style='width:100px;' maxlength='10'  onblur='szqjcxCheck(this)' onkeyup='szqjcxCheck(this)'></li>");
			html.append("<input type='hidden' name='cxzd' value='");
			html.append(searchConfigModel.getSearchName());
			html.append("'>");
			html.append("</ul></dd></dl>");
		}
		// 时间区间类型
		config.setSearchType(SearchTypeEnum.SJQJCX.toString());
		// 查询所配置的查询条件
		searchList = searchDao.getSearchConfigList(config);
		String sjly = null;
		String format = "yyyyMMdd";
		for (int i = 0; searchList != null && i < searchList.size(); i++) {
			SearchConfigModel searchConfigModel = searchList.get(i);
			String sfqy = searchConfigModel.getOnOrOff();
			if (sfqy == null || !sfqy.equals("1")) {
				continue;
			}
			sjly = searchConfigModel.getValueSource();
			if (!StringUtil.isNull(sjly)) {
				format = sjly;
			}
			html.append("<dl><dt>");
			html.append(searchConfigModel.getSearchLabel());
			html.append("：");
			html.append("</dt>");
			html.append("<dd><ul name='sjqjcx'>");
			html
					.append("<li><input type='text' name='start'  onfocus='WdatePicker({dateFmt:\""
							+ format + "\"})' value='' ></li>");
			html
					.append("<li>至<input type='text' name='end' onfocus='WdatePicker({dateFmt:\""
							+ format + "\"})' value='' > </li>");
			html.append("<input type='hidden' name='cxzd' value='");
			html.append(searchConfigModel.getSearchName());
			html.append("'>");
			html.append("<input type='hidden' name='sjly' value='");
			html.append(format);
			html.append("'>");
			html.append("</ul></dd></dl>");
		}
		// 字符时间区间类型
		format = "yyyyMMdd";
		config.setSearchType(SearchTypeEnum.ZFSJQJCX.toString());
		// 查询所配置的查询条件
		searchList = searchDao.getSearchConfigList(config);
		for (int i = 0; searchList != null && i < searchList.size(); i++) {
			SearchConfigModel searchConfigModel = searchList.get(i);
			String sfqy = searchConfigModel.getOnOrOff();
			if (sfqy == null || !sfqy.equals("1")) {
				continue;
			}
			sjly = searchConfigModel.getValueSource();
			if (!StringUtil.isNull(sjly)) {
				format = sjly;
			}
			html.append("<dl><dt>");
			html.append(searchConfigModel.getSearchLabel());
			html.append("：");
			html.append("</dt>");
			html.append("<dd><ul name='zfsjqjcx'>");
			html
					.append("<li><input type='text' name='start' onfocus='WdatePicker({dateFmt:\""
							+ format + "\"})' value='' ></li>");
			html
					.append("<li>至<input type='text' name='end' onfocus='WdatePicker({dateFmt:\""
							+ format + "\"})' value='' > </li>");
			html.append("<input type='hidden' name='cxzd' value='");
			html.append(searchConfigModel.getSearchName());
			html.append("'>");
			html.append("<input type='hidden' name='sjly' value='");
			html.append(format);
			html.append("'>");
			html.append("</ul></dd></dl>");
		}
		return html.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.search.service.svcinterface.ISearchService#getSearchModel(
	 * com.woshidaniu.common.query.ModelBase)
	 */
	public SearchModel getSearchModel(Object o) throws Exception {
		SearchModel searchModel = (SearchModel) ReflectHelper
				.getValueByFieldName(o, "searchModel");
		if (searchModel == null) {
			return new SearchModel();
		}
		SearchModel newModel = new SearchModel();
		newModel.setSuperSearchData(searchModel.getSuperSearchData());
		newModel.setSearchSign(searchModel.getSearchSign());
		newModel.setConfigList(searchModel.getConfigList());
		newModel.setQuerySQL(new SearchSqlBuilder(newModel).getSQL());
		return newModel;
	}

	/*
	 * 生成其他类型sql语句及参数,20131111
	 */
	private void createOtherSql(SearchModel searchModel,
			StringBuilder querySQL, Map<String, String> params) {
		String cxzd = null;
		String type = null;
		String value = null;
		String sjly = null;
		int seq = 0;// 序列号,防止重复
		// 数字区间查询
		String szqjcxValue = searchModel.getSzqjcxValue();
		if (szqjcxValue != null && !szqjcxValue.trim().equals("")) {
			List<?> list = JsonUtil.jsonToList(szqjcxValue);
			for (Object object : list) {
				net.sf.ezmorph.bean.MorphDynaBean bean = (net.sf.ezmorph.bean.MorphDynaBean) object;
				if (bean == null) {
					continue;
				}
				cxzd = (String) bean.get("cxzd");
				type = (String) bean.get("type");
				value = (String) bean.get("value");
				if (StringUtil.isNull(cxzd) || StringUtil.isNull(type)
						|| StringUtil.isNull(value)) {
					continue;
				}

				String paramKey = new StringBuilder("params_").append(cxzd)
						.append("_").append(type).append(seq++).toString();
				if (type.equals("1")) {// 开始
					querySQL.append(" and to_number(");
					querySQL.append(cxzd);
					querySQL.append(")");
					querySQL.append(">=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
				if (type.equals("2")) {// 结束
					querySQL.append(" and to_number(");
					querySQL.append(cxzd);
					querySQL.append(")");
					querySQL.append("<=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
			}
		}

		// 时间区间查询
		String sjqjcxValue = searchModel.getSjqjcxValue();
		if (sjqjcxValue != null && !sjqjcxValue.trim().equals("")) {
			List<?> list = JsonUtil.jsonToList(sjqjcxValue);
			for (Object object : list) {
				net.sf.ezmorph.bean.MorphDynaBean bean = (net.sf.ezmorph.bean.MorphDynaBean) object;
				if (bean == null) {
					continue;
				}
				cxzd = (String) bean.get("cxzd");
				type = (String) bean.get("type");
				value = (String) bean.get("value");
				sjly = (String) bean.get("sjly");
				if (StringUtil.isNull(cxzd) || StringUtil.isNull(type)
						|| StringUtil.isNull(value)) {
					continue;
				}

				String paramKey = new StringBuilder("params_").append(cxzd)
						.append("_").append(type).append(seq++).toString();
				if (type.equals("1")) {// 开始
					querySQL.append(" and to_char(");
					querySQL.append(cxzd);
					querySQL.append(",'");
					querySQL.append(changeSjgs(sjly));
					querySQL.append("')");
					querySQL.append(">=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
				if (type.equals("2")) {// 结束
					querySQL.append(" and to_char(");
					querySQL.append(cxzd);
					querySQL.append(",'");
					querySQL.append(changeSjgs(sjly));
					querySQL.append("')");
					querySQL.append("<=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
			}
		}

		// 字符时间区间查询
		String zfsjqjcxValue = searchModel.getZfsjqjcxValue();
		if (zfsjqjcxValue != null && !zfsjqjcxValue.trim().equals("")) {
			List<?> list = JsonUtil.jsonToList(zfsjqjcxValue);
			for (Object object : list) {
				net.sf.ezmorph.bean.MorphDynaBean bean = (net.sf.ezmorph.bean.MorphDynaBean) object;
				if (bean == null) {
					continue;
				}
				cxzd = (String) bean.get("cxzd");
				type = (String) bean.get("type");
				value = (String) bean.get("value");
				if (StringUtil.isNull(cxzd) || StringUtil.isNull(type)
						|| StringUtil.isNull(value)) {
					continue;
				}

				String paramKey = new StringBuilder("params_").append(cxzd)
						.append("_").append(type).append(seq++).toString();
				if (type.equals("1")) {// 开始
					querySQL.append(" and ");
					querySQL.append(cxzd);
					querySQL.append(">=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
				if (type.equals("2")) {// 结束
					querySQL.append(" and ");
					querySQL.append(cxzd);
					querySQL.append("<=#{searchModel.params.").append(paramKey)
							.append("}");
					params.put(paramKey, value);
				}
			}
		}

	}

	/*
	 * 收集模糊查询条件的参数
	 */
	private Map<String, List<String>> buildBlurQuery(SearchModel searchModel) {

		String blurType = searchModel.getBlurType();
		String blurValue = searchModel.getBlurValue();

		if (StringUtil.isNull(blurType) || StringUtil.isNull(blurValue)) {
			return new HashMap<String, List<String>>();
		}

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		String[] values = blurValue.split(" ");

		if (blurType.indexOf("!blurType!") > -1) {
			String[] types = blurType.split("!blurType!");

			for (String type : types) {

				if (!StringUtil.isEmpty(type))

					map.put(type, Arrays.asList(values));
			}

		} else {
			map.put(blurType, Arrays.asList(values));
		}

		return map;
	}

	/*
	 * 收集选择项查询条件 的参数
	 */
	private Map<String, List<String>> buildSelectQuery(SearchModel searchModel) {

		String selectOptions = searchModel.getSelectOptions();

		if (StringUtil.isEmpty(selectOptions)) {
			return new HashMap<String, List<String>>();
		}

		String[] options = selectOptions.split("!selectOption!");
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (int i = 0; i < options.length; i++) {
			String[] params = options[i].split("!search!");
			String typeKey = params[0];
			String value = params[1];

			List<String> values = map.get(typeKey) == null ? new ArrayList<String>()
					: map.get(typeKey);
			values.add(value);
			map.put(typeKey, values);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.search.service.svcinterface.ISearchService#getLinkageList(
	 * com.woshidaniu.search.dao.entities.SearchLinkageModel)
	 */
	public List<SearchLinkageModel> getLinkageList(SearchLinkageModel model) {
		List<SearchLinkageModel> linkageList = searchDao.getLinkageList(model);
		return linkageList;
	}

	/*
	 * 时间格式处理，由界面格式转为oracle格式
	 */
	private String changeSjgs(String sjgs) {
		if (sjgs != null) {
			sjgs = sjgs.replace("HH", "HH24");
			sjgs = sjgs.replace("hh", "HH");
			sjgs = sjgs.replace("mm", "MI");
		}
		return sjgs;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.ISearchService#getMoreOptions(com.woshidaniu.search.dao.entities.SearchLinkageModel)
	 */
	public String getMoreOptions(SearchLinkageModel model) throws Exception {
		
		SearchConfigModel config = new SearchConfigModel();
		config.setSearchSign(model.getSearchSign());
		config.setSearchName(model.getSearchName());
		SearchConfigModel searchConfig = searchDao.getSearchConfig(config);
		
		//查询出影响该字段的关联
		List<SearchLinkageModel> linkageList = searchDao.getLinkageList(model);
		
		if (null != linkageList && linkageList.size() > 0){
			
			JSONObject json = JSONObject.fromObject(model.getLinkageValue());
			
			for (SearchLinkageModel linkModel : linkageList){
				
				if (json.containsKey(linkModel.getLinkageName())){
					linkModel.setLinkageValue(json.getString(linkModel.getLinkageName()));
				}
			}
			
			String options = buildLinkageContent(searchConfig, linkageList, Integer.valueOf(model.getPageNumber()));
			
			return options;
		}
		
		return getOptionsHtml(searchConfig,Integer.valueOf(model.getPageNumber()));
	}

}
