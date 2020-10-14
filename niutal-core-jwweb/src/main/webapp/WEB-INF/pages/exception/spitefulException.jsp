<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%
	String stylePath = MessageUtil.getText("system.stylePath");
	String jsVersion = MessageUtil.getText("niutal.jsVersion");
	String cssVersion = MessageUtil.getText("niutal.cssVersion");
	String systemPath = request.getContextPath();
%>	
<!doctype html>
<head>
	<title>恶意刷新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/plugins/bootstrap/css/bootstrap.min.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/niutal-ui.css?ver=<%=cssVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=stylePath%>/assets/css/error.css?ver=<%=cssVersion%>" />
	<script type="text/javascript">
	
	var _systemPath = "<%=systemPath %>";
	var _stylePath = "<%=stylePath %>";
	
	//表示是独立错误页面
	if(!document.getElementById("yhgnPage")){
			//加载jquery
		var script = document.createElement( "script" );
			//设置属性
			script.setAttribute('type', 'text/javascript');
			script.setAttribute('media', "all");
			script.setAttribute('charset', "utf-8");
	        script.setAttribute('src', "<%=stylePath%>/assets/js/jquery-1.11.1-min.js?ver=<%=jsVersion%>");
	      	//加载资源加载器
	        script = document.createElement( "script" );
			//设置属性
			script.setAttribute('type', 'text/javascript');
			script.setAttribute('media', "all");
			script.setAttribute('charset', "utf-8");
	        script.setAttribute('src', "<%=stylePath%>/assets/plugins/bootstrap/js/bootstrap.min.js?ver=<%=jsVersion%>");
	     	// 先把js/css加到页面；如果script节点没有添加到DOM树中，在chrome和firefox中不会响应script的load事件
            document.getElementsByTagName("head")[0].appendChild(script);  
	}
	</script>
</head>
<body>
	<div class="form-horizontal sl_all_form sl_all_bg" style="min-height: 500px;">
		<div class="row">
			<div class="col-md-12 col-sm-12 align-center">
	    		<div class="red " style="font-size: 20px;margin-top: 150px;" role="alert" id="messageTip">
				  
				</div>
		  	</div>
		</div>
	</div>
</body>
<script type="text/javascript">

var times_1 = 0;
var intervalFunc = function(){
	if(jQuery){
		jQuery(function($) {

			$("#messageTip").html("${msg}");
			var time = parseInt($("#times").data("time"));
			var	interval = window.setInterval(function(){
				time -= 1;
				if(time <= 0){
					window.clearInterval(interval);
					$("#messageTip").html("请重新刷新页面或者点击按钮!").removeClass("red2").addClass("green");
				}
				$("#times").text(time);
			}, 1000);
		});
	}else{
		if(times_1 <= 10){
			window.setTimeout(intervalFunc, 300);
		}
	}
	times_1 += 1;
};
window.setTimeout(intervalFunc,100);
</script>
</html>
