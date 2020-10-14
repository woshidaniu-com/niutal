package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.YhglModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户管理
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月16日上午11:44:44
 */
public interface IYhglService extends BaseService<YhglModel> {

	
	
	/**
	 * 
	 * <p>方法说明：增加用户并分配角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午11:52:46<p>
	 * @param model
	 * @param jsdms
	 * @return boolean
	 */
	public boolean insert(YhglModel model ,String[] jsdms);
	
	
	
	/**
	 * 
	 * <p>方法说明：修改用户信息及分配的角色信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日上午11:53:31<p>
	 * @param model
	 * @param jsdms
	 * @return boolean
	 */
	public boolean update(YhglModel model ,String[] jsdms);
	
	
	
	
	
	/**
	 * 
	 * <p>方法说明：删除用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:45:20<p>
	 * @param zgh 职工号
	 * @return boolean
	 */
	public boolean scYhxx(String[] zgh) ;
	
	
	
	/**
	 * 
	 * <p>方法说明：批量修改用户密码<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:45:54<p>
	 * @param zghArr 职工号
	 * @param password 密码 
	 * @return boolean
	 */
	public boolean updateYhmm(String[] zghArr , String password);
	
	/**
	 * 批量修改用户密码
	 * @param zghArr 职工号列表
	 * @param password 与职工号对应的密码列表
	 * @return boolean
	 */
	public boolean updateYhmmBatch(String[] zghArr,String passwords[]);

	/**
	 * 
	 * <p>方法说明：保存用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月12日下午5:16:08<p>
	 * @param ids 职工号
	 * @param sjfw json格式数据范围
	 * @return boolean
	 */
	public boolean insertYhsjfw(String ids , String sjfw);
	
	
	
	/**
	 * 
	 * <p>方法说明：查询用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日下午1:57:27<p>
	 * @param zgh 职工号
	 * @return list
	 */
	public List<Map<String,String>> getSjfwByYh(String zgh);
	
	/**
	 * 
	 * @description	： 获得用户数据范围数据
	 * @param zgh 职工号
	 * @param sjdm 数据代码
	 * @return
	 */
	public List<String> getYhsjfwList(String zgh,String sjdm);


	/**
	 * @description	： 更新提交用户数据范围设置
	 * @param ids
	 * @param sjdm
	 * @param pinyin
	 * @param key
	 * @param check 
	 * @return
	 */
	public boolean updateCheckYhsjfw(String ids, String sjdm,String value, boolean check);
	
}
