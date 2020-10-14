package com.woshidaniu.jcsj.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.jcsj.dao.entities.ZydmModel;

/**
 * 专业代码维护
 * @author Administrator
 *
 */
@Repository(value="zfxgZydmDao")
public interface IZydmDao extends BaseDao<ZydmModel> {

	/**
	 * 专业代码列表（按学院）
	 * @param xydm
	 * @return
	 */
	public List<ZydmModel> cxZydmList(String xydm);
	
	/**
	 * 验证专业代码是否存在
	 * @param t
	 * @return
	 */
	public ZydmModel valideZydm(ZydmModel t);
	
	
	/**
	 * 查询专业代码
	 * @return
	 */
	public List<ZydmModel> cxZydm();
	
}
