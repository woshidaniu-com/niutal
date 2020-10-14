<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<%
	String systemPath = request.getContextPath();
	Enumeration<String> paramNames = request.getParameterNames();
	String format = "PDF";
	StringBuffer buffer = new StringBuffer();
	while (paramNames != null && paramNames.hasMoreElements()) {
		String name = (String) paramNames.nextElement();
		String value = request.getParameter(name);
		if(name.equalsIgnoreCase("format")){
			format = value;		
		}else{
			buffer.append(name).append("=").append(value).append("&");
		}
	}
	format = (format == null || format.length() == 0) ? "PDF" : format.toUpperCase();
	buffer.append("t=").append(new Date().getTime());
%>	
	
<script type="text/javascript" src="<%=systemPath%>/ReportServer?op=emb&resource=finereport.js"></script>
<link rel="stylesheet" type="text/css" href="<%=systemPath%>/ReportServer?op=emb&resource=finereport.css"/>  
<script type="text/javascript">
jQuery(function($){

	var _format = "<%=format %>"; 		
	if("PDF" == _format){
		//PDF打印，true时弹出打印对话框，false不弹出
		FR.doURLPDFPrint( "<%=systemPath %>/ReportServer?<%=buffer.toString()%>",false);  
	}else if("FLASH" == _format){
		//Flash打印不支持静默打印
		FR.doURLFlashPrint( "<%=systemPath %>/ReportServer?<%=buffer.toString()%>",false);  
	}else if("APPLET" == _format){
		//applet打印，true时弹出打印对话框，false不弹出
		FR.doURLAppletPrint( "<%=systemPath %>/ReportServer?<%=buffer.toString()%>",false);  
	}
    $.ajax({  
         url : FR.servletURL,
         data : {  
             op : 'fr_utils',  
             cmd : 'gs_gc'  
         },  
         async : false
    });  
	
});
</script>
</head>
<body style="height:100%">
	
</body>
</html>
