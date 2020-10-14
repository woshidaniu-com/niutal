package com.woshidaniu.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.SjbzModel;

/**
 * 行政区划查询
 * @author Penghui.Qu
 *
 */
public interface IXzqhService extends BaseService<SjbzModel> {
	public static final String XZQHLX_SHENG="sheng";//省
	public static final String XZQHLX_SHI="shi";//市
	public static final String XZQHLX_XIAN="xina";//县
	
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
	 * @return Map,key 上级代码 value 子区域
	 */
	public Map<String,List<SjbzModel>> getChildrens();
	
	
	/**
	 * 市
	 * 
	 * @param qfxzq 是否区分行政区
	 * 
	 * @return
	 */
	public List<SjbzModel> getShiList(String sheng, boolean qfxzq) throws Exception;
	
	/**
	 * 市
	 * 
	 * @return
	 */
	public List<SjbzModel> getShiList(String sheng) throws Exception;
	
	/**
	 * 县
	 * @return
	 */
	public List<SjbzModel> getXianList(String shi) throws Exception;
	
	/**
	 * 获取省市县
	 * @param xzqhdm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getShengShiXian(String xzqhdm);
	
	/**
	 * 获取行政区号名称合并列表
	 * @return
	 */
	public List<SjbzModel>  getXzqhMcHbList();
	
	/**
	 * 模糊查询行政区号名称列表
	 * @return
	 */
	public List<SjbzModel>  likeQueryXzqhMcHbList(HashMap<String, Object> mp);
	
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
	 * 获取直辖市map列表
	 * @return
	 */
	public Map<String, String> getZxsMap();

	/**
	 * 加载国家
	 * @return
	 */
	public List<SjbzModel> likeQueryGuoJiaList(HashMap<String, Object> queryMap);
}
