/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 CjwtAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午2:01:41
 * @功能描述 在线咨询-常见问题
 * 
 */
@Controller("zxzxCjwtAction")
@Scope("prototype")
public class CjwtAction extends BaseAction implements ModelDriven<CjwtModel>{

	private static final long serialVersionUID = 5080950125617209478L;

	private CjwtModel model = new CjwtModel();
	
	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService service;
	
	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;
	
	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService zxwtService;
	
	/**
	 * 查询
	 * @return
	 */
	public String cxCjwt(){
		ValueStack valueStack = getValueStack();
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<CjwtModel> pagedList = service.getPagedList(model);
				queryModel.setItems(pagedList);
				valueStack.set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		kzdkModel kzdkModel = new kzdkModel();
		kzdkModel.setSfqy("1");
		List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
		valueStack.set("bkdmList", bkdmlList);
		return SUCCESS;
	}
	
	/**
	 * 增加
	 * @return
	 */
	public String zjCjwt(){
		ValueStack valueStack = getValueStack();
		kzdkModel kzdkModel = new kzdkModel();
		kzdkModel.setSfqy("1");
		List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
		valueStack.set("bkdmList", bkdmlList);
		return SUCCESS;
	}
	
	/**
	 * 增加-保存
	 * @return
	 */
	public String zjBcCjwt(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			//检查重复
			CjwtModel queryModel = new CjwtModel();
			queryModel.setWtbt(model.getWtbt());
			int count = service.getCount(queryModel);
			if(count == 0){
				model.setWtid(UniqID.getInstance().getUniqIDHash());
				model.setCjsj(DateUtils.format(new Date()));
				updateResult = service.insert(model);
				message = getText(updateResult ? "I99001" : "I99002");
			}else{
				message = getText("I99015", new String[]{model.getWtbt()});
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
	public String xgCjwt(){
		CjwtModel queryModel = service.getModel(model.getWtid());
		if(queryModel != null){
			model.setWtid(queryModel.getWtid());
			model.setWtbt(queryModel.getWtbt());
			model.setBkdm(queryModel.getBkdm());
			model.setSffb(queryModel.getSffb());
			model.setWtnr(queryModel.getWtnr());
			model.setWthf(queryModel.getWthf());
			model.setCjsj(queryModel.getCjsj());
			ValueStack valueStack = getValueStack();
			kzdkModel kzdkModel = new kzdkModel();
			kzdkModel.setSfqy("1");
			List<kzdkModel> bkdmlList = kzdkService.getModelList(kzdkModel);
			valueStack.set("bkdmList", bkdmlList);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改-保存
	 * @return
	 */
	public String xgBcCjwt(){
		String message = "";
		try{
			boolean updateResult = Boolean.FALSE;
			//检查重复
			CjwtModel queryModel = new CjwtModel();
			queryModel.setWtid(model.getWtid());
			queryModel.setWtbt(model.getWtbt());
			int count = service.getCount(queryModel);
			if(count == 0){
				updateResult = service.update(model);
				message = getText(updateResult ? "I99003" : "I99004");
			}else{
				message = getText("I99015", new String[]{model.getWtbt()});
			}
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
	public String scBcCjwt(){
		String message = "";
		try {
			if(model.getWtid() != null){
				String[] wtids = model.getWtid().split(",");
				List<String> wtidList = new ArrayList<String>();
				for (String wtid : wtids) {
					wtidList.add(wtid);
				}
				service.batchDelete(wtidList);
				message = getText("I99005");
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 启用/停用
	 * @return
	 */
	public String qytyCjwt(){
		CjwtModel queryModel = service.getModel(model.getWtid());
		if(queryModel != null){
			model.setWtid(queryModel.getWtid());
			model.setWtbt(queryModel.getWtbt());
			model.setSffb(queryModel.getSffb());
		}
		return SUCCESS;
	}
	
	/**
	 * 启用/停用 保存
	 * @return
	 */
	public String qytyBcCjwt(){
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
	
	@Override
	public CjwtModel getModel() {
		return model;
	}
	
}
