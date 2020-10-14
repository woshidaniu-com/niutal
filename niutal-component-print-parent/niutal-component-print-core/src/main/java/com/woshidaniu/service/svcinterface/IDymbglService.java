package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.DymbglModel;
import com.woshidaniu.dao.entities.DymblxModel;
import com.woshidaniu.dao.entities.DysjxModel;
import com.woshidaniu.dao.entities.DysjyModel;

/**
 * 打印模板管理Service
 * @author guoqb[1127]
 * @date 2017-2-27 16:28:43
 */
public interface IDymbglService extends BaseService<DymbglModel>{

	//打印模板启用状态：启用
	static String DYMB_QYZT_QY = "1";
	//打印模板启用状态：停用
	static String DYMB_QYZT_TY = "0";
	
	static String CHANGE_DYMBLX_OP_TYPE_ZJ = "zj";
	static String CHANGE_DYMBLX_OP_TYPE_XG = "xg";

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
	 * @date 2017-3-17 15:14:27
	 * @param dm
	 * @return List
	 */
	List<DysjxModel> getDysjxListByDymblxdm(String dm);

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
	 * 删除打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 16:29:35
	 * @param ids
	 * @return boolean
	 */
	boolean scDymbgl(String ids);

	/**
	 * 根据打印模板类型获取打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-17 17:22:14
	 * @param mblxdm
	 * @return boolean
	 */
	boolean getDymbByDymblx(DymbglModel model);
	
	/**
	 * 根据打印模板类型获取打印模板个数
	 * @author zhidong[1571]
	 * @date 2020-7-29
	 * @param mblxdm
	 * @return int
	 */
	int getDymbByDymblxCount(DymbglModel model);

	/**
	 * 新增打印模板
	 * @author guoqb[1127]
	 * @date 2017-3-20 16:02:49
	 * @param model
	 * @return Map
	 */
	Map<String, Object> insertDymb(DymbglModel model);

	/**
	 * 修改打印模板名称
	 * @author guoqb[1127]
	 * @date 2017-3-20 16:47:26
	 * @param model
	 * @return Map
	 */
	Map<String, Object> updateDymbMc(DymbglModel model);

	/**
	 * 修改打印模板类型
	 * @author guoqb[1127]
	 * @date 2017-3-20 17:52:53
	 * @param model
	 * @return Map
	 */
	Map<String, Object> changeDymblx(String op,DymbglModel model);

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
	 * @description: 上传背景图
	 * @author : Hanyc
	 * @date : 2019-06-13 11:59:37
	 * @param req
	 * @return
	 */
	boolean updateBg(MultipartHttpServletRequest req);

	/**
	 * @description: 获取背景
	 * @author : Hanyc
	 * @date : 2019-06-13 13:35:16
	 * @param id
	 * @return
	 */
	Object getBg(String id);
}