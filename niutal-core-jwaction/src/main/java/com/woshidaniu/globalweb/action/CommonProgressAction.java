package com.woshidaniu.globalweb.action;

import java.math.BigDecimal;

import com.woshidaniu.basicutils.BlankUtils;

/**
 * 
 *@类名称: CommonProgressAction.java
 *@类描述：通用进度计算Action
 *@创建人：kangzhidong
 *@创建时间：Sep 17, 2015 5:30:12 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class CommonProgressAction extends CommonBaseAction {

	/**
	 * 
	 *@描述：查询处理进度
	 *@创建人:kangzhidong
	 *@创建时间:Sep 17, 20155:31:02 PM
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxProgressStatus(){
		String processed_key = getRequest().getParameter("key");
		Object processed_value = getSession().getAttribute(processed_key);
		if(BlankUtils.isBlank(processed_value)){
			getValueStack().set(DATA, -1);
		}else{
			float percentage = Float.parseFloat(processed_value.toString());
			if(Math.round(percentage) >= 100){
				//此次成绩更新处理完成，移除标识
				getSession().removeAttribute(processed_key);
			}
			getValueStack().set(DATA,Math.round(BigDecimal.valueOf(percentage).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()));
		}
		return DATA;
	}

	
}

