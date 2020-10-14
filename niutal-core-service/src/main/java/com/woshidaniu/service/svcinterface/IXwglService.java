package com.woshidaniu.service.svcinterface;


import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.XwglModel;


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
	public boolean scXw(String idStr) ;
	
	
	/**
	 * 发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgFbxw(String idStr) ;
	
	
	/**
	 * 取消发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxfbxw(String idStr) ;
	
	
	/**
	 * 置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgZdxw(String idStr) ;
	
	
	
	/**
	 * 取消置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxzdXw(String idStr) ;
	


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
}
