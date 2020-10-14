<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="zf" uri="/design-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<zf:func-styles func_code="${func_code}" opt_code="${opt_code}" theme="design" />
</head>
<body>
<zf:func-elements func_code="${func_code}" opt_code="${opt_code}" theme="design" cacheable="1">
</zf:func-elements>
<zf:func-resources func_code="${func_code}" opt_code="${opt_code}" theme="design" cacheable="1">
</zf:func-resources>
<zf:func-javascript func_code="${func_code}" opt_code="${opt_code}" theme="design" staticable="1">
</zf:func-javascript>
<zf:func-javascript-include func_code="${func_code}" opt_code="${opt_code}" theme="design" />
</body>
</html>