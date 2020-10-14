<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagefunc_v5.ini"%>
<script type="text/javascript">
	jQuery(function($) {
		//加载默认页面
		onClickMenu.call($("#topButton"), '${dyym}', '${gnmkdm}');
	});
</script>
<style type="text/css">	
	.active{font-weight: bolder;}
	#navbar-tabs li{ margin-top: 2px;}
	#navbar-tabs li a{ border-top: 2px solid transparent;}
	#navbar-tabs li.active a{border-top: 2px solid #0770cd;}
</style>
</head>
<body>
	<!-- 头部 开始 -->
	<header class="navbar-inverse top2">
		<div class="container" id="navbar_container">
			<s:if test="gnList.size()>1">
				<s:iterator value="gnList" status="gn">
					<s:if test="#gn.first">
						<div class="navbar-header">
							<button class="navbar-toggle" type="button"
								data-toggle="collapse" data-target=".bs-navbar-collapse">
								<span class="sr-only"><s:property value="gnmkmc" /></span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a href="#" class="navbar-brand"><s:property value="gnmkmc" /></a>
						</div>
					</s:if>
					<s:elseif test="1 != xslx">
						<s:if test="#gn.index==1">
							<nav class="navbar-collapse bs-navbar-collapse collapse"
								role="navigation" style="">
								<ul class="nav navbar-nav" id="navbar-nav">
									<li class="active"><a href="#" id="topButton"
										onclick="onClickMenu.call(this,'<s:property value="dyym"/>','<s:property value="gnmkdm"/>')">
											<s:property value="gnmkmc" />
									</a></li>
						</s:if>
						<s:else>
							<li><a href="#"
								onclick="onClickMenu.call(this,'<s:property value="dyym"/>','<s:property value="gnmkdm"/>')">
									<s:property value="gnmkmc" />
							</a></li>
						</s:else>
						<s:if test="#gn.index + 1 == gnList.size()">
							</ul>
							</nav>
						</s:if>
					</s:elseif>
				</s:iterator>
			</s:if>
			<s:else>
				<s:iterator value="gnList" status="gn">
					<s:if test="#gn.first">
						<div class="container">
							<div class="navbar-header">
								<button class="navbar-toggle" type="button"
									data-toggle="collapse" data-target=".bs-navbar-collapse">
									<span class="sr-only"><s:property value="gnmkmc" /></span> <span
										class="icon-bar"></span> <span class="icon-bar"></span> <span
										class="icon-bar"></span>
								</button>
								<a href="#" id="topButton" class="navbar-brand"
									onclick="onClickMenu('<s:property value="dyym"/>','<s:property value="gnmkdm"/>')">
									<s:property value="gnmkmc" />
								</a>
							</div>
						</div>
					</s:if>
				</s:iterator>
			</s:else>
		</div>
	</header>
	<s:if test="(gnList.size()>1) && (1 == xslx)">
		<div class="container sl_all_bg" style="padding-bottom: 0px;">
			<ul class="nav nav-tabs" id="navbar-tabs" role="tablist"
				style="padding-left: 15px">
				<s:iterator value="gnList" status="gn">
					<s:if test="#gn.first">
					</s:if>
					<s:else>
						<li><a href="#" data-dyym="<s:property value="dyym"/>"
							data-gnmkdm="<s:property value="gnmkdm"/>" role="tab"
							onclick="onClickMenu('<s:property value="dyym"/>','<s:property value="gnmkdm"/>')"
							data-toggle="tab" style="margin-top: 0px !important;margin-bottom: 0px !important;line-height: 1.1"> <s:property value="gnmkmc" />
						</a></li>
					</s:else>
				</s:iterator>
			</ul>
		</div>
	</s:if>
	<!--头部 结束 -->
	<div style="width: 100%; padding: 0px; margin: 0px;" id="bodyContainer">
		<!-- requestMap中的参数为系统级别控制参数，请勿删除 -->
		<form id="requestMap">
			<input type="hidden" id="sessionUserKey"
				value="<s:property value="sessionUser"/>" /> <input type="hidden"
				id="gnmkdmKey" value="<s:property value="gnmkdm"/>" />
		</form>
		<div class="container sl_all_bg" id="yhgnPage">
			<!-- 放置页面显示内容 -->
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="bottom.jsp" />
	<!-- footer-end -->
</body>
<script type="text/javascript">
	jQuery(function($) {	
		if ($("#navbar-tabs").length > 0) {
			$("#navbar-tabs li:eq(0) a").tab('show');
		}
	});
</script>
</html>
