package com.woshidaniu.designer.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.db.core.utils.JSQLParserUtils;

public abstract class FuncDataUtils {

	public static List<Map<String,String>> getDataColumnList(String data_sql,String ...filters ) throws JSQLParserException {
		List<Map<String,String>> dataColumnList = new ArrayList<Map<String,String>>();
		//解析查询列
		List<String> selectItems =  JSQLParserUtils.getSelectItems(data_sql);
		if(!BlankUtils.isBlank(selectItems)){
			Map<String,String> row = null;
			int index = 0;
			
			for (String field_name : selectItems) {
				//进行字段过滤
				if( filters != null && ArrayUtils.contains(filters, field_name)){
					continue;
				}
				row =  new HashMap<String, String>();
				row.put("field_guid", "field" + index);
				row.put("field_name", field_name);
				row.put("field_index", field_name);
				dataColumnList.add(row);
				index ++;
			}
		}
		return dataColumnList;
	}
	
	public static List<Map<String,String>> getDataColumnList(final List<String> selectItems,List<BaseMap> tableColumnList,String ...filters ) {
		List<Map<String,String>> dataColumnList = new ArrayList<Map<String,String>>();
		try {
			if(!BlankUtils.isBlank(selectItems)){
				Map<String,String> row = null;
				int index = 0;
				//筛选查询字段匹配的表字段
				List<BaseMap> tableColumnList_filter = CollectionUtils.findAll(tableColumnList, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						BaseMap columnMap = (BaseMap)object;
						if(selectItems.contains(columnMap.get("field_name"))){
							return true;
						}
						return false;
					}
				});
				//循环查询字段
				for (String field_name : selectItems) {
					//进行字段过滤
					if( filters != null && ArrayUtils.contains(filters, field_name)){
						continue;
					}
					row =  new HashMap<String, String>();
					row.put("field_guid", "field" + index);
					row.put("field_name", field_name);
					row.put("field_index", field_name);
					for (BaseMap columnMap : tableColumnList_filter) {
						if(field_name.equalsIgnoreCase(columnMap.get("field_name") != null ? columnMap.get("field_name").toString() : "")){
							row.put("field_comment", columnMap.get("field_comment") != null ? columnMap.get("field_comment").toString() : "" );
							break;
						}
					}
					dataColumnList.add(row);
					index ++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return dataColumnList;
	}
	
	public static int getFileCount(int count,int batch_size){
		//得到当前sheet 的总行数，并计算当前sheet需要多少个线程进行导入操作
		int thread_num = 1;
		if(count > batch_size){
			thread_num = (count - count%batch_size)/batch_size;
			if(count%batch_size>0){
				thread_num = thread_num+1;
			}
		}
		return thread_num;//Double.valueOf(Math.ceil(count/batch_size)).intValue();
	}
}
