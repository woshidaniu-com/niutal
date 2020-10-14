<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="org.owasp.encoder.Encode"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String stylePath = MessageUtil.getText("system.stylePath");
	String systemPath = request.getContextPath();
	String resourceVersion = MessageUtil.getText("niutal_resource_version");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	Integer license_status = (Integer)pageContext.getServletContext().getAttribute("SERVLET_CONTEXT_LICENSE_CHECK_STATUS");
%>	
<title><%=MessageUtil.getText("system.title") %></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Copyright" content="woshidaniu" />

<script type="text/javascript">
	if(!window._loaded_resource){
		window._loaded_resource = {};
	}
	var _path = "<%=systemPath %>";
	var _stylePath = "<%=stylePath %>";
	var _rv = "<%=resourceVersion %>";
	var _license_status = "<%=license_status %>";
	/**同步加载脚本文件，并防止重复加载*/
	function loadJs(id,url){
		if(window._loaded_resource[id]){return;}
		var scriptObj=document.getElementById(id);
		if(scriptObj != null && scriptObj != "undefined"){return false;}
	    var xmlHttp = null;
	    if(window.ActiveXObject){
	        try {xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");}
	        catch (e) {xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");}
	    }
	    else if(window.XMLHttpRequest){xmlHttp = new XMLHttpRequest();}
	    xmlHttp.open("GET",url,false);
	    xmlHttp.send(null);
	    if (xmlHttp.readyState == 4){
	        if((xmlHttp.status >= 200 && xmlHttp.status <300) || xmlHttp.status == 0 || xmlHttp.status == 304){
	            var myHead = document.getElementsByTagName("HEAD").item(0);
	            var myScript = null;
            	myScript = document.createElement( "script" );
	            myScript.language = "javascript";
	            myScript.type = "text/javascript";
	            myScript.id = id;
	            try{
	                myScript.appendChild(document.createTextNode(xmlHttp.responseText));
	            }catch (ex){
	                myScript.text = xmlHttp.responseText;
	            }
	            myHead.appendChild( myScript );
	            window._loaded_resource[id] = url;
	            return true;
	        }
	        else{return false;}
	    }else{return false;}
	}

	/**加载header.js*/
	/**默认加载jquery*/
	loadJs('header',_path + '/js/head.min.js?_rv_=' + _rv);
	loadJs('jquery',_path + '/js/jquery/jquery-1.6.4.min.js?_rv_=' + _rv);
	/**资源加载器：脚本，样式*/
	var ResourceLoader = head;
	
	ResourceLoader.load(_stylePath + 'css/public.css?_rv_=' + _rv)
	.load(_stylePath + 'css/module.css?_rv_=' + _rv)
	.load(_stylePath + 'css/global.css?_rv_=' + _rv)
	.load(_path + '/css/jqGrid/jquery-ui-1.7.1.custom.css?_rv_=' + _rv)
	.load(_path + '/css/jqGrid/ui.jqgrid.css?_rv_=' + _rv);
	
	ResourceLoader.load(_stylePath + '/js/lhgdialog/lhgdialog.min.js?skin=iblue')
	.load(_path + '/js/globalweb/comm/message.js?_rv_=' + _rv, function(){
		if(console){
			//console.debug('-------------Message.script is ready--------------');
		}
		//检查软件授权状态
		if(_license_status == 2){
			watermark({"watermark_txt":"授权即将到期"});
		}
		if(_license_status == 3){
			watermark({"watermark_txt":"软件使用过期"});
		}
		if(_license_status == 4 || _license_status == 5){
			watermark({"watermark_txt":"授权文件非法"});
		}
		if(_license_status == -1){
			watermark({"watermark_txt":"授权文件不存在"});
		}
	})
	.load(_path + '/js/globalweb/comm/adapter_message.js?_rv=' + _rv)
	.load(_path + '/js/globalweb/comm/operation.js?_rv_=' + _rv)
	.load(_path + '/js/jquery/jquery.form.js?_rv_=' + _rv)
	.load(_path + '/js/My97DatePicker/WdatePicker.js?_rv_=' + _rv)
	.load(_path + '/csrf/script/get.html',function(){
		if(console){
			//console.debug('-------------Invoke Csrf Callback--------------');
		}
	});
	
	ResourceLoader.ready(document, function(){
		if(console){
			//console.debug('-------------DOM is ready--------------');
		}
	});
	
	/**处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外*/
	document.onkeydown = function(e) {            
		var ev = e || window.event; //获取event对象          
  		var obj = ev.target || ev.srcElement; //获取事件源             
  		var t = obj.type || obj.getAttribute('type'); 
  		//获取事件源类型            
  		//获取作为判断条件的事件类型          
  		var vReadOnly = obj.readOnly;            
  		var vDisabled = obj.disabled;            
  		//处理undefined值情况            
  		vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;          
  		vDisabled = (vDisabled == undefined) ? true : vDisabled;            
  		//当敲Backspace键时，事件源类型为密码或单行、多行文本的，             
 		 //并且readOnly属性为true或disabled属性为true的，则退格键失效            
 		 var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);            
  		//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效           
 		var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";           
 		 //判断           
 		 if (flag2 || flag1) return false;      
 	 }
 	 
</script>


