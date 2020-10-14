package com.woshidaniu.plugins.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.dao.entities.BigComboSelectModel;
import com.woshidaniu.service.svcinterface.IBigComboSelectService;
import com.woshidaniu.util.xml.BaseDataReader;


@Controller
@Scope("prototype")
public class BigComboSelectAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2529287998172616175L;
	
	@Autowired
	private IBigComboSelectService bigComboSelectService;
	
	public String loadNormalTypePage(){
		
		return "loadNormalTypePage";
	}
	
	public String loadXzqTypePage(){
		
		return "loadXzqTypePage";
	}
	
	/**
	 * 加载学院
	 * @return
	 */
	public String getXyDataList(){
		try {
			HttpServletRequest request = getRequest();
			ValueStack vs=getValueStack();
			String initialFlag = request.getParameter("initialFlag");
			String initial = request.getParameter("initial");
			List<BigComboSelectModel> initialList = null;
			List<BigComboSelectModel> returnList = new ArrayList<BigComboSelectModel>(); 
			Map dataMap = new HashMap();
			if("true".equals(initialFlag)){
				initialList = bigComboSelectService.getAllXYInitial();
				List<String> sb = new ArrayList<String>();
				for(BigComboSelectModel bcs : initialList){
					sb.add(bcs.getInitialname());
				}
				dataMap.put("initial", StringUtils.join(sb,","));
			}
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
			if("true".equals(initialFlag)){
				if(initialList != null && initialList.size() > 0){
					String firstInitial = initialList.get(0).getInitialname();
					returnList = bigComboSelectService.getXYDataByInitial(firstInitial);
				}
			}else{
				returnList = bigComboSelectService.getXYDataByInitial(initial);
			}
			dataMap.put("data", returnList);
			vs.set(DATA, dataMap);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 加载数据
	 * @return
	 */
	public String getBigComboDataList(){
		try {
			HttpServletRequest request = getRequest();
			ValueStack vs=getValueStack();
			String initialFlag = request.getParameter("initialFlag");
			String hasInitial = request.getParameter("hasInitial");
			String initial = request.getParameter("initial");
			String bigComboId = request.getParameter("bigComboId");
			String parentName = request.getParameter("parentName");
			String parentId = request.getParameter("parentId");
			String secondparentName = request.getParameter("secondparentName");
			String secondParentId = request.getParameter("secondParentId");
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			HashMap<String,String> optionMap = BaseDataReader.getBigComboSelect(bigComboId);
			
			List<BigComboSelectModel> initialList = null;
			List<BigComboSelectModel> returnList = new ArrayList<BigComboSelectModel>();
			
			if("true".equals(hasInitial)){
				if("true".equals(initialFlag)){
					initialList = bigComboSelectService.getBigComboInitial(optionMap.get("name"), 
							optionMap.get("table"),parentName,parentId,secondparentName,secondParentId);
					List<String> sb = new ArrayList<String>();
					for(BigComboSelectModel bcs : initialList){
						sb.add(bcs.getInitialname());
					}
					dataMap.put("initial", StringUtils.join(sb,","));
				}
				if("true".equals(initialFlag)){
					if(initialList != null && initialList.size() > 0){
						String firstInitial = initialList.get(0).getInitialname();
						returnList = bigComboSelectService.getBigComboDataByInitial(optionMap.get("id"), optionMap.get("name"), optionMap.get("table"), firstInitial,parentName,parentId,secondparentName,secondParentId);
					}
				}else{
					returnList = bigComboSelectService.getBigComboDataByInitial(optionMap.get("id"), optionMap.get("name"), optionMap.get("table"), initial,parentName,parentId,secondparentName,secondParentId);
				}
			}else{
				returnList = bigComboSelectService.getBigComboDataWithoutInitial(optionMap.get("id"), optionMap.get("name"), optionMap.get("table"),parentName,parentId,secondparentName,secondParentId);
			}
			
			dataMap.put(DATA, returnList);
			vs.set(DATA, dataMap);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 加载行政区
	 * @return
	 */
	public String getXzqBigComboDataList(){
		try {
			ValueStack vs=getValueStack();
			List<BigComboSelectModel> returnList = null;
			HttpServletRequest request = getRequest();
			String searchType = request.getParameter("searchType");
			String parentId = request.getParameter("parentId");
			if(searchType.equals("sheng")){
				returnList = bigComboSelectService.getShengList();
			}else if(searchType.equals("shi")){
				returnList = bigComboSelectService.getShiList(parentId);
			}else if(searchType.equals("xian")){
				returnList = bigComboSelectService.getXianList(parentId);
			}
			vs.set(DATA, returnList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public IBigComboSelectService getBigComboSelectService() {
		return bigComboSelectService;
	}

	public void setBigComboSelectService(
			IBigComboSelectService bigComboSelectService) {
		this.bigComboSelectService = bigComboSelectService;
	}

}
