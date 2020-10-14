/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

/**
 * @author 康康（1571）
 * handler的上下文
 */
public interface HandlerContext {
	
	/**
	 * @description	： 获得属性
	 * @param key
	 * @return
	 */
	Object getAttr(String key);
	
	/**
	 * @description	： 移除属性
	 * @param key
	 * @return
	 */
	Object removeAttr(String key);
	
	/**
	 * @description	： 放入属性
	 * @param key
	 * @param value
	 * @return
	 */
	Object putAttr(String key, Object value);
	
	/**
	 * @description	： 当前操作用户
	 * @return
	 */
	String getUserName();
	
	/**
	 * @description	： 获得sqlSession
	 * @return
	 */
	SqlSession getSqlSession();
	
	/**
	 * @description	： 获得导入模块代码
	 * @return
	 */
	String getDrmkdm();

	/**
	 * @description	： 获得导入模块名称
	 * @return
	 */
	String getDrmkmc();

	/**
	 * @description	： 获得导入表名称
	 * @return
	 */
	String getDrbmc();

	/**
	 * @description	： 获得导入方式
	 * 1 插入
	 * 2 更新
	 * 3 插入并更新
	 * @return
	 */
	String getCrfs();
	
	/**
	 * @description	： 获得选择的列名称
	 * @return
	 */
	List<String> getSelectColumns();

}
