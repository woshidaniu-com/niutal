package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.XsxxModel;

@Repository("xsxxDao")
public interface IXsxxDao extends BaseDao<XsxxModel>{
	
	/**
	 * 增加学生密码
	 * @param model
	 * @return
	 */
	public int insertXsmm(XsxxModel model);
	
	/**
	 * 修改学生信息
	 * @param model
	 * @return
	 */
	public int updateXsmm(XsxxModel model);
	
	/**
	 * 批量删除学生密码
	 * @param model
	 * @return
	 */
	public int batchDeleteXsmm(List<XsxxModel> list);
}
