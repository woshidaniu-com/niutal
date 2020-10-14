<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="zf" uri="/design-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<zf:func-styles func_code="${func_code}" opt_code="cx" theme="design" />
</head>
<body>
<zf:func-toolbar func_code="${func_code}" opt_code="cx" theme="design" id="main_toolbar" >
</zf:func-toolbar>
<zf:func-body func_code="${func_code}" opt_code="cx" theme="design" id="main_body" cacheable="1">
</zf:func-body>
<zf:func-resources func_code="${func_code}" opt_code="cx" theme="design" cacheable="1">
	
</zf:func-resources>
<zf:func-javascript func_code="${func_code}" opt_code="cx" theme="design" staticable="1">
	
</zf:func-javascript>
<zf:func-javascript-include func_code="${func_code}" opt_code="cx" theme="design" />
</body>
</html>