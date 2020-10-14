<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.lang.*"%>
<%@ page language="java" import="java.net.*"%>
<%@ page language="java" import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<%
	String systemPath = request.getContextPath();
	String format = request.getParameter("format");
		   format = (format == null || format.length() == 0) ? "PDF" : format.toUpperCase();
	String reportlets = request.getParameter("reportlets");	 
	try {
			reportlets = URLDecoder.decode(reportlets, "utf-8");
	} catch (UnsupportedEncodingException e) {
	}
%>	
	
<script type="text/javascript" src="<%=systemPath%>/ReportServer?op=emb&resource=finereport.js"></script>
<link rel="stylesheet" type="text/css" href="<%=systemPath%>/ReportServer?op=emb&resource=finereport.css"/>   
<script type="text/javascript">
jQuery(function($){
alert( '<%=reportlets%>');
	var _format = "<%=format %>";  
	var config = {   
		url : "<%=systemPath %>/ReportServer",
		isPopUp : false,   
		data : {
			reportlets: '<%=reportlets%>'
		}   
    }; 
	if("PDF" == _format){
		//PDF打印，true时弹出打印对话框，false不弹出
		FR.doURLPDFPrint(config);  
	}else if("FLASH" == _format){
		//Flash打印不支持静默打印
		FR.doURLFlashPrint(config);  
	}else if("APPLET" == _format){
		//applet打印，true时弹出打印对话框，false不弹出
		FR.doURLAppletPrint(config);  
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
