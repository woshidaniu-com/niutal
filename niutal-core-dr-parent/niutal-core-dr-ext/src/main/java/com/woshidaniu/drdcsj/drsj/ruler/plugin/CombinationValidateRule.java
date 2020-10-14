/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.plugin;

import java.util.List;
import java.util.Map;

/**
 * @author kzd
 */
public interface CombinationValidateRule {

	void init();
	
	void destroy();
	
	/**
	 * 批量方式
	 * @param drlmcs 导入列
	 * @param drlValues 导入列值
	 * @param count 当前条数
	 * @return
	 */
	boolean[] validate(String[] drlmcs, Map<String, List<String>> drlValues);
	
}
