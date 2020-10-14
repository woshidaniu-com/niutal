/**
 * 
 */
package com.woshidaniu.component.bpm.management.biz.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.component.bpm.common.BaseDao;
import com.woshidaniu.component.bpm.management.biz.dao.entities.ProcessBizModel;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：ProcessBizDao
 * <p>
 * @className:com.woshidaniu.component.bpm.management.biz.dao.daointerface.ProcessBizDao.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月10日上午11:42:44
 */
@Repository("processBizDao")
public interface IProcessBizDao extends BaseDao<ProcessBizModel>{

	
}
