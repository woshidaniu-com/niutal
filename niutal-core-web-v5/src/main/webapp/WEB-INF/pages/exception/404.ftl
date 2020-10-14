<!doctype html>
<head>
	<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/css/error.css?ver=${messageUtil("niutal.cssVersion")}" />
	<!--jQuery核心框架库 -->
	<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery-1.11.3-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
</head>
<body>
<div class="error_v5">
	<div class="error_ico"><i class="error1"></i></div>
	<div class="error_con">
		<p class="error_title">抱歉，</p>
		<p class="error_text0 hidden">您访问的页面不存在</p>
		<p class="error_text"></p>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function(e) {
		$(window).resize();
		
		changeText($(".error_text0"),$(".error_text"),50);
	});
	//计算高度
	window.onresize = function(){  
		var body_height	=	$(window).height();
		var error_height	=	$(".error_v5").height() + 50;
		var margin_top	=	(body_height - error_height)/2;
		var margin_top	= 	(margin_top>0) ? margin_top : 0;
		$(".error_v5").css("margin-top",margin_top);
	} 
	
	//文字打印效果
	function changeText(cont1,cont2,speed){
		var Otext=cont1.text();
		var Ocontent=Otext.split("");
		var i=0;
		function show(){
			if(i<Ocontent.length)
			{		
				cont2.append(Ocontent[i]);
				i=i+1;
			};
		};
			var Otimer=setInterval(show,speed);	
	};
</script>

</body>
</html>