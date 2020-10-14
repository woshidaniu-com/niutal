package com.woshidaniu.pwdmgr.api.provider.def;

import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.provider.CacheKeyProvider;

public class DefaultCacheKeyProvider implements CacheKeyProvider {

	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public String genKey(String prefix, BindData data) {
		/*StringBuilder builder = new StringBuilder(prefix ).append("_").append(data.getUsername()).append("_");
    	for (String key : data.getMap().keySet()) {
    		builder.append(key).append("_").append(data.getMap().get(key));
		}
        return  builder.toString();*/
		return prefix + "_" + data.getUsername();
	}
	
}
