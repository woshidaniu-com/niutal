package com.woshidaniu.tjcx.action;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 快照设置action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class KzszAction extends BaseAction implements ModelDriven<KzszModel> {

	private static final long serialVersionUID = 1L;
	private String format = "yyyy-MM-dd HH:mm:ss";
	private IKzszService service;
	private KzszModel model = new KzszModel();
	private String xmdm = null;
	private String gltj = null;
	private String bbhxl = null;
	private String bbzxl = null;
	private String tsx = null;
	private String oper = null;

	public String kzsz() {
		return "kzsz";
	}

	public String kzszUpdate(){
		KzszModel modelInstance = service.getModel(model);	
		try {
			BeanUtils.copyProperties(model, modelInstance);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "kzszUpdate";
	}
	
	public String saveUpdate(){
		//User user = SessionFactory.getUser();
		User user = getUser();
		KzszModel model2 = service.getModel(model.getKzszid());
		model2.setCzy(user.getYhm());
		model2.setTjsj(DateUtils.format(format));
		model2.setSzmc(model.getSzmc());
		String sfgy = model.getSfgy();
		model2.setSfgy(sfgy);
		if(sfgy != null && sfgy.equals("1")){
			model2.setKzjsr(model.getKzjsr());
		}else{
			model2.setKzjsr("");
		}
		boolean result = service.update(model2);
		String key = result ? "I99001" : "I99002";
		getValueStack().set("result", getText(key));
		
		return "kzszUpdate";
	}
	
	public String kzszDelete(){
		String ids = getRequest().getParameter("ids");
		boolean result = false;
		try{
			if(StringUtils.isNotBlank(ids)){
				String[] split = StringUtils.split(ids, ",");
				for (String string : split) {
					model.setKzszid(string);
					result = service.deleteCxkz(model);
				}
			}
		}catch(Exception e){
			logException(e);
		}
		
		String key = result ? "I99005" : "I99006";
		getValueStack().set(DATA, getText(key));
		return DATA;
	}
	
	public String kzcx() {
		try {
			//User user = SessionFactory.getUser();
			User user = getUser();
			model.setCzy(user.getYhm());
			QueryModel queryModel = model.getQueryModel();
			List<KzszModel> list = service.getPagedList(model);
			queryModel.setItems(list);
			getValueStack().set(DATA, queryModel);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	/**
	 * 快照修改
	 * 
	 * @return
	 */
	public String kzxg() {
		return "kzxg";
	}

	/**
	 * 快照详情
	 * 
	 * @return
	 */
	public String kzxq() {
		String gnmk = "tjcxError";		
		if(model.getKzszid() != null){
			model = service.getModel(model);
			if(model != null){
				String kzlx = model.getKzlx();
				String bbhxl = model.getBbhxl();
				if (kzlx != null && kzlx.equals("2") || bbhxl != null
						&& !bbhxl.trim().equals("")) {
					gnmk = "tjbb";
				} else {
					gnmk = "tjcx";
				}
			}
		}		
		return gnmk;
	}

	public String save() {
		try {
			// kzszModel.setKzszid("4");
			//User user = SessionFactory.getUser();
			User user = getUser();
			model.setCzy(user.getYhm());
			model.setTjsj(DateUtils.format(format));

			// kzszModel.setXmdm("xsxx");
			// kzszModel.setSzmc("年级为2012男");
			// kzszModel.setGltj("nj='2012' and xb='1'");
			// kzszModel.setBbhxl("xy");
			// kzszModel.setBbzxl("xb");
			// kzszModel.setTsx("zs");
			service.insert(model);
			getValueStack().set(DATA, model.getKzszid());
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public String operation() {
		try {
			if (oper != null) {
				if (oper.equals("del")) {
					String id = getRequest().getParameter("id");
					model.setKzszid(id);
					boolean result = service.deleteCxkz(model);
					getValueStack().set(DATA, result);
				} else if (oper.equals("edit")) {
					//User user = SessionFactory.getUser();
					User user = getUser();
					KzszModel model2 = service.getModel(model.getKzszid());
					model2.setCzy(user.getYhm());
					model2
							.setTjsj(DateUtils.format(format));
					model2.setSzmc(model.getSzmc());
					String sfgy = model.getSfgy();
					model2.setSfgy(sfgy);
					if(sfgy != null && sfgy.equals("1")){
						model2.setKzjsr(model.getKzjsr());
					}
					boolean result = service.update(model2);
					getValueStack().set(DATA, result);
				}
			}

		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public String delete() {
		try {
			// kzszModel.setKzszid("4");

			// kzszModel.setXmdm("xsxx");
			// kzszModel.setSzmc("年级为2012男");
			// kzszModel.setGltj("nj='2012' and xb='1'");
			// kzszModel.setBbhxl("xy");
			// kzszModel.setBbzxl("xb");
			// kzszModel.setTsx("zs");

			boolean result = service.deleteCxkz(model);
			getValueStack().set(DATA, result);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public IKzszService getService() {
		return service;
	}

	public void setService(IKzszService service) {
		this.service = service;
	}

	public KzszModel getModel() {
		return model;
	}

	public void setModel(KzszModel model) {
		this.model = model;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getGltj() {
		return gltj;
	}

	public void setGltj(String gltj) {
		this.gltj = gltj;
	}

	public String getBbhxl() {
		return bbhxl;
	}

	public void setBbhxl(String bbhxl) {
		this.bbhxl = bbhxl;
	}

	public String getBbzxl() {
		return bbzxl;
	}

	public void setBbzxl(String bbzxl) {
		this.bbzxl = bbzxl;
	}

	public String getTsx() {
		return tsx;
	}

	public void setTsx(String tsx) {
		this.tsx = tsx;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

}
