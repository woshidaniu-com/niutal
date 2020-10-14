<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<link rel="stylesheet" href="<%=systemPath %>/css/plugins/bigcomboselect.css" type="text/css" media="all" />
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>		
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zdybd/zdybdUtil.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zdybd/zdybd.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/bigcomboselect.js"></script>
<script src="<%=systemPath %>/js/My97DatePicker/WdatePicker.js?_rv_=<%=resourceVersion%>"></script>

<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/xsxxgl/xgXsxx.js?_rv_=<%=resourceVersion%>"></script>
</head>
<body>
<form id="inputForm"  method="post">
	<input type="hidden" id="curXh" name="curXh" value="${xh}"/>
	<div  id="content_jcsj_xsxxgl_xgxsxx"></div>
	<div id="zpHidDiv" style="display:none;">
		<input type="hidden" name="path" id="xszpPath"/>
		<img src="<%=systemPath%>/zfxg/xsxxgl/cxXszp.html?xh=${xh}" width="129" height="163" id="xszp"/>
		<button type="button" onclick="showDialog('上传照片',400,70,'<%=systemPath%>/zfxg/xsxxgl/xgUploadPic.html');">上传照片</button>
	</div>
</form>

</body>
</html>