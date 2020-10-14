package com.woshidaniu.taglibs.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.taglibs.dao.entities.DatasetModel;
import com.woshidaniu.taglibs.service.svcinterface.IDatasetService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称: BaseQueryFieldAction.java
 *@类描述：基础自定义查询字段action
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午03:07:57
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DatesetTaglibAction extends BaseAction implements ModelDriven<DatasetModel>  { 

	protected DatasetModel model = new DatasetModel();
	@Resource
	protected IDatasetService service;
	/**公共service*/
	@Resource
	protected ICommonSqlService commonSqlService;	

	/**
	 * 
	 *@描述：跳转至【基础查询字段】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxQueryFieldBaseIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			getValueStack().set("gnmkdmPairList", getCommonSqlService().getGnmkdmPairList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjBaseQueryField(){
		getValueStack().set("gnmkdmPairList", getCommonSqlService().getGnmkdmPairList());
		//getValueStack().set("czdmPairList", getCommonSqlService().getCzdmPairList());
		//字段值来源:'APP':'程序内置','SQL':'数据库','XML':'XML数据','Spring':'Spring集合对象','Fixed':'固定值' 
		getValueStack().set("sourceTypeList", BaseDataReader.getCachedBaseDataList("sourceTypeList"));
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjBaseQueryFieldData(){
		try {
			getService().insert(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息新增"}));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgBaseQueryField(){
		try {
			getValueStack().set("gnmkdmPairList", getCommonSqlService().getGnmkdmPairList());
			//字段值来源:'APP':'程序内置','SQL':'数据库','XML':'XML数据','Spring':'Spring集合对象','Fixed':'固定值' 
			getValueStack().set("sourceTypeList", BaseDataReader.getCachedBaseDataList("sourceTypeList"));
			//getValueStack().set("czdmPairList", getCommonSqlService().getCzdmPairList());
			BeanUtils.copyProperties(model, getService().getModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgBaseQueryFieldData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息修改"}));
		}
		return DATA;
	}
	
	
	@Override
	public DatasetModel getModel() {
		return model;
	}

	public IDatasetService getService() {
		return service;
	}

	public void setService(IDatasetService service) {
		this.service = service;
	}

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}
	
}

