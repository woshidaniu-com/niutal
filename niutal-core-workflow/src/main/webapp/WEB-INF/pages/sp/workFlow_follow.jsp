<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>
<body>
  	<s:property value="lcgzHtml" escapeHtml="false"/>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/sp/workFlow_follow.js?ver=<%=resourceVersion%>"></script>
</html>