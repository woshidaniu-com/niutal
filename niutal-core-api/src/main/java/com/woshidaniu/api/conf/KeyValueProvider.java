package com.woshidaniu.api.conf;

public interface KeyValueProvider<T> {

	public T get(String key);
	
	public boolean set(String key, String value);
	
}
