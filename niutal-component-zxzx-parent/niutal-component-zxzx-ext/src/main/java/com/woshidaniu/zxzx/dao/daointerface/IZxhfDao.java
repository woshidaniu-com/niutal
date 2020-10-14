/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zxzx.dao.entities.ZxhfModel;

/**
 * @类名 IZxhfDao.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月25日 下午3:08:19
 * @功能描述 在线咨询-咨询回复
 * 
 */
@Repository("zxzxZxhfDao")
public interface IZxhfDao extends BaseDao<ZxhfModel>{

	/**
	 * 删除
	 * @param hfid
	 * @return
	 */
	public int delete(ZxhfModel model);

}
