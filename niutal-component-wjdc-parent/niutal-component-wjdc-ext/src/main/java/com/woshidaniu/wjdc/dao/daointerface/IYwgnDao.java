/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.wjdc.dao.entites.YwgnModel;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Repository("ywgnDao")
public interface IYwgnDao {
	/**
	 * 查询业务功能
	 * @return
	 */
	List<YwgnModel> getYwgnList();
	/**
	 * 查询所有业务功能
	 * @return
	 */
	List<YwgnModel> getAllYwgnList();
}
