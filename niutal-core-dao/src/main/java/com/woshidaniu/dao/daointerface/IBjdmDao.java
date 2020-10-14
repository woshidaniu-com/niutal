package com.woshidaniu.dao.daointerface;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.jcsj.dao.entities.BjdmModel;
import org.springframework.stereotype.Repository;

/**
 * 班级代码维护
 * @author Administrator
 *
 */
@Repository(value="bjdmDao")
public interface IBjdmDao extends BaseDao<BjdmModel> {

}
