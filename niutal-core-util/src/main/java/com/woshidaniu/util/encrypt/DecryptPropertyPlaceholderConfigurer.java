package com.woshidaniu.util.encrypt;

import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：扩展spring属性文件解析器，对.properties文件部分属性值解密
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月17日上午10:06:36
 */
@Deprecated
public class DecryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	
	private List<String> encryptKeys ;
	
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		
		if (encryptKeys.contains(propertyName)){
			return new DBEncrypt().dCode(propertyValue.getBytes());
		}
		return propertyValue;
	}

	public void setEncryptKeys(List<String> encryptKeys) {
		this.encryptKeys = encryptKeys;
	}
	
	
}
