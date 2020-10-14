package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.dao.entities.GBMzModel;
import com.woshidaniu.dao.entities.GBXzqModel;
import com.woshidaniu.dao.entities.GBXbModel;
import com.woshidaniu.dao.entities.GBXlModel;
import com.woshidaniu.dao.entities.GBXwModel;
import com.woshidaniu.dao.entities.GBZzmmModel;


/**
 * 国标基础数据DAO，用于缓存
 * 
 * Create on 2013-7-16 下午05:24:27
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
@Repository("baseGbBasicDataDao")
public interface BaseGbBasicDataDao {

	/**
	 * 得到民族国标MODEL
	 * 
	 * @return List<GBMzModel>
	 */
	List<GBMzModel> getMingZhuModelList();

	/**
	 * 得到性别国标MODEL
	 * 
	 * @return List<GBXbModel>
	 */
	List<GBXbModel> getXingBieModelList();

	/**
	 * 得到学历国标MODEL
	 * 
	 * @return List<GBXlModel>
	 */
	List<GBXlModel> getXueLiModelList();

	/**
	 * 得到学位国标MODEL
	 * 
	 * @return List<GBXwModel>
	 */
	List<GBXwModel> getXueWeiModelList();

	/**
	 * 得到政治面貌国标MODEL
	 * 
	 * @return List<GBZzmmModel>
	 */
	List<GBZzmmModel> getZhengZhiMianMaoModelList();
	
	/**
	 * 得到行政区国标MODEL
	 * 
	 * @return List<GBXzqModel>
	 */
	List<GBXzqModel> getXingZhengQuModelList();
}
