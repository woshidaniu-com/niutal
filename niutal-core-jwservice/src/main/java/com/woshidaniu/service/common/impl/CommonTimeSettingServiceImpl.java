/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.cache.core.utils.CacheKeyUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.daointerface.ICommonTimeSettingDao;
import com.woshidaniu.entities.TimeSettingModel;
import com.woshidaniu.service.common.ICommonTimeSettingService;
import com.woshidaniu.service.common.ITimeSettingCallback;

/**
 * 
 *@类名称:CommonTimeSettingServiceImpl.java
 *@类描述：功能开放时间控制service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-2-10 上午09:14:23
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */

@After
@Logger(model="N0105",business="N010530")
@Service("timeSettingService")
public class CommonTimeSettingServiceImpl extends BaseServiceImpl<TimeSettingModel, ICommonTimeSettingDao> implements ICommonTimeSettingService {

	 /**
     * yyyy-MM-dd HH:mm:ss格式SimpleDateFormat对象
     * */
    private SimpleDateFormat FORMAT_ONE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ICommonTimeSettingDao dao;
    @Override
    public void afterPropertiesSet() throws Exception {
       super.afterPropertiesSet();
       super.setDao(dao);
       
    }
	@Resource
    private ICommonQueryDao commonQueryDao;
	
	@Override
	public List<TimeSettingModel> getTreeGridModelList(TimeSettingModel model) {
		return getDao().getTreeGridModelList(model);
	}

	@Override
	public void updateStatus(TimeSettingModel model) {
		getDao().updateStatus(model);
		if(isCacheSupport()){
			//删除指定功能模块代码+操作代码对应的缓存
			Cache cache = this.getCache();
			cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeSetting", new String[]{model.getGnmkdm(),model.getCzdm()}));
			cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeControlList", ""));
		}
	}

	@Override
	public void updateTimeSetting(TimeSettingModel model) {
		getDao().updateTimeSetting(model);
		if(isCacheSupport()){
			//删除指定功能模块代码+操作代码对应的缓存
			Cache cache = this.getCache();
			cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeSetting", new String[]{model.getGnmkdm(),model.getCzdm()}));
			cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeControlList", ""));
		}
	}
	
	@Override
	public TimeSettingModel getTimeSetting(String gnmkdmKey, String czdm) {
		TimeSettingModel timeSettingModel = new TimeSettingModel();
		TimeSettingModel model = null;
		timeSettingModel.setGnmkdm(gnmkdmKey);
		timeSettingModel.setCzdm(czdm);
		if(isCacheSupport()){
			//删除指定功能模块代码+操作代码对应的缓存
			Cache cache = this.getCache();
			//根据条件生成唯一key
			String autoKey = CacheKeyUtils.genKey(getClass(), "getTimeSetting", new String[]{gnmkdmKey,czdm});
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				model =  (TimeSettingModel) valueWrapper.get();
			}else{
				//缓存过期重新查询
				model = getDao().getModel(timeSettingModel);
				//缓存一周
				if(null != model ){
					cache.put(autoKey, model);
				}
			}
		}else{
			model = getDao().getModel(timeSettingModel);
		}
		return model;
	}

	/**
	 * 
	 *@描述：得到所有受到时间控制的 功能模块代码+操作代码  集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6下午03:42:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	@SuppressWarnings("unchecked")
	public List<String>  getTimeControlList(){
		List<String> list = null;
		if(isCacheSupport()){
			//删除指定功能模块代码+操作代码对应的缓存
			Cache cache = this.getCache();
			//根据条件生成唯一key
			String autoKey = CacheKeyUtils.genKey(getClass(), "getTimeControlList", "");
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				list =  (List<String>) valueWrapper.get();
			}else{
				//缓存过期重新查询
				list = getDao().getTimeControlList();
				//缓存一周
				if(null!=list){
					cache.put(autoKey,list);
				}
			}
		}else{
			list = getDao().getTimeControlList();
		}
		return list;
	}
	
	@Override
	public boolean isInTimeSetting(String gnmkdmKey, String czdm,ITimeSettingCallback callback) {
		return callback.doInTimeSetting(this.getTimeSetting(gnmkdmKey, czdm));
	}

	public boolean isOnTimeControl(String gnmkdmKey,String czdm){
		//查询受到时间控制的 功能模块代码+操作代码 组合 的集合
		List<String> controlList = this.getTimeControlList();
		if(!BlankUtils.isBlank(controlList) &&  controlList.contains(gnmkdmKey + czdm)){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isInTimeSetting(String gnmkdmKey, String czdm) {
		//查询对应功能的开放时间控制
		TimeSettingModel model = this.getTimeSetting(gnmkdmKey, czdm);
		//如果设置过，且启用 才进行判断
		if(null != model && BooleanUtils.parse(model.getSfsy())){
			//得到数据库时间
			String dbTime = commonQueryDao.getDatabaseTime("yyyy-MM-dd HH24:mi:ss");
			try {
				if(FORMAT_ONE.parse(dbTime).before(FORMAT_ONE.parse(model.getKssj())) || FORMAT_ONE.parse(dbTime).after(FORMAT_ONE.parse(model.getJssj()))){
					return false;
				}else{
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 *@描述：保存时间设置信息 表格形式
	 *@创建人:wwb
	 *@创建时间:2015-2-10下午06:03:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	@Comment(recordSQL=true,recordDesc=false)
	public void updateSettingTime(TimeSettingModel model){
		
		List<String> gnmkdmList = model.getGnmkdmList();
		List<String> czdmList = model.getCzdmList();
		List<String> kssjList = model.getKssjList();
		List<String> jssjList = model.getJssjList();
		List<String> sfsyList = model.getSfsyList();
		List<String> tsxxList = model.getTsxxList();
		//循环获取列表内容，进行更新操作
		for(int i=0;i<kssjList.size();i++){
			TimeSettingModel timeSettingModel = new TimeSettingModel();
			timeSettingModel.setGnmkdm(gnmkdmList.get(i));
			timeSettingModel.setCzdm(czdmList.get(i));
			timeSettingModel.setKssj(kssjList.get(i));
			timeSettingModel.setJssj(jssjList.get(i));
			timeSettingModel.setSfsy(sfsyList.get(i));
			timeSettingModel.setTsxx(tsxxList.get(i));
			this.dao.updateSettingTime(timeSettingModel);
			if(cacheSupport){
				//删除指定功能模块代码+操作代码对应的缓存
				Cache cache = this.getCache();
				cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeSetting", new String[]{timeSettingModel.getGnmkdm(),timeSettingModel.getCzdm()}));
				cache.evict( CacheKeyUtils.genKey(getClass(), "getTimeControlList", ""));
			}
		}
	}
	
	/**
	 * 
	 *@描述：覆盖getModelList方法，增加缓存功能
	 *@创建人:wwb
	 *@创建时间:2015-2-10下午06:03:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public List<TimeSettingModel> getModelList(TimeSettingModel model) {
		List<TimeSettingModel> list = dao.getModelList(model);
		if(!BlankUtils.isBlank(list)){
			 return list;
		}
		return new ArrayList<TimeSettingModel>();
	}

	public ICommonQueryDao getCommonQueryDao() {
		return commonQueryDao;
	}

	public void setCommonQueryDao(ICommonQueryDao commonQueryDao) {
		this.commonQueryDao = commonQueryDao;
	}
	
	@Override
	public boolean isCacheSupport() {
		return true;
	}

}
