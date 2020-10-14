package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IWdyyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

@Controller
@Scope("prototype")
public class IndexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1701172969179269969L;
	@Autowired
	private IWdyyService wdyyService;
	@Autowired
	private IJsglService jsglService;
	
	private String url;
	
	private String curmkdm;
	
	/**
	 * 
	 * <p>方法说明：初始化首页<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月19日下午4:20:35<p>
	 * @return
	 */
	public String initMenu() {
		
        //密码强度验证  强度小于等于1,则为弱密码,跳转首页
//        if(StringUtil.isEmpty(user.getYhmmdj()) || Integer.valueOf(user.getYhmmdj()) <= 1){
//    		return "indexPage";
//        }
		try {
			ValueStack vs = getValueStack();
			User user= getUser();
			String jsdm = user.getJsdm();
			List<HashMap<String, String>> topMenuList = null;
//			if (User.UserType.STUDENT.toString().toLowerCase().equals(jsdm)){
//				//学生角色
//				topMenuList = service.getStudentTopMenuList();
//			} else {
//				//非学生角色
//				topMenuList = service.getTeacherTopMenuList(jsdm);
//			}
			vs.set("topMenuList", topMenuList);
			
			if (user.getJsdms() != null && user.getJsdms().size() > 1){
				List<JsglModel> cxJsxxZgh = jsglService.getJsxxListByZgh(user.getYhm());
				vs.set("jsxxList", cxJsxxZgh);
			}
			return User.UserType.STUDENT.toString().equalsIgnoreCase(jsdm) ? "stupage" : "teapage";
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
	/**
	 * 
	 * <p>方法说明：用户功能页面<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年8月19日下午4:19:41<p>
	 * @return
	 */
	public String cxGnPage(){
//			try {
//				AncdModel ancdModel  = new AncdModel();
//				List<AncdModel> list = new ArrayList<AncdModel>();
//				//功能代码为空，跳入错误页面
//				if(StringUtils.isBlank(model.getGnmkdm())){
//					return "pageError";
//				}
//				String dyym = model.getDyym();
//				ancdModel.setDyym(dyym);
//				ancdModel.setGnmkdm(model.getGnmkdm());
//				ancdModel.setGnmkmc(URLDecoder.decode(model.getGnmkmc(), "utf-8"));
//				list.add(ancdModel);
//				getValueStack().set("gnList", list);
//			} catch (Exception e) {
//				logException(e);
//				return ERROR;
//			}
			return "gnPage";
		}
	
	
	
	
	

	public String center(){
		return "center";
	}
	
	public String sessionOut(){
	    return Action.LOGIN;
	}
	




	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public IWdyyService getWdyyService() {
		return wdyyService;
	}


	public void setWdyyService(IWdyyService wdyyService) {
		this.wdyyService = wdyyService;
	}

	public String getCurmkdm() {
		return curmkdm;
	}

	public void setCurmkdm(String curmkdm) {
		this.curmkdm = curmkdm;
	}
 

}
