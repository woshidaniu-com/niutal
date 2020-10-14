package com.woshidaniu.jcsj.service.svcinterface;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.jcsj.dao.entities.XsxxglModel;

public interface IXsxxglService extends BaseService<XsxxglModel> {

	/**
	 * 增加学生信息
	 * 
	 * @param model
	 * @return
	 */
	public boolean zjXsxx(XsxxglModel model);

	/**
	 * 修改学生信息
	 * 
	 * @param model
	 * @return
	 */
	public boolean xgXsxx(XsxxglModel model);

	/**
	 * 删除学生信息
	 * 
	 * @param xhs
	 *            学号集字符串，例：xh,xh,xh
	 * @return
	 */
	public boolean scXsxx(String xhs);

	public XsxxglModel getXszp(String xh);


	public String unZip(String tempFilePath)
			throws Exception;

	public List<HashMap<String, String>> savePhoto(String photoPath,
			String photoNameType);

	public File printZpdr(String photoNameType,
			List<HashMap<String, String>> zpscjgList)
			throws Exception;

	/**
	 * @类功能描述: 验证专业是否改变
	 * @作者： 陈敏杰【913】
	 * @时间： 2014-11-21下午04:25:21
	 * @版本： V1.0
	 * @修改记录: 类修改者-修改日期-修改说明
	 */
	public boolean chkZyischg(XsxxglModel model);

	/**
	 * @类功能描述: 收费删除学生处理
	 * @作者： 陈敏杰【913】
	 * @时间： 2014-11-25下午02:13:01
	 * @版本： V1.0
	 * @修改记录: 类修改者-修改日期-修改说明
	 */
	public int scXsxxForSf(String xhs);
}
