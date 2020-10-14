package com.woshidaniu.service.common;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.global.GlobalSysValues;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.BjdmModel;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.entities.JcsjModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.NjdmModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XqdmModel;
import com.woshidaniu.entities.ZydmModel;
import com.woshidaniu.entities.ZyfxdmModel;

/**
* 
* 类名称：ICommonSqlService 
* 类描述： 公共服务类
* 创建人：caozf
* 创建时间：2012-6-26 上午08:41:27 
* @version 
*
*/
public interface ICommonSqlService {
	
	
	public String getConstant(String key);
	
	/***
	 * 
	 *@描述		：查询学校信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午5:34:29
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public Map<String, String> getXxxx();
	
	/**
	 * 
	 *@描述：获得学校成绩秘钥
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-8下午07:55:38
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String getXxcjmy();
	
	/***
	 * 
	 *@描述：根据GlobalXtszx获取系统设置表中设置项值。
	 *@注意：设置项值通过枚举获取，如果以后增加系统设置项同时需要增加枚举中变量
	 *@创建人:majun
	 *@创建时间:2015-3-10下午04:01:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param szx
	 *@return
	 */
	public String cxXtszxz(String xtszx);
	
	
	/***
	 * 
	 *@描述：根据GlobalXtszx获取系统设置表中设置项值。
	 *@注意：设置项值通过枚举获取，如果以后增加系统设置项同时需要增加枚举中变量
	 *@创建人:jiangyy
	 *@创建时间:2017-12-28下午04:01:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param szx
	 *@return
	 */
	public String cxCacheXtszxz(String xtszx);
	
	/**
	 * 
	 *@描述：查询系统内置项值,(从jw_jcdml_xtnzb表中取值)
	 *@创建人:majun
	 *@创建时间:2014-7-18上午08:40:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String getInnerParam(GlobalSysValues zdm);
	
	
	/**
	 * 
	 *@描述：批量查询系统内置表
	 *@创建人:jiangyy
	 *@创建时间:2018-6-20上午09:30:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<Map<String, String>> getInnerParams(List<String> zdmList);
	
	/**
	 * 
	 *@描述：查询所有角色
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:55:30
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JsglModel> queryAllJs();
	
	/**
	 * 
	 *@描述：分页查询符合条件的角色信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午08:51:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<JsglModel> getPagedJsxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有校区
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:57:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<XqdmModel> queryAllXq();
	
	
	/**
	 * 
	 *@描述：查询所有星期
	 *@创建人:James
	 *@创建时间:2014-12-30上午09:57:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JcsjModel> queryXqj();
	
	/**
	 * 
	 *@描述：分页查询符合条件的校区信息集合
	 *@创建人:zzh
	 *@创建时间:2016-4-6上午08:51:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<XqdmModel> getPagedXqxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有年级
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:57:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param njnum
	 *@return
	 *@throws Exception
	 */
	public List<NjdmModel> queryAllNj();
	
	/**
	 * 
	 *@描述：查询所有年级
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:57:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param njnum
	 *@return
	 *@throws Exception
	 */
	public List<NjdmModel> queryNjList(Map<String,Object> map);
	
	/**
	 * 
	 *@描述：根据年级代码ID查询年级信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param njdm_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public NjdmModel getNjdmModel(String njdm_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的年级集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午08:51:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<NjdmModel> getPagedNjList(CommonModel model);
	

	/**
	 * 
	 *@描述：查询所有机构；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:31:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BmdmModel> queryAllJgxx();
	
	/**
	 * 
	 *@描述：分页查询符合条件的机构信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedJgxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据机构ID查询机构信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param jg_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public BmdmModel getJgxxModel(String jg_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的机构信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedJgxxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有开课部门；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:19:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BmdmModel> queryAllKkbm();
	
	
	/**
	 * 
	 *@描述：查询所有开课部门；且不进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:19:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BmdmModel> queryKkbmNoScope();
	
	

	/**
	 * 
	 *@描述：分页查询符合条件的开课部门集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:19:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedKkbmList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据开课部门ID查询开课部门信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param jg_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public BmdmModel getKkbmModel(String kkbm_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的开课部门集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-11-3下午03:46:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedKkbmPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有的教师部门信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:48:23
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> queryAllJsbm();
	
	/**
	 * 
	 *@描述：分页查询符合条件的教师部门信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:48:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedJsbmList(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的教师部门信息集合for Autocomplete组件；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午02:47:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedJsbmPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有学生学院
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:57:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public List<BmdmModel> queryAllXy();
	
	/**
	 * 
	 *@描述：查询所有学生学院(没有数据范围)
	 *@创建人:jiangyy
	 *@创建时间:2018-1-29 上午09:57:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public List<BmdmModel> queryXyNoScope();
	
	/**
	 * 
	 *@描述：查询符合条件的学生学院信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:42:42
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<BmdmModel> queryXyxxList(CommonModel model);

	/**
	 * 
	 *@描述：根据学院ID查询学院信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param xxx_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public BmdmModel getXyxxModel(String xy_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的学生学院信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedXyxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的学生学院信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedXyxxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据学院ID查询该院下的所有系信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12上午09:42:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@ 
	 */
	public List<BmdmModel> queryXxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据系ID查询系信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param xxx_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public BmdmModel getXxxModel(String xxx_id);
	
