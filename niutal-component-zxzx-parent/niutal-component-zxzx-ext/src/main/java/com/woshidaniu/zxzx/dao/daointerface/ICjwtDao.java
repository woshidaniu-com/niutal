/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;

/**
 * @类名 ICjwtDao.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午1:57:29
 * @功能描述 在线咨询-常见问题
 * 
 */
@Repository("zxzxCjwtDao")
public interface ICjwtDao extends BaseDao<CjwtModel> {

	/**
	 * 根据咨询ID删除
	 * @param zxidList
	 * @return
	 */
	public int batchDeleteByZxid(List<String> zxidList);
	
	/**
	 * web端调用
	 * @param model
	 * @return
	 */
	public List<CjwtModel> getPagedListWeb(CjwtModel model);
	
	/**
	 * web端调用 不分页
	 * @param model
	 * @return
	 */
	public List<CjwtModel> getListWeb(CjwtModel model);
}
