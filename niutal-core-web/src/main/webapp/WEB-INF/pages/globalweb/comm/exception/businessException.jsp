<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/simple_pagehead_v4.ini"%>
<style  type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body class="login_bg" style="height: 400px;">
	<div class="page_prompt">
		<div class="page_promptcon">
			<h3>Error Message</h3>
			<p>
				<s:property value="exception.message" />
			</p>
		</div>
	</div>
</body>
</html>
