package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.PWModel;

/**
 * @className: IMbglDao
 * @description: 模版管理DAO
 * @author : Hanyc
 * @date: 2018-12-03 09:28:44
 * @version V1.0
 */
@Repository("PWmbglDao")
public interface IMbglDao extends BaseDao<PWModel> {
	List<PWModel> getList(PWModel model);
}