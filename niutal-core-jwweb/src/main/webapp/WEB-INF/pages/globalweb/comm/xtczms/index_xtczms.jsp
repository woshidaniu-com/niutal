<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/head_v5.ini"%>
	<script type="text/javascript">
		jQuery(function($){
			//绑定退出按钮
			$("#exit").click(function(){
				document.location.href =  _systemPath + "/xtgl/login_logout.html";
			});
			//绑定修改密码
			$("#updatePassword").click(function(){
				$.showDialog(_path+"/xtgl/mmgl_xgMm.html",'修改密码',addConfig);
			});
			//Ajax动态加载新闻内容
			$("#newsnotice").load("index_cxNews.html", {limit: 5});
			//Ajax动态加载用户首页显示信息
			$("#yhxxIndex").load("index_cxYhxxIndex.html?xt=jw");

			//兼容菜单超出窗口显示问题
			$('li.dropdown-submenu').on('mouseenter', function () {
			  // do something…
			  	var dropdown_menu = $(this).find("ul.dropdown-menu");
			  	if(($(dropdown_menu).offset().left + $(dropdown_menu).outerWidth()) > $(window).width()){
			  		$(dropdown_menu).css("left","-100%");
				}
			});
				
		});
	</script>
	<style type="text/css">
		.navbar-right .dropdown-menu label i.glyphicon{
		    background-image: none;
		    background-position: 0 0;
		    background-repeat: repeat;
		    display: inline;
		    height: auto !important;
		    line-height: 1;
		    margin-top: 0 !important;
		    top: 2px !important;
		    vertical-align: baseline;
		    width: auto !important;
		}
	</style>	
</head>
<body>
<!-- top -->
<div class="navbar navbar-default navbar-static-top top1" style="margin-bottom: 15px;">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand logo_2" href="#"><img src="<%=stylePath %>/assets/images/logo/logo_jw_w.png" style="margin-top:-3px" /><%=MessageUtil.getLocaleText("system.title")%></a>
		</div>
	</div>
</div>
<!-- top-end --> 
<div style="width: 100%;padding: 0px;margin: 0px;" id="bodyContainer">
	<input type="hidden" id="sessionUserKey" value="<s:property value="user.yhm"/>" />
	<input type="hidden" id="gnmkdmKey" value="<s:property value="gnmkdm"/>" />
	<div class="container sl_all_bg" id="yhgnPage" style="min-height: 615px !important;">
		<div class="form-horizontal sl_all_form" style="padding-bottom: 15px;">
			<div class="row clearfix"  style="height: 450px;padding-bottom: 30px;overflow-y: auto;">
				<div class="col-md-12 col-sm-12">
				${model.czms}
				</div>
			</div>
			<div class="row" style="border-top: 1px solid #e6e6e6;padding-top: 15px;">
				<div class="col-md-12 col-sm-12 align-center">
					<button id="btn_yd" type="button" class="btn btn-default" style="width: 140px;" disabled="disabled">
				      	已阅读 <span class="badge" id="badge_text">5</span>
				    </button>
			  	</div>
			</div>
		</div>
		
		<script type="text/javascript">
		jQuery(function($){
			var count 	= 5;
			var timer 	= window.setInterval(function(){
				//限制时间已到
				if(count == 0){
					//移除定时器
					window.clearInterval(timer);
					//绑定已阅读点击事件
					$("#btn_yd").prop("disabled",false).addClass("btn-primary").unbind().click(function(){
						//全局文档添加参数
						//加载功能主页：且添加不再进入提示信息页面的标记字段
						window.location.href = _path+'${model.dyym}';
					});
				}else{
					count -= 1;
				}
				$("#badge_text").text(count);
			}, 1000);
			
		});
		</script>
	</div>
</div>
<!-- footer --> 
<jsp:include page="../../bottom.jsp"/>
<!-- footer-end -->
</body>
</html>
