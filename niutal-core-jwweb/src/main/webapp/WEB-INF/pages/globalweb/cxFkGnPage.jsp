<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagefunc_v5.ini"%>
		<style type="text/css"  href="<%=stylePath %>/assets/css/error.css?ver=<%=cssVersion%>"></style>
		<script type="text/javascript">
		jQuery(function($){
			//计算位置
			var margin_top	=	(jQuery("#yhgnPage").innerHeight() - 200)/2;
				margin_top	= 	(margin_top>0) ? margin_top : 0;
			$("#yhgnPage").html('<p id="loading_status" class="text-center header smaller lighter"  style="margin-top: '+margin_top+'px;"><i class="icon-spinner icon-spin orange  bigger-500"></i></br> 网页正在载入数据中.请等待....</p>');	
			$("#yhgnPage").load(_path + '${dyym}');
		});
	</script>
	</head>
	<body>
		<!-- 头部 开始 -->
		<!-- top -->
		<header class="navbar-inverse top2">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse"
					data-target=".bs-navbar-collapse">
					<span class="sr-only">${gnmkmc}</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand">${gnmkmc}</a>
			</div>			
		</div>
		</header>
		<!--头部 结束 -->
		<div style="width: 100%;padding: 0px;margin: 0px;" id="bodyContainer">
			<div class="container sl_all_bg" id="yhgnPage" style="min-height: 620px !important;">
				<!-- 放置页面显示内容 -->
			</div>
		</div>
		<!-- footer -->
		<jsp:include page="bottom.jsp" />
		<!-- footer-end -->
	</body>
</html>
