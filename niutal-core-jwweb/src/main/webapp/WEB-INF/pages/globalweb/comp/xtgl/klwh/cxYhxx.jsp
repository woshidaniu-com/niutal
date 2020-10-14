<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<!--按钮 开始 -->
<div class="row sl_add_btn">
    <div class="col-sm-12">
    	<!-- 加载当前菜单栏目下操作    : N010202  -->
     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
		<!-- 加载当前菜单栏目下操作 -->
    </div>
</div>
<!--查询条件  开始 -->
<div id="searchBox2"></div>
<!--查询条件  结束  -->
<!--JQGrid 开始 -->
<div class="formbox">
	<table id="tabGrid"></table>
	<div id="pager"></div>
</div>
<!--JQGrid 结束  -->
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/searchbox.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/klwh.js?ver=<%=jsVersion%>"></script>
</html>
