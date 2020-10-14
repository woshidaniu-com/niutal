package com.woshidaniu.service.utils;

import java.util.ArrayList;
import java.util.List;

import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.basicutils.BlankUtils;

public class YhglUtils {

	public static List<String> getNewList(String[] newArr,List<JsglModel> jsList){
		List<String> newList = new ArrayList<String>();
		if(!BlankUtils.isBlank(newArr)){
			boolean isAdd = false;
			//循环新提交的角色
			for (String jsdm : newArr) {
				//判断是否是在交集中已有此角色代码
				isAdd = false;
				for (JsglModel jsglModel : jsList) {
					if(jsglModel.getJsdm().equals(jsdm)){
						isAdd = true;
						break;
					}else{
						continue;
					}
				}
				//无则代表新增角色
				if(!isAdd){
					newList.add(jsdm);
				}
			}
		}
		return newList;
	}
	
	public static List<String> getDeleteList(String[] newArr,List<JsglModel> jsList){
		List<String> deleteList = new ArrayList<String>();
		if(!BlankUtils.isBlank(newArr)){
			boolean isRemove = true;
			for (JsglModel jsglModel : jsList) {
				isRemove = true;
				//循环新提交的角色
				for (String jsdm : newArr) {
					if(jsglModel.getJsdm().equals(jsdm)){
						isRemove = false;
						break;
					}else{
						continue;
					}
				}
				//无则代表删除角色
				if(isRemove){
					deleteList.add(jsglModel.getJsdm());
				}
			}
		}
		return deleteList;
	}
	
}
