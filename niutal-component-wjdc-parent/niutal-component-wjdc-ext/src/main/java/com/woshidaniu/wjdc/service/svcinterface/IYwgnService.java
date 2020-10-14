/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.svcinterface;

import java.util.List;

import com.woshidaniu.wjdc.dao.entites.YwgnModel;

/**
 * @author Penghui.Qu(445)
 *   说明：问卷调查-业务功能
 * @author zhidong(1571)
 *   补充,整理,优化
 */
public interface IYwgnService {
	/**
	 * @description	：查询业务功能
	 * @return
	 */
	public List<YwgnModel> getYwgnList();
	/**
	 * @description	： 获得所有业务功能配置，关联试题
	 * @return
	 */
	public List<YwgnModel> getAllYwgnList();
}
