package com.woshidaniu.ws.utils;

import org.jdom2.Document;
import org.jdom2.Element;

import com.woshidaniu.common.exception.BusinessRuntimeException;
import com.woshidaniu.util.base.BlankUtil;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.xml.JDomUtils;

public final class StaffUnifiedUtils {

	/**
	 * 
	 *@描述：解析返回的xml，获取guid
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-1上午11:11:18
	 *@param xmlString
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String getGUID(String xmlString){
		if(!BlankUtil.isBlank(xmlString)){
			//解析返回的xml字符串
			Document document = JDomUtils.buildXML(xmlString);
			//得到根节点
			Element rootElement = document.getRootElement();
			//尝试获取失败节点
			Element error_element = rootElement.getChild("error_code");
			//判断异常节点元素是否不为空获：则抛出异常
			if(null!=error_element){
				throw new BusinessRuntimeException(error_element.getTextTrim() + ":"+MessageUtil.getWsText(error_element.getTextTrim()));
			}else{
				/*//尝试获取成功节点
				Element success_element = rootElement.getChild("success");
				
				if(null != success_element){
					//获得成功标记节点提示信息key
					String nodeText = MessageUtil.getWsText(success_element.getTextTrim());
				}*/
				
				//尝试获取guid节点
				Element guid_element = rootElement.getChild("guid");
				
				return guid_element.getTextTrim();
			}
		}
		return null;
	}
	
}
