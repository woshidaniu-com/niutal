package com.woshidaniu.daointerface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.entities.PairModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.woshidaniu.common.dao.BaseDao;
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
 * 类描述：通用公共Dao
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-6-26
 * @version 
 */
@Component("commonSqlDao")
public interface ICommonSqlDao extends BaseDao<Object> {

	/***
	 * 
	 *@描述		：查询学校信息
	 *@创建人	: kangzhidong
	 *@创建时间	: 2016年4月20日下午5:34:29
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public Map<String, String> getXxxx();
	
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
	public String getInnerParam(@Param("zdm")String zdm);
	
	
	
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
	public List<Map<String, String>> getInnerParams(CommonModel model);
	
	
	
	/**
	 * 
	 *@描述：查询所有角色；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:30:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JsglModel> queryAllJsByScope();
	
	/**
	 * 
	 *@描述：分页查询角色信息；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午08:53:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<JsglModel> getPagedJsxxListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有校区；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-11-10下午03:05:31
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<XqdmModel> queryAllXqByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询校区信息；且进行数据范围控制
	 *@创建人:zzh
	 *@创建时间:2016-4-6上午08:53:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<XqdmModel> getPagedXqxxListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有星期
	 *@创建人:James
	 *@创建时间:2014-12-26下午03:00:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<JcsjModel> queryXqj();
	
	/**
	 * 
	 *@描述：查询所有年级；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:30:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param njnum
	 *@return
	 */
	public List<NjdmModel> queryAllNjByScope();
	
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
	public NjdmModel getNjdmModel(@Param("njdm_id")String njdm_id);
	
	/**
	 * 
	 *@描述：查询所有年级；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:30:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param njnum
	 *@return
	 */
	public List<NjdmModel> queryNjListByScope(CommonModel model);	
	
	/**
	 * 
	 *@描述：分页查询年级；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-24上午08:53:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<NjdmModel> getPagedNjListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有机构；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:31:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jgpxzd 机构排序方式：1:按机构代码排序；2:按机构名称排序
	 *@return
	 */
	public List<BmdmModel> queryAllJgxxByScope(@Param("jgpxzd")String jgpxzd);

	/**
	 * 
	 *@描述：获取所有机构信息，不进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午01:51:55
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jgpxzd 机构排序方式：1:按机构代码排序；2:按机构名称排序
	 *@return
	 */
	public List<BmdmModel> getAllJgxxList(@Param("jgpxzd")String jgpxzd);
	
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
	public BmdmModel getJgxxModel(@Param("jg_id")String jg_id);
	
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
	public List<BmdmModel> getPagedJgxxListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的机构信息集合for Autocomplete组件；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedJgxxPairListByScope(CommonModel model);
	
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
	public List<BmdmModel> queryAllKkbmByScope(CommonModel model);

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
	public BmdmModel getKkbmModel(@Param("kkbm_id")String kkbm_id);
	
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
	public List<BmdmModel> getPagedKkbmListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的开课部门集合for Autocomplete组件；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-11-3下午03:46:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedKkbmPairListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有教师部门信息；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-12下午03:29:41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BmdmModel> queryAllJsbmByScope(CommonModel model);
	
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
	public List<BmdmModel> getPagedJsbmListByScope(CommonModel model);
	
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
	public List<PairModel> getPagedJsbmPairListByScope(CommonModel model);
	
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
	public List<BmdmModel> queryXyxxListByScope(CommonModel model);
	
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
	public BmdmModel getXyxxModel(@Param("xy_id")String xy_id);
	
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
	public List<BmdmModel> getPagedXyxxListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：分页查询符合条件的学院信息集合for Autocomplete组件
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-30下午01:56:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<PairModel> getPagedXyxxPairListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询符合条件的系信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午02:08:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<BmdmModel> queryXxxListByScope(CommonModel model);
	
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
	public BmdmModel getXxxModel(@Param("xxx_id")String xxx_id);
	
