<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/defaultHead.ftl" /]
	</head>
	<body>
	<!-- top -->
	<header class="navbar top2">
		<div class="container">
			<div class="navbar-header"><a href="#" class="navbar-brand">通知详情</a></div>
		</div>
	</header>
	<!-- top-end -->
	<div class="container sl_all_bg newsdisp"  style="min-height: 600px !important;">
		<h3 class="text-center">${model.xwbt}</h3>
		<h5 class="text-center news_title1">
		   <span>发布人：${model.fbrxm}</span>
			<span>发布时间：${model.fbsj}</span>
		</h5>
		<hr>
		<div class="news_con" style="word-break:break-all;">
			${model.fbnr}
		</div>
	</div>
	 [#include "/globalweb/bottom.ftl" /]
	</body>
</html>