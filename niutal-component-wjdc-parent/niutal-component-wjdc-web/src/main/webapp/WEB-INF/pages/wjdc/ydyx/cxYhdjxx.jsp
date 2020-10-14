<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<%@ include file="/ydyx/pagehead.ini"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>问卷调查</title>
<meta name="keywords" content="移动问卷调查" />
<meta name="description" content="移动问卷调查" />

<script type="text/javascript">
	jQuery(function(){
		//获取用户答卷列表
		var url = _path+'/zfxg/wjdc/ydyhdjgl_queryYhdjxx.html?timestamp='+ new Date().getTime();
		jQuery.getJSON(url, {}, function(data){
			if(data != null || data.size > 0){
					jQuery.each(data , function(i, n){
						var wjid = data[i]['wjid'];
						var wjlx = data[i]['wjlx'];
						var wjlxmc = data[i]['wjlxmc'];
						var wjmc = data[i]['wjmc'];
						var djzt = data[i]['djzt'];
						var li = jQuery("<li>");
						var href = _path+"/zfxg/wjdc/ydstgl_yhdj.html?wjModel.wjid="+wjid;
						jQuery('<a>', {"href": href}).append(jQuery('<p>', {"class": "float_l", text: wjmc}))
									 .append(jQuery('<p>', {"class": "float_r", text: djzt})).appendTo(li)
						jQuery('#wjlist').append(li);
					});
				}
			var myscroll=new iScroll("wrapper");
			});
	});
</script>
</head>

<body>
<div class="bodymain">
    <%--<header class="header">
		<div class="header_back"><a href="#"></a></div>
    	<div class="header_title">我的问卷</div>
    </header>
    --%><div class="mainframe" id="wrapper">
		<div class="mainframe1">
		
			<div class="ass_information">
				<ul id="wjlist"></ul>
			</div>
		</div>
    </div>
	<%--<div class="footer">
		<ul>
			<li class="f1 cur"><a href="#">首页</a></li>
			<li class="f2"><a href="#">自助报到</a></li>
			<li class="f3"><a href="#">信息查看</a></li>
		</ul>
	</div>
--%></div>
</body>
</html>