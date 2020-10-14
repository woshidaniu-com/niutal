<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/head_v5.ini"%>
<title><%=MessageUtil.getLocaleText("system.title") %></title>
<style type="text/css">
.title {
	float: left;
	font: 12px/28px SimSun;
}

.new {
	display: block;
	float: left;
	width: 22px;
	height: 11px;
	background-position: -30px 0px;
	margin-top: 9px;
	margin-left: 5px;
}
</style>
</head>
<body>
	<header class="navbar-inverse top2">
		<div class="container">
			<div class="navbar-header">
				<a href="#" class="navbar-brand">通知公告</a>
			</div>
		</div>
	</header>
	<!-- top-end -->
	<div class="container sl_all_bg newsdisp"
		style="min-height: 600px !important;">
		<div id="fixtop" class="panel panel-default">
			<s:form cssClass="form-horizontal sl_all_form" role="form"
				name="form" method="post"
				action="/xtgl/xwgl_cxXwListGyxtzgg.html" theme="simple"
				onsubmit="return false;">
				<div id="searchBox" class="row">
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label">通知标题</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="xwbt" id="xwbt"
									placeholder="输入查询条件，按Enter 查询">
							</div>
						</div>
					</div>
				</div>
			</s:form>
			<!-- form -end  -->
			<ul class="nav nav-tabs sl_nav_tabs" role="tablist" id="myTab">
				<!-- <li id="second"><a role="tab" data-toggle="tab" href="#bksytzgg">本科生院通知公告</a></li> -->
				<li class="active" id="first"><a role="tab" data-toggle="tab" href="#gyxtzgg">通知公告</a></li>
			</ul>
			<div>
				<div class="tab-content">
					<div id="gyxtzgg" class="tab-pane in active">
						<table id="tabGrid1"></table>
						<div id="pager1"></div>
					</div>
					<!-- <div id="bksytzgg" class="tab-pane">
						<table id="tabGrid2"></table>
						<div id="pager2"></div>
					</div> -->
				</div>
			</div>
		</div>
	</div>
	<!-- footer --> 
	<jsp:include page="../../../bottom.jsp"/>
	<!-- footer-end -->
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xwgl/cxMoreXwList.js?ver=<%=jsVersion%>"></script>
</html>
