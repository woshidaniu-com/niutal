package com.woshidaniu.jcsj.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.jcsj.dao.entities.ZydmModel;

/**
 * 专业代码维护
 * @author Administrator
 *
 */
public interface IZydmService extends BaseService<ZydmModel> {

	/**
	 * 增加专业代码
	 * @param model
	 * @return
	 */
	public boolean zjZydm(ZydmModel model);

	
	/**
	 * 删除专业代码
	 * @param ids
	 * @return
	 */
	public String scZydm(String ids);

	
	/**
	 * 按学院查询专业代码
	 * @param xydm
	 * @return
	 */
	public List<ZydmModel> cxZydmList(String xydm);

	
	
	/**
	 * 验证专业代码是否存
	 * @param t
	 * @return
	 */
	public ZydmModel valideZydm(ZydmModel t);

	
	/**
	 * 查询全部专业代码 
	 * @return
	 */
	public List<ZydmModel> cxZydm();


}
