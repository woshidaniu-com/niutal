package com.woshidaniu.common.datarange;

import java.util.List;

import com.woshidaniu.common.log.User;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据资源规则
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月22日下午1:47:25
 */
public interface DataRangeService {

		/**
		 * 
		 * <p>方法说明：获取用户数据范围<p>
		 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
		 * <p>时间：2017年3月22日下午1:48:38<p>
		 * @param user 登录用户
		 * @return list
		 */
		List<String> getDataRangeList(User user);
}
