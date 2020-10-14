<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
		String stylePath = MessageUtil.getText("system.stylePath");
		String reportPath = MessageUtil.getText("system.reportLocation");
%>	
<!doctype html>
<html>
<head>
<script type="text/javascript" src="<%=systemPath%>/js/jquery/jquery-1.11.1-min.js"></script>
</head>
<body style="height:100%">
<!-- 隐藏的打印条件 -->
<form id="reportSearchForm" class="form-horizontal " role="form"  action="" method="post" style="display: none">
	<s:if test="requestMap!=null and requestMap.size() > 0 ">
	<s:iterator value="requestMap" var="requestRow">
	<input type="hidden" name="<s:property value="requestRow.key"/>" value="<s:property value="requestRow.value"/>"/>
	</s:iterator>
	</s:if>
</form>
<script type="text/javascript">
jQuery(function($){
	$("#reportSearchForm").submit();
});
</script>
</body>
</html>
