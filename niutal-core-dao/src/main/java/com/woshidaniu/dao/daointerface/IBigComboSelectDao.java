package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.BigComboSelectModel;

@Repository("bigComboSelectDao")
public interface IBigComboSelectDao extends BaseDao<BigComboSelectModel>{
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
	public List<BigComboSelectModel> getXYDataByInitial(BigComboSelectModel bcs);
	
	/**
	 * 查询首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboInitial(BigComboSelectModel bcs);
	
	/**
	 * 查询数据by首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboDataByInitial(BigComboSelectModel bcs);
	
	/**
	 * 查询数据不跟据首字母
	 * @param hashMap
	 * @return
	 */
	public List<BigComboSelectModel> getBigComboDataWithoutInitial(BigComboSelectModel bcs);
	
	/**
	 * 省
	 * @return
	 */
	public List<BigComboSelectModel> getShengList() throws Exception;
	
	/**
	 * 市
	 * @return
	 */
	public List<BigComboSelectModel> getShiList(String sheng) throws Exception;
	
	/**
	 * 加载直辖市下区
	 * @param sheng
	 * @return
	 * @throws Exception
	 */
	public List<BigComboSelectModel> getZxsqList(String sheng) throws Exception;
	
	/**
	 * 县
	 * @return
	 */
	public List<BigComboSelectModel> getXianList(String shi) throws Exception;

}
