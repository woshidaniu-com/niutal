<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<title><%=MessageUtil.getLocaleText("system.title") %></title>
	<%@ include file="/WEB-INF/pages/globalweb/head/head_v5.ini"%>
	<sitemesh:write property='head' />
</head>
<body class="body-container">
	<!-- 放置页面显示内容 -->
	<sitemesh:write property='body' />
</body>
<sitemesh:write property='html' />
</html>