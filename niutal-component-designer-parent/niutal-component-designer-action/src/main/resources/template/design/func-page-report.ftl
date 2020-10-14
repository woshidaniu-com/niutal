<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="zf" uri="/design-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
	<zf:func-styles func_code="${func_code}" opt_code="${opt_code}" theme="design" />
</head>
<body>
<zf:func-report func_code="${func_code}" opt_code="${opt_code}" theme="design" id="func_report" cacheable="1">
</zf:func-report>
<zf:func-resources func_code="${func_code}" opt_code="${opt_code}" theme="design" cacheable="1">
</zf:func-resources>
<zf:func-javascript func_code="${func_code}" opt_code="${opt_code}" theme="design" staticable="1">
</zf:func-javascript>
<zf:func-javascript-include func_code="${func_code}" opt_code="${opt_code}" theme="design" />
</body>
</html>