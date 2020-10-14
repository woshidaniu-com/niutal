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
<html>
<head>
	<title><s:text name="error.title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/niutal-ui.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/error.css?ver=<%=cssVersion%>" />
	<script type="text/javascript">
	//表示是独立错误页面
	if(!document.getElementById("yhgnPage")){
		//加载jquery
		var script = document.createElement( "script" );
			//设置属性
			script.setAttribute('type', 'text/javascript');
			script.setAttribute('media', "all");
			script.setAttribute('charset', "utf-8");
	        script.setAttribute('src', "<%=stylePath%>/assets/js/jquery-1.11.1-min.js?ver=<%=jsVersion%>"); 
	     	// 先把js/css加到页面；如果script节点没有添加到DOM树中，在chrome和firefox中不会响应script的load事件
            document.getElementsByTagName("head")[0].appendChild(script);  
	}
	</script>
</head>
<body>
<div class="error_v5">
	<div class="error_ico"><i class="error3"></i></div>
	<div class="error_con">
		<p class="error_title"><s:text name="error.timeout"/>，</p>
		<p class="error_text2"><span class="time">5</span><s:text name="error.timeout_msg"/><a href="#" class="go_back0" onclick="logout();"><s:text name="error.backnow"/></a></p>
	</div>
</div>
<script type="text/javascript">
function lazyGo() {
	var sec = $(".error_text2 .time").text();
	$(".error_text2 .time").text(--sec);
	if (sec > 0){
		setTimeout("lazyGo();", 1000);
	}else{
		window.location.href = "<%=systemPath%>/xtgl/login_logout.html";
	}
}

function logout(){
	document.location.href = "<%=systemPath%>/xtgl/login_logout.html";
}

var times_1 = 0;
var intervalFunc = function(){
	if(jQuery){
		jQuery(function($) {
			//计算高度
			window.onresize = function(){  
				var body_height	=	$(window).height();
				var error_height	=	$(".error_v5").height() + 50;
				var margin_top	=	(body_height - error_height)/2;
				var margin_top	= 	(margin_top>0) ? margin_top : 0;
				$(".error_v5").css("margin-top",margin_top);
			} 
			
			$(window).resize();
			setTimeout("lazyGo();", 1000);
		});
	}else{
		if(times_1 <= 10){
			window.setTimeout(intervalFunc, 200);
		}
	}
	times_1 += 1;
};
window.setTimeout(intervalFunc,100);

</script>
</body>
</html>
