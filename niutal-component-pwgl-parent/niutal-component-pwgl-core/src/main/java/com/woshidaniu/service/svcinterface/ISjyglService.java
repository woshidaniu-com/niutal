package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * @className: ISjyglService
 * @description: 数据源管理接口
 * @author : Hanyc
 * @date: 2018-12-03 09:29:35
 * @version V1.0
 */
public interface ISjyglService extends BaseService<PWModel>{

	/**
	 * @description: 获取未选中的字段
	 * @author : Hanyc
	 * @date : 2018-12-03 10:16:54
	 * @param model
	 * @return
	 */
	List<PWModel> getRestColsList(PWModel model);

	/**
	 * @description: 获取数据源列表
	 * @author : Hanyc
	 * @date : 2018-12-03 13:50:16
	 * @return
	 */
	List<SjbzModel> getSjyList();

	/**
	 * @description: 根据模块代码获取输出的字段信息
	 * @author : Hanyc
	 * @date : 2018-12-05 17:18:48
	 * @return
	 */
	PWModel getModelByMkdm(PWModel model);
}