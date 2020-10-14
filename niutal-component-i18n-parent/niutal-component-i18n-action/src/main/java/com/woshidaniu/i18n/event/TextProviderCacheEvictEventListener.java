package com.woshidaniu.i18n.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.woshidaniu.common.cache.TextProviderCacheFactory;
/**
 * 
 *@类名称		： TextProviderCacheEvictEventListener.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月23日 下午3:02:58
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component
public class TextProviderCacheEvictEventListener implements ApplicationListener<TextProviderCacheEvictEvent> {

	@Override
	public void onApplicationEvent(TextProviderCacheEvictEvent event) {
		TextProviderCacheFactory.getMultipleResourceBundleMap().clear();
	}
	
}
