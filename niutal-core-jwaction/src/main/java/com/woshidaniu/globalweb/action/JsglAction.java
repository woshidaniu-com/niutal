package com.woshidaniu.globalweb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IJcsjService;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称:JsglAction.java
 *@类描述： 角色管理控制
 *@创建人：kangzhidong
 *@创建时间：2015-1-13 下午02:52:52
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class JsglAction extends CommonBaseAction implements ModelDriven<JsglModel> {

	// 全局MODEL
	protected JsglModel model = new JsglModel();
	//角色管理SERVICE
	@Resource
	private IJsglService jsglService;
	//用户管理SERVICE
	@Resource
	private IYhglService yhglService;
	//基础数据service
	@Resource(name="jcsjService")
	private IJcsjService jcsjService;

	
	/**
	 * 功能模块信息列表
	 * @throws
	 */
	private void setGndmValueStack() throws Exception{
		boolean sfersq = !getUser().isAdmin() ? getJsglService().getYhEjsq(getUser().getJsdm()) : true; 
		if(sfersq){
			getValueStack().set("flag","1");
		}else{
			getValueStack().set("flag","0");
		}
		List<JsglModel> gnmkList = getJsglService().getGnmkYj(model);
		getValueStack().set("gnmkList", gnmkList);
	}

	/**
	 * 
	 * 方法描述: 角色查询 
	 * 参数 @return 
	 * 参数说明 
	 * 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxJsxx() {
		try {
			 if (QUERY.equals(model.getDoType())) {
				model.setYhm(getUser().getYhm());
				QueryModel queryModel = model.getQueryModel();
				List<JsglModel> jsxxList = null;
				if(getUser().isAdmin()){
					jsxxList = getJsglService().getAdminTreeGridModelList(model);
				}else{
					model.setJsdm(getUser().getJsdm());
					jsxxList = getJsglService().getTreeGridModelList(model);
				}
				for (JsglModel jsglModel : jsxxList) {
					jsglModel.setExpanded(true);
				}
				queryModel.setItems(jsxxList);
				getValueStack().set(DATA, queryModel);
				return DATA;
			 }else{
				ValueStack vs = getValueStack();
				List<HashMap<String, String>> sfList = BaseDataReader.getCachedOptionList("isNot");
				vs.set("sfList", sfList);
				
				// 查询当前登录用户正激活角色信息
				model.setJsdm(getUser().getJsdm());
				JsglModel jsdmModel2 = getJsglService().getModel(model);
				//判断是否允许二级授权
				if("0".equals(jsdmModel2.getSfejsq())){
					getValueStack().set("sfejsqFlag", jsdmModel2.getSfejsq());
				}
				
				return SUCCESS;
			}
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
	/**
	 * 
	 *@描述：增加角色 /增加子角色
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:30:47
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjJsxx() {
		try {
			setGndmValueStack();
			//0025	角色类型代码表
			getValueStack().set("jslxList", getJcsjService().getJcsjList("0025"));
			getValueStack().set("isPublic", BaseDataReader.getCachedBaseDataList("isPublic"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存角色 / 保存子角色
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午05:16:10
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjBcJsxx() { 
		try {
			if(getUser().isAdmin()){
				//如果是管理员，则新增角色功能增加的角色的父级角色为空
				model.setFjsdm("");
			}else{
				//如果是普通角色，则新增角色功能增加的角色的父级角色为该用户当前登录角色
				model.setFjsdm(getUser().getJsdm());
			}
			model.setYhm(getUser().getYhm());
			// 保存角色信息
			getJsglService().zjJsxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	public String xzzjsJsxx() {
		try {
			//判断是否是子角色添加操作
			if(StringUtils.isEmpty(model.getFjsdm())){
				getValueStack().set(Result.MESSAGE, "父级角色代码不能为空！");
				return Result.EX_WARN;
			}
			setGndmValueStack();
			JsglModel tmp = new JsglModel();
					  tmp.setJsdm(model.getFjsdm());
			//取得父角色信息
			getValueStack().set("parent",getJsglService().getModel(tmp) );
			//0025	角色类型代码表
			getValueStack().set("jslxList",getJcsjService().getJcsjList("0025"));
			getValueStack().set("isPublic", BaseDataReader.getCachedBaseDataList("isPublic"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String xzzjsBcJsxx() { 
		try {
			//判断是否是子角色添加操作
			if(StringUtils.isEmpty(model.getFjsdm())){
				getValueStack().set(DATA, "父级角色代码不能为空！");
				return DATA;
			}
			model.setYhm(getUser().getYhm());
			// 保存子角色信息
			getJsglService().zjSubJsxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：修改角色 
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午05:16:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgJsxx() {
		try {
			
			setProperty("jslxList",getJcsjService().getJcsjList("0025"));
			try {
				JsglModel jsglModel = new JsglModel();
				jsglModel.setJsdm(getRequest().getParameter("jsdm"));
				
				// 查询单个角色信息
				jsglModel = getJsglService().getModel(jsglModel);
				PropertyUtils.copyProperties(model, jsglModel);
				
				getValueStack().set("isPublic", BaseDataReader.getCachedBaseDataList("isPublic"));
				
			} catch (Exception e) {
				logException(e);
			}
			setGndmValueStack();
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存对角色信息的修改
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:29:38
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcJsxx() {
		try {
			getJsglService().xgJsxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：删除角色信息 
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午05:15:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String scJsxx() throws Exception {
		try {
			// 删除角色信息
			String pks    = getRequest().getParameter("ids");
			model.setPkValue(pks);
			//判断要删除角色是否已经分配用户或者不允许删除
			boolean canDelete = true;
			List<JsglModel> deleteList = new ArrayList<JsglModel>();
			String pkValues[] = model.getPkValue().split(",");
			for (int i = 0; i < pkValues.length; i++) {
				model.setJsdm(pkValues[i]);
				JsglModel jsglmodel = getJsglService().getModel(model);
				if("0".equals(jsglmodel.getSfksc())){
					getValueStack().set(DATA, getText("I99015",new String[]{jsglmodel.getJsmc()}));
					canDelete = false;
					break;
				}else if(Integer.valueOf(jsglmodel.getZjsnum()) > 0){
					canDelete = false;
					getValueStack().set(DATA, getText("I99014",new String[]{jsglmodel.getJsmc()}));
					break;
				}else if(Integer.valueOf(jsglmodel.getYhnum()) > 0){
					canDelete = false;
					getValueStack().set(DATA, getText("I99013",new String[]{jsglmodel.getJsmc()}));
					break;
				}else{
					deleteList.add(jsglmodel);
				}
			}
			if(canDelete){
				model.setDeleteList(deleteList);
				getJsglService().scJsxx(model);
				getValueStack().set(DATA, getText("I99005"));
			}
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}
	

	/**
	 * 
	 *@描述：角色分配用户 页面跳转
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午07:48:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJsyhfpIndex() {
		ValueStack vs = getValueStack();
		try {
			// 查询单个角色信息
			JsglModel jsglModel = getJsglService().getModel(model);
			YhglModel yhglModel = new YhglModel();
			PropertyUtils.copyProperties(yhglModel, model);
			vs.set("model", jsglModel);// 角色信息
			vs.set("xyList", getCommonSqlService().queryAllJgxx());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存角色分配用户
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午08:23:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String fpyhForJsyhzj() {
		try {
			// 保存角色分配用户信息
			getJsglService().zjJsyhfpxx(model);
			getValueStack().set(DATA, "1");
		} catch (Exception e) {
			getValueStack().set(DATA, "0");
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：删除角色已分配用户
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午08:23:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String fpyhForJsyhsc() {
		try {
			jsglService.scJsyhfpxx(model);
			getValueStack().set(DATA, "1");
		} catch (Exception e) {
			getValueStack().set(DATA, "0");
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：角色分配用户，未分配用户列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午07:47:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String cxFpyhWfpYhxx() throws Exception {
		try {
			List<YhglModel>  list = getJsglService().getPagedListWfpYh(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(list);
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, model.getQueryModel());
		}
		return DATA;
	}
        
	/**
	 * 
	 *@描述： 角色分配用户，已分配用户列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午07:47:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
    public String cxFpyhYfpYhxx() throws Exception{
        try {
			List<YhglModel>  list = getJsglService().getPagedListYfpYh(model);
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(list);
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, model.getQueryModel());
		}
        return DATA;
    }
            
	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public JsglModel getModel() {
		model.setModelBase(getUser());
		model.setUser(getUser());
		return model;
	}

	public IJcsjService getJcsjService() {
		return jcsjService;
	}

	public void setJcsjService(IJcsjService jcsjService) {
		this.jcsjService = jcsjService;
	}

	public void setModel(JsglModel model) {
		this.model = model;
	}
	
}
