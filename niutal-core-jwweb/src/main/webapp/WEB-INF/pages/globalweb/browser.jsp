<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
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
	<title>升级提示</title>
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/css/bootstrap.min.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/css/error.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/css/niutal-ui.css?ver=<%=cssVersion%>" />
	<!--jQuery核心框架库 -->
	<script type="text/javascript" src="<%=stylePath%>/js/jquery-1.11.1-min.js?ver=<%=jsVersion%>"></script>
	<style type="text/css">
	.browser_list li{cursor: pointer;}
	.ie10,.ie11{
		background:url(<%=systemPath%>/css/images/ie_logo64.png) 0px 0px no-repeat;
	}
	</style>
</head>
<body class="bg-white">
	
	<div class="tips_top" style="display:none;">
		<div class="w_1000">
			<span>亲爱的用户，为了达到最优的网站体验效果，我们建议您安装/使用下列最新版本浏览器：</span>
			<ul class="browser_list">
				<li>
					<a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/"
						target="_blank"><i class="ico_browser i"></i>Internet Explorer</a>
				</li>
				<li>
					<a href="http://www.google.cn/intl/zh-CN/chrome/browser/"
						target="_blank"><i class="ico_browser c"></i>chrome</a>
				</li>
				<li>
					<a href="http://www.firefox.com.cn/download/" target="_blank"><i
						class="ico_browser f"></i>Firefox</a>
				</li>
				<li>
					<a href="http://rj.baidu.com/soft/detail/12966.html?ald"
						target="_blank"><i class="ico_browser s"></i>safari</a>
				</li>
			</ul>
			<a href="#" class="close"><i class="ico_browser"></i>
			</a>
		</div>
	</div>
	
	<div class="tips">
		<p class="t1">
			<span>您正在使用的浏览器版本过低，请立即升级至</span><a href="<%=systemPath%>/browserFile/IE9Setup.rar" 
			 class="ico_browser ie ie9" target="_blank"></a><br/>
		</p>
		<p class="t1">
			或安装/使用下列其它版本浏览器：
		</p>
		<ul class="browser_list" style="margin: 0;padding: 0;">
<%--			<li onclick="xzFile('ie10')" >--%>
<%--        		<a ><i class="ico_browser ie10"></i>IE10</a>--%>
<%--			</li>--%>
<%--			<li onclick="xzFile('ie11')" >--%>
<%--        		<a><i class="ico_browser ie11"></i>IE11</a>--%>
<%--			</li>--%>
<%--			<li onclick="xzFile('chrome')" >--%>
<%--				<a><i class="ico_browser c"></i>chrome</a>--%>
<%--			</li>--%>
<%--			<li onclick="xzFile('firefox')">--%>
<%--				<a><i class="ico_browser f"></i>Firefox</a>--%>
<%--			</li>--%>
<%--			<li onclick="xzFile('safari')">--%>
<%--				<a><i class="ico_browser s"></i>safari</a>--%>
<%--			</li>--%>
			<li>
				<a href="<%=systemPath%>/browserFile/IE10Setup.rar" target="_blank"><i class="ico_browser ie10"></i>IE10</a>
			</li>
			<li>
				<a href="<%=systemPath%>/browserFile/IE11Setup.rar" target="_blank"><i class="ico_browser ie11"></i>IE11</a>
			</li>
			<li>
				<a href="<%=systemPath%>/browserFile/ChromeSetup.rar" target="_blank"><i class="ico_browser c"></i>chrome</a>
			</li>
			<li>
				<a href="<%=systemPath%>/browserFile/FirefoxSetup.rar" target="_blank"><i class="ico_browser f"></i>Firefox</a>
			</li>
			<li>
				<a href="<%=systemPath%>/browserFile/SafariSetup.rar" target="_blank"><i class="ico_browser s"></i>safari</a>
			</li>
		</ul>
	</div>

	<script type="text/javascript">
	
	function xzFile(browserType){
		window.open("<%=systemPath%>/xtgl/init_xzBrowser.html?browserType="+browserType);
	}
	
	
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

	function detectOS() {
		var sUserAgent = navigator.userAgent;

		var isWin = (navigator.platform == "Win32")
				|| (navigator.platform == "Windows");
		var isMac = (navigator.platform == "Mac68K")
				|| (navigator.platform == "MacPPC")
				|| (navigator.platform == "Macintosh")
				|| (navigator.platform == "MacIntel");
		if (isMac)
			return "Mac";
		var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
		if (isUnix)
			return "Unix";
		var isLinux = (String(navigator.platform).indexOf("Linux") > -1);

		var bIsAndroid = sUserAgent.toLowerCase().match(/android/i) == "android";
		if (isLinux) {
			if (bIsAndroid)
				return "Android";
			else
				return "Linux";
		}
		if (isWin) {
			var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1
					|| sUserAgent.indexOf("Windows 2000") > -1;
			if (isWin2K)
				return "Win2000";
			var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1
					|| sUserAgent.indexOf("Windows XP") > -1;
			if (isWinXP)
				return "WinXP";
			var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1
					|| sUserAgent.indexOf("Windows 2003") > -1;
			if (isWin2003)
				return "Win2003";
			var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1
					|| sUserAgent.indexOf("Windows Vista") > -1;
			if (isWinVista)
				return "WinVista";
			var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1
					|| sUserAgent.indexOf("Windows 7") > -1;
			if (isWin7)
				return "Win7";
		}
		return "other";
	}
	////document.writeln("您的操作系统是：" + detectOS());
	///alert(detectOS());

 
</script>


</body>
</html>