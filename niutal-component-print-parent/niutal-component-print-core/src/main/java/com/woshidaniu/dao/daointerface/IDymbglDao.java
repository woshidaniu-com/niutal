package com.woshidaniu.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.DymbglModel;
import com.woshidaniu.dao.entities.DymblxModel;
import com.woshidaniu.dao.entities.DysjxModel;
import com.woshidaniu.dao.entities.DysjyModel;

/**
 * 打印模板管理dao
 * @author guoqb[1127]
 * @date 2017-2-27 16:14:16
 */
@Repository("dymbglDao")
public interface IDymbglDao extends BaseDao<DymbglModel>{

	/**
	 * 查询所有数据源列表
	 * @author guoqb[1127]
	 * @date 2017年3月1日 16:43:43
	 * @return List
	 */
	List<DysjyModel> getDysjyList();

	/**
	 * 根据打印模板类型代码查询所有数据项列表
	 * @author guoqb[1127]
	 * @date 2017-3-15 11:33:20
	 * @param mblxdm
	 * @return List
	 */
	List<DysjxModel> getDysjxListByDymblxdm(String mblxdm);

	/**
	 * 模板类型列表
	 * @author guoqb[1127]
	 * @date 2017-3-17 14:40:41
	 * @return List
	 */
	List<DymblxModel> getDymblxList();

	/**
	 * 修改打印模板启用状态
	 * @author guoqb[1127]
	 * @date 2017-3-17 14:40:41
	 * @param model
	 * @return boolean
	 */
	boolean updateDymbQyzt(DymbglModel model);

	/**
	 * 根据打印模板类型获取打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 17:22:14
	 * @param model
	 * @return int
	 */
	int getDymbByDymblx(DymbglModel model);

	/**
	 * 根据打印模板名称获取打印模板数量【排除自己】
	 * @author guoqb[1127]
	 * @date 2017-3-20 17:07:45
	 * @param model
	 * @return int
	 */
	int getModelByMc(DymbglModel model);

	/**
	 * 根据打印模版类型与职工号查找打印模板内容
	 * @author guoqb[1127]
	 * @date 2017-3-22 17:21:41
	 * @param map
	 * @return DymbglModel
	 */
	DymbglModel getDymbByDymblxAndZgh(Map<String, String> map);

	/**
	 * 验证模板类型是否存在
	 * @author guoqb[1127]
	 * @date 2017-3-27 11:21:02
	 * @param mblxdm
	 * @return boolean
	 */
	boolean yzmblxSfcz(String mblxdm);

	/**
	 * 根据模板名称修改
	 * @author guoqb[1127]
	 * @date 2017-4-7 11:24:01
	 * @param model
	 * @return int
	 */
	int updateDymbMc(DymbglModel model);

	/**
	 * @description: 更新背景
	 * @author : Hanyc
	 * @date : 2019-06-13 12:01:58
	 * @param id
	 * @param bg
	 * @return
	 */
	int updateBg(@Param("id")String id, @Param("bg")byte[] bg);

	/**
	 * @description: 获取背景
	 * @author : Hanyc
	 * @date : 2019-06-13 13:35:16
	 * @param id
	 * @return
	 */
	Object getBg(@Param("id")String id);
}