<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
 	<style type="text/css">
	#zdpzForm .ui-jqgrid-bdiv{
		overflow-y: auto !important;
	}
	</style>
</head>
<body>
<s:form id="zdpzForm" cssClass="form-horizontal sl_all_form sl_all_bg" method="post" theme="simple" cssStyle="padding: 0px;">	
	<table id="zdpzTabGrid"></table>
	<div id="zdpzPager"></div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jqGridZdpz.js?ver=<%=jsVersion%>"></script>
</html>
