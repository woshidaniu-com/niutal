/**
 * 
 */
package com.woshidaniu.tjcx;

import com.woshidaniu.tjcx.dao.entites.YsjModel;

/**
 * @author zhidong.kang
 *
 */
public final class TjcxThreadLocalUtil {

	static final ThreadLocal<YsjModel> YSJ_THREADLOCAL = new ThreadLocal<YsjModel>();

	public static void setYsjThreadLocal(YsjModel model) {
		YSJ_THREADLOCAL.set(model);
	}

	public static YsjModel getYsjThreadLocal() {
		return YSJ_THREADLOCAL.get();
	}

	public static void clear() {
		YSJ_THREADLOCAL.remove();
	}
}