	/**
	 * 
	 *@描述：根据条件；分页查询系信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午02:03:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedXxxList(CommonModel model);
	
	
	/**
	 * 
	 *@描述：分页查询符合条件的系信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedXxxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有专业
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:59:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public List<ZydmModel> queryAllZy();
	
	/**
	 * 
	 *@描述：按所属部门查询专业信息列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:59:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 *@throws Exception
	 */
	public List<ZydmModel> queryZyxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据专业信息ID查询专业方向信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param zyxx_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public ZydmModel getZyxxModel(String zyh_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的专业信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<ZydmModel> getPagedZyxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的专业信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedZyxxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：按所属专业查询专业方向信息列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午03:20:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<ZyfxdmModel> queryZyfxList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据专业方向ID查询专业方向信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param zyfx_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public ZyfxdmModel getZyfxModel(String zyfx_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的专业方向信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-22上午10:18:37
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<ZyfxdmModel> getPagedZyfxList(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的专业方向集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedZyfxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有班级
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午10:00:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public List<BjdmModel> queryAllBj();
	

	/**
	 * 
	 *@描述：按所属部门、专业查询班级信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午10:00:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 *@throws Exception
	 */
	public List<BjdmModel> queryBjxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：根据班号ID查询班级信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param zyfx_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public BjdmModel getBjxxModel(String bh_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的班级信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BjdmModel> getPagedBjxxList(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的班级信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedBjxxPairList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询角色List，该方法适用页面角色下拉框使用。
	 *@创建人:majun
	 *@创建时间:2014-6-18下午02:39:56
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JsglModel> getJsList(Map<String,Object> map);
	
	/**
	 * 
	 *@描述		：角色管理
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 23, 20161:53:55 PM
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<JsglModel> getJsList();
	
	/**
	 * 
	 *@描述		：查询角色信息
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 23, 20161:55:14 PM
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getJsMapList();
	
	/**
	 * 
	 *@描述：根据类型代码获得类型数据；以Map形式返回
	 *		1：仅仅获得 dm,mc 两个字段
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12上午09:18:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param lxdm
	 *@return
	 */
	public List<JcsjModel> queryJcsjList(String lxdm);
	/**
	 * 
	 *@描述：查询大学期List,该方法使用要特别注意：针对一般学校该方法基本满足使用。
	 *@备注：在什么情况下，使用大学期或小学期可以咨询钟磊。
	 *@创建人:majun
	 *@创建时间:2014-7-18上午09:33:49
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JcsjModel> getXqList();
	/**
	 * 
	 *@描述：查询小学期List,获取
	 *@备注：在什么情况下，使用大学期或小学期可以咨询钟磊。
	 *@创建人:majun
	 *@创建时间:2014-7-18上午09:53:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param syxq 
	 *@return
	 */
	public List<JcsjModel> getXxqList();
	/***
	 * 
	 *@描述：查询教学场地信息
	 *@创建人:majun
	 *@创建时间:2014-7-30下午03:05:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<Map<String,String>> getPagedJxcdListByScope(CommonModel model);
	/**
	 * 
	 *@描述：查询专业年级
	 *@创建人:majun
	 *@创建时间:2014-7-30下午06:48:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<Map<String,String>> getPagedZynjListByScope(CommonModel model);
	
	/**
	 *@描述：查询用户
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-12下午07:45:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<Map<String, String>> getPagedYhList(CommonModel model);
	
	/**
	 * 
	 *@描述：查询符合条件的学年代码集合
	 *@创建人:m
	 *@创建时间:2014-10-22 14.6
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param  
	 *@return
	 */
	public List<BaseMap> getXnMapList();
	
	/**
	 * 
	 *@描述：查询基础数据表List
	 *@创建人:m
	 *@创建时间:2014-10-21  11.55
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param lxdm 类型代码
	 *@return
	 */
	public List<BaseMap> getJcsjList(String lxdm);
	
	/**
	 * 
	 *@描述：获得【功能模块代码表】中所有进行不重复处理后的功能模块代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午03:56:40
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PairModel> getGnmkdmPairList();
	
	/**
	 * 
	 *@描述：获得【操作代码表】中所有进行不重复处理后的操作代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午03:56:21
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PairModel> getCzdmPairList();

	/**
	 * 
	 *@描述：查询所有机构信息 ，没有数据范围
	 *@创建人:gc
	 *@创建时间:2015-5-14上午11:06:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BmdmModel> getAllJgxxList();
    /**
     * 
     *@描述：查询授予学位信息
     *@创建人:huyy
     *@创建时间:2015-5-15上午10:41:54
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public List<JcsjModel> queryXwxxList();
    /**
     * 
     *@描述：考试方式查询
     *@创建人:huyy
     *@创建时间:2015-5-15下午02:43:25
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public List<JcsjModel> queryKsfsList();

	public List<NjdmModel> queryAllNj2(String xhId);
    /**
     * 
     *@描述：获取系统设置当前年度
     *@创建人:huyy
     *@创建时间:2015-5-27下午01:38:57
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public String getDqnd();
	
	/**
	 * 
	 *@描述：得到系统内置参数值
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-23上午10:05:42
	 *@param zdm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String getInnerParam(String zdm);
	
	
	/**
	 * 
	 *@描述：得到系统内置参数值
	 *@创建人:jiangyy
	 *@创建时间:2017-12-28上午10:05:42
	 *@param zdm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String getCacheInnerParam(String zdm);
	
	
	/**
	 * 
	 *@描述：查询考试名称
	 *@创建人:jiangyy
	 *@创建时间:2018-5-17	下午03:16:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 *@throws Exception
	 */
	public List<CommonModel> queryKsmcList(CommonModel model);
	
}
