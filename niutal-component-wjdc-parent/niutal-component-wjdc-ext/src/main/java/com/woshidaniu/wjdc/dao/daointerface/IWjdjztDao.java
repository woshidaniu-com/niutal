/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.wjdc.dao.entites.WjdjztModel;

@Repository("wjdjztDao")
public interface IWjdjztDao {

	List<Map<String,String>> getPagedDjztList(WjdjztModel model);
	
	int getDjztListCount(WjdjztModel model);
	
	List<Map<String,String>> getDjztList(WjdjztModel model);
	
	List<Map<String, String>> getStflMapList(@Param("wjid")String wjid);

	List<Map<String, String>> getStflfz(@Param("wjid")String wjid);

	int getWjCount(@Param("wjid")String wjid);

	List<Map<String, String>> getYffWjList();

}
