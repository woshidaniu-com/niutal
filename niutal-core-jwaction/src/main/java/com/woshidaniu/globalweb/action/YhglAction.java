package com.woshidaniu.globalweb.action;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.annotation.MethodAccess;
import com.woshidaniu.common.annotation.RefererAccess;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.encrypt.algorithm.MD5Codec;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称:YhglAction.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2014-12-18 下午07:14:11
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
@RefererAccess
public class YhglAction extends CommonBaseAction implements ModelDriven<YhglModel> {

	protected YhglModel model = new YhglModel();

	private JsglModel jsglmodel = new JsglModel();
	@Resource
	private IYhglService yhglService;
	@Resource
	private IJsglService jsglService;
	
	/**
	 *查询类型表
	 * 
	 * @throws Exception
	 */
	public void setValueStack() throws Exception {
		ValueStack vs = getValueStack();

		// 查询角色列表
		model.setYhm(getUser().getYhm());
		model.setJsgybj("1");
		List<JsglModel> jsxxList = getYhglService().cxJsdmList(model);
		getValueStack().set("jsxxList", jsxxList);

		int size = jsxxList.size();
		vs.set("col", size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)): 1);
		
		getValueStack().set("jgxxList", getCommonSqlService().queryAllJgxx());
		// 岗位级别列表
		//vs.set("gwjbList", jsglService.cxGwjbList());
	}
	
	/**
	 * 查询机构代码类型库
	 */
	public void setJgdmStack() throws Exception{
		ValueStack vs = getValueStack();
		//查询部门代码列表
		List<BmdmModel> jgdmsList = getCommonSqlService().getAllJgxxList();
		vs.set("jgdmsList", jgdmsList);
	}

	/**
	 * 
	 *@描述：用户信息查询
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:06:34
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	@MethodAccess("cx")
	public String cxYhxx() {
		try {
			model.setJgh_id(getUser().getYhlybid());
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				//queryModel.setCountSqlId("getPagedCount");
				queryModel.setItems(getYhglService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：增加用户信息 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:05:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjYhxx() {
		try {
			model.setSfqy("1");
			getValueStack().set("isNot", BaseDataReader.getCachedBaseDataList("isNot"));
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存增加用户信息 参数
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:05:48
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String zjBcYhxx() {
		try {
			// 密码加密
			model.setKl(MD5Codec.encrypt(model.getKl()));
			getYhglService().zjYhxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：跳转至修改用户信息 页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午02:54:05
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgYhxx() {
		try {
			model.setYhm(URLDecoder.decode(model.getYhm(),"UTF-8"));
			// 查询单个用户信息
			YhglModel yhglModel = getYhglService().getModel(model);
			setValueStack();
			setJgdmStack();
			getValueStack().set("model", yhglModel);
			getValueStack().set("isNot", BaseDataReader.getCachedBaseDataList("isNot"));
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：保存修改后的用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午02:54:24
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcYhxx() {
		try {
			//当前操作的操作人
			model.setXgr(getUser().getYhm());
			// 修改用户信息
			getYhglService().xgYhxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：删除用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:06:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String scYhxx() throws Exception {
		try {
			String pks = getRequest().getParameter("ids");
			model.setPkValue(pks);
			getYhglService().scYhxx(model);
			getValueStack().set(DATA, getText("I99005"));
		} catch (Exception e) {
			getValueStack().set(DATA, getText("I99006"));
			logStackException(e);
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：查看用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:06:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String ckYhxx() {
		try {
			ValueStack vs = getValueStack();
			model.setYhm(URLDecoder.decode(model.getYhm(),"UTF-8"));
			YhglModel yhglModel = new YhglModel();
			// 查询单个用户信息
			yhglModel = getYhglService().getModel(model);

			try {
				BeanUtils.copyProperties(model, yhglModel);
			} catch (Exception e) {
				logStackException(e);
			}
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}
	/**
	 * 
	 *@描述：根据用户名查询用户已有角色
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-23下午02:07:55
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String ckYhjsxx() {
		try {
			model.setYhm(URLDecoder.decode(model.getYhm(),"UTF-8"));
			//查询被操作用户的已有角色信息
			List<JsglModel> jsList= getJsglService().cxJsxxListByYhm(model.getYhm());
			getValueStack().set("jsList", jsList);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：设置所属角色 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午04:06:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String szssjsYh() throws Exception {
		try {
			//查询被操作用户的已有角色信息
			model.setYhm(URLDecoder.decode(model.getYhm(),"UTF-8"));
			List<JsglModel> jsList= getJsglService().cxJsxxListByYhm(model.getYhm());
			getValueStack().set("jsList", jsList);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "szSsjs";
	}

	/**
	 * 
	 * @description: 查询角色信息和用户与角色相关的数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-6-4
	 * @time : 上午11:59:18 
	 * @return
	 * @throws Exception
	 */
	public String cxYhjs() throws Exception {
		try {
			//查询属于当前登录用户的角色列表 
			model.setYhm(getUser().getYhm());
			List<JsglModel> jsxxList = getYhglService().getTreeGridJsdmList(model);
			for (JsglModel jsglModel : jsxxList) {
				jsglModel.setExpanded(true);
			}
			QueryModel queryModel = model.getQueryModel();
			queryModel.setItems(jsxxList);
		} catch (Exception e) {
			logException(e);
		}
		getValueStack().set(DATA, model.getQueryModel());
		return DATA;
	}
	
	/**
	 * 
	 *@描述：保存用户设置所属角色 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午02:23:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String szssjsSaveYh() throws Exception {
		try {
			//当前操作的操作人
			model.setXgr(getUser().getYhm());
			// 修改用户所属角色
			getYhglService().szSsjs(model);			
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：密码初始化 参数
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-12下午02:23:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String mmcsh() throws Exception {
		try {
			model.setPkValue(URLDecoder.decode(model.getPkValue(),"UTF-8"));
			String psw = MessageUtil.getText("system.init.psw","888888");
			model.setKl(MD5Codec.encrypt(psw));
			getYhglService().mmCsh(model);
			getValueStack().set(DATA, getText("I99010", new String[]{psw}));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99010"));
		}
		return DATA;
	}

	
	/**
	 * 密码修改  20140724马俊。如果不用删除
	 * 
	 * @return
	 * @throws Exception
	 
	public String xgMmQz() throws Exception {
		User user = getUser();
		model.setYhm(user.getYhm());
		
		if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
			// 查询单个用户信息
			YhglModel yhglModel = getYhglService().getModel(model);

			ValueStack vs = getValueStack();
			if (!Encrypt.encrypt(model.getYmm()).equals(yhglModel.getKl())) {
				vs.set("message", "原密码错误，请重新输入！");
				vs.set("ok", "false");
				return SUCCESS;
			}
			boolean boo = getYhglService().xgMm(model);
			if (boo) {
				//修改密码成功 重新设置session中的yhmmdj(用户密码等级)
				SessionFactory.getUser().setYhmmdj(model.getYhmmdj());
				
				vs.set("message", "修改成功，请重新登录！");
				vs.set("ok", "true");

				 
			} else {
				vs.set("message", "修改失败！");
				vs.set("ok", "false");
			}
		}
		return SUCCESS;
	}*/

	/**
	 * 
	 * 方法描述: 根据角色查看所分配的用户 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String ckJsyh() {
		try {
			ValueStack vs = getValueStack();
			JsglModel jsglModel = new JsglModel();

			jsglModel.setJsdm(getRequest().getParameter("jsdm"));

			// 根据角色代码得到角色名称
			jsglModel = jsglService.cxJsmcByJsdm(jsglModel);

			// 根据角色代码查询所属用户
			List<YhglModel> yhList = getYhglService().cxYhByJsdm(model);
			vs.set("yhList", yhList);

			int size = yhList.size();
			vs.set("col",
					size > 4 ? (size % 4 == 0 ? size / 4 : (size / 4 + 1)) : 1);

			BeanUtils.copyProperties(model, jsglModel);
			vs.set("model", model);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}
	
	/**
	 * 方法描述: 批量数据授权 
	 * 参数 @return 参数说明 
	 * 返回类型 String 返回类型
	 * @throws
	 */
	public String cxPlsjsq(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getYhglService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			//查询所有角色代码
			setValueStack();
			//查询所有机构
			setJgdmStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 方法描述: 验证职工号是否已经存在 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String valideZgh() throws Exception {
		YhglModel yhglModel = new YhglModel();
		yhglModel.setYhm(getRequest().getParameter("pkValue"));
		// 查询单个用户信息
		yhglModel = getYhglService().getModel(yhglModel);

		if (null != yhglModel) {
			getValueStack().set("data", "该职工号已经存在!");
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：查询输入的原密码是否正确
	 *@创建人:majun
	 *@创建时间:2014-7-23下午07:22:18
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxCheckYhMm(){
		ValueStack vs= getValueStack();
		boolean result = false;
		try{
			//这个值必须是session中获取，防止页面篡改以便修改他人密码
			model.setYhm(getUser().getYhm());
			result = getYhglService().checkYhMm(model.getYhm(), model.getKl());
		} catch (Exception e) {
			logStackException(e);
		}finally{
			vs.set(DATA, result);
		}
		return DATA;
	}
	
	/***
	 * 
	 *@描述：首页修改密码 
	 *@创建人:majun
	 *@创建时间:2014-7-23下午06:56:19
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 *@throws Exception
	 */
	public String xgMm() throws Exception {
		if (OPER_SAVE.equalsIgnoreCase(getRequest().getParameter("doType"))) {
			try {
				//这个值必须是session中获取，防止页面篡改以便修改他人密码
				model.setYhm(getUser().getYhm());
				getYhglService().xgMm(model);
				getValueStack().set(DATA, getText("I99003") + "请重新登录!");
			} catch (Exception e) {
				logStackException(e);
				getValueStack().set(DATA, getText("I99004"));
			}
			return DATA;
		}
		return SUCCESS;
	}
	
	
	public YhglModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public JsglModel getJsglmodel() {
		return jsglmodel;
	}

	public void setModel(YhglModel model) {
		this.model = model;
	}

	public void setJsglmodel(JsglModel jsglmodel) {
		this.jsglmodel = jsglmodel;
	}
	
	
}
