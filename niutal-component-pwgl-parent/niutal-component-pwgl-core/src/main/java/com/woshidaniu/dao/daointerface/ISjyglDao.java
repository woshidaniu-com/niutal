package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * @className: ISjyglDao
 * @description: 数据源管理DAO
 * @author : Hanyc
 * @date: 2018-12-03 09:28:34
 * @version V1.0
 */
@Repository("PWsjyglDao")
public interface ISjyglDao extends BaseDao<PWModel> {

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