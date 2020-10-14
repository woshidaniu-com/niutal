package com.woshidaniu.service.svcinterface;


import java.util.List;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.XwglModel;


/**
 * 
* 
* 类名称：XwglService 
* 类描述： 新闻管理
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public interface IXwglService extends BaseService<XwglModel>{
	
	public static final String SFFB_WFB="0";//是否发布：未发布
	public static final String SFFB_YFB="1";//是否发布：已发布
	
	public static final String SFZD_WZD="0";//是否置顶：未置顶
	public static final String SFZD_YZD="1";//是否置顶：已置顶
	
	public static final String FBDX_TRECHER="tea";//发布对象：老师
	public static final String FBDX_STUDENT="stu";//发布对象：学生
/*	*//**
	 * 查询单条新闻
	 * @param bean
	 * @return
	 * @
	 *//*
	public XwglModel cxDtxw(XwglModel bean) ;
*/
	
	
	/**
	 * 修改单条新闻
	 * @param bean
	 * @return
	 * @
	 */
/*	public boolean xgDtxw(XwglModel bean) ;
*/
	
	/**
	 * 新增新闻
	 * @param bean
	 * @return
	 * @
	 */
	/*public boolean zjXw(XwglModel bean) ;*/

	
	/**
	 *删除新闻
	 * @param bean
	 * @return
	 * @
	 */
	public boolean scXw(XwglModel model) ;
	
	
	/**
	 * 发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgFbxw(XwglModel model) ;
	
	
	/**
	 * 取消发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxfbxw(XwglModel model) ;
	
	
	/**
	 * 置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgZdxw(XwglModel model) ;
	
	
	
	/**
	 * 取消置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxzdXw(XwglModel model) ;
	
	/**
	 * 
	 * @description: 记录查阅新闻（通知）信息
	 * @author : kangzhidong
	 * @date : 2014-3-25
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int insertNewsLog(XwglModel model);
	
	/**
	 * 
	 * @description: 查询个人可以接收的通知信息
	 * @author : kangzhidong
	 * @date : 2014-3-24
	 * @time : 下午02:04:31 
	 * @param model
	 * @return
	 */
	public List<XwglModel> getGrtzList(XwglModel model);
	
	/**
	 * 分页查询新闻
	 * @param model
	 * @return
	 * @
	 */
/*	public List<XwglModel> fycxXw(XwglModel model) ;*/
	
	
	/**
	 * 增加保存新闻
	 * @param model
	 * @return
	 */
	public boolean zjBcXw(XwglModel model);
	
	/**
	 * 修改保存新闻
	 * @param model
	 * @return
	 */
	public boolean xgBcXw(XwglModel model);
	/**
	 * 查询学生信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeXsxx(XwglModel model);

	/**
	 * 查询教师信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeJsxx(XwglModel model);

	/**
	 * 查询岗位信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeGwxx(XwglModel model);


	/**
	 *@描述：判断通知是否可以删除
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-22上午10:17:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param ids
	 *@return
	 */
	public boolean canDelete(String ids);
	
	/**
	 * 
	 *@描述		： 分页查询所有新闻
	 *@创建人		: kangzhidong
	 *@创建时间	: Jul 7, 20162:24:55 PM
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<XwglModel> cxXwList(XwglModel model);
	
}
