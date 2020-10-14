/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;

/**
 * @类名 kzdkAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 下午4:54:52
 * @功能描述 在线咨询-板块信息
 * 
 */
@Controller("zxkzdkxxAction")
@Scope("prototype")
public class kzdkAction extends BaseAction implements ModelDriven<kzdkModel>{

	private static final long serialVersionUID = 2754034767127933132L;
	
	private kzdkModel model = new kzdkModel();
	
	private Map<String,String> postData = new HashMap<String, String>();
	
	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService service;

	/**
	 * 查询
	 * @return
	 */
	public String cxkzdk(){
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<kzdkModel> pagedList = service.getPagedList(model);
				queryModel.setItems(pagedList);
				getValueStack().set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		return SUCCESS;
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String zjkzdk(){
		return SUCCESS;
	}
	
	/**
	 * 增加-保存
	 * @return
	 */
	public String zjBckzdk(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			//检查重复
			kzdkModel queryModel = new kzdkModel();
			queryModel.setBkmc(model.getBkmc());
			List<kzdkModel> isExist = service.getModelList(queryModel);
			if(isExist.size() == 0){
				model.setBkdm(UniqID.getInstance().getUniqIDHash());
				Integer maxXsxs = service.getMaxXsxs();
				model.setXsxs(((maxXsxs==null ? 0 : maxXsxs) +1)+"");
				updateResult = service.insert(model);
				message = getText(updateResult ? "I99001" : "I99002");
			}else{
				message = getText("I99015", new String[]{model.getBkmc()});
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String xgkzdk(){
		kzdkModel queryModel = service.getModel(model.getBkdm());
		if(queryModel != null){
			model.setBkdm(queryModel.getBkdm());
			model.setBkmc(queryModel.getBkmc());
			model.setSfqy(queryModel.getSfqy());
			model.setXsxs(queryModel.getXsxs());
		}
		return SUCCESS;
	}
	/**
	 * 修改-保存
	 * @return
	 */
	public String xgBckzdk(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			//检查重复
			kzdkModel queryModel = new kzdkModel();
			queryModel.setBkmc(model.getBkmc());
			queryModel.setBkdm(model.getBkdm());
			int count = service.getCount(queryModel);
			if(count == 0){
				updateResult = service.update(model);
				message = getText(updateResult ? "I99003" : "I99004");
			}else{
				message = getText("I99015", new String[]{model.getBkmc()});
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 启用停用
	 * @return
	 */
	public String qytykzdk(){
		return this.xgkzdk();
	}
	
	/**
	 * 启用停用-保存
	 * @return
	 */
	public String qytyBckzdk(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			updateResult = service.update(model);
			message = getText(updateResult ? "I99003" : "I99004");
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	/**
	 * 删除-保存
	 * @return
	 */
	public String scBckzdk(){
		String message = "";
		//需要先判断是否存在于【教师-板块表信息】
		//需要先判断是否存在于【常见咨询问题表】
		try {
			if(model.getBkdm() != null){
				String[] bkdms = model.getBkdm().split(",");
				List<String> bkdmList = new ArrayList<String>();
				for (String bkdm : bkdms) {
					int checkCanDelete = service.checkCanDelete(bkdm);
					if(checkCanDelete == 0){
						bkdmList.add(bkdm);
					}
				}
				
				boolean batchDelete = Boolean.FALSE;
				if(bkdmList.size() > 0){
					batchDelete = service.batchDelete(bkdmList);
					message = getText(batchDelete ? "I99005" : "I99006");
				}else{
					message = getText("ZXZX_I014");
				}
				
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 顺序设置
	 * @return
	 */
	public String sxszkzdk(){
		//只查询启用的咨询板块
		kzdkModel queryModel = new kzdkModel();
		queryModel.setSfqy("1");
		List<kzdkModel> modelList = service.getModelList(queryModel);
		getValueStack().set("kzdkList", modelList);
		return SUCCESS;
	}
	
	/**
	 * 顺序设置-保存
	 * @return
	 */
	public String sxszBckzdk(){
		String message = null;
		try{
			if(postData.size() > 0){
				boolean updateResult = service.kzdkXssz(postData);
				message = getText(updateResult ? "I99001" : "I99002");
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	public IkzdkService getService() {
		return service;
	}

	public void setService(IkzdkService service) {
		this.service = service;
	}


	@Override
	public kzdkModel getModel() {
		return model;
	}

	public Map<String, String> getPostData() {
		return postData;
	}

	public void setPostData(Map<String, String> postData) {
		this.postData = postData;
	}
	
	
}
