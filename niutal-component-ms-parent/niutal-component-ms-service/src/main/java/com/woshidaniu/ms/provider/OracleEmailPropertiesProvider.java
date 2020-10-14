package com.woshidaniu.ms.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.javamail.JavaMailKey;
import com.woshidaniu.javamail.provider.def.DefaultEmailPropertiesProvider;
/**
 * 
 *@类名称		： OracleEmailPropertiesProvider.java
 *@类描述		：整合系统参数设置表和邮箱服务配置表数据，为邮件发送服务提供配置支持
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月17日 上午11:42:47
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleEmailPropertiesProvider extends DefaultEmailPropertiesProvider {

	/**
	 * 要从数据库提取的属性值key
	 */
	protected List<String> propKeys = new ArrayList<String>();
	
	@Resource(name = "keyValueProvider")
	protected KeyValueProvider<String> keyValueProvider;
	
	@Override
	public Properties props() {
		//调用父类方法
		super.props();
		
		props.putAll(System.getProperties());
		
		//根据给出的key查找数据库存储的参数
		if(getPropKeys() != null){
			for (String key : getPropKeys()) {
				props.setProperty(key, StringUtils.killNull(getKeyValueProvider().get(key)));
			}
		}
		
		try {
			
			String mail_user = getKeyValueProvider().get(JavaMailKey.MAIL_USER);
			String mail_password = getKeyValueProvider().get(JavaMailKey.MAIL_PASSWORD);
			String mail_host = getKeyValueProvider().get(JavaMailKey.MAIL_HOST);
			String mail_port = getKeyValueProvider().get(JavaMailKey.MAIL_PORT);
			
			props.setProperty(JavaMailKey.MAIL_HOST, mail_host);
			props.setProperty(JavaMailKey.MAIL_FROM_DESC, getKeyValueProvider().get(JavaMailKey.MAIL_FROM_DESC));
			props.setProperty(JavaMailKey.MAIL_PORT, mail_port);
			props.setProperty(JavaMailKey.MAIL_USER, mail_user);
			props.setProperty(JavaMailKey.MAIL_PASSWORD, mail_password);
			
			props.setProperty(JavaMailKey.MAIL_SMTP_HOST, mail_host);
			props.setProperty(JavaMailKey.MAIL_SMTP_PORT, mail_port);
			//commons-mail需要设置mail.smtp.user和mail.smtp.password，才能正常发送邮件
			props.setProperty(JavaMailKey.MAIL_SMTP_USER, mail_user);
			props.setProperty(JavaMailKey.MAIL_SMTP_PASSWORD, mail_password);
			
			props.setProperty(JavaMailKey.MAIL_SMTP_AUTH, String.valueOf(BooleanUtils.parse(getKeyValueProvider().get(JavaMailKey.MAIL_SMTP_AUTH))));
			props.setProperty(JavaMailKey.MAIL_SMTP_TIMEOUT,  getKeyValueProvider().get(JavaMailKey.MAIL_SMTP_TIMEOUT));
			props.setProperty(JavaMailKey.MAIL_TRANSPORT_PROTOCOL, getKeyValueProvider().get(JavaMailKey.MAIL_TRANSPORT_PROTOCOL));
			props.setProperty(JavaMailKey.MAIL_SMTP_SSL_ENABLE, String.valueOf(BooleanUtils.parse(getKeyValueProvider().get(JavaMailKey.MAIL_SMTP_SSL_ENABLE))));
	 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	public KeyValueProvider<String> getKeyValueProvider() {
		return keyValueProvider;
	}

	public void setKeyValueProvider(KeyValueProvider<String> keyValueProvider) {
		this.keyValueProvider = keyValueProvider;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public List<String> getPropKeys() {
		return propKeys;
	}

	public void setPropKeys(List<String> propKeys) {
		this.propKeys = propKeys;
	}
	
}
