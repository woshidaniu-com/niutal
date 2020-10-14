<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String systemPath = request.getContextPath();
	String format = request.getParameter("format");
	   	   format = (format == null || format.length() == 0) ? "PDF" : format.toUpperCase();
%>
<!doctype html>
<html>
<head>	
<script type="text/javascript" src="<%=systemPath%>/ReportServer?op=emb&resource=finereport.js"></script>
<link rel="stylesheet" type="text/css" href="<%=systemPath%>/ReportServer?op=emb&resource=finereport.css"/>
<style type="text/css">
.container{
	 border: 1px solid #0483d4;
    border-radius: 4px;
    color: #000;
    font-size: 24px;
    line-height: 50px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 50px;
    padding-left: 15px;
    padding-right: 15px;
    width: 50%;
}
</style>
</head>
<body style="height:100%">
	<div class="container ">
	报表打印中...<br/>
	当前页面为报表调用界面，打印完成后请自行关闭!</div>
</body>
<script type="text/javascript">
jQuery(function($){
/**

 	批量打印多张报表url格式如：http://localhost:8075/WebReport/ReportServer?reportlets=[{reportlet:'reportname1.cpt',paraname:'paravalue'},{reportlet:'reportname2.cpt',paraname:'paravalue'}]

 	调用内置的打印方法直接使用完整的url进行批量打印：

    var printurl="http://localhost:8075/WebReport/ReportServer?reportlets=[{reportlet:'reportname1.cpt',paraname:'paravalue'},{reportlet:'reportname2.cpt',paraname:'paravalue'}]";  
    FR.doURLPDFPrint(printurl,true);   //get方式传参  

 	如批量打印的模板过多的话，url就很长，而get方式对长度有限制，url过长时会导致打印失败。推荐批量打印的时候用post方式，reportlets参数打包在数据包中传输，不在url中显示，从而缩短url长度，另外安全性较好，如下：

    var printurl="http://localhost:8075/WebReport/ReportServer";       
    var reportlets = FR.cjkEncode("[{reportlet: '/doc/Primary/Parameter/Parameter_1.cpt', 地区 : '华东'}, {reportlet: '/doc/Primary/Parameter/Parameter_1.cpt', 地区 : '华北'}]");  
            var config = {  
            url : printurl,  
            isPopUp : false,  
            data : {  
                reportlets: reportlets  
            }  
    };  
    FR.doURLPDFPrint(config);  

 	注：调用打印方法中的第二个参数为true表示弹出对话框，为false表示不弹出对话框即静默打印。
 	
 */
//定义参数数组
var requestArr = [],requestRow = [];
	//组织报表请求参数	
 	<%
 	
	Enumeration<String> paramNames = request.getParameterNames();
	StringBuffer buffer = new StringBuffer();
	while (paramNames != null && paramNames.hasMoreElements()) {
		String name = (String) paramNames.nextElement();
		String value = request.getParameter(name);
		if(!name.equalsIgnoreCase("format")){
		%>	
		requestRow.push("<%=name%>:'<%=value %>'");
		<%		
		}
	}
	%>
	requestArr.push("{" + requestRow.join(",") + "}");
//使用FineReport自带的方法cjkEncode进行转码  
var reportlets = "["+requestArr.join(",")+"]";//FR.cjkEncode();  
//说明：config为参数配置，参数可以以post方式传递给服务器， config数据格式为 {url : url,isPopUp : isPopUp,data:{reportlets:reportlets}}.
var config = {
	//url为需要打印的报表路径，
	url : "<%=systemPath %>/ReportServer?__cumulatepagenumber__=false&sessionID=<%=request.getSession().getId()%>",
	//isPopUp布尔值（true/false），表示是否进行静默打印，true为弹出打印对话框，false为不弹出。 
	isPopUp : false,   
	//data为需要打印的报表以及报表参数。
	data : {
		//reportlets 格式为: "[{reportlet: '1.cpt', p1: 'a'}, {reportlet: '1.cpt', p1: 'b'}]";
		reportlets: reportlets
	}   
};
<%if("FLASH".equals(format)){ %>
	//Flash打印不支持静默打印，true/false效果相同
	FR.doURLFlashPrint(config); 
	//打印完成后，调用一次报表服务器的内存回收
    $.ajax({  
         url 	: FR.servletURL,
         data 	: {  
             op : 'fr_utils',  
             cmd : 'gs_gc'  
         },  
         async : false
    });  
});
<%}else if("APPLET".equals(format)){%>
	//applet打印，true时弹出打印对话框，false不弹出
	FR.doURLAppletPrint(config); 
	//打印完成后，调用一次报表服务器的内存回收
    $.ajax({  
         url 	: FR.servletURL,
         data 	: {  
             op : 'fr_utils',  
             cmd : 'gs_gc'  
         },  
         async : false
    });  
});
<%}else if("PDF".equals(format)){%>
	//PDF打印，true时弹出打印对话框，false不弹出
	FR.doURLPDFPrint(config);
	//打印完成后，调用一次报表服务器的内存回收
    $.ajax({  
         url 	: FR.servletURL,
         data 	: {  
             op : 'fr_utils',  
             cmd : 'gs_gc'  
         },  
         async : false
    });  
});
<%}%>
</script>
</html>
