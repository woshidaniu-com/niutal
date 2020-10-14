<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<title>${messageUtil("system.title")} - 浏览器下载</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />
	<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	[#include "/globalweb/head/niutal-ui-nodep.ftl" /]
	<style type="text/css">
	.browser_list li{cursor: pointer;}
	.ie10,.ie11{
		background:url('${base}/css/images/ie_logo64.png') 0px 0px no-repeat;
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
			<li>
				<a href="${base}/browser/download.zf?browserType=IE10" target="_blank"><i class="ico_browser ie10"></i>IE10</a>
			</li>
			<li>
				<a href="${base}/browser/download.zf?browserType=IE11" target="_blank"><i class="ico_browser ie11"></i>IE11</a>
			</li>
			<li>
				<a href="${base}/browser/download.zf?browserType=Chrome" target="_blank"><i class="ico_browser c"></i>chrome</a>
			</li>
			<li>
				<a href="${base}/browser/download.zf?browserType=Firefox" target="_blank"><i class="ico_browser f"></i>Firefox</a>
			</li>
			<li>
				<a href="${base}/browser/download.zf?browserType=Safari" target="_blank"><i class="ico_browser s"></i>safari</a>
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