	/**
	 * 
	 *@描述：分页查询符合条件的系信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-26下午02:08:40
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BmdmModel> getPagedXxxListByScope(CommonModel model);
	
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
	public List<PairModel> getPagedXxxPairListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有专业；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:41:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<ZydmModel> queryAllZyByScope();
	
	/**
	 * 
	 *@描述：按所属部门查询专业信息列表；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:41:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<ZydmModel> queryZyxxListByScope(CommonModel model) ;
	
	/**
	 * 
	 *@描述：根据专业信息ID查询专业方向信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午04:07:10
	 *@param zyh_id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public ZydmModel getZyxxModel(@Param("zyh_id")String zyh_id);
	
	/**
	 * 
	 *@描述：按所属部门分页查询符合条件的专业信息集合；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<ZydmModel> getPagedZyxxListByScope(CommonModel model);
	
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
	public List<PairModel> getPagedZyxxPairListByScope(CommonModel model);
	
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
	public List<ZyfxdmModel> queryZyfxListByScope(CommonModel model);
	
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
	public ZyfxdmModel getZyfxModel(@Param("zyfx_id")String zyfx_id);
	
	/**
	 * 
	 *@描述：按所属专业查询专业方向信息列表；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-22上午10:19:44
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<ZyfxdmModel> getPagedZyfxListByScope(CommonModel model);
	
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
	public List<PairModel> getPagedZyfxPairListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述：查询所有班级；且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:48:59
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<BjdmModel> queryAllBjByScope(CommonModel model) ;
	
	/**
	 * 
	 *@描述：按所属部门、专业查询班级信息集合;且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-19上午09:52:04
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public List<BjdmModel> queryBjxxByScope(CommonModel model);
	
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
	public BjdmModel getBjxxModel(@Param("bh_id")String bh_id);

	/**
	 * 
	 *@描述：按所属部门、专业分页查询符合条件的班级信息集合;且进行数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-12下午02:29:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<BjdmModel> getPagedBjxxListByScope(CommonModel model);
	
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
	public List<PairModel> getPagedBjxxPairListByScope(CommonModel model);
	
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
	public List<JsglModel> getJsListByScope(CommonModel model);
	
	/**
	 * 
	 *@描述		：查询角色信息
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 23, 20161:56:39 PM
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getJsMapList();
	
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
	public List<BaseMap> getJcsjList(@Param(value="localeKey")String localeKey,@Param(value="lxdm")String lxdm);
 
	
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
	public List<PairModel> getGnmkdmPairList(@Param(value="localeKey")String localeKey);
	
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
	public List<PairModel> getCzdmPairList(@Param(value="localeKey")String localeKey);

	public List<NjdmModel> queryAllNjByScope2(String xhId);
    /**
     * 
     *@描述：获取系统设置当前年度
     *@创建人:huyy
     *@创建时间:2015-5-27下午01:40:57
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public String getDqnd();
    /**
     * 
     *@描述：获取课程性质
     *@创建人:huyy
     *@创建时间:2015-6-3上午09:47:14
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public List<JcsjModel> queryKcxzList();

	/**
	 * 
	 *@描述：根据传入条件，根据拼装SQL返回结果List，
	 *@备注: 该方法SQL拼装建议在service层完成,系统中没有提供service层的公共实现方法。
	 *@创建人:majun
	 *@创建时间:2014-7-29上午08:55:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param tableName 表字段
	 *@param tabCol    查询列
	 *@param tabColV   查询条件
	 *@说明: 拼装SQL为：select ${tabCol} from ${tableName} where ${tabColV}
	 *该方法实现好不好有待考证
	 *@return
	 */
	@Deprecated
	public List<Map<String,String>> queryResultList(@Param(value="tableName")String tableName,
													@Param(value="tabCol")String tabCol,
													@Param(value="tabColV")String tabColV);
	@Deprecated
	public List<String> queryStrList(@Param(value="tableName")String tableName,
									 @Param(value="tabCol")String tabCol,
									 @Param(value="tabColV")String tabColV);
	@Deprecated
	public List<Map<String,Object>> queryResultObjList(@Param(value="tableName")String tableName,
													   @Param(value="tabCol")String tabCol,
													   @Param(value="tabColV")String tabColV);
	@Deprecated
	public Map<String,Object> querySingleResult(@Param(value="tableName")String tableName,
												@Param(value="tabCol")String tabCol,
												@Param(value="tabColV")String tabColV);
	
	
	
	/**
     * 
     *@描述：获取学校成绩密钥(以前有的，代码迁移丢了，现在补上)
     *@创建人:jiangyy
     *@创建时间:2017-11-30下午01:40:57
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
	public String getXxcjmy();
	
	
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
