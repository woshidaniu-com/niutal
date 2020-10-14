<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.woshidaniu.util.base.StringUtil"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ page import="com.woshidaniu.basicutils.LocaleUtils"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="zf" uri="/niutal-tags"%>
<!doctype html>
<html lang="zh-CN">
<s:if test="#session.user == null ">
<head>
	<%
		String stylePath = MessageUtil.getText("system.stylePath");
		String reportPath = MessageUtil.getText("system.reportLocation");
		String jsVersion = MessageUtil.getText("niutal.jsVersion");
		String cssVersion = MessageUtil.getText("niutal.cssVersion");
		long refreshInterval = StringUtil.getSafeLong(MessageUtil.getText("refresh.interval"), "10");
		String systemPath = request.getContextPath();
		String localeKey = LocaleUtils.getLocaleKey(request);
	%>
	<title>异常页面</title>
	<!-- 
		content的取值为webkit,ie-comp,ie-stand之一，区分大小写，分别代表用webkit内核，IE兼容内核，IE标准内核。
		若页面需默认用极速核，增加标签：<meta name=”renderer” content=”webkit” />
		若页面需默认用ie兼容内核，增加标签：<meta name=”renderer” content=”ie-comp” />
		若页面需默认用ie标准内核，增加标签：<meta name=”renderer” content=”ie-stand” /> 
	 -->
	<meta name="renderer" content="webkit|ie-comp|ie-stand" />
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />	
	<!--jQuery核心框架库 -->
	<script type="text/javascript" src="<%=stylePath%>/assets/js/other_jquery/jquery-1.11.3.min.js?ver=<%=jsVersion%>"></script>
	<script type="text/javascript" src="<%=stylePath%>/assets/js/jquery-migrate-1.4.1.min.js?ver=<%=jsVersion%>"></script>
	<!--jQuery浏览器检测 -->
	<script type="text/javascript" src="<%=systemPath%>/js/browse/browse-judge.js?ver=<%=jsVersion%>"></script>
	<script type="text/javascript">
		//浏览器版本验证
		var broswer = broswer();
		if(broswer.msie==true||broswer.safari==true||broswer.mozilla==true||broswer.chrome==true){
			if(broswer.msie==true&&broswer.version<9){
			   window.location.href = _path+"/xtgl/init_cxBrowser.html";
			}
		}else{
			 window.location.href = _path+"/xtgl/init_cxBrowser.html";
		}
	</script>
	<!--Bootstrap布局框架-->
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/plugins/bootstrap/css/bootstrap.min.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/error.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/niutal-ui.css?ver=<%=cssVersion%>" />
</head>
<body>
	<!-- 放置页面显示内容 -->
	<sitemesh:write property='body' />
	<sitemesh:write property='html' />
</body>	
</s:if>
<s:else>
<head>
	<title>&nbsp;</title>
	<%@ include file="/WEB-INF/pages/globalweb/head/head_v5.ini"%>
	<sitemesh:write property='head' />
</head>
<body>
	<!-- 头部 开始 -->
	<header class="navbar-inverse top2">
		<div class="container" id="navbar_container">
			<zf:navbar/>
		</div>
	</header>
	<zf:navtabs/>
	<!--头部 结束 -->
	<div style="width: 100%; padding: 0px; margin: 0px;" id="bodyContainer">
		<!-- requestMap中的参数为系统级别控制参数，请勿删除 -->
		<form id="requestMap">
			 <input type="hidden" id="sessionUserKey" value="<s:property value="sessionUser"/>" /> 
			 <s:if test="gnmkdm != null ">
			 	<input type="hidden" id="gnmkdmKey" value="<s:property value="gnmkdm"/>" />
			 </s:if>
			 <s:else>
			 	<input type="hidden" id="gnmkdmKey" value="<%= request.getParameter("gnmkdm") %>" />
			 </s:else>
		</form>
		<div class="container container-func sl_all_bg" id="yhgnPage">
			<div id="innerContainer">
				<!-- 放置页面显示内容 -->
				<sitemesh:write property='body' />
				<sitemesh:write property='html' />
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="/WEB-INF/pages/globalweb/bottom.jsp" />
	<!-- footer-end -->
</body>
<script type="text/javascript">
	jQuery(function($) {	
		if ($("#navbar-tabs").length > 0) {
			$("#navbar-tabs li:eq(0) a").tab('show');
		}
		
		setMinheight();
		
		$('#yhgnPage').resize(function(){
			var minHeight  = $(this).data('min-height');
			var innerHeight =  $('#innerContainer').outerHeight(true);
			//console.log("minHeight:" + minHeight + ",innerHeight:" + innerHeight);
			$(this).css('min-height', Math.max(minHeight,innerHeight));
		});
		
		function setMinheight(){
			//计算grid页面高度
			var docuemntHeight = $(document).height();
			var topHeight = $('header').height();
			var footerHeight = $('.footer').height();
			var containerHeight = docuemntHeight-topHeight-footerHeight-60;
			$('#yhgnPage').css('min-height',containerHeight).data('min-height',containerHeight);
		}
	});
</script>
</s:else>
</html>