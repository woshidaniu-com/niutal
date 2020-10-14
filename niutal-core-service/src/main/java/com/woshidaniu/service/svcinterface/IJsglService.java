package com.woshidaniu.service.svcinterface;


import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.AncdModel;
import com.woshidaniu.dao.entities.EjqxModel;
import com.woshidaniu.dao.entities.GnqxModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.Menu;
import com.woshidaniu.dao.entities.YhglModel;





/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：角色管理Service
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月15日下午3:49:37
 */
public interface IJsglService extends BaseService<JsglModel>{

	
	/**
	 * 
	 * <p>方法说明：查询角色功能权限<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午3:58:08<p>
	 * @param zgh 职工号
	 * @param jsdm 角色代码
	 * @return 菜单列表
	 */
	public List<Menu> getGnqxByJsdm(String zgh ,String jsdm);
	
	
	
	/**
	 * 
	 * <p>方法说明：查询所有的功能权限<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月24日上午10:15:44<p>
	 * @return 菜单列表
	 */
	public List<Menu> getAllGnqxList();
	
	
	/**
	 * 
	 * <p>方法说明：查询角色功能按钮列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月15日下午5:52:43<p>
	 * @param jsdm 角色代码
	 * @param zgh 职工号
	 * @param gnmkdm 功能模块代码
	 * @return 功能菜单按钮
	 */
	public List<AncdModel> getButtonList(String jsdm,String zgh ,String gnmkdm);
	
	
	
	/**
	 * 
	 * <p>方法说明：查询角色按钮列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月24日下午4:42:23<p>
	 * @param jsdm 角色代码
	 * @return 功能菜单按钮
	 */
	public List<AncdModel> getButtonList(String jsdm);
	
	
	/**
	 * 
	 * <p>方法说明：根据职工号查询用户角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午8:48:14<p>
	 * @param zgh 职工号
	 * @return 角色列表
	 */
	public List<JsglModel> getJsxxListByZgh(String zgh);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：根据角色代码删除角色相关信息（系统内置角色不受影响）<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午9:22:30<p>
	 * @param jsdm 角色代码
	 * @return boolean 结果
	 */
	public boolean deleteJsxxById(String jsdm);
	
	
	
	
	
	/**
	 * 
	 * <p>方法说明：角色未分配用户列表<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午4:42:13<p>
	 * @param model
	 * @return 用户列表
	 */
    public List<YhglModel> getPagedWfpyhList(JsglModel model);
    
    
    
    
    /**
     * 
     * <p>方法说明：角色已分配用户列表<p>
     * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
     * <p>时间：2017年3月16日下午4:42:33<p>
     * @param model
     * @return 用户列表
     */
    public List<YhglModel> getPagedYfpyhList(JsglModel model);
	
	
	
	
    /**
     * 
     * <p>方法说明：保存用户分配<p>
     * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
     * <p>时间：2017年3月17日上午10:05:28<p>
     * @param jsdm 角色代码
     * @param zghs 职工号数组
     * @return boolean
     */
	public  boolean insertYhfp(String jsdm , String[] zghs);
	
	

    
	/**
	 * 
	 * <p>方法说明：取消用户分配<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月17日上午10:28:59<p>
	 * @param jsdm 角色代码
     * @param zgh 职工号数组
     * @return boolean
	 */
	public  boolean deleteYhfp(String jsdm , String[] zghs);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：功能授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月24日下午3:26:22<p>
	 * @param jsdm 角色代码 
	 * @param gnczids 功能操作ID
	 * @return boolean
	 */
	public boolean insertGnqx(String jsdm, String[] gnczids);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：二级授权<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日下午1:43:20<p>
	 * @param model 二级权限信息
	 * @return boolean
	 */
	public boolean insertEjsq(EjqxModel model);
	
	
	/**
	 * 
	 * <p>方法说明：二级授权用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日上午9:55:52<p>
	 * @param model 角色代码
	 * @return list
	 */
	public List<YhglModel> getPagedEjsqList(EjqxModel model);
	
	
	
	/**
	 * 
	 * <p>方法说明：二级授权详细<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月27日下午3:59:09<p>
	 * @param ejqxModel  二级授权信息
	 * @return List
	 */
	public List<AncdModel> getEjqxList(EjqxModel ejqxModel);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：查询角色数据权限<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月28日下午3:22:58<p>
	 * @param jsdm 角色代码
	 * @return list
	 */
	public List<String> getSjqxByJsdm(String jsdm);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：保存数据权限<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月28日下午3:17:13<p>
	 * @param jsdm 角色代码
	 * @param gzids 资源IDs
	 * @return int
	 */
	public boolean insertSjqx(String jsdm , String[] gzids);

	
	
	
	/**
	 * 
	 * <p>方法说明：查询拥有功能权限的角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日上午10:18:58<p>
	 * @param model 功能权限对象
	 * @return 角色列表
	 */
	List<JsglModel> getGnqxRole(GnqxModel model);
	
	
	/**
	 * 
	 * <p>方法说明：查询拥有功能权限的用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日上午10:22:02<p>
	 * @param model 功能权限对象
	 * @return 用户列表
	 */
	List<YhglModel> getGnqxUser(GnqxModel model);
	
	
	
	/**
	 * 
	 * <p>方法说明：查询二级权限用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]</a><p>
	 * <p>时间：2017年5月31日下午3:04:59<p>
	 * @param model
	 * @return
	 */
	List<YhglModel> getPagedEjqxUser(GnqxModel model);
	
}
