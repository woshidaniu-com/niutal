package com.woshidaniu.common.freemarker;

import org.springframework.core.Ordered;

/**
 * 
 * @className	： RuntimeVariables
 * @description	： 运行时配置
 * @author 		：康康（1571）
 * @date		： 2018年6月22日 上午10:09:44
 * @version 	V1.0
 */
public interface RuntimeVariables extends Ordered{

	public String value(String key);
	
}
