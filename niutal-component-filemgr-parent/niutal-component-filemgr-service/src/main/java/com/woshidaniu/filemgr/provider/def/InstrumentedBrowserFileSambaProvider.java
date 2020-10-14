package com.woshidaniu.filemgr.provider.def;

import static com.codahale.metrics.MetricRegistry.name;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.codahale.metrics.Timer;
import com.codahale.metrics.biz.MetricsFactory;

public class InstrumentedBrowserFileSambaProvider extends BrowserFileSmabaProvider implements InitializingBean {

	@Resource
	protected MetricsFactory metricsFactory;
	protected Timer getTimer;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		this.getTimer = metricsFactory.getTimer(name(this.getClass(), "browser", "samba", "gets"));
	}
	
	@Override
	public File get(String browserType) throws Exception {
		final Timer.Context ctx = getTimer.time();
        try {
            return super.get(browserType);
        } finally {
            ctx.stop();
        }
	}

	
}
