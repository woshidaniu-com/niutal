package com.woshidaniu.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.datarange.DataRange;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户管理DAO
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月16日下午1:41:03
 */
@Repository("yhglDao")
public interface IYhglDao extends BaseDao<YhglModel>{
	
	
	/**
	 * 
	 * <p>方法说明：分页查询<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月28日下午5:51:03<p>
	 * @param model
	 * @return list
	 */
	@DataRange(info = "{TEACHER_SZBM:'JGDM'}", dataIds = { "teacher" } )
	public List<JsglModel> getPagedList(JsglModel model);
	
	
	/**
	 * 
	 * <p>方法说明：增加用户角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午1:40:39<p>
	 * @param zgh 职工号
	 * @param jsdms 角色代码
	 * @return int
	 */
	public int zjYhjsxx(@Param(value = "zgh") String zgh,@Param(value = "jsdms") String[] jsdms);
	
	
	
	
	/**
	 * 
	 * <p>方法说明：删除用户<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午2:47:07<p>
	 * @param zgh 职工号
	 * @return int
	 */
	public int scYhxx(@Param(value = "zgh") String[] zgh);
	
	
	
	/**
	 * 
	 * <p>方法说明：删除用户角色<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月16日下午1:44:22<p>
	 * @param zgh 职工号
	 * @return int
	 */
	public int scYhjsxx(@Param(value = "zgh") String[] zgh);
	
	
	
	/**
	 * 
	 * <p>方法说明：批量修改用户密码<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年3月29日上午9:43:26<p>
	 * @param zghs 职工号
	 * @param password 密码
	 * @return int
	 */
	public int updateYhmm(@Param(value = "zghs") String[] zghs , @Param(value = "password")String password);
	
	
	
	/**
	 * 
	 * <p>方法说明：批量存储用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日上午9:15:21<p>
	 * @param list 
	 * @return int
	 */
	public int insertYhsjfw(List<Map<String,String>> list);
	
	
	
	/**
	 * 
	 * <p>方法说明：删除用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日上午9:20:01<p>
	 * @param zghs 职工号
	 * @return int
	 */ 
	public int deleteYhsjfw(@Param(value = "zghs")String[] zghs);
	
	
	
	/**
	 * 
	 * <p>方法说明：查询用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日下午1:57:27<p>
	 * @param zgh 职工号
	 * @return list
	 */
	public List<Map<String,String>> getSjfwByYh(@Param(value = "zgh") String zgh);
	
	
	
	/**
	 * 
	 * <p>方法说明：按类型查询用户数据范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年4月13日下午1:57:51<p>
	 * @param zgh 职工号
	 * @param sjdm 数据类型
	 * @return list
	 */
	public List<String> getYhsjfwList(@Param(value = "zgh") String zgh,@Param(value = "sjdm") String sjdm);


	/**
	 * 
	 * @description	： 删除选中的数据范围
	 * @param ids
	 * @param sjdm
	 * @param value
	 * @return
	 */
	public boolean deleteCheckedYhsjfw(@Param(value = "zgh")String id, @Param(value = "sjdm")String sjdm, @Param(value = "value")String value);


	/**
	 * 
	 * @description	： 插入新数据范围
	 * @param ids
	 * @param sjdm
	 * @param value
	 * @return
	 */
	public boolean insertCheckedYhsjfw(@Param(value = "zgh")String id, @Param(value = "sjdm")String sjdm, @Param(value = "value")String value);
	
}
