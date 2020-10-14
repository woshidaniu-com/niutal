<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%
	String stylePath = MessageUtil.getText("system.stylePath");
	String jsVersion = MessageUtil.getText("niutal.jsVersion");
	String cssVersion = MessageUtil.getText("niutal.cssVersion");
	String systemPath = request.getContextPath();
%>	
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/niutal-ui.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/error.css?ver=<%=cssVersion%>" />
	<script type="text/javascript">
	//表示是独立错误页面
	if(!document.getElementById("yhgnPage")){
			//加载jquery
		var script = document.createElement( "script" );
			//设置属性
			script.setAttribute('type', 'text/javascript');
			script.setAttribute('media', "all");
			script.setAttribute('charset', "utf-8");
	        script.setAttribute('src', "<%=stylePath%>/assets/js/jquery-1.11.1-min.js?ver=<%=jsVersion%>"); 
	     	// 先把js/css加到页面；如果script节点没有添加到DOM树中，在chrome和firefox中不会响应script的load事件
            document.getElementsByTagName("head")[0].appendChild(script);  
	}
	</script>
	<sitemesh:write property='head' />
</head>
<body class="body-container">
	<!-- 放置页面显示内容 -->
	<sitemesh:write property='body' />
</body>
<sitemesh:write property='html' />
</html>