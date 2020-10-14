package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.designer.dao.daointerface.IBaseWidgetDetailDao;
import com.woshidaniu.designer.dao.daointerface.IBaseWidgetParameterDao;
import com.woshidaniu.designer.dao.daointerface.IBaseWidgetResourceDao;
import com.woshidaniu.designer.dao.entities.BaseWidgetDetailModel;
import com.woshidaniu.designer.dao.entities.BaseWidgetParameterModel;
import com.woshidaniu.designer.dao.entities.BaseWidgetResourceModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetDetailService;



/**
 * 
 *@类名称: BaseWidgetDetailImpl.java
 *@类描述：功能js组件描述信息service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午08:44:13
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0315")
@Service
public class BaseWidgetDetailServiceImpl extends BaseServiceImpl<BaseWidgetDetailModel, IBaseWidgetDetailDao> implements IBaseWidgetDetailService {
	
	@Resource
	protected IBaseWidgetParameterDao baseWidgetParameterDao;
	@Resource
	protected IBaseWidgetResourceDao baseWidgetResourceDao;
	@Resource
	protected IBaseWidgetDetailDao dao;
	@Resource
	protected ICommonQueryDao queryDao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(BaseWidgetDetailModel model) {
		
		String widget_guid = getQueryDao().getSysGuid();
		
		model.setWidget_guid(widget_guid);
		
		dao.insert(model);
		
		//判断参数不为空
		if( !BlankUtils.isBlank(model.getParameters())){
			for (BaseWidgetParameterModel element : model.getParameters()) {
				element.setWidget_guid(widget_guid);
				baseWidgetParameterDao.insert(element);
			}
		}
		//判断资源不为空
		if( !BlankUtils.isBlank(model.getResources())){
			for (BaseWidgetResourceModel element : model.getResources()) {
				element.setWidget_guid(widget_guid);
				baseWidgetResourceDao.insert(element);
			}
		}
		return true;
	}
	
	@Override
	public BaseWidgetDetailModel getModel(BaseWidgetDetailModel model) {
		//查询数据
		BaseWidgetDetailModel tmpModel = dao.getModel(model);
		if(!BlankUtils.isBlank(tmpModel)){
			//根据功能组件ID查询该【功能js组件初始化参数】信息
			tmpModel.setParameters(getBaseWidgetParameterDao().getWidgetParameterList(tmpModel.getWidget_guid()));
			//根据功能组件ID查询该【组件脚本样式资源】信息
			tmpModel.setResources(getBaseWidgetResourceDao().getWidgetResourceList(tmpModel.getWidget_guid()));
		}
		return tmpModel;
	}
	
	@Override
	@Comment
	public boolean update(BaseWidgetDetailModel model) {
		
		//根据功能组件ID查询该【功能js组件初始化参数】信息
		List<BaseWidgetParameterModel> parameters = getBaseWidgetParameterDao().getWidgetParameterList(model.getWidget_guid());
		//判断参数不为空
		if( !BlankUtils.isBlank(model.getParameters()) && !BlankUtils.isBlank(parameters)){
			
			//删除移除的历史参数
			for (BaseWidgetParameterModel oldElement : parameters) {
				boolean isHas = false;
				for (BaseWidgetParameterModel newElement : model.getParameters()) {
					if(!BlankUtils.isBlank(newElement.getParam_guid()) && oldElement.getParam_guid().equals(newElement.getParam_guid())){
						isHas = true;
						break;
					}
				}
				//历史参数被删除
				if(!isHas){
					baseWidgetParameterDao.delete(oldElement);
				}
			}
			//添加和更新新的资源
			for (BaseWidgetParameterModel newElement : model.getParameters()) {
				boolean isHas = false;
				for (BaseWidgetParameterModel oldElement : parameters) {
					if(!BlankUtils.isBlank(newElement.getParam_guid()) && newElement.getParam_guid().equals(oldElement.getParam_guid())){
						isHas = true;
						break;
					}
				}
				//历史资源更新
				if(isHas){
					baseWidgetParameterDao.update(newElement);
				}else{
					newElement.setWidget_guid(model.getWidget_guid());
					baseWidgetParameterDao.insert(newElement);
				}
			}
		} else if( !BlankUtils.isBlank(model.getParameters()) && BlankUtils.isBlank(parameters)){
			//添加和更新新的资源
			for (BaseWidgetParameterModel element : model.getParameters()) {
				element.setWidget_guid(model.getWidget_guid());
				baseWidgetParameterDao.insert(element);
			}
		} else if(!BlankUtils.isBlank(parameters)){
			//没有参数设置,且有历史参数，则删除历史参数
			for (BaseWidgetParameterModel element : parameters) {
				baseWidgetParameterDao.delete(element);
			}
		}
		
		//根据功能组件ID查询该【组件脚本样式资源】信息
		List<BaseWidgetResourceModel> resources = getBaseWidgetResourceDao().getWidgetResourceList(model.getWidget_guid());
		//判断资源不为空
		if( !BlankUtils.isBlank(model.getResources()) && !BlankUtils.isBlank(resources)){
			//删除移除的历史资源
			for (BaseWidgetResourceModel oldElement : resources) {
				boolean isHas = false;
				for (BaseWidgetResourceModel newElement : model.getResources()) {
					if(!BlankUtils.isBlank(newElement.getResource_guid()) && oldElement.getResource_guid().equals(newElement.getResource_guid())){
						isHas = true;
						break;
					}
				}
				//历史资源被删除
				if(!isHas){
					baseWidgetResourceDao.delete(oldElement);
				}
			}
			//添加和更新新的资源
			for (BaseWidgetResourceModel newElement : model.getResources()) {
				boolean isHas = false;
				if(!BlankUtils.isBlank(newElement.getResource_guid())){
					for (BaseWidgetResourceModel oldElement : resources) {
						if(newElement.getResource_guid().equals(oldElement.getResource_guid())){
							isHas = true;
							break;
						}
					}
				}
				//历史资源更新
				if(isHas){
					baseWidgetResourceDao.update(newElement);
				}else{
					newElement.setWidget_guid(model.getWidget_guid());
					baseWidgetResourceDao.insert(newElement);
				}
			}
		} else if( !BlankUtils.isBlank(model.getParameters()) && BlankUtils.isBlank(parameters)){
			//添加和更新新的资源
			for (BaseWidgetResourceModel element : model.getResources()) {
				element.setWidget_guid(model.getWidget_guid());
				baseWidgetResourceDao.insert(element);
			}
		}else if(!BlankUtils.isBlank(parameters)){
			//没有资源设置,且有历史资源，则删除历史资源
			for (BaseWidgetResourceModel element : resources) {
				baseWidgetResourceDao.delete(element);
			}
		}
		//更新组件本身信息
		dao.update(model);
		
		return true;
	}
	
	@Override
	@Comment
	public boolean delete(BaseWidgetDetailModel model) {
		
		dao.delete(model);
		
		return true;
	}

	@Override
	public int getUseCount(BaseWidgetDetailModel model) {
		return dao.getUseCount(model);
	}
	
	@Override
	public List<BaseWidgetDetailModel> getWidgetDetailList(){
		return dao.getWidgetDetailList();
	}

	public List<BaseWidgetDetailModel> getFuncWidgetDetailList(DesignFuncModel model){
		return dao.getFuncWidgetDetailList(model);
	}
	
	public IBaseWidgetParameterDao getBaseWidgetParameterDao() {
		return baseWidgetParameterDao;
	}

	public void setBaseWidgetParameterDao(
			IBaseWidgetParameterDao baseWidgetParameterDao) {
		this.baseWidgetParameterDao = baseWidgetParameterDao;
	}

	public IBaseWidgetResourceDao getBaseWidgetResourceDao() {
		return baseWidgetResourceDao;
	}

	public void setBaseWidgetResourceDao(
			IBaseWidgetResourceDao baseWidgetResourceDao) {
		this.baseWidgetResourceDao = baseWidgetResourceDao;
	}

	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}
	
}
