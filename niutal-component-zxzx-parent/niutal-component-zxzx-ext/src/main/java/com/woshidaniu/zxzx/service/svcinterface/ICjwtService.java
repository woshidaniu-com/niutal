/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;

/**
 * @类名 ICjwtService.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午1:59:07
 * @功能描述 在线咨询-常见问题
 * 
 */
public interface ICjwtService extends BaseService<CjwtModel> {

	/**
	 * 根据咨询ID删除
	 * @param zxidList
	 * @return
	 */
	public boolean batchDeleteByZxid(List<String> zxidList);
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
