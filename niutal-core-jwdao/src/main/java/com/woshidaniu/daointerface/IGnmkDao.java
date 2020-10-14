package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.GnmkModel;


/**
 * 
 * 
 * 类名称：IGnmkDao
 * 类描述：功能模块dao
 * 创建人：huangxp
 * 创建时间：2012-6-28
 * 修改人：huangxp
 * 修改时间：2012-6-28
 * 修改备注： 
 * @version 
 *
 */
public interface IGnmkDao extends BaseDao<GnmkModel> {
    
    /***
     * 
     *@描述：查询功能的包含那些操作权限
     *@创建人:majun
     *@创建时间:2014-7-15上午11:27:00
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@param model
     *@return
     */
    public List<GnmkModel> cxUrlqx(GnmkModel model);
}
