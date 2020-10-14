<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<style>
.ui-jqgrid tr.jqgrow td {
  	white-space: normal !important;
  	vertical-align:middle;
  	text-align: center;
}
.ui-jqgrid tr.jqgrow td a{
	text-decoration: underline;
	color: blue;
}
</style>
<body style="padding: 2px;">
<form action="<%=systemPath %>/xtgl/yhgl_szssjsSaveYh.html" data-toggle="validation"   role="form"  class="form-horizontal sl_all_form" id="ajaxForm" method="post" theme="simple">
	<div class="formbox "  style="width: 100%">
		<table id="YhjsGrid" style="width: 100%"></table>
		<div id="YhjsGridPager" style="width: 100%"></div>
	</div>
	<input type="hidden" id="yhm" name="yhm" value="<s:property value="model.yhm"/>" />
	<s:iterator value="jsList">
		<!-- 可能会被改变的角色：并组成最终新的角色集合；这里指 当前操作人 与被操作人的角色集合的交集-->
		<input type="hidden" name="cbvjsxx" id="cbvjsxx_<s:property value="jsdm"/>" value="<s:property value="jsdm"/>" />
	</s:iterator>
<form>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/yhgl/yhgl_szSsjs.js?ver=<%=jsVersion%>"></script>
</html>