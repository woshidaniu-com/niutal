/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;

/**
 * @类名 IkzdkDao.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 下午4:43:14
 * @功能描述 在线咨询-板块信息
 * 
 */
@Repository("zxkzdkxxDao")
public interface IkzdkDao extends BaseDao<kzdkModel> {
	
	/**
	 * 查询是否存在于常见问题表中和问题咨询表中
	 * @param model
	 * @return
	 */
	Integer checkCanDelete(@Param("bkdm")String bkdm);
	
	/**
	 * 
	 * @return
	 */
	Integer getMaxXsxs();
	
	/**
	 * @description	： 查询所有板块列表
	 * @return
	 */
	List<kzdkModel> getModelListWeb(kzdkModel model);

	/**
	 * @description	： 查询咨询板块
	 * @return
	 */
	List<kzdkModel> getkzdkModelListWeb();
}
