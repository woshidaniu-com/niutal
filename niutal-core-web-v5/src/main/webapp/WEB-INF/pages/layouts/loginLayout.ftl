<!doctype html>
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	[#include "/globalweb/head/niutal-ui-meta.ftl" /]
	[#include "/globalweb/head/niutal-ui-login.ftl" /]
	
	<sitemesh:write property='head'/>
</head>
<body style="background:#fafafa;" >
	<sitemesh:write property='body'/>
	[#include "/globalweb/bottom.ftl" /]
</body>
<!--[if lt IE 9]>
<!--jQuery浏览器检测 -->
<script type="text/javascript" src="${base}/js/browse/browse-judge.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<![endif]-->
</html>

