package com.woshidaniu.globalweb.action;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.global.GlobalJcsjlx;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.XwglModel;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.service.svcinterface.IXwglService;
import com.woshidaniu.util.xml.BaseDataReader;


/**
 * 
 *@类名称:XwglAction.java
 *@类描述：通知管理
 *@创建人：kangzhidong
 *@创建时间：2015-1-22 上午09:15:39
 *@版本号:v1.0
 */
public class XwglAction extends CommonBaseAction implements ModelDriven<XwglModel>{
	
	protected static final long serialVersionUID = 1L;
	 
	protected XwglModel model = new XwglModel();
	@Resource
	protected IXwglService service;
 
	public void setService(IXwglService service) {
		this.service = service;
	}

	public XwglModel getModel() {
		model.setUser(getUser());
		return model;
	}
	
	/**
	 * 发布对象、是否列表
	 */
	public void setValueStack(){
		ValueStack vs = getValueStack();
		List<HashMap<String, String>> fbdxList = BaseDataReader.getCachedOptionList("fbdx");
		vs.set("fbdxList", fbdxList);

		List<HashMap<String, String>> sfList = BaseDataReader.getCachedOptionList("isNot");
		vs.set("sfList", sfList);
	}
	
	
	/**
	 * 
	 *@描述：新闻查询
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:35
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXw() {
		try {
			if (QUERY.equals(model.getDoType())){
				//因为使用到缓存，这里必须先查询数据，再获取QueryModel对象
				List<XwglModel> list = getService().getPagedList(model);
				QueryModel queryModel = model.getQueryModel();
				//调用缓存查询
				queryModel.setItems(list);
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxXw";
	}
	

	/**
	 * 
	 *@描述：请求增加新闻页面
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:27
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjXw() {
		// 生成初始化数据
		model.setSffb("1");
		model.setSfzd("0");
		String currentDate = getCommonQueryService().getDatabaseTime();
		model.setFbsj(currentDate);
		getValueStack().set("currentDate", currentDate);
		setValueStack();
		return "zjXw";
	}

	/**
	 * 
	 *@描述：保存新增加闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjBcXw() {
		try {
			User user = getUser();
			model.setFbr(user.getYhm());
			getService().zjBcXw(model);
			getValueStack().set(DATA, getText("I99001"));
		}  catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：修改新闻页面
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgXw() {
		try {
			XwglModel model = getService().getModel(this.model);
			getValueStack().set("currentDate", getCommonQueryService().getDatabaseTime());
			PropertyUtils.copyProperties(this.model, model);
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "xgXw";
	}
	
	
	/**
	 * 
	 *@描述：保存修改新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcXw() {
		try {
			//model.setFbnr(model.getXwnr());
			model.setFbr(getUser().getYhm());
			getService().xgBcXw(model);
			getValueStack().set(DATA, getText("I99001"));
		}  catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：查看新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:52:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String ckXw() {
		try {
			//判断查看时，是否需要记录
			if("save".equals(model.getDoType())){
				User user = getUser();
				model.setXwbh(model.getXwbh());
				if(!BlankUtils.isBlank(user)&&!BlankUtils.isBlank(user.getYhm())){
					model.setYdr(user.getYhm());
					model.setYhm(user.getYhm());
				}
				getService().insertNewsLog(model);
			}
			XwglModel model = service.getModel(this.model);
			BeanUtils.copyProperties(this.model, model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "ckXw";
	}
	
	/**
	 * 
	 *@描述：删除新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:51:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	//@exception("E","N011001-03001")
	//@exception("R","N011001-03001")
	public String scXw() {
		try {
			String ids = getRequest().getParameter("ids");
			model.setXwbh(ids);
			boolean optAble = true;
			List<XwglModel> xwglList = getService().getModelList(model);
			for (XwglModel xwglModel : xwglList) {
				if("1".equals(xwglModel.getSffb())){
					optAble = false;
				}
				if(!optAble){
					break;
				}
			}
			if(optAble){
				model.setXwbh(ids);
				model.setYhm(getUser().getYhm());
				getService().scXw(model);
				getValueStack().set(DATA, getText("I99005"));
			}else{
				getValueStack().set(DATA, getText("W-N011001-03001"));
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}
	
	
	/**
	 * 
	 *@描述：发布新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:50:40
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String fbXw() {
		try {
			String ids = getRequest().getParameter("ids");
			model.setXwbh(ids);
			boolean optAble = true;
			List<XwglModel> xwglList = getService().getModelList(model);
			for (XwglModel xwglModel : xwglList) {
				if("1".equals(xwglModel.getSffb())){
					optAble = false;
				}
				if(!optAble){
					break;
				}
			}
			if(optAble){
				model.setYhm(getUser().getYhm());
				getService().xgFbxw(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"发布"}));
			}else{
				getValueStack().set(DATA, getText("W-N011001-03002"));
			}
		} catch (Exception e) {
			getValueStack().set(DATA, getText("I99008",new String[]{"发布"}));
		}
		return DATA;
	}
	
	
	
	/**
	 * 
	 *@描述：取消发布新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:49:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String qxfbXw() {
		try {
			String ids = getRequest().getParameter("ids");
			model.setXwbh(ids);
			boolean optAble = true;
			List<XwglModel> xwglList = getService().getModelList(model);
			for (XwglModel xwglModel : xwglList) {
				if("0".equals(xwglModel.getSffb())){
					optAble = false;
				}
				if(!optAble){
					break;
				}
			}
			if(optAble){
				model.setYhm(getUser().getYhm());
				getService().xgQxfbxw(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"取消发布"}));
			}else{
				getValueStack().set(DATA, getText("W-N011001-03003"));
			}
			
		} catch (Exception e) {
			getValueStack().set(DATA, getText("I99008",new String[]{"取消发布"}));
		}
		return DATA;
	}
	
	
	
	/**
	 * 
	 *@描述：新闻置顶
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:48:42
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zdXw() {
		try {
			String ids = getRequest().getParameter("ids");
			model.setXwbh(ids);
			boolean optAble = false;
			List<XwglModel> xwglList = getService().getModelList(model);
			for (XwglModel xwglModel : xwglList) {
				if("1".equals(xwglModel.getSfzd())){
					optAble = true;
				}
				if(!optAble){
					break;
				}
			}
			if(optAble){
				getValueStack().set(DATA, getText("W-N011001-03004"));
			}else{
				model.setYhm(getUser().getYhm());
				getService().xgZdxw(model); 
				getValueStack().set(DATA, getText("I99007", new String[]{"置顶"}));
			}
		} catch (Exception e) {
			getValueStack().set(DATA, getText("I99008", new String[]{"置顶"}));
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：取消置顶新闻
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:48:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String qxzdXw() {
		try {
			String ids = getRequest().getParameter("ids");
			model.setXwbh(ids);
			boolean optAble = false;
			List<XwglModel> xwglList = getService().getModelList(model);
			for (XwglModel xwglModel : xwglList) {
				if("1".equals(xwglModel.getSfzd())){
					optAble = true;
				}
				if(!optAble){
					break;
				}
			}
			if(optAble){
				model.setYhm(getUser().getYhm());
				getService().xgQxzdXw(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"取消置顶"}));
			}else{
				getValueStack().set(DATA, getText("W-N011001-03005"));
			}
		}catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"取消置顶"}));
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：查询面向学生页面
	 *@创建人:kangzhidong
	 *@创建时间:2014-8-6下午07:48:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxMxStudentIndex(){
		getValueStack().set("xqList", getCommonSqlService().queryAllXq());//校区
		getValueStack().set("xyList", getCommonSqlService().queryAllXy());//学院
		getValueStack().set("njList", getCommonSqlService().queryAllNj());//年级
		 
		getValueStack().set("xbList", getCommonSqlService().getJcsjList(GlobalJcsjlx.XB));//性别
		getValueStack().set("pyccList", getCommonSqlService().getJcsjList(GlobalJcsjlx.PYCC));//层次
		getValueStack().set("xslbList", getCommonSqlService().getJcsjList(GlobalJcsjlx.XSLBDM));//学生类别
		return "cxMxStudentIndex";
	}
	/**
	 * 
	 *@描述：查询学生信息
	 *@创建人:m
	 *@创建时间:2014-8-6下午07:48:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXsxx(){
		if(QUERY.equals(model.getDoType())){
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(getService().getPagedByScopeXsxx(model));
			getValueStack().set(DATA, queryModel);
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：查询学生信息
	 *@创建人:m
	 *@创建时间:2014-8-6下午07:48:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxMxteacherIndex(){
		getValueStack().set("xqList", getCommonSqlService().queryAllXq());//校区
		getValueStack().set("xyList", getCommonSqlService().queryAllXy());//学院
		getValueStack().set("xbList", getCommonSqlService().getJcsjList(GlobalJcsjlx.XB));//性别
		getValueStack().set("xnmList", getCommonSqlService().getXnMapList());//学年
		getValueStack().set("xqmList", getCommonSqlService().getXqList());//学期
		return "cxMxteacherIndex";
	}
	/**
	 * 
	 *@描述：查询教师信息
	 *@创建人:m
	 *@创建时间:2014-10-22 14.41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJsxx(){
		if(QUERY.equals(model.getDoType())){
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(getService().getPagedByScopeJsxx(model));
			getValueStack().set(DATA, queryModel);
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：查询岗位信息
	 *@创建人:m
	 *@创建时间:2014-8-6下午07:48:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxMxGwIndex(){
		getValueStack().set("jsList", getCommonSqlService().getJsMapList());//角色
		return "cxMxGwIndex";
	}
	/**
	 * 
	 *@描述：查询岗位信息
	 *@创建人:m
	 *@创建时间:2014-10-22 14.41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxGwxx(){
		if(QUERY.equals(model.getDoType())){
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(getService().getPagedByScopeGwxx(model));
			getValueStack().set(DATA, queryModel);
		}
		return DATA;
	}

	public String cxMoreXwList(){
		try {
			if (QUERY.equals(model.getDoType())) {
				// 因为使用到缓存，这里必须先查询数据，再获取QueryModel对象
				User user = getUser();
				model.setYhm(user.getYhm());
				model.setGglb("通知公告");
				if(user.isStudent()){
					model.setYhlx("1");
				}else if(user.isTeacher()){
					model.setYhlx("2");
				}else{
					model.setYhlx("3");
				}
				List<XwglModel> list = getService().cxXwList(model);
				QueryModel queryModel = model.getQueryModel();
				// 调用缓存查询
				queryModel.setItems(list);
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public IXwglService getService() {
		return service;
	}
	
	
}
