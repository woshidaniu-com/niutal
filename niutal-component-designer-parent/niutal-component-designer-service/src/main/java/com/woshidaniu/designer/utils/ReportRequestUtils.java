/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.designer.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.MapRowModel;

/**
 *@类名称		： ReportRequestUtils.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2016-6-28 下午04:13:39
 *@修改人		：
 *@修改时间	：
 *@版本号		: v1.0
 */
public class ReportRequestUtils {
	

	/**
	 * 
	 *@描述			: 解析报表请求参数
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-28下午04:43:19
	 *@param mapRow
	 *@param requestList
	 *@param request
	 *@param funcModel
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static Map<String,List<String>> getMapParameters(MapRowModel requestMap,List<MapRowModel> requestList) {
		//解析请求的参数
		Map<String,List<String>> requestMaps = new HashMap<String, List<String>>();
		String tmpVal = null;
		//解析查询区域条件参数
		if(!BlankUtils.isBlank(requestMap)){
			//获取条件参数
			Map<String, String> mapRow = requestMap.getRow();
			//对model中的map进行处理
			for (String key : mapRow.keySet()) {
				tmpVal = mapRow.get(key);
				if(!BlankUtils.isBlank(tmpVal)){
					if(BlankUtils.isBlank(requestMaps.get(key))){
						requestMaps.put(key, new ArrayList<String>());
					}
					requestMaps.get(key).add(tmpVal);
				}
			}
		}
		//解析选择行数据参数
		List<MapRowModel> killNullRequestList = CollectionUtils.killNull(requestList);
		if(!BlankUtils.isBlank(killNullRequestList)){
			for (MapRowModel rowModel : killNullRequestList) {
				//对model中的map进行处理
				for (String key : rowModel.getRow().keySet()) {
					tmpVal = rowModel.getRow().get(key);
					if(!BlankUtils.isBlank(tmpVal)){
						if(BlankUtils.isBlank(requestMaps.get(key))){
							requestMaps.put(key, new ArrayList<String>());
						}
						requestMaps.get(key).add(tmpVal);
					}
				}
			}
		}
		return requestMaps;
	}
	
	/**
	 * 
	 *@描述			: 解析报表请求参数
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-28下午04:43:19
	 *@param mapRow
	 *@param requestList
	 *@param request
	 *@param funcModel
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static List<PairModel> getPairParameters(MapRowModel requestMap,List<MapRowModel> requestList,String func_type) {
		//解析请求的参数
		Map<String,List<String>> requestMaps = ReportRequestUtils.getMapParameters(requestMap, requestList);
		List<PairModel> params = new ArrayList<PairModel>();
		for (String key : requestMaps.keySet()) {
			//系统自定义功能类型；默认：数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'预览打印',4:'快速打印',5:'数据导出',6:'数据删除'
		    /*if("3".equals(func_type)){
		    	try {
					//params.add(new PairModel(key, StringUtil.join(requestMaps.get(key), ",")));
					params.add(new PairModel(key, URLDecoder.decode(StringUtil.join(requestMaps.get(key), ","), "UTF-8")));
				} catch (Exception e) {
					params.add(new PairModel(key, StringUtil.join(requestMaps.get(key), ",")));
				}
		   	}else{*/
		   		params.add(new PairModel(key, StringUtils.join(requestMaps.get(key), ",")));
		   	//}
		}
		return params;
	}
	
}
