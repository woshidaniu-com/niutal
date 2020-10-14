package com.woshidaniu.globalweb.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 类名称：文本编辑器
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-10-25
 */
@Controller
@Scope("prototype")
public class KindEditorAction {
 
    
 public String uploadJson(){
	 return "uploadJson";
 }
    
 public String fileManagerJson(){
	 return "fileManagerJson";
 }
    
}
