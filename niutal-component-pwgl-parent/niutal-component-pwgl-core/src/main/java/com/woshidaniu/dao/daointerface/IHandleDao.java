package com.woshidaniu.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.PWModel;

/**
 * @className: IHandleDao
 * @description: 对外
 * @author : Hanyc
 * @date: 2018-12-05 17:36:35
 * @version V1.0
 */
@Repository("PWHandleDao")
public interface IHandleDao extends BaseDao<PWModel> {

	List<HashMap<String, Object>> getDataList(PWModel model);
}