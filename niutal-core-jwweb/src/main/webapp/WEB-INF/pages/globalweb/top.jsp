<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.common.log.User"%>
<%@page import="JsglModel"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<%
		String appLogo = MessageUtil.getText("system.logo.app");
	%>

	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?ver=<%=jsVersion%>"></script>
	<script type="text/javascript">
	jQuery(function($) {
		viewMenu();
		//初始化密码
		checkPassStrength();
		navigateInit();//导航事件初始化

		//Ajax动态加载新闻内容
		$(".newsnotice").load("index_cxNews.html", {limit: 7});

	 	/*
		jQuery.ajax({
			url:"index_cxNews.html",
			type:"post",
			dataType:"json",
			success:function(data){
				alert(data);
			}
		});
	*/
		//禁用右键
		//jQuery(document.body).disableContextMenu();
	});
	
	function logout(){

		showConfirmDivLayer('确定注销？',{'okFun':function(){
			document.location.href="login_logout.html";
		 }
		});
	}
	//将当前选中的菜单样式突出
	function viewMenu() {
		jQuery('ul.ul_find > li').removeClass();
		var classbz = jQuery('#classbz').val();
		if (classbz != null && classbz != "") {
			classbz = "li_"+classbz;
			jQuery('#'+classbz).parent().addClass('current');
		} else {
			jQuery('#li_page').parent().addClass('current');
		}
	}
	
	//修改密码
	function xgMm(){
		showWindow('修改密码',450,320,'<%=jsPath %>/xtgl/yhgl_xgMm.html');
	}
	
	//验证密码强度
	function checkPassStrength(){
		var yhmmdj="${user.yhmmdj}";
		//用户密码等级小于等于1  ,也就是弱密码
		if(parseInt(yhmmdj) <= 1){
			alert('您的密码过于简单,为了系统安全,请先修改密码!','',{'clkFun':function(){
				xgMm();
		  	}});
		}
	}
	//导航事件 初始化
	function navigateInit(){
		jQuery(".menu .nav .ul_find a[id^='li_']").click(function(){
			var mkdm = jQuery(this).attr("id").substr(3);
			navigate(mkdm);				
		});			
	}

	//导航事件 
	function navigate(gnmkdm,params){
		var mkdm = "";
		var curmkdm = "";
		if(gnmkdm == null || gnmkdm == ""){
			return;
		}else if(gnmkdm.length <= 3 || gnmkdm == "page"){
			mkdm = gnmkdm;
		}else{
			mkdm = gnmkdm.substr(0,3);
			curmkdm = gnmkdm;
		}
		var url = "<%=systemPath %>/xtgl/";
		jQuery('ul.ul_find > li').removeClass();
		if(mkdm == "page"){
			jQuery('#li_page').parent().addClass('current');
			url += "index_initMenu.html";
			location.href = url;	
		}else{
			jQuery("#li_" + mkdm).parent().addClass('current');
			url += "index_content.html?gnmkdm=" + mkdm;
			if(curmkdm != ""){
				url += "&curmkdm=" + curmkdm;
			}
			if(params != null && params != ""){
				url += "&params=" + params;
			}
			jQuery(parent.document).find("#mainframe").load(url);
		}
	}
</script>
<!-- top -->
<div class="navbar navbar-default navbar-static-top top1">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><img src="<%=stylePath %>/assets/images/logo.png" width="290" height="30"></a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<li>
				<a href="#" class="dropdown-toggle grxx" data-toggle="dropdown"><img src="<%=stylePath %>/assets/images/user_logo.jpg" width="28" height="28"></a>
				<ul class="dropdown-menu">
					<li><a href="#">未读通知</a></li>
					<li class="divider"></li>
					<li class="dropdown-header">当前还没有新消息</li>
					<li class="dropdown-header">查看所有通知</li>
					<li><a href="#">未读通知</a></li>
					<li class="divider"></li>
					<li><a href="#">退出</a></li>
				</ul>
			</li>
		</ul>
		<div class="navbar-form navbar-right">
			<input type="text" class="form-control" placeholder="搜索服务功能">
		</div>
	</div>
</div>
<!-- top-end --> 
