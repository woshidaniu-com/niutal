/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;

/**
 * @description	： 统计时间和次数的Formatter
 * @author 		：康康（1571）
 */
public class TimeFormatterFactory implements FormatterFactory{
	
	private static final Logger log = LoggerFactory.getLogger(TimeFormatterFactory.class);
	
	private FormatterFactory orginal;
	
	private Map<Class<?>,Long> timeCache = new HashMap<Class<?>,Long>();
	private Map<Class<?>,Long> countCache = new HashMap<Class<?>,Long>();

	public TimeFormatterFactory(FormatterFactory orginal) {
		super();
		this.orginal = orginal;
	}

	@Override
	public ImportFormatter getFormatter(String lsjgsh) {
		ImportFormatter delagate = this.orginal.getFormatter(lsjgsh);
		
		//TODO 这个类比较难处理,以后有时间做一下
		//if(delagate instanceof CSqlFormatter) {
		//	
		//}
		return new TimeProxyImportFormatter(delagate);			
	}
	
	/**
	 * @description	： 打印每种Formatter的耗时
	 */
	public void tips() {
		Iterator<Entry<Class<?>, Long>> it = timeCache.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Class<?>, Long> e = it.next();
			Class<?> clazz = e.getKey();
			Long count = countCache.get(clazz);
			log.info("格式化器:[{}]总调用次数:[{}],总消耗时间:[{}]ms",clazz,count,e.getValue());
		}
	}
	
	private class TimeProxyImportFormatter implements ImportFormatter{
		
		private ImportFormatter orginalFormatter;

		public TimeProxyImportFormatter(ImportFormatter orginalFormatter) {
			this.orginalFormatter = orginalFormatter;
		}

		@Override
		public FormatResult format(DrlpzModel drlpzModel, String value) {
			long start = System.currentTimeMillis();
			
			FormatResult formatResult = this.orginalFormatter.format(drlpzModel, value);
			
			
			//执行时间
			{
				long end = System.currentTimeMillis();
				long costTime = end - start;
				
				Long time = timeCache.get(this.orginalFormatter.getClass());
				if(time == null) {
					timeCache.put(this.orginalFormatter.getClass(),new Long(costTime));
				}else {
					long newCost = time.longValue() + costTime;
					timeCache.put(this.orginalFormatter.getClass(),new Long(newCost));
				}
			}
			
			//调用次数
			{
				Long count = countCache.get(this.orginalFormatter.getClass());
				if(count == null) {
					countCache.put(this.orginalFormatter.getClass(),Long.valueOf(1));
				}else {
					long newCount = count.longValue() + 1;
					countCache.put(this.orginalFormatter.getClass(),Long.valueOf(newCount));
				}
			}
			
			return formatResult;
		}
	}
}
