package com.woshidaniu.ms.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.sms.client.provider.def.DefaultSmsPropertiesProvider;
/**
 * 
 *@类名称		： OracleSmsPropertiesProvider.java
 *@类描述		：整合系统参数设置表数据，为短信发送服务提供配置支持
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月17日 下午2:49:24
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleSmsPropertiesProvider extends DefaultSmsPropertiesProvider {

	/**
	 * 要从数据库提取的属性值key
	 */
	protected List<String> propKeys = new ArrayList<String>();
	
	/**
	 * 多参数获取接口
	 */
	@Resource(name = "keyValuesProvider")
	protected KeyValueProvider<Map<String, String>> keyValuesProvider;

	@Override
	public Properties props() {
		//调用父类方法
		super.props();
		//根据给出的key查找数据库存储的参数
		if(getPropKeys() != null){
			for (String key : getPropKeys()) {
				//此处因为结果中可能包含null值不能使用 props.putAll(t);
				Map<String, String> values = getKeyValuesProvider().get(key);
				if( null != values && !values.isEmpty()){
					for (String key2 : values.keySet()) {
						props.setProperty(key2, StringUtils.killNull(values.get(key2)));
					}
				}
			}
		}
		return props;
	}
	
	public List<String> getPropKeys() {
		return propKeys;
	}

	public void setPropKeys(List<String> propKeys) {
		this.propKeys = propKeys;
	}

	public KeyValueProvider<Map<String, String>> getKeyValuesProvider() {
		return keyValuesProvider;
	}

	public void setKeyValuesProvider(KeyValueProvider<Map<String, String>> keyValuesProvider) {
		this.keyValuesProvider = keyValuesProvider;
	}
	
}
