package com.woshidaniu.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * 行政区划查询
 * @author Penghui.Qu
 *
 */
@Repository("xzqhDao")
public interface IXzqhDao extends BaseDao<SjbzModel> {

	/**
	 * 省
	 * @return
	 */
	public List<SjbzModel> getShengList() throws Exception;
	
	
	
	/**
	 * 
	 * <p>方法说明：查询行政区划的二三级地区<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2017年2月4日上午10:55:32<p>
	 * @return
	 */
	public List<SjbzModel> getChildrens();
	
	
	
	/**
	 * 市
	 * @return
	 */
	public List<SjbzModel> getShiList(String sheng) throws Exception;
	
	
	/**
	 * 加载直辖市下区
	 * @param sheng
	 * @return
	 * @throws Exception
	 */
	public List<SjbzModel> getZxsqList(String sheng) throws Exception;
	
	/**
	 * 县
	 * @return
	 */
	public List<SjbzModel> getXianList(String shi) throws Exception;
	
	/**
	 * 获取省级
	 * @param xzqhdm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getSheng(String xzqhdm);
	
	/**
	 * 获取市级
	 * @param xzqhdm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getShi(String xzqhdm);
	
	/**
	 * 获取市级
	 * @param xzqhdm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getXian(String xzqhdm);
	
	/**
	 * 获取行政区号名称合并列表
	 * @return
	 */
	public List<SjbzModel> getXzqhMcHbList();
	
	/**
	 * 模糊行政区号名称列表
	 * @return
	 */
	public List<SjbzModel> likeQueryXzqhMcHbList(HashMap<String, Object> mp);
	/**
	 * 获取省级
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryShengList(HashMap<String,Object>map);
	/**
	 * 获取市级
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryShiList(HashMap<String,Object>map);

	/**
	 * 加载国家
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryGuoJiaList(HashMap<String, Object> map);
}
