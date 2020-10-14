/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zxzx.dao.entities.ZxwtEventModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;

/**
 * @类名 IZxwtDao.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午7:18:29
 * @功能描述 在线咨询-咨询问题
 * 
 */
@Repository("zxzxZxwtDao")
public interface IZxwtDao extends BaseDao<ZxwtModel> {

	/**
	 * 用于导出
	 * @param model
	 * @return
	 */
	public List<Map<String,String>> getMapList(ZxwtModel model);
	
	/**
	 * 用于批量回复数据导出
	 * @param bkdm
	 * @param zgh
	 */
	public List<ZxwtModel> getBatchDataList(@Param("bkdms")String[] bkdms);
	
	/**
	 * 用于web端查询
	 * @param model
	 * @return
	 */ 
	
	public List<ZxwtModel> getPagedListWeb(ZxwtModel model);
	
	/**
	 * 用于web端我的咨询查询
	 * @param model
	 * @return
	 */ 
	public List<ZxwtModel> getPagedListMytopicWeb(ZxwtModel model);
	
	
	/**
	 * 获取热门咨询(点击量)
	 * @param num
	 * @return
	 */
	public List<ZxwtModel> getTopListWeb(@Param("bkdm")String bkdm, @Param("num")int num);
	
	/**
	 * 获取热门咨询(活跃度)
	 * @param num
	 * @return
	 */
	public List<ZxwtModel> getTopListWebAsActive(@Param("bkdm")String bkdm, @Param("num")int num, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public int updateDjl(ZxwtModel model);
	
	/**
	 *  插入事件表数据
	 * @param model
	 * @return
	 */
	public int insertEvent(ZxwtEventModel model);
	
	/**
	 * 用户删除数据web
	 * @param list
	 * @return
	 */
	public int batchDeleteWeb(@Param("list")List list,@Param("zxr")String yhm);
	
	/**
	 * 用户更新数据
	 */
	public int updateWeb(ZxwtModel model);
}
