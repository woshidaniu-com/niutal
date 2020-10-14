package com.woshidaniu.jcsj.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.jcsj.dao.entities.BjdmModel;

/**
 * 班级代码维护
 * @author Administrator
 *
 */
@Repository(value="zfxgBjdmDao")
public interface IBjdmDao extends BaseDao<BjdmModel> {

}
