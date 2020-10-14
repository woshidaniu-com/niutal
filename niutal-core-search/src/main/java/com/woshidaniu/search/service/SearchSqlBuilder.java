package com.woshidaniu.search.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.text.StrMatcher;
import org.apache.commons.lang3.text.StrTokenizer;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchModel;
import com.woshidaniu.util.base.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchSqlBuilder implements Serializable{

	private static final long serialVersionUID = 8892094628133354330L;
	private SearchModel seachModel;
	
	
	public SearchSqlBuilder(SearchModel seachModel) {
		super();
		this.seachModel = seachModel;
	}

	/**
	 * java日期类型和Oracle日期类型格式转换
	 * @param dateFormat
	 * @return
	 */
	private String mapDateFormateToOracle(String dateFormat) {
		if(dateFormat == null){
			return "YYYY-MM-DD HH24:MI:SS";
		}else{
			return StringUtil.replaceEach(dateFormat, new String[]{"HH","mm"},  new String[]{"HH24","mi"});
		}
	}
	
	/**
	 * 构建查询sql语句
	 * @return
	 */
	public String getSQL() {
		if(StringUtil.isBlank(seachModel.getSuperSearchData())){
			return "";
		}
		if(seachModel.getConfigList() == null){
			SearchConfigCache configCache = (SearchConfigCache) 
					ServiceFactory.getService("searchConfigCache");	
			seachModel.setConfigList(configCache.getSearchConfig(seachModel.getSearchSign()));
		}
		if(seachModel.getConfigList() == null){
			return "";
		}
		JSONObject jo = JSONObject.fromObject(seachModel.getSuperSearchData());
		//模糊查询部分
		JSONObject mhcxObject = jo.getJSONObject("mhcx");
		String mhcxType = mhcxObject.getString("mhcxType");
		String mhcxValue = mhcxObject.getString("mhcxValue");
		List<String> mhcxSql = new ArrayList<String>();
		StrTokenizer mhcxValueTokens = new StrTokenizer(mhcxValue, new CommonSplitMatcher(" ,-:;\t".toCharArray()));
		if(StringUtil.isNotBlank(mhcxValue)){
			//模糊查询类型为全部
			if(StringUtil.equals(mhcxType, "-1")){
				//查询出模糊查询全部的字段
				for (SearchConfigModel config : seachModel.getConfigList()) {
					if(StringUtil.equals(config.getSearchType().toLowerCase(), "mhcx")){
						while(mhcxValueTokens.hasNext()){
							String currentValue = mhcxValueTokens.nextToken();
							mhcxSql.add(" (" + (StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName()) + " like '%" + currentValue +"%') ");
						}
						mhcxValueTokens.reset();
					}
				}
			}else{
				for (SearchConfigModel config : seachModel.getConfigList()) {
					if(StringUtil.equals(config.getSearchType().toLowerCase(), "mhcx") && 
							StringUtil.equals(config.getSearchName(), mhcxObject.getString("mhcxType"))){
						while(mhcxValueTokens.hasNext()){
							String currentValue = mhcxValueTokens.nextToken();
							mhcxSql.add(" (" + (StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName()) + " like '%" + currentValue +"%') ");
						}
						mhcxValueTokens.reset();
					}
				}
			}
		}
		//条件查询部分 , 时间区间 sjqjcx , zfsjqjcx , 数字区间
		List<String> tjcxSql = new ArrayList<String>();
		List<String> sjqjcxSql = new ArrayList<String>();
		List<String> zfsjqjcxSql = new ArrayList<String>();
		List<String> szqjcxSql = new ArrayList<String>();
		for (SearchConfigModel config :seachModel.getConfigList()) {
			String searchName = config.getSearchName();
			String searchType = config.getSearchType();
			if(StringUtil.equalsIgnoreCase(searchType, "tjcx")){
				List<String> innerSql = new ArrayList<String>();
				if(jo.containsKey(searchName)){
					JSONArray jsonArray = jo.getJSONArray(searchName);
					for (Object object : jsonArray) {
						String val  = (String)object;
						innerSql.add(" (" + (StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName())  + " = '" + val + "') ");
					}
					tjcxSql.add(" (" + StringUtil.join(innerSql, " or ") + ") ");
				}
			}else if(StringUtil.equalsIgnoreCase(searchType, "sjqjcx")){
				if(jo.containsKey(searchName)){
					JSONObject jsonObject = jo.getJSONObject(searchName); 
					String start = jsonObject.getString("start");
					String end = jsonObject.getString("end");
					String zd = StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName();
					
					if(StringUtil.isBlank(start) && StringUtil.isBlank(end)){
						continue;
					}else{
						List<String> se = new ArrayList<String>();
						String dateFormat = config.getValueSource();
						dateFormat = mapDateFormateToOracle(dateFormat);
						if(StringUtil.isNotBlank(start)){
							String s = zd + " >= to_date('" + start + "' , '" + dateFormat + "')";
							se.add(s);
						}
						if(StringUtil.isNotBlank(end)){
							String e = zd   + " <= to_date('" + end + "' , '" + dateFormat + "')";
							se.add(e);
						}
						String join = " ( "+ StringUtil.join(se, " and ") + " ) ";
						sjqjcxSql.add(join);
					}
				}
			}else if(StringUtil.equalsIgnoreCase(searchType, "zfsjqjcx")){
				if(jo.containsKey(searchName)){
					JSONObject jsonObject = jo.getJSONObject(searchName); 
					String start = jsonObject.getString("start");
					String end = jsonObject.getString("end");
					if(StringUtil.isBlank(start) && StringUtil.isBlank(end)){
						continue;
					}else{
						
					}
					String zd = StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName();
					
					if(StringUtil.isBlank(start) && StringUtil.isBlank(end)){
						continue;
					}else{
						List<String> se = new ArrayList<String>();
						if(StringUtil.isNotBlank(start)){
							String s = zd + " >= '" + start + "'";
							se.add(s);
						}
						if(StringUtil.isNotBlank(end)){
							String e = zd   + " <= '" + end + "'";
							se.add(e);
						}
						String join = " ( "+ StringUtil.join(se, " and ") + " ) ";
						zfsjqjcxSql.add(join);
					}
					
				}
			}else if(StringUtil.equalsIgnoreCase(searchType, "szqjcx")){
				if(jo.containsKey(searchName)){
					JSONObject jsonObject = jo.getJSONObject(searchName); 
					String start = jsonObject.getString("start");
					String end = jsonObject.getString("end");
					if(StringUtil.isBlank(start) && StringUtil.isBlank(end)){
						continue;
					}else{
						
					}
					String zd = StringUtil.isNotBlank(config.getSearchByname()) ? config.getSearchByname() : config.getSearchName();		
					if(StringUtil.isBlank(start) && StringUtil.isBlank(end)){
						continue;
					}else{
						List<String> se = new ArrayList<String>();
						if(StringUtil.isNotBlank(start)){
							String s = zd + " >= to_number('" + start  + "')";
							se.add(s);
						}
						if(StringUtil.isNotBlank(end)){
							String e = zd   + " <= to_number('" + end + "')";
							se.add(e);
						}
						String join = " ( "+ StringUtil.join(se, " and ") + " ) ";
						szqjcxSql.add(join);
					}
				}
			}
		}
		List<String> finalSql = new ArrayList<String>();
		if(mhcxSql.size() > 0){
			finalSql.add(" (" + StringUtil.join(mhcxSql, " or ") + " )");
		}
		if(tjcxSql.size() > 0){
			finalSql.add(StringUtil.join(tjcxSql, " and "));
		}
		if(sjqjcxSql.size() > 0){
			finalSql.add(StringUtil.join(sjqjcxSql, " and "));
		}
		if(zfsjqjcxSql.size() > 0){
			finalSql.add(StringUtil.join(zfsjqjcxSql, " and "));
		}
		if(szqjcxSql.size() > 0){
			finalSql.add(StringUtil.join(szqjcxSql, " and "));
		}
		if(finalSql.size() > 0){
			return " and " + StringUtil.join(finalSql, " and ");
		}else {
			return null;
		}
	}

	static class CommonSplitMatcher extends StrMatcher{
		private final char[] chars;
		CommonSplitMatcher(char chars[]) {
            super();
            this.chars = chars.clone();
            Arrays.sort(this.chars);
        }
		@Override
		public int isMatch(char[] buffer, int pos, int bufferStart,
				int bufferEnd) {
			 return Arrays.binarySearch(chars, buffer[pos]) >= 0 ? 1 : 0;
		}}
}
