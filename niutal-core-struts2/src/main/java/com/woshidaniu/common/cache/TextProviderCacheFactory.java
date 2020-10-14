package com.woshidaniu.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.opensymphony.xwork2.TextProviderFactory;
import com.woshidaniu.basemodel.bundle.MultipleResourceBundle;

public final class TextProviderCacheFactory {

	private static final TextProviderFactory tpf = new TextProviderFactory();
	// 用于保存TextProvider的实例对象
    private static final ConcurrentMap<String, MultipleResourceBundle> resourceBundle_Map = new ConcurrentHashMap<String, MultipleResourceBundle>();
	
    public static TextProviderFactory getTextProviderFactory() {
		return tpf;
	}
    
	public static ConcurrentMap<String, MultipleResourceBundle> getMultipleResourceBundleMap() {
		return resourceBundle_Map;
	}
    
}
