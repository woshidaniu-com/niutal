<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String systemPath = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<script type="text/javascript">
 	window.location="<%=systemPath%>/xtgl/dl_loginForward.html?language=${localeKey}&_t=" + new Date().getTime();
</script>
</head>
<body>
</body>
</html>
