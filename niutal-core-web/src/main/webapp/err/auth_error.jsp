<%@ page import="org.owasp.encoder.Encode"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/simple_pagehead_v4.ini"%>
<style> 
html, body {
        height: 100%;
        margin: 0;
        padding: 0;
}
</style>
</head>
<body class="login_bg" style="height:400px;">
<!--友情提醒start-->
<div class="page_prompt_yx">
  <p style="font-weight:bold">
  	您【<%=Encode.forHtmlContent(request.getParameter("yhm"))  %>】当前未授权，暂时无法访问！
  </p>
</div>
<!--友情提醒end-->
</body>
</html>