[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
<head>
	<title>${messageUtil("system.zxzx.title")}</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />
	<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	[#include "/globalweb/head/head_v5.ftl" /]
	<!--在线咨询个性化样式-->
	<link rel="stylesheet" type="text/css" href="${base}/css/zxzx/web/zxzx.css?ver=${messageUtil("niutal.cssVersion")}" />
	
	
<script type="text/javascript">
	jQuery(function(){
		[#if openStatus.isOpen]
		jQuery("#askQuestion").click(function() {
			$.showDialog('newTopic.zf','我 要 提 问', $.extend({},modifyConfig,{
				width : '900px',
				height : '540px'
			}));
		});
		[/#if]
		
		jQuery("#pagingSubmit").click(function() {
			return false;
		});
		
		/**版块点击事件*/
		$("[datatype='search-bkdm']").click(
			function() {
				$("input[name='webSearchBkdmValue']").val($(this).attr("datavalue"));
				$("#zxzx_web_form").submit();
			});

		$("#searchZxkzdtn").click(function() {
			var searchValue = $.trim($("#zxzxSearch").val());
			$("#webSearchValue").val(searchValue);
			$("#zxzx_web_form").submit();
		});
		
		$('.main-body-con').append($('#myTabContent'));
		
	});
</script>
<sitemesh:write property='head'/>
</head>
<body>
	<div class="header">
    	<div class="container">
        	<span class="header-title">在线咨询</span>
    	</div>
	</div>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-body" id="inside">
				
				<div class="main-body-con">
					<ul class="nav nav-tabs nav-justified" role="tablist">
						<li role="presentation" class="active">
							<a  href="${base}/zxzx/web/index.zf?layout=zxzxWebindexLayout" id="tlq">讨论区</a>
						</li>
						<li role="presentation">
							<a href="${base}/zxzx/web/faq.zf?layout=zxzxWebindexLayout" id="cjwt">常见问题</a>
						</li>
						<li role="presentation">
							<a href="${base}/zxzx/web/top.zf?layout=zxzxWebindexLayout" id="rmzx">热门资讯</a>
						</li>
						<li role="presentation">
							<a href="${base}/zxzx/web/contactUs.zf?layout=zxzxWebindexLayout" id="lxwm">联系我们</a>
						</li>
						[@shiro.authenticated]
						<li role="presentation">
							<a href="${base}/zxzx/web/myTopic.zf?layout=zxzxWebindexLayout" id="wdzx">我的咨询</a>
						</li>
						[/@shiro.authenticated]
					</ul>
				</div>
				
				<div id="myTabContent" class="tab-content ">
						<div class="panel-body" style="border-bottom: 1px solid #ddd;margin-top:15px;padding:15px"">
							<div class="col-sm-6">
								<i><span><img src="${base}/css/zxzx/images/ico2.jpg">开放时间：
									[#if zxzxConfig.CSSZ_KSSJ_DM == null]
									--
									[#else]
									${zxzxConfig.CSSZ_KSSJ_DM}
									[/#if]
									至  
									[#if zxzxConfig.CSSZ_JSSJ_DM == null]
									不限
									[#else]
									${zxzxConfig.CSSZ_JSSJ_DM}
									[/#if]
								</span></i>
							</div>
						</div>
					
					<sitemesh:write property='body'/>
					
				</div>
			</div>
			</div>
			</div>
			<div class="footer">
			    <div class="container">
			        <p>版权所有© Copyright 1999-2017 我是大牛  浙ICP备 中国</p>
			    </div>
			</div>
		</body>
<!--[if lt IE 9]>
<!--jQuery浏览器检测 -->
<script type="text/javascript" src="${base}/js/browse/browse-judge.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<![endif]-->
[#include "/globalweb/head/niutal-ui-validation.ftl" /]
</html>

