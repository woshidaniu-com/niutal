/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.utils;

import java.util.ArrayList;
import java.util.List;

import com.woshidaniu.entities.PairModel;
import com.woshidaniu.basicutils.BlankUtils;

/**
 *@类名称:FixedStringUtils.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Oct 27, 2015 12:40:49 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */

public class FixedStringUtils {
	
	public static List<PairModel> getFixedList(String field_source,String split){
		List<PairModel> field_data = new ArrayList<PairModel>();
		if(!BlankUtils.isBlank(field_source)){
			for (String key : field_source.split(",")) {
				if(key.indexOf(split) > 0){
					String[] arrStrings = key.split(split);
					field_data.add(new PairModel(arrStrings[0],arrStrings[1]));
				}else{
					field_data.add(new PairModel(key,key));
				}
			}
		}
		return field_data;
	} 
	
	public static String getQuerySQL(String field_source){
		String bm = null;// 表名
		String dm = null;// 代码
		String mc = null;// 名称
		String param = null;//参数
		StringBuffer sb = new StringBuffer();
		if (field_source != null) {
			String[] szzs1 = field_source.split("[|]");
			field_source = szzs1[0];
			//参数
			if(szzs1.length > 1){
				param = szzs1[1];
			}
			String[] szzs = field_source.split(":");
			if (szzs != null && szzs.length > 1) {
				//表名
				bm = szzs[0];
				//字段代码
				dm = szzs[1].split(",")[0];
				//字段名称
				mc = szzs[1].split(",")[1];
				//组织SQL
				sb.append("select distinct ").append(dm).append(" key,").append(mc).append(" value ");
				sb.append(" from ").append(bm).append(" where ").append(dm).append(" is not null ");
				if(param != null){
					sb.append(" and ");
					sb.append(param);
				}
				sb.append(" order by  ");
				sb.append(dm);
			}
		}
		return sb.toString();
	} 
	
}
