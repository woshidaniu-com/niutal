/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import java.util.Map;


/**
 * @author xiaokang
 *	常规验证器
 *
 */
public interface CommonValidator {
	/**
	 * 验证数据
	 * @return
	 */
	 boolean validate(Map<Object,Object> originalData);
}
