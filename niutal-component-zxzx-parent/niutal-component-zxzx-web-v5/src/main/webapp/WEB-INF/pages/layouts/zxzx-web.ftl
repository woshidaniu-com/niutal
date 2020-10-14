[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
<head>
<title>${messageUtil("system.zxzx.title")}</title>
<meta charset="utf-8" />
<meta name="Copyright" content="woshidaniu" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- 强制让文档与设备的宽度保持1：1 -->
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<!-- 删除默认的苹果工具栏和菜单栏 -->
<meta name="apple-mobile-web-app-capable" content="yes">
<!-- 在web app应用下状态条（屏幕顶部条）的颜色 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<!-- 禁止了把数字转化为拨号链接 -->
<meta name="format-detection" content="telephone=no">
<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

[#include "/layouts/zxzx-web-head_v5.ftl" /]
<!--在线咨询个性化样式-->
<link rel="stylesheet" type="text/css" href="${base}/css/zxzx/web/zxzx-web.css?ver=${messageUtil(" niutal.cssVersion")}" />
<!--在线咨询个性化脚本-->
<script src="${base}/js/zxzx/zxzx-web.js" type="text/javascript" charset="utf-8"></script>
<!-- 模板引擎 -->
<script src="${base}/js/zxzx/template-render.js" type="text/javascript" charset="utf-8"></script>

<sitemesh:write property='head' />
</head>
<body>
	<header id="top">
		<div class="container">
			<p>在线咨询</p>
		</div>
	</header>

	<div class="container zxzx">
		<div class="search-area clearfix">
			<div class="search">
				<input type="text" placeholder="请输入查找的内容" id="zxzx-web-search-box" /> 
				<i id="zxzx-web-search-box-remove" class="glyphicon glyphicon-remove"></i>
				<i class="glyphicon glyphicon-search"></i> 
			</div>
		</div>

		<div class="content clearfix">
			<div class="left-area hidden-xs">
			
				<div class="item active" id="topic" data-url="${base}/zxzx/web/page/topic.zf" data-content="topic-content">
					全部问题
					<!--全部问题<span class="number count">(77)</span> -->
				</div>
				
				<div class="item" id="faq" data-url="${base}/zxzx/web/page/faq.zf" data-content="faq-content">
					常见问题
					<!--常见问题<span class="number count">(77)</span> -->
				</div>
				
				[#if sfxswdwt == '1']
				<div class="item" id="my-topic" data-url="${base}/zxzx/web/page/auth/my-topic.zf" data-content="my-topic-content">
					我的提问
					<!--我的提问<span class="number count">(77)</span> -->
				</div>
				[/#if]
				
				[#if sfxswytw == '1']
				<div class="item last" id="askDialog">
					<div class="question">
						<span class="icon"><i class="ask"></i></span>我要提问
					</div>
				</div>
				[/#if]
				
			</div>
			<div class="right-area">
				<div class="web-loading hidden2"></div>
				<div class="item active" id="topic-content"></div>
				<div class="item" id="faq-content"></div>
				<div class="item" id="my-topic-content"></div>
			</div>

		</div>
	</div>
	
	<!-- 移动端按钮 -->
	<div class="footer-nav hidden-lg hidden-md hidden-sm" mobile-navigator>
		<div class="item allQ">
			<span topic-selected-page="topic">所有问题</span> <i class="triangle-bottomright"></i>
			<div id="mobile-topic-blocks" class="tier">
				<div class="item menu sub">
					<a href="javascript:void(0);" data-page="topic">所有问题</a>
				</div>
				<div class="item menu sub">
					<a href="javascript:void(0);"  data-page="faq">常见问题</a>
				</div>
			</div>
		</div>
		
		[#if sfxswdwt == '1']
		<div class="item menu myq">
			<a href="javascript:void(0);" data-page="my-topic">我的提问</a>
		</div>
		[/#if]
		
		[#if sfxswytw == '1']
		<div class="item menu myq">
			<a href="javascript:void(0);" data-page="askDialog">我要提问</a>
		</div>
		[/#if]
		
	</div>
	<!-- 移动端按钮 -->

	<div class="footer">
		<div class="container">
			<p>版权所有© Copyright 1999-2017 我是大牛 苏ICP备 中国</p>
		</div>
	</div>
</body>
</html>

