<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<head>
		<title>通知详情</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/head_v5.ini"%>
	</head>
<body>
<!-- top -->
<header class="navbar-inverse top2">
	<div class="container">
		<div class="navbar-header"><a href="#" class="navbar-brand">通知详情</a></div>
	</div>
</header>
<!-- top-end -->
<div class="container sl_all_bg newsdisp"  style="min-height: 600px !important;">
	<h3 class="text-center">${xwbt}</h3>
	<h5 class="text-center news_title1">
	   <span>发布人：${fbrxm}</span>
		<span>发布时间：${fbsj}</span>
		<span>浏览人数：${yds}</span>
	</h5>
	<hr>
	<div class="news_con">
		${fbnr}
	</div>
</div>
<!-- footer -->
<jsp:include page="/WEB-INF/pages/globalweb/bottom.jsp" />
<!-- footer-end -->
</body>
</html>
