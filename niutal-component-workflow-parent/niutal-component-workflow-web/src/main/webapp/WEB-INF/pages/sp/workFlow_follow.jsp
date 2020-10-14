<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	.zt1 .tg,.zt1 .wtg{position: static !important;}
	.zt1 .more{display:none;}
	</style>
</head>
<body>
  	<s:property value="lcgzHtml" escapeHtml="false"/>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/sp/workFlow_follow.js?ver=<%=jsVersion%>"></script>
</html>