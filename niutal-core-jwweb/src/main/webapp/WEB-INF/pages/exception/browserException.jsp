<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%
	String stylePath = MessageUtil.getText("system.stylePath");
	String jsVersion = MessageUtil.getText("niutal.jsVersion");
	String cssVersion = MessageUtil.getText("niutal.cssVersion");
	String systemPath = request.getContextPath();
%>	
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
	<title><s:text name="download.title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/niutal-ui.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/error.css?ver=<%=cssVersion%>" />
	<script type="text/javascript" src="<%=stylePath%>/assets/js/jquery-1.11.1-min.js?ver=<%=jsVersion%>"></script>
	<style type="text/css">
	.ie10,.ie11{
		background:url('<%=systemPath%>/css/images/ie_logo64.png') 0px 0px no-repeat;
	}
	</style>
</head>
<body class="bg-white">
	<div class="tips">
		<p class="t1">
			<span><s:text name="download.msg01"/></span> <a
				href="http://www.microsoft.com/zh-cn/download/internet-explorer-9-details.aspx"
				class="ico_browser ie ie9" target="_blank"></a>
		</p>
		 <p class="t1">
			 ：
		</p>
		<ul class="browser_list" style="margin: 0;padding: 0;">
			<li>
        		<a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/"
					target="_blank"><i class="ico_browser ie11"></i>IE11</a>
			</li>
			<li>
        		<a href="http://www.google.cn/intl/zh-CN/chrome/browser/"
					target="_blank"><i class="ico_browser c"></i>chrome</a>
			</li>
		    <li>
		        <a href="http://www.firefox.com.cn/download/"
		         target="_blank"><i class="ico_browser f"></i>Firefox</a>
			</li>
			<li>
				<a href="http://rj.baidu.com/soft/detail/12966.html?ald"
					target="_blank"><i class="ico_browser s"></i>safari</a>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function(e) {
			$(window).resize();
		});
		//计算高度
		window.onresize = function() {
			var body_height = $(window).height();
			var error_height = $(".tips").height() + 50;
			var margin_top = (body_height - error_height) / 2;
			var margin_top = (margin_top > 0) ? margin_top : 0;
			$(".tips").css("margin-top", margin_top);
		}
	</script>
</body>
</html>