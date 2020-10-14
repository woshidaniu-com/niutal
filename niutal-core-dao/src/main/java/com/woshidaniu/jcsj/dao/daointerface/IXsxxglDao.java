package com.woshidaniu.jcsj.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.jcsj.dao.entities.XsxxglModel;


@Repository("xsxxglDao")
public interface IXsxxglDao extends BaseDao<XsxxglModel>{

	/**
	 * 按学号查询学生照片
	 * @param xh
	 * @return
	 */
	public XsxxglModel getXszp(String xh);
	
	public XsxxglModel getModelSimple(XsxxglModel model);

	public String getZydmByBjdm(String bjdmId);

	public int batchDeleteForSf(List<XsxxglModel> list);
	
}
