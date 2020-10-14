/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.excel;

import java.util.List;
import java.util.Map;
/**
 * @author xiaokang
 */
public interface IDrFzData {

	/**
	 * DM -- 代码 
	 * MC -- 名称
	 * 大写
	 * @return
	 */
	public List<Map<String , String>> data();
	
}
