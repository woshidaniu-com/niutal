package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.XsxxModel;

public interface IXsxxService extends BaseService<XsxxModel> {

	public XsxxModel getStudent(String yhm,String mm);
	
	/**
	 * 同步学生信息
	 * @param model
	 * @return
	 */
	public boolean synXsxx(XsxxModel model);
	
	/**
	 * 初始化学生密码   新增学生密码初始化， 非修改
	 * @param model
	 * @return
	 */
	public boolean initXsmm(XsxxModel model) throws Exception;
	
	/**
	 * 删除学生信息
	 * @param xhs 学号集字符串，例：xh,xh,xh
	 * @return
	 */
	public boolean scXsxx(String xhs);
	
	/**
	 * 删除学生密码
	 * @param xhs 学号集字符串，例：xh,xh,xh
	 * @return
	 */
	public boolean scXsmm(String xhs);
}
