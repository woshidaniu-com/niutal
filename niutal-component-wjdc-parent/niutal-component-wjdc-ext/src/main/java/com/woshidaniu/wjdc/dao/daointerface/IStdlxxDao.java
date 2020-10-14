/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.wjdc.dao.entites.StdlxxModel;

@Repository("stdlxxDao")
public interface IStdlxxDao {

	/**
	 * @description	： 查询试题大类信息
	 * @param wjid
	 * @return
	 */
	public List<StdlxxModel> getStldxx(String wjid);
	
	/**
	 * @description	： 插入试题大类信息
	 * 
	 * 参数是List<Map<String,String>>
	 * 每个map必须具有key
	 * 
	 * wjid
	 * stdlid
	 * stdlmc
	 * xssx
	 * dqfs
	 * 
	 * @param list 试题大类信息
	 * @return int
	 */
	public int insertStdlxx(List<?> list);
}
