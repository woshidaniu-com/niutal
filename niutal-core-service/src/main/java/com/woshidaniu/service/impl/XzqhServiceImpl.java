package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.RegexUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IXzqhDao;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.IXzqhService;

/**
 * 行政区划加载实现
 * @author Penghui.Qu
 *
 */
@Service("xzqhService")
public class XzqhServiceImpl extends BaseServiceImpl<SjbzModel, IXzqhDao>
		implements IXzqhService {

	private Map<String,String> zxsMap = null;
	

	@Resource
	private IXzqhDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	public void setZxsMap(Map<String, String> zxsMap) {
		this.zxsMap = zxsMap;
	}
	
	public Map<String, String> getZxsMap() {
		return zxsMap;
	}

	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXzqhService#getShengList()}
	 */
	public List<SjbzModel> getShengList() throws Exception {
		return dao.getShengList();
	}

	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXzqhService#getShiList(String)}
	 */
	public List<SjbzModel> getShiList(String sheng, boolean qfxzq) throws Exception {
		//直接市调不同的方法查询下级列表
		if(zxsMap.containsKey(sheng) && qfxzq){
			return dao.getZxsqList(sheng);
		}
		
		return dao.getShiList(sheng);
	}
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXzqhService#getShiList(String)}
	 */
	public List<SjbzModel> getShiList(String sheng) throws Exception {
		//直接市调不同的方法查询下级列表
		if(zxsMap.containsKey(sheng)){
			return dao.getZxsqList(sheng);
		}
		
		return dao.getShiList(sheng);
	}
	
	
	
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXzqhService#getXianList(String)}
	 */
	public List<SjbzModel> getXianList(String shi) throws Exception {
		return dao.getXianList(shi);
	}
	
	/**
	 * 获取省市县
	 * @param xzqhdm
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getShengShiXian(String xzqhdm) {
		HashMap<String, String> ssx=new HashMap<String, String>();
		if(xzqhdm == null || xzqhdm.length() > 6 || zxsMap == null){
			return ssx;
		}
		
		String xzqhlx=getXzqhlx(xzqhdm);
		HashMap<String, String> sheng=null;
		HashMap<String, String> shi=null;
		HashMap<String, String> xian=null;
		if(XZQHLX_SHENG.equals(xzqhlx)){
			sheng=dao.getSheng(xzqhdm);
			
			if(sheng != null){
				ssx.put("shengDm", sheng.get("DM"));
				ssx.put("shengMc", sheng.get("MC"));
			}else{
				ssx.put("shengDm", "");
				ssx.put("shengMc", "");
			}
			
			ssx.put("shiDm", "");
			ssx.put("shiMc", "");
			ssx.put("xianDm", "");
			ssx.put("xianMc", "");
		}else if(XZQHLX_SHI.equals(xzqhlx)){
			sheng=dao.getSheng(xzqhdm);
			shi=dao.getShi(xzqhdm);
			
			if(sheng != null){
				ssx.put("shengDm", sheng.get("DM"));
				ssx.put("shengMc", sheng.get("MC"));
			}else{
				ssx.put("shengDm", "");
				ssx.put("shengMc", "");
			}
			
			if(shi != null){
				ssx.put("shiDm", shi.get("DM"));
				ssx.put("shiMc", shi.get("MC"));
			}else{
				ssx.put("shiDm", "");
				ssx.put("shiMc", "");
			}
			
			ssx.put("xianDm", "");
			ssx.put("xianMc", "");
		}else if(XZQHLX_XIAN.equals(xzqhlx)){
			sheng=dao.getSheng(xzqhdm);
			shi=dao.getShi(xzqhdm);
			xian=dao.getXian(xzqhdm);
			
			if(sheng != null){
				ssx.put("shengDm", sheng.get("DM"));
				ssx.put("shengMc", sheng.get("MC"));
			}else{
				ssx.put("shengDm", "");
				ssx.put("shengMc", "");
			}
			//验证是否是行政区    这里是将行政区的市一级 控制 为县一级，这个是当前系统的设置
			if(isZxq(xzqhdm)){
				if(xian != null){
					ssx.put("shiDm", xian.get("DM"));
					ssx.put("shiMc", xian.get("MC"));
				}else{
					ssx.put("shiDm", "");
					ssx.put("shiMc", "");
				}
			}else{
				if(shi != null){
					ssx.put("shiDm", shi.get("DM"));
					ssx.put("shiMc", shi.get("MC"));
				}else{
					ssx.put("shiDm", "");
					ssx.put("shiMc", "");
				}
			}
			
			if(xian != null){
				ssx.put("xianDm", xian.get("DM"));
				ssx.put("xianMc", xian.get("MC"));
			}else{
				ssx.put("xianDm", "");
				ssx.put("xianMc", "");
			}
		}else{
			return ssx;
		}
		return ssx;
	}
	
	/**
	 * 验证行政区号类型
	 * @param xzqhdm
	 * @return
	 */
	private String getXzqhlx(String xzqhdm){
		if(xzqhdm == null || xzqhdm.length() != 6){
			return "";
		}
		String sheng=xzqhdm.substring(0,2);
		String shi=xzqhdm.substring(2,4);
		String xian=xzqhdm.substring(4,6);
		String ll="00";
		
		if(!ll.equals(sheng) && ll.equals(shi) && ll.equals(xian)){
			return XZQHLX_SHENG;
		}else if(!ll.equals(sheng) && !ll.equals(shi) && ll.equals(xian)){
			return XZQHLX_SHI;
		}else if(!ll.equals(sheng) && !ll.equals(shi) && !ll.equals(xian)){
			return XZQHLX_XIAN;
		}else{
			return "";
		}
	}
	
	/**
	 * 是否是行政区
	 * @param xzqhdm
	 * @return 是：true，否：false
	 */
	private boolean isZxq(String xzqhdm){
		if(xzqhdm == null || xzqhdm.length() > 6){
			return false;
		}
		StringBuffer shengdm = new StringBuffer();
		shengdm.append(xzqhdm.substring(0,2));
		shengdm.append("0000");
		
		//考虑是否是  市辖区
		if(zxsMap.containsKey(shengdm.toString())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取行政区号名称合并列表
	 * @return
	 */
	public List<SjbzModel> getXzqhMcHbList(){
		return dao.getXzqhMcHbList();
	}
	
	/**
	 * 模糊查询行政区号名称列表
	 * @return
	 */
	public List<SjbzModel>  likeQueryXzqhMcHbList(HashMap<String, Object> mp){
		return dao.likeQueryXzqhMcHbList(mp);
	}
	
	/**
	 * 获取省级
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryShengList(HashMap<String, Object> map) {
		return dao.likeQueryShengList(map);
	}
	
	/**
	 * 获取市级
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryShiList(HashMap<String, Object> map) {
		return dao.likeQueryShiList(map);
	}
	
	/**
	 * 加载国家
	 * @param map
	 * @return
	 */
	public List<SjbzModel> likeQueryGuoJiaList(HashMap<String, Object> map) {
		return dao.likeQueryGuoJiaList(map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IXzqhService#getChildrens()
	 */
	public Map<String, List<SjbzModel>> getChildrens() {
		List<SjbzModel> childrens = dao.getChildrens();
		Map<String, List<SjbzModel>> map = new HashMap<String, List<SjbzModel>>();
		
		for (SjbzModel model : childrens){
			String parentDm = null;
			String shengDm = String.format("%s0000", StringUtils.substring(model.getDm(), 0, 2));
			
			//直辖市
			if (zxsMap.containsKey(shengDm)){
//				if (!RegexUtil.isContentMatche(model.getDm(),"[0-9]{4}+[0]{2}")){
					parentDm = String.format("%s0000", StringUtils.substring(model.getDm(), 0, 2));
//				}
			} else {
				//正则校验代码为二级
				if (RegexUtils.isContentMatche(model.getDm(),"[0-9]{4}+[0]{2}")){
					parentDm = String.format("%s0000", StringUtils.substring(model.getDm(), 0, 2));
				} else {
					parentDm = String.format("%s00", StringUtils.substring(model.getDm(), 0, 4));
				}
			}
			
			if (parentDm == null) continue;
			
			if (map.containsKey(parentDm)){
				map.get(parentDm).add(model);
			} else {
				List<SjbzModel> list = new ArrayList<SjbzModel>();
				list.add(model);
				map.put(parentDm,list);
			}
		}
		return map;
	}
}
