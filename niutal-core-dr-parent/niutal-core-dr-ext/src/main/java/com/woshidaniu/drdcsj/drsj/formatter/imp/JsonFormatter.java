/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatterContants;

/**
 * @author 982 张昌路
 * json模式格式化
 */
public class JsonFormatter implements ImportFormatter{
	
	private static Logger log = LoggerFactory.getLogger(JsonFormatter.class); 

	private final Map<String,Map<String,String>> cache = new HashMap<String,Map<String,String>>(); 
	
	@Override
	@SuppressWarnings("unchecked")
	public FormatResult format(DrlpzModel drlpzModel, String value) {
		//空不做处理
		if(StringUtils.isBlank(value)){
			return new DefaultFormatResult(value);
		}
		// 获取列数据格式化参数
		String orignFormmterParam = drlpzModel.getLsjgsh();
		
		if (StringUtils.isNotBlank(orignFormmterParam)) {
			// 如果不存在json数据标志
			if (orignFormmterParam.toLowerCase().indexOf(ImportFormatterContants._JSON) < 0) {
				throw new IllegalArgumentException("非法json数据["+ orignFormmterParam +"]");
			} else {
				//缓存探测
				Map<String,String> pair = this.cache.get(orignFormmterParam);
				if(pair != null) {
					String result = pair.get(value);
					if(result != null) {
						if(log.isTraceEnabled()) {
							log.trace("命中缓存:{},源文本:{}",value,orignFormmterParam);							
						}
						return new DefaultFormatResult(result);
					}
				}else {
					pair = new HashMap<String,String>();
				}
				
				String formmterParam = orignFormmterParam.substring(orignFormmterParam.toLowerCase().indexOf(ImportFormatterContants._JSON)+ ImportFormatterContants._JSON_LENGHT, orignFormmterParam.length());
				JSONObject json = JSONObject.fromObject(formmterParam);
				
				Set<String> keySet = json.keySet();
				Iterator<String> it = keySet.iterator();
				while(it.hasNext()) {
					String k = it.next();
					String v = json.getString(k);
					pair.put(k, v);
				}
				this.cache.put(orignFormmterParam, pair);
				if(log.isTraceEnabled()) {
					log.trace("存入缓存key:{} value:{}",orignFormmterParam,pair);					
				}
				
				String tempFormmateValue = pair.get(value);
				// 不存在对应格式化的值
				if (null == tempFormmateValue || StringUtils.isBlank(tempFormmateValue.toString())) {
					String error = getErrorMessage(drlpzModel, value, json);
					return new DefaultFormatResult(null,error);
				} else {
					return new DefaultFormatResult(tempFormmateValue.toString());
				}
			}
		}else {
			//can't happen
			return new DefaultFormatResult(value);
		}
	}

	/**
	 * 获取错误信息 
	 * @param drlpzModel 导入列信息
	 * @param value 列值
	 * @param json JSONObject对象
	 * @return String 错误信息
	 */
	@SuppressWarnings("unchecked")
	private String getErrorMessage(DrlpzModel drlpzModel, String value,JSONObject json) {
		if (StringUtils.isNotBlank(drlpzModel.getGshxx())) {
			return "[" + drlpzModel.getDrlmc() + "]"+drlpzModel.getGshxx();
		}
		String jhts = null;
		Iterator<String> it = json.entrySet().iterator();
		while (it.hasNext()) {
			jhts += it.next() + ",";
		}
		return "[" + drlpzModel.getDrlmc() + "]的值只能在[" + jhts + "]集合中";
	}
}
