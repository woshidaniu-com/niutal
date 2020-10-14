package com.woshidaniu.designer.utils;

import com.woshidaniu.security.algorithm.MD5Codec;

public class FuncCacheKeyUtils {

	/**
	 * 
	 *@描述：根据【 功能代码 + 操作代码 + 模板名称 + id/name + 后缀】生成MD5加密后的key
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-28上午10:01:21
	 *@param suffix
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String getCacheKey(String func_code,String opt_code,String suffix) {
		return MD5Codec.encrypt( func_code + "-" + opt_code + "-" +  suffix);
	}
	
}
