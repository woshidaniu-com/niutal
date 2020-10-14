/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.dao.entities.XsxxModel;
/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Repository("wjdcExportDao")
public interface IWjdcExportDao {
	/**
	 * @description	： 查询问卷回答学生基础信息
	 * @param wjid 问卷id
	 * @return
	 */
	public List<XsxxModel> queryWjhdxsBaseInfo(@Param("wjid")String wjid);
	/**
	 * @description	： 查询回答问卷的答卷人id集合
	 * @param wjid 问卷id
	 * @return
	 */
	public List<String> queryWjhdDjr(@Param("wjid")String wjid);
}
