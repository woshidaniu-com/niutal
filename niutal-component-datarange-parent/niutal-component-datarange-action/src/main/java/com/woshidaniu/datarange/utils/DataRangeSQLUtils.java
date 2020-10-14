package com.woshidaniu.datarange.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.dao.entities.SjfwzModel;
import com.woshidaniu.web.WebContext;

import ognl.Ognl;
import ognl.OgnlException;

@SuppressWarnings("unused")
public final class DataRangeSQLUtils {
	
	//1：匹配字段过滤时候查询的表
	private static String table_name = "tableName";
	//2：匹配字段过滤时候查询的表的关联表
	private static String table_related = "tableRelated";
	//3：匹配的过滤字段
	private static String match_field = "matchField";
	//4：匹配字段过滤时候查询的表的条件字段名称
	private static String table_field = "tableField";
	//5：数据范围对象的字段名称
	private static String filter_field = "filterField";
	//6：原查询SQL中与对应过滤查询表的过滤字段的字段名称[此参数在 in 的模式下可没有]
	private static String mapper_field = "mapperField";
	
	private static final String emptyParm = " ";// 空条件
	private static final String reqParm = " 1=2 ";// 返回不成功则，则条件为1=2
	private static final String pasParm = " 1=1 ";// 如果jg_id=-1或jg_id=-2,则直接返回成功
	
	private static Pattern pattern_find = Pattern.compile("(?:(?:\\{)([^\\{\\}]*?)(?:\\}))+");
	private static Pattern pattern_limit = Pattern.compile("(?:(?:limit\\()([^\\(\\)]*)(?:\\))(?:\\[(\\w+)\\])*)+");
	private static Pattern pattern_func = Pattern.compile("(?:(?:func\\()([^\\(\\)]*)(?:\\))(?:\\[(\\w+)\\])*)+");
	private static Pattern pattern_bitand = Pattern.compile("(?:(?:bitand\\()([^\\(\\)]*)(?:\\))(?:\\[(\\w+)\\])*)+");
		
	public static void main(String[] args) {
		
		List<SjfwdxModel> sjfwdxList = new ArrayList<SjfwdxModel>();
		
		sjfwdxList.add(new SjfwdxModel("1", "SCHOOL", "", "", "全校	", "1", "1", "0"));
		sjfwdxList.add(new SjfwdxModel("2", "niutal_xtgl_jgdmb", "jg_id", "jgmc", "部门", "5", "1", "1"));
		sjfwdxList.add(new SjfwdxModel("3", "niutal_xtgl_zydmb", "zyh_id", "zymc", "专业	", "7", "1", "0"));
		sjfwdxList.add(new SjfwdxModel("4", "niutal_xtgl_bjdmb", "bh", "bj", "班级", "7", "1", "0"));
		sjfwdxList.add(new SjfwdxModel("7", "view_xjgl_xsjbxxb", "xh_id", "xm", "学生", "8", "1", "0"));
		sjfwdxList.add(new SjfwdxModel("8", "niutal_xtgl_xqdmb", "xqh", "xqmc", "校区", "4", "1", "1"));
		sjfwdxList.add(new SjfwdxModel("9", "view_xtgl_kkbm", "kkbm_id", "kkbmmc", "开课部门", "1", "1", "1"));
		sjfwdxList.add(new SjfwdxModel("10","jw_xjgl_xsxzdmb", "xsxzm", "xsxzmc", "学生性质", "9", "1", "1"));
		
		List<String> dataRangeFields = new ArrayList<String>();
		for (SjfwdxModel sjfwdxModel : sjfwdxList) {
			dataRangeFields.add(sjfwdxModel.getZddm());
		}
		
		
		List<SjfwzModel> dataRanges = new ArrayList<SjfwzModel>();
		
		dataRanges.add(new SjfwzModel("计算机学院[2010年,2014年]","jg_id=306;njdm_id=2010,2014"));
		dataRanges.add(new SjfwzModel("计算机学院[2007年,2008年]","zyh_id=306;njdm_id=2007,2008"));
		dataRanges.add(new SjfwzModel("民族生【学生性质】","xsxzm=4"));
		dataRanges.add(new SjfwzModel("计算机学院","jg_id=306"));
		dataRanges.add(new SjfwzModel("外语学院","jg_id=313"));
		
		
		
		List<SjfwzModel> jgList = new ArrayList<SjfwzModel>();
		jgList.add(new SjfwzModel("计算机学院 ","jg_id=306"));
		jgList.add(new SjfwzModel("外语学院 ","jg_id=309"));
		
		/*System.out.println("========================bitand 函数的数据范围限制 ================================");*/
		String originalSQL_a0x = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { t.xh_id in bitand('jw_xjgl_xsjbxxb';'xh_id';'a.xsbj';'xsxzm')} ";
		String originalSQL_a0y = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t { bitand('t.xsbj';'xsxzm')} ";
		System.out.println("bitand SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a0x, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("bitand SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a0y,  dataRangeFields, dataRanges, jgList ,  null));
		 		
