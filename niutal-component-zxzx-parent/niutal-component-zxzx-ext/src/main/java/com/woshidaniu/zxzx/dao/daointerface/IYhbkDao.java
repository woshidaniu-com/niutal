/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.zxzx.dao.entities.YhbkModel;

/**
 * @类名 IYhbkDao.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月23日 下午4:36:31
 * @功能描述 在线咨询-用户板块
 * 
 */
@Repository("zxzxYhbkDao")
public interface IYhbkDao extends BaseDao<YhbkModel> {
	
	/**
	 * 获取用户版块代码列表
	 * @param zgh
	 * @return
	 */
	public List<String> getYhbkdmList(String zgh);
	
	/**
	 * 查询用户板块列表
	 * @param zgh
	 * @return
	 */
	public List<YhbkModel> getYhbkList(String zgh);
	
	/**
	 * 批量插入
	 * @param modelList
	 * @return
	 */
	public int batchInsert(List<YhbkModel> modelList);
	
	/**
	 * 查询已分配用户信息
	 * @return
	 */
	public List<YhglModel> getPagedListYfpYhxx(YhbkModel model);
	
	/**
	 * 查询未分配用户信息
	 * @return
	 */
	public List<YhglModel> getPagedListWfpYhxx(YhbkModel model);
}
