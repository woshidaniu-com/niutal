package com.woshidaniu.api.conf;

import java.util.Properties;

public interface PropertiesProvider {

	public Properties props();
	
	public boolean set(String key, String value);
	
	public void setProps(Properties props);
	
}