		/**/
		System.out.println("========================limit 函数的数据范围限制 ================================");
		String originalSQL_b0x = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { limit('xs_xsjbxxb';'ssxy_id';'jg_id') } ";
		String originalSQL_b0y = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t { limit('jw_xjgl_xsxjxxb';'jw_xjgl_xsjbxxb';'xsbj,jg_id,njdm_id';'xsxzdm,jg_id,njdm_id';'xn_id=xh_id')} ";
		System.out.println("limit SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_b0x, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("limit SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_b0y,  dataRangeFields, dataRanges, jgList ,  null));
		
		
		/*System.out.println("========================仅用到数据范围组合中主要约束字段；则以主要约束字段数据范围为主================================");
		
		String originalSQL_a01 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { t.xh in func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id') } ";
		String originalSQL_a02 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { instr[a.xx_id,func('xs_xsjbxxb';'wm_concat[xh]';'ssxy_id';'jg_id')] > 0 }";
		String originalSQL_a03 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { func('xs_xsjbxxb';'wm_concat[xh]';'ssxy_id';'jg_id') like '%'|| t.xh ||'%' } ";
		String originalSQL_a04 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t {func('xs_xsjbxxb';'xh';'zy_id';'zyh_id';'xh1=xh2')}";
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a01, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a02, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a03, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a04,  dataRangeFields, dataRanges, jgList ,  null));
		
		*/
		/*System.out.println("========================要控制字段未设置数据范围；则无组数据范围================================");
		String originalSQL_a11 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { t.xh in func('xs_xsjbxxb';'xh';'bh_id,nj_id';'bh_id,njdm_id')}  ";
		String originalSQL_a12 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t {func('xs_xsjbxxb';'xh';'bh_id,nj_id';'bh_id,njdm_id';'xh1=xh2')}";
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a11, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a12,  dataRangeFields, dataRanges, jgList ,  null));
		*/
		
		/*System.out.println("========================仅用到组合中的附属条件；则不启用此组数据范围================================");
		String originalSQL_a21 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { t.xh in func('xs_xsjbxxb';'xh';'nj_id';'njdm_id')} ";
		String originalSQL_a22 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t {func('xs_xsjbxxb';'xh';'nj_id';'njdm_id';'xh1=xh2')}";
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a21, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a22,  dataRangeFields, dataRanges, jgList ,  null));
		*/
		System.out.println("========================启用全部数据范围================================");
		String originalSQL_a31 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t where { t.xh in func('xs_xsjbxxb';'xh';'jg_id,zyh_id,nj_id';'jg_id,zyh_id,njdm_id')} { t.xh in func('xs_xsjbxxb';'xh';'jg_id,zyh_id,nj_id';'jg_id,zyh_id,njdm_id')} ";
		String originalSQL_a32 = " select t.xh,t.xsjbxxb_id,t.xqdmb_id,t.ssxy_id,t.zyfxdmb_id,t.zyh_id,t.xzbdmb_id,t.nj_id from xs_xsjbxxb t {func('xs_xsjbxxb';'xh';'jg_id,zyh_id,nj_id';'jg_id,zyh_id,njdm_id';'xh1=xh2')}";
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a31, dataRangeFields,  dataRanges, jgList ,null));
		System.out.println("range SQL 2:"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a32,  dataRangeFields, dataRanges, jgList ,  null));
		
		/*System.out.println("=======================超级管理员；则跳过所有的数据范围限制================================");
		List<SjfwzModel> dataRanges2 = new ArrayList<SjfwzModel>(dataRanges);
		dataRanges2.add(new SjfwzModel("超级管理员","jg_id=-1"));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a31, dataRangeFields,  dataRanges2, jgList ,null));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a32, dataRangeFields,  dataRanges2, jgList ,  null));
		*/
		System.out.println("=======================全校；则跳过所有的数据范围限制================================");
		List<SjfwzModel> dataRanges3 = new ArrayList<SjfwzModel>(dataRanges);
		dataRanges3.add(new SjfwzModel("全校","jg_id=-2"));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a31,  dataRangeFields, dataRanges3, jgList ,null));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a32,  dataRangeFields, dataRanges3, jgList ,  null));
		
		System.out.println("=======================全部学院；则取所有学院的权限================================");
		List<SjfwzModel> dataRanges4 = new ArrayList<SjfwzModel>(dataRanges);
		dataRanges4.add(new SjfwzModel("全部学院","jg_id=-3"));
		
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a31,  dataRangeFields, dataRanges4, jgList ,null));
		System.out.println("range SQL :"+ DataRangeSQLUtils.getFuncDataRangeSQL(originalSQL_a32,  dataRangeFields, dataRanges4, jgList ,  null));
		
	}
	
	
	public static List<SjfwzModel> filter(List<SjfwzModel> dataRanges,String kzdx) {
		if(!BlankUtils.isBlank(dataRanges) && !BlankUtils.isBlank(kzdx) ){
			return CollectionUtils.findAll(dataRanges, new DataRangePredicate(kzdx));
		}
		return dataRanges;
	}

	/**
	 * 
	 *@描述：数据范围SQL处理方法：根据用户已有数据范围和SQL语句中的特定函数；对原SQL进行数据范围的包装处理
	 *<pre> 可匹配如下类型语句：
	 * 		{ t.xh in func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id') }
	 * 		{ instr[a.xx_id,func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id')] > 0 }
	 * 		{ func('xs_xsjbxxb';'wm_concat[xh]';'ssxy_id';'jg_id') like '%'|| t.xh ||'%' }
	 * 		{ func('xs_xsjbxxb';'xh';'jg_id,zyh_id,nj_id';'jg_id,zyh_id,njdm_id';'xh1=xh2')}
	 * 		{ limit('xs_xsjbxxb';'ssxy_id';'jg_id') }
	 * 		{ limit('jw_xjgl_xsxjxxb';'jw_xjgl_xsjbxxb';'xsbj,jg_id,njdm_id';'xsxzdm,jg_id,njdm_id';'xn_id=xh_id')}
	 * 		{ bitand('t.xsbj';'xsxzm')}
	 * 		{ t.xh_id in bitand('jw_xjgl_xsjbxxb';'xh_id';'a.xsbj';'xsxzm')}
	 * </pre>		
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-18上午11:51:49
	 *@param originalSQL
	 *@param dataRangeFields
	 *@param dataRanges
	 *@param allCollegeDataRanges
	 *@param defaultLimts
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String getFuncDataRangeSQL(String originalSQL,List<String> dataRangeFields,List<SjfwzModel> dataRanges,List<SjfwzModel> allCollegeDataRanges, String defaultLimts) {
		//匹配所有{}包括的内容
		Matcher matcher = pattern_find.matcher(originalSQL);
		//有数据范围，且受控于数据范围
		if (controllable(dataRanges)) {
			allCollegeDataRanges = BlankUtils.isBlank(allCollegeDataRanges)?new ArrayList<SjfwzModel>():allCollegeDataRanges;
			List<Map<String, String>> matcherFuncs = new ArrayList<Map<String, String>>();
			Map<String, String> tempSQLMap = new HashMap<String, String>();
			String kzdxStr = null;
			int index = 0;
			// 查找匹配的{func()}片段
			while (matcher.find()) {
				// 获取匹配的{}的内容
				String full_segment = matcher.group(0);
				// 取得{}内容开始结束位置
				int begain = originalSQL.indexOf(full_segment);
				int end = begain + full_segment.length();
				// {}中间的内容
				String dataRangRule = matcher.group(1);
				//是否替换的标记
				boolean replace = true;
				// 对func内容进行解析
				String dataRangeSQL = "";
				// 匹配每段数据范围规则内容中的limit()
				Matcher matcherLimit = pattern_limit.matcher(dataRangRule);
				// 匹配每段数据范围规则内容中的func()
				Matcher matcherFunc = pattern_func.matcher(dataRangRule);
				// 匹配每段数据范围规则内容中的bitand()
				Matcher matcherBitand = pattern_bitand.matcher(dataRangRule);
				// 不使用while,这里只匹配一次limit
				if (matcherLimit.find()) {
					//构建基于数据现在的临时表
					buildTableTmpSQL(tempSQLMap,matcherLimit, dataRangRule, dataRangeFields, dataRanges, allCollegeDataRanges, defaultLimts);
					// 添加标记；表示非替换处理
					replace = false;
				}else if (matcherFunc.find()) { 
					// 拼接原func前SQL；如:xsjtb.nj in
					int begainIndex = dataRangRule.indexOf("func");
					String begainSQL = dataRangRule.substring(0, begainIndex);
					//在{}区域内符合当前正则表达式的整段字符
					String segmentStr = matcherFunc.group(0);
					// 获取()中间的内容
					String ruleStr = matcherFunc.group(1);
					// 控制对象
					kzdxStr = matcherFunc.group(2);
					//去除func()部分后的字符内容
					String sqlStr  = dataRangRule.substring(0, begainIndex) + dataRangRule.substring(begainIndex + segmentStr.length());
					/*
					 * 1. 	{func("jw_xjgl_xsxjxxb","xm","bj","bh_id","xsxm")} 模式表示 使用 内连接模式，处理逻辑为包裹原SQL，与筛选表组成全连接查询
					 */
					if (sqlStr.trim().length() == 0) {
						// 添加标记；表示非替换处理
						replace = false;
						// 解析func的表名或视图名,匹配字段,表字段,数据范围需过滤的条件字段
						matcherFuncs.add(getResolvedMap("func",ruleStr));
					}
					/*
					 * 2. 	{ t.xh in func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id') } 模式表示 使用 in 关键字，处理逻辑为用解析的SQL 替换匹配区域字段
					 * 		{ instr[a.xx_id,func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id')] > 0 }
					 * 		{ func('xs_xsjbxxb';'wm_concat[xh]';'ssxy_id';'jg_id') like '%'|| t.xh ||'%' }
					 */
					else {
						// 添加标记；表示替换处理
						replace = true;
						// 在循环的过程中，替换掉当前的片段
						dataRangeSQL = getFragmentSQL(matcherFunc , dataRangRule,dataRangeFields , dataRanges , allCollegeDataRanges, defaultLimts);
					}
					
				}else if (matcherBitand.find()) {
					// 在循环的过程中，替换掉当前的片段
					dataRangeSQL = getPartBitandSQL(matcherBitand, dataRangRule, dataRangeFields , dataRanges, allCollegeDataRanges, defaultLimts);
					// 添加标记；表示替换处理
					replace = true;
				}
				
				// 如果是替换处理，则在每次的循环中进行处理SQL
				if (true == replace) {
					
					//SQL片段不为空
					if(!BlankUtils.isBlank(dataRangeSQL)){
						//得到当前条件片段之前的sql,并去除换行空格等
						//.replaceAll("(\r \n(\\s*\r \n)+)", "\r\n").replaceAll(" +","");
						String tmp = originalSQL.substring(0, begain).replaceAll("[\\s]+", " ").trim();
						//判断当前条件前面SQL是否以where结尾
						if( !tmp.toLowerCase().endsWith("where")){
							dataRangeSQL =  " and " + dataRangeSQL;
						}
					}
					originalSQL = originalSQL.substring(0, begain) + dataRangeSQL + originalSQL.substring(end);
					index += 1;
				}else{
					//内连接模式；去除规则func部分
					originalSQL = originalSQL.substring(0, begain) + " " + originalSQL.substring(end);
				}
			}
			StringBuilder dataRangeSQL  = new StringBuilder();
			//如果有表连接模式的数据范围的处理
			if(matcherFuncs.size()>0 ){
				String existsSQL = getExistsSQL(matcherFuncs, dataRangeFields , filter(dataRanges, kzdxStr) , allCollegeDataRanges, defaultLimts);
				if(!BlankUtils.isBlank(existsSQL)){
					//先清除StringBuilder对象中的数据，然后拼装SQL
					dataRangeSQL.delete(0, dataRangeSQL.length()).append(" SELECT tmp.* FROM (").append(originalSQL).append(") tmp ").append(existsSQL);
					//重置原始SQL为经过处理后的SQL
					originalSQL = dataRangeSQL.toString();
				}
			}
			//如果临时表数据不为空，表示有使用{limit()} 数据范围表达式
			if(!tempSQLMap.isEmpty()){
				//替换表名
				for (String table : tempSQLMap.keySet()) {
					//originalSQL = originalSQL.replace(table, table + "_temp");
					originalSQL = originalSQL.replace(table, tempSQLMap.get(table));
				}
				/*
				//先清除StringBuilder对象中的数据，然后拼装SQL
				dataRangeSQL.delete(0, dataRangeSQL.length()).append(" with ").append(StringUtils.join(tempSQLMap.values(), ",")).append(originalSQL);
				//重置原始SQL为经过处理后的SQL
				originalSQL = dataRangeSQL.toString();
				*/
			}
		}else{
			// 查找匹配的{func()}片段
			while (matcher.find()) {
				// 获取匹配的{}的内容
				String full_segment = matcher.group(0);
				// 取得{}内容开始结束位置
				int begain = originalSQL.indexOf(full_segment);
				int end = begain + full_segment.length();
				//内连接模式；去除规则func部分
				originalSQL = originalSQL.substring(0, begain) + " " + originalSQL.substring(end);
			}
			
			/*// 未定义数据范围
			//===============未定义数据范围组============================="
			// 判断是否需设置默认数据范围字段
			if (!BlankUtils.isBlank(defaultLimts)) {
				//===============启用默认数据范围=============================
				// 构建默认数据范围条件SQl
				String conditions = getDefaultLimtSQL(defaultLimts,"tmpx");
				//有默认条件
				if(!BlankUtils.isBlank(conditions)){
					StringBuilder dataRangeSQL  = new StringBuilder(" SELECT tmpx.* FROM (");
					dataRangeSQL.append(originalSQL);
					dataRangeSQL.append(") tmpx where ");
					dataRangeSQL.append(conditions);
					originalSQL = dataRangeSQL.toString();
				}
			}*/
		}
		
		//得到当前条件片段之前的sql,并去除换行空格等
		String tmp = originalSQL.replaceAll("[\\s]+", " ").trim();
		//判断当前条件前面SQL是否以where结尾
		if(tmp.toLowerCase().endsWith("where")){
			originalSQL = originalSQL.substring(0, originalSQL.toLowerCase().lastIndexOf("where"));
		}
		//将原使用[]符号表示的函数重新转换成数据库可识别的函数
		return originalSQL.replace("[", "(").replace("]", ")");
	}	 
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-18上午09:24:26
	 *@param starSQL
	 *@param ruleStr
	 *@param dataRangeFields
	 *@param dataRanges
	 *@param allCollegeDataRanges
	 *@param defaultLimts
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected static String getPartBitandSQL(Matcher matcher,String dataRangRule,List<String> dataRangeFields,List<SjfwzModel> dataRanges, List<SjfwzModel> allCollegeDataRanges,String defaultLimts) {
		//在{}区域内符合当前正则表达式的整段字符
		String segmentStr = matcher.group(0);
		// 获取()中间的内容
		String ruleStr = matcher.group(1);
		// 控制对象
		String kzdxStr = matcher.group(2);
		//拷贝副本
		List<SjfwzModel> dataRangeCopy = new ArrayList<SjfwzModel>(filter(dataRanges, kzdxStr));
		//全学院权限
		boolean isAllCollege = isAllCollege(dataRangeCopy);
		if(isAllCollege){
			dataRangeCopy.clear();
			dataRangeCopy.addAll(allCollegeDataRanges);
		}
		/*
		 * 匹配所有{}包括的内容；如： 
		 * { bitand('t.xsbj';'xsxzm')}
		 * { t.xh_id in bitand('jw_xjgl_xsjbxxb';'xh_id';'a.xsbj';'xsxzm')}
		 * 
		 * keys = new String[]{table_field};
		 * keys = new String[]{table_name,table_field,match_field,filter_field};
		 */
		Map<String, String> matcherRule = getResolvedMap("bitand",ruleStr);
		String tableField = matcherRule.get(table_field); // 表字段
		//'jg_id,zyh_id,njdm_id'
		String filterField = matcherRule.get(filter_field); // 数据范围需过滤的条件字段
		//具体权限
		int sum = 0;
		for (SjfwzModel dataRange : dataRangeCopy) {
			//解析当前行数据范围值
			//jg_id=[2],njdm_id=[2,3,4,FB01BAAAE1251C9EE040007F01001CB3,FC2D5DBF2C58DE51E040007F01006012]
			//xsxzm=[2]
			Map<String, String[]> resolvedRangeMap = getResolvedRangeMap(dataRange.getSjfwztj());
			if(resolvedRangeMap.keySet().contains(filterField)){
				sum += Integer.parseInt(StringUtils.getSafeStr(resolvedRangeMap.get(filterField)[0], "0"));
			}
		}
		if(sum > 0){
			
			// 拼接原func前SQL；如:xsjtb.nj in
			int begainIndex = dataRangRule.indexOf("bitand");
			//去除func()部分后的字符内容
			String sqlStr  = dataRangRule.substring(0, begainIndex) + dataRangRule.substring(begainIndex + segmentStr.length());
			String begainSQL = dataRangRule.substring(0, begainIndex);
			String endSQL = dataRangRule.substring(begainIndex + segmentStr.length());
			/*
			 * 1. { bitand('t.xsbj';'xsxzm')} 模式表示 使用 内连接模式，处理逻辑为包裹原SQL，与筛选表组成全连接查询
			 */
			if (sqlStr.trim().length() == 0) {
				//select xh_id from jw_xjgl_xsjbxxb  where bitand(nvl(127,127),my_sum(xsbj,',')) > 0
				return " bitand("+sum+","+tableField+") > 0 ";
			}
			/*
			 * 2. { t.xh_id in bitand('jw_xjgl_xsjbxxb','xh_id','xsbj','xsxzm')} 模式表示 使用 in 关键字，处理逻辑为用解析的SQL 替换匹配区域字段
			 */
			else {
				String tableName = matcherRule.get(table_name); // 表名或视图名
				String matchField = matcherRule.get(match_field); // 匹配字段
				StringBuilder sqlBuilder = new StringBuilder(); // 返回SQL
				sqlBuilder.append(begainSQL).append( " ( select ").append(matchField);
				sqlBuilder.append(" from ").append(tableName.toLowerCase().indexOf("from") > -1 ? ("(" + tableName +")") : tableName).append(" a ");
				sqlBuilder.append( "where " + "bitand("+sum+","+tableField+") > 0" + ") ").append(endSQL);
				return sqlBuilder.toString();
			}
		}else{
			return emptyParm;
		}
	}
	
	/**
	 * 
	 *@描述：SQL片段 
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-17下午03:44:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param starSQL
	 *@param ruleStr
	 *@param dataRanges
	 *@param defaultLimts
	 *@return
	 */
	protected static String getFragmentSQL(Matcher matcher,String dataRangRule,List<String> dataRangeFields,List<SjfwzModel> dataRanges, List<SjfwzModel> allCollegeDataRanges, String defaultLimts) {
		/*
		 * 匹配所有{}包括的内容；如： 
		 * 	{ t.xh in func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id') } 
		 * 	{ instr[a.xx_id,func('xs_xsjbxxb';'xh';'ssxy_id';'jg_id')] > 0 }
		 * 	{ func('xs_xsjbxxb';'wm_concat[xh]';'ssxy_id';'jg_id') like '%'|| t.xh ||'%' }
		 * 
		 * keys = new String[]{table_name,match_field,table_field,filter_field,mapper_field};
		 */
		//在{}区域内符合当前正则表达式的整段字符
		String segmentStr = matcher.group(0);
		// 获取()中间的内容
		String ruleStr = matcher.group(1);
		// 控制对象
		String kzdxStr = matcher.group(2);
				
		Map<String, String> matcherRule = getResolvedMap("func",ruleStr);
		//规则解析正确
		if(!matcherRule.isEmpty()){
			//拷贝副本
			List<SjfwzModel> dataRangeCopy = new ArrayList<SjfwzModel>(filter(dataRanges, kzdxStr));
			//全学院权限
			boolean isAllCollege = isAllCollege(dataRangeCopy);
			if(isAllCollege){
				dataRangeCopy.clear();
				dataRangeCopy.addAll(allCollegeDataRanges);
			}
			String tableName = matcherRule.get(table_name); // 表名或视图名
			String matchField = matcherRule.get(match_field); // 匹配字段
			int begainIndex = dataRangRule.indexOf("func");
			
			String begainSQL = dataRangRule.substring(0, begainIndex);
			String endSQL = dataRangRule.substring(begainIndex + segmentStr.length());
			StringBuilder fragmentSQL = new StringBuilder();
			// 构建数据范围限制条件SQL
			List<String> conditions = getDataRangeLimitSQL(dataRangeCopy, matcherRule, dataRangeFields, "a", isAllCollege);
			if(conditions.size()>0){
				fragmentSQL.append(begainSQL).append( " ( select ").append(matchField);
				fragmentSQL.append(" from ").append(tableName.toLowerCase().indexOf("from") > -1 ? ("(" + tableName +")") : tableName).append(" a ");
				fragmentSQL.append( "where ").append(StringUtils.join(conditions, " or ")).append(")").append(endSQL);
			}
			return fragmentSQL.toString();
		}else{
			return emptyParm;
		}
	}
	
	
	/**
	 * 
	 * @description: 获取内连接SQL
	 * @author : kangzhidong
	 * @date : 2014-6-10
	 * @time : 上午09:49:52 
	 * @param matchers
	 * @param dataRanges
	 * @param defaultLimts
	 * @return
	 */
	protected static void buildTableTmpSQL(Map<String, String> tempSQLMap,Matcher matcher,String dataRangRule,List<String> dataRangeFields,List<SjfwzModel> dataRanges, List<SjfwzModel> allCollegeDataRanges, String defaultLimts){
		// 获取()中间的内容
		String ruleStr = matcher.group(1);
		// 控制对象
		String kzdxStr = matcher.group(2);
		//有数据范围，且受控于数据范围
		if (!BlankUtils.isBlank(ruleStr)) {
			/*
			 * 匹配所有{}包括的内容；如： 
			 * { limit('xs_xsjbxxb';'ssxy_id';'jg_id') } 
			 * { limit('jw_xjgl_xsxjxxb';'jw_xjgl_xsjbxxb';'xsbj,jg_id,njdm_id';'xsxzdm,jg_id,njdm_id';'xn_id=xh_id')}
			 * 
			 * keys = new String[]{table_name,table_field,filter_field};
			 * keys = new String[]{table_name,table_related,table_field,filter_field,mapper_field};
			 */
			Map<String, String> matcherRule = getResolvedMap("limit",ruleStr);
			//规则解析正确
			if(!matcherRule.isEmpty()){
				//拷贝副本
				List<SjfwzModel> dataRangeCopy = new ArrayList<SjfwzModel>(filter(dataRanges , kzdxStr));
				//全学院权限
				boolean isAllCollege = isAllCollege(dataRangeCopy);
				if(isAllCollege){
					dataRangeCopy.clear();
					dataRangeCopy.addAll(allCollegeDataRanges);
				}
				String tableName = matcherRule.get(table_name); // 表名或视图名
				String alias = " tb0";
				// 构建数据范围限制条件SQL
				List<String> conditions = getDataRangeLimitSQL(dataRangeCopy, matcherRule, dataRangeFields, alias , isAllCollege);
				//有匹配的数据范围SQL
				if(conditions.size()>0){
					StringBuilder tempSQL = new StringBuilder();
					switch (matcherRule.size()) {
						case 3:{
							//tempSQL.append(tableName).append("_temp as ( ");
							tempSQL.append(" ( ");
								//组织内部进过数据范围限制的SQL
								tempSQL.append(" select tb0.* from ").append(tableName.toLowerCase().indexOf("from") > -1 ? ("(" + tableName +")") : tableName).append(alias);
								tempSQL.append(" where ").append(conditions.size() > 0 ? "(" : "").append(StringUtils.join(conditions, " or ")).append(conditions.size() > 0 ? ")" : "");
							tempSQL.append(")");
						};break;
						case 4:{
							
							//匹配字段过滤时候查询的表的关联表
							String tableRelated = matcherRule.get(table_related);
							//tempSQL.append(tableName).append("_temp as ( ");
							tempSQL.append(" ( ");
								//组织内部进过数据范围限制的SQL
								tempSQL.append(" select tb0.* from ").append(tableRelated.toLowerCase().indexOf("from") > -1 ? ("(" + tableRelated +") ") : tableRelated).append(alias);
								tempSQL.append(" where ").append(conditions.size() > 0 ? "(" : "").append(StringUtils.join(conditions, " or ")).append(conditions.size() > 0 ? ")" : "");
							tempSQL.append(")");
						};break;
						case 5:{
							
							//匹配字段过滤时候查询的表的关联表
							String tableRelated = matcherRule.get(table_related);
							//tempSQL.append(tableName).append("_temp as ( ");
							tempSQL.append(" ( ");
								//组织内部进过数据范围限制的SQL
								tempSQL.append(" select tmp.* from ").append(tableName.toLowerCase().indexOf("from") > -1 ? ("(" + tableName +")") : tableName).append(" tmp,");
								tempSQL.append(tableRelated.toLowerCase().indexOf("from") > -1 ? ("(" + tableRelated +")") : tableRelated).append(alias);
								tempSQL.append(" where ").append(conditions.size() > 0 ? "(" : "").append(StringUtils.join(conditions, " or ")).append(conditions.size() > 0 ? ")" : "");
								// 构建两个表关联关系条件SQL
								List<String> mappers = getTableMapperSQL( matcherRule, alias);
								tempSQL.append(" and ").append(mappers.size() > 0 ? "(" : "").append(StringUtils.join(mappers, " and ")).append(mappers.size() > 0 ? ")" : "");
								
							tempSQL.append(")");
						};break;
					}
					//正确组织了SQL
					if(tempSQL.length() > 0){
						tempSQLMap.put(tableName, tempSQL.toString());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @description: 获取exists SQL
	 * @author : kangzhidong
	 * @date : 2014-6-10
	 * @time : 上午09:49:52 
	 * @param matchers
	 * @param dataRanges
	 * @param defaultLimts
	 * @return
	 */
	protected static String getExistsSQL(List<Map<String, String>> matchers,List<String> dataRangeFields,List<SjfwzModel> dataRanges, List<SjfwzModel> allCollegeDataRanges, String defaultLimts){
		//有数据范围，且受控于数据范围
		if (!BlankUtils.isBlank(matchers)) {
			//拷贝副本
			List<SjfwzModel> dataRangeCopy = new ArrayList<SjfwzModel>(dataRanges);
			//全学院权限
			boolean isAllCollege = isAllCollege(dataRangeCopy);
			if(isAllCollege){
				dataRangeCopy.clear();
				dataRangeCopy.addAll(allCollegeDataRanges);
			}
			StringBuilder existsSQL = new StringBuilder();
			StringBuilder partSQL = new StringBuilder();
			List<String> exists = new ArrayList<String>();
			List<String> mappers = null;
			
			for (int index = 0; index < matchers.size(); index++) {
				String alias = " tb"+index;
				/*
				 * 匹配所有{}包括的内容；如： 
				 * {func("jw_xjgl_xsxjxxb","xm","bj","bh_id")}
				 * {func('jw_xjgl_xsxjxxb';'xh_id';'jg_id,zyh_id,njdm_id,bh_id';'jg_id,zyh_id,njdm_id,bh_id';'xh_id=xh_id')}
				 * 
				 * keys = new String[]{table_name,match_field,table_field,filter_field,mapper_field};
				 */
				Map<String, String> matcherRule = matchers.get(index);
				// 构建数据范围限制条件SQL
				List<String> conditions = getDataRangeLimitSQL(dataRangeCopy, matcherRule, dataRangeFields, alias , isAllCollege);
				//有条件
				if(conditions.size() > 0 ){
					String tableName = matcherRule.get(table_name); // 表名或视图名
					String matchField = matcherRule.get(match_field); // 匹配字段
					//清除一个exists SQL判断的builder内容
					partSQL.delete(0, partSQL.length());
					//开始构建exists SQL
					partSQL.append("exists (");
					partSQL.append(" select ").append(alias).append(".").append(matchField);
					partSQL.append(" from ").append(tableName.toLowerCase().indexOf("from") > -1 ? ("(" + tableName +")") : tableName).append(alias);
					partSQL.append(" where ");
					// 构建两个表关联关系条件SQL
					mappers = getTableMapperSQL( matcherRule, alias);
					partSQL.append(mappers.size() > 0 ? "(" : "").append(StringUtils.join(mappers, " and ")).append(mappers.size() > 0 ? ") " : "");
					partSQL.append(" and ").append(conditions.size()>1?" ( ":"").append(StringUtils.join(conditions, " or ")).append(conditions.size()>1?" ) ":"");
					partSQL.append(" ) ");
					//添加一段exists SQL
					exists.add(partSQL.toString());
				}else{
					partSQL.delete(0, partSQL.length());
				}
			}
			if(exists.size()>0){
				//拼装数据范围SQL
				existsSQL.append(" where ").append(StringUtils.join(exists, " and "));
			}
			return existsSQL.toString();
		}else{
			return emptyParm;
		}
	}

	/**
	 * 
	 * @description: 获得默认数据范围key-value的Map集合
	 * @author : kangzhidong
	 * @date : 2014-6-10
	 * @time : 上午11:38:45 
	 * @param matcherRule
	 * @param defaultLimts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static String getDefaultLimtSQL(String defaultLimts,String alias) {
		//无默认限制
		if(BlankUtils.isBlank(defaultLimts)){
			return emptyParm;
		}else{
			List<String> conditions = new ArrayList<String>();
			String[] limitFields = StringUtils.splits(defaultLimts, ",");
			//循环限制字段
			for (String limitField : limitFields) {
				//从User对象取默认数据范围属性的值
				Object delault = null;
				try {
					Object tree = Ognl.parseExpression("#"+limitField);
					delault = Ognl.getValue(tree, WebContext.getUser());
					//从SessionMap对象取默认数据范围属性的值
					if(!BlankUtils.isBlank(delault)){
						delault = Ognl.getValue(tree, SessionFactory.getSessionMap());
					}
					//从ContextMap对象取默认数据范围属性的值
					if(!BlankUtils.isBlank(delault)){
						delault = Ognl.getValue(tree, SessionFactory.getContextMap());
					}
					//从ApplicationMap对象取默认数据范围属性的值
					if(!BlankUtils.isBlank(delault)){
						delault = Ognl.getValue(tree, SessionFactory.getApplicationMap());
					}
				} catch (OgnlException e) {
				}
				if(!BlankUtils.isBlank(delault)){
					Class clazz = delault.getClass();
					List<Object> objects = new ArrayList<Object>();
					if(clazz.isArray()){
						for (int index = 0; index < Array.getLength(delault); index++) {
							objects.add(Array.get(delault, index));
						}
					}else if(delault instanceof Collection){
						objects.addAll(((Collection)delault));
					}else if(clazz.isPrimitive() || delault instanceof String){
						 //基本数据类型,java内置对象，封装类
						objects.add(String.valueOf(delault));
					}else{
						objects.add(null);
					}
					
					List<String> parts = new ArrayList<String>();
					if(objects.size() > 1){
						List<String> tmp = new ArrayList<String>();
						for (Object obj : objects) {
							tmp.add(alias+"."+limitField + "='" + (BlankUtils.isBlank(obj) ? "null" : obj.toString()) + "'");
						}
						parts.add((tmp.size() > 1 ? " (" : "")+StringUtils.join(tmp, " or ")+(tmp.size() > 1 ? ") " : ""));
					}else{
						parts.add(alias+"."+limitField + "='" + (BlankUtils.isBlank(objects.get(0)) ? "null" : objects.get(0).toString()) + "'");
					}
					if(parts.size()>0){
						conditions.add(StringUtils.join(parts, " and "));
					}
				}else{
					conditions.add(alias+"."+limitField+"=null");
				}
			}
			return StringUtils.join(conditions, " and ");
		}
	}
	
	protected static List<String> getTableMapperSQL(Map<String, String> matcherRule,String alias) {
		// 数据范围需过滤的条件字段
		String mapperField = matcherRule.get(mapper_field);
		// 分解映射的字段
		String[] mapperField_split = StringUtils.splits(mapperField, ",");
		if (mapperField_split.length == 0 ) {
			throw new RuntimeException("匹配字段与过滤字段映射关系不能为空，请检查第5个参数！");
		}
		//构建两个表直接的关联关系
		List<String> mappers = new ArrayList<String>();
		for (String split_str : mapperField_split) {
			String[] split_arr = StringUtils.splits(split_str, "=");
			mappers.add(alias+"."+split_arr[0]+" =  tmp."+split_arr[1]);
		}
		return mappers;
	}
	
	
	/**
	 * 
	 *@描述： 构建数据范围限制条件SQL
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-18上午10:19:07
	 *@param dataRangeCopy
	 *@param matcherRule
	 *@param dataRangeFields
	 *@param alias
	 *@param isAllCollege
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected static List<String> getDataRangeLimitSQL(List<SjfwzModel> dataRangeCopy,Map<String, String> matcherRule,List<String> dataRangeFields,String alias,boolean isAllCollege) {
		// 构建数据范围限制条件SQL
		List<String> conditions = new ArrayList<String>();
		//具体权限
		for (SjfwzModel dataRange : dataRangeCopy) {
			List<String> parts = getDataRangeRowSQL(matcherRule, dataRangeFields , dataRange, alias , isAllCollege);
			if(parts.size()>0){
				conditions.add((parts.size() > 1 ? " ( " : "") + StringUtils.join(parts, " and ") + (parts.size() > 1 ? " ) " : "") );
			}
		}
		return conditions;
	}
	
	protected static List<String> getDataRangeRowSQL(Map<String, String> matcherRule,List<String> dataRangeFields,SjfwzModel dataRange,String alias,boolean isAllCollege) {
		
		//范例：{func('xs_xsjbxxb';'xh';'ssxy_id,zy_id,nj_id';'jg_id,zyh_id,njdm_id';'xh1=xh2')}
		//'ssxy_id,zy_id,nj_id'
		String tableField = matcherRule.get(table_field); // 表字段
		//'jg_id,zyh_id,njdm_id'
		String filterField = matcherRule.get(filter_field); // 数据范围需过滤的条件字段
		//[ssxy_id,zy_id,nj_id]
		String[] tableFields = StringUtils.splits(tableField, ",");
		//[jg_id,zyh_id,njdm_id]
		String[] filterFields = StringUtils.splits(filterField, ",");
		//条件集合
		List<String> conditions = new ArrayList<String>();
		if (tableFields!=null &&  filterFields!=null && tableFields.length == filterFields.length) {
			Set<String> dataRangeFields_final = new HashSet<String>();
			for (String dataRangeField : dataRangeFields) {
				if(!BlankUtils.isBlank(dataRangeField)){
					dataRangeFields_final.add(dataRangeField);
				}
			}
			
			//解析当前行数据范围值
			//jg_id=[2],njdm_id=[2,3,4,FB01BAAAE1251C9EE040007F01001CB3,FC2D5DBF2C58DE51E040007F01006012]
			Map<String, String[]> resolvedRangeMap = getResolvedRangeMap(dataRange.getSjfwztj());
			//判断有无数据范围解析结果
			if(!BlankUtils.isBlank(resolvedRangeMap)){
				
				//判断当前数据范围组合是否作为条件
				
				/**
				 * 过滤字段： zydm_id,jg_id,njdm_jd
				 * 用于数据范围：jg_id=319
				 */
				boolean flag = false;
				for (String key : filterFields) {
					//当前数据范围组的key必须都在过滤条件；才会被作为一个过滤条件生成SQL
					if(ArrayUtils.contains(resolvedRangeMap.keySet().toArray(), key) && ArrayUtils.contains(dataRangeFields_final.toArray(), key)){
						flag = true;
						break;
					}
				}
				//判断是否作为条件
				if(flag){
					// 循环 筛选，过滤字段
					for (int f = 0; f < filterFields.length; f++) {
						//数据范围表匹配字段：第f个过滤字段
						String filter_filed = filterFields[f];
						//用于限制范围的查询表：第f个表字段
						String table_filed = tableFields[f];
						//取得表字段对应的数据范围值 ;如：njdm_id=2010,2012 >> [2010,2012]
						String[] filed_splits = resolvedRangeMap.get(filter_filed);
						//跳过特殊意义的数据范围定义值
						if(filter_filed.equalsIgnoreCase("jg_id")){
							// 如果jg_id为-3则为全学院权限
							if (filed_splits!=null && ( ArrayUtils.contains(filed_splits, "-1")||ArrayUtils.contains(filed_splits, "-2")||ArrayUtils.contains(filed_splits, "-3")) ) {
								//跳过特殊意义的数据范围值
								continue;
							}
						}else{
							//全学院权限时，跳过其他数据范围限制字段，仅 jg_id 可通过
							if(isAllCollege){
								continue;
							}
						}
						
						//得到匹配数据范围值
						if(filed_splits!=null ){
							List<String> parts = new ArrayList<String>();
							if(filed_splits.length > 1){
								List<String> tmp = new ArrayList<String>();
								for (String string : filed_splits) {
									tmp.add(alias+"."+table_filed + "='" + string + "'");
								}
								parts.add((tmp.size() > 1 ? " (" : "")+StringUtils.join(tmp, " or ")+(tmp.size() > 1 ? ") " : ""));
							}else{
								parts.add(alias+"."+table_filed + "='" + filed_splits[0] + "'");
							}
							
							if(parts.size()>0){
								conditions.add(StringUtils.join(parts, " and "));
							}
						}
					}
				}
				
				
			}
		}
		return conditions;
	}
	
	/**
	 * 
	 *@描述：是否受到数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-17下午02:36:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param dataRanges
	 *@return
	 */
	public static boolean controllable(List<SjfwzModel> dataRanges) {
		//存在数据范围
		if(!BlankUtils.isBlank(dataRanges)){
			boolean flag = true;
			//循环数据范围对象
			for (SjfwzModel dataRange : dataRanges) {
				//存在数据范围
				if(!BlankUtils.isBlank(dataRange)){
					//解析当前行数据范围值
					//jg_id=[2],njdm_id=[2,3,4,FB01BAAAE1251C9EE040007F01001CB3,FC2D5DBF2C58DE51E040007F01006012]
					Map<String, String[]> resolvedRangeMap = getResolvedRangeMap(dataRange.getSjfwztj());
					//获得机构数据范围
					String[] filed_splits = resolvedRangeMap.get("jg_id");
					// 如果部门代码为-1则为超级管理员权限,为-2则为全校权限,为-3则为全学院权限,直接返回条件成功。
					if (filed_splits!=null && (ArrayUtils.contains(filed_splits, "-1") || ArrayUtils.contains(filed_splits, "-2"))) {
						flag = false;
						break;
					}
				}
			}
			return flag;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 *@描述：是否全学院权限
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-17下午05:24:12
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param dataRanges
	 *@return
	 */
	public static boolean isAllCollege(List<SjfwzModel> dataRanges) {
		//存在数据范围
		if(!BlankUtils.isBlank(dataRanges)){
			boolean flag = false;
			//循环数据范围对象
			for (SjfwzModel dataRange : dataRanges) {
				//存在数据范围
				if(!BlankUtils.isBlank(dataRange)){
					//解析当前行数据范围值
					//jg_id=[-3],njdm_id=[2,3,4,FB01BAAAE1251C9EE040007F01001CB3,FC2D5DBF2C58DE51E040007F01006012]
					Map<String, String[]> resolvedRangeMap = getResolvedRangeMap(dataRange.getSjfwztj());
					//获得机构数据范围
					String[] filed_splits = resolvedRangeMap.get("jg_id");
					// 如果jg_id为-3则为全学院权限
					if (filed_splits!=null && ArrayUtils.contains(filed_splits, "-3") ) {
						flag = true;
						break;
					}
				}
			}
			return flag;
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 
	 *@描述：分解数据范围对象
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-17下午02:08:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param rangeStr
	 *@return
	 */
	public static Map<String, String[]> getResolvedRangeMap(String rangeStr){
		Map<String, String[]> hashMap = new HashMap<String, String[]>();
		if(!BlankUtils.isBlank(rangeStr)){
			// 分解当前数据范围的数据范围组合，1个以上
			//jg_id=2;njdm_id=2,3,4,FB01BAAAE1251C9EE040007F01001CB3,FC2D5DBF2C58DE51E040007F01006012
			String[] splits = StringUtils.splits(rangeStr, ";");
			//循环组合
			for (int i = 0; i < splits.length; i++) {
				// 分解当前数据范围组第i个数据范围项
				String[] limit = StringUtils.splits(splits[i], "=");
				// 得到数据范围字段
				String filed_key = limit[0];
				// 得到数据范围值
				String filed_value = limit[1];
				// 分割数据范围值为数组：解决多个值的问题
				String[] filed_splits = StringUtils.splits(filed_value, ",");
				// 放到Map中
				hashMap.put(filed_key, filed_splits);
			}
		}
		return hashMap;
	}
	
	protected static Map<String, String> getResolvedMap(String prefix,String ruleStr) {
		Map<String, String> hashMap = new HashMap<String, String>();
		/*
		 * 匹配所有{}包括的内容；如： 
		 * { xsjtb.nj in func("jw_xjgl_xsxjxxb","xm","bj","bh_id") }
		 * { func("jw_xjgl_xsxjxxb","xm","bj","bh_id")}
		 * { func('jw_xjgl_xsxjxxb';'xh_id';'jg_id,zyh_id,njdm_id,bh_id';'jg_id,zyh_id,njdm_id,bh_id';'xh_id=xh_id')}
		 * { limit('xs_xsjbxxb';'ssxy_id';'jg_id') } 
		 * { limit('jw_xjgl_xsxjxxb';'jw_xjgl_xsjbxxb';'xsbj,jg_id,njdm_id';'xsxzdm,jg_id,njdm_id';'xn_id=xh_id')}
		 * { bitand('t.xsbj';'xsxzm')}
		 * { t.xh_id in bitand('jw_xjgl_xsjbxxb';'xh_id';'a.xsbj';'xsxzm')}
		 */
		// 分解SQL语句
		String[] ruleStrs = StringUtils.splits(ruleStr, ";");
		/*
		 * 1：匹配字段过滤时候查询的表,
		 * 2：匹配字段过滤时候查询的表的关联表
		 * 3：匹配的过滤字段,
		 * 4：匹配字段过滤时候查询的表的条件字段名称,
		 * 5：数据范围对象的字段名称
		 * 6：原查询SQL中与对应过滤查询表的过滤字段的字段名称[此参数在 in 的模式下可没有]
		 */
		String[] keys = null;
		if("func".equalsIgnoreCase(prefix)){
			/*
			 * 匹配所有{}包括的内容；如： 
			 * {xsjtb.nj in func("jw_xjgl_xsxjxxb","xm","bj","bh_id") }
			 * {func("jw_xjgl_xsxjxxb","xm","bj","bh_id")}
			 * {func('jw_xjgl_xsxjxxb';'xh_id';'jg_id,zyh_id,njdm_id,bh_id';'jg_id,zyh_id,njdm_id,bh_id';'xh_id=xh_id')}
			 */
			keys = new String[]{table_name,match_field,table_field,filter_field,mapper_field};
		}else if("limit".equalsIgnoreCase(prefix)){
			/*
			 * 匹配所有{}包括的内容；如： 
			 * { limit('xs_xsjbxxb';'ssxy_id';'jg_id') } 
			 * { limit('jw_xjgl_xsxjxxb';'jw_xjgl_xsjbxxb';'xsbj,jg_id,njdm_id';'xsxzdm,jg_id,njdm_id';'xn_id=xh_id')}
			 */
			switch (ruleStrs.length) {
				case 3:{
					keys = new String[]{table_name,table_field,filter_field};
				};break;
				case 4:{
					keys = new String[]{table_name,table_related,table_field,filter_field};
				};break;
				case 5:{
					keys = new String[]{table_name,table_related,table_field,filter_field,mapper_field};
				};break;	
			}
		}else if("bitand".equalsIgnoreCase(prefix)){
			/*
			 * 匹配所有{}包括的内容；如： 
			 * { bitand('t.xsbj';'xsxzm')}
			 * { t.xh_id in bitand('jw_xjgl_xsjbxxb';'xh_id';'a.xsbj';'xsxzm')}
			 */
			switch (ruleStrs.length) {
				case 2:{
					keys = new String[]{table_field,filter_field};
				};break;
				case 4:{
					keys = new String[]{table_name,table_field,match_field,filter_field};
				};break;
			}
		}
		if(keys != null && ruleStrs.length > 0){
			for (int i = 0; i < ruleStrs.length; i++) {
				if(i<keys.length){
					hashMap.put(keys[i], ruleStrs[i].replace("\"", "").replace("\'", ""));
				}else{
					hashMap.put(keys[i], null);
				}
			}
		}
		return hashMap;
	}

}
