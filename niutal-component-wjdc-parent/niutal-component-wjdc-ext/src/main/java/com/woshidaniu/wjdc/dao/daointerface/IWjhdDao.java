/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.wjdc.dao.entites.WjhdModel;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Repository("wjhdDao")
public interface IWjhdDao {

	public List<WjhdModel> queryWjhd(@Param("wjid")String wjid,@Param("djrid")String djrid);
	
	public List<WjhdModel> queryWjSthd(@Param("wjid")String wjid,@Param("stid")String stid,@Param("djrid")String djrid);
}
