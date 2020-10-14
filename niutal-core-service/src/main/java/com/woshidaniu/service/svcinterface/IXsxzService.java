package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.XsxzModel;

/**
 * 选择学生信息
 * @author Jiangdong.Yi
 *
 */
public interface IXsxzService extends BaseService<XsxzModel> {
	/**
	 * 查询学生信息列表
	 * @param String
	 * @return
	 */
	public List<XsxzModel> getModelList(String ids);
}
