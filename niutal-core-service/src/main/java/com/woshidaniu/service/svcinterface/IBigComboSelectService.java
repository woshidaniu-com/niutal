package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.BigComboSelectModel;

public interface IBigComboSelectService extends BaseService<BigComboSelectModel>{
	
	/**
	 * 查询学院首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getAllXYInitial();
	
	/**
	 * 查询学院数据by首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getXYDataByInitial(String initial);
	
	/**
	 * 查询首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboInitial(String name, String table, String parentName,String parentId,String secondparentName, String secondParentId);
	
	/**
	 * 查询数据by首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboDataByInitial(String id, String name, String table, String initial,String parentName,String parentId,String secondparentName, String secondParentId);
	
	/**
	 * 查询数据不跟据首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboDataWithoutInitial(String id, String name, String table,String parentName,String parentId,String secondparentName, String secondParentId);
	
	public List<BigComboSelectModel> getShengList() throws Exception;
	
	public List<BigComboSelectModel> getShiList(String sheng) throws Exception;
	
	public List<BigComboSelectModel> getXianList(String shi) throws Exception;
}
