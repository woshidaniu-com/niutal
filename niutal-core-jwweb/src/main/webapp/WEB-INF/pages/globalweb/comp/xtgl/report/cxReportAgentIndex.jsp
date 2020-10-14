<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!doctype html>
<html>
<head> 
	<meta http-equiv="X-UA-Compatible" content="IE=9">  
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript">
	jQuery(function($){
		
		function getQueryStringRegExp(name) {   
		   var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");     
		   if (reg.test(location.href)) {   
		   	return unescape(RegExp.$2.replace(/\+/g, " "));   
		   } else {   
		    return "";   
		   }   
		}  
		//try{
			if(window.top){
				//报表页面document对象
				var doc = window.top.document;
				var reportFrame = doc.getElementById('reportFrame');
				if(reportFrame){
					if(doc.getElementById('ReportModal') && jQuery.contains(doc.getElementById('ReportModal'),doc.getElementById('reportFrame'))){
						//doc.getElementById('reportFrame').height = 450;
						$(reportFrame).height(450);
					}else{
						//解析参数
						//iframe高度
						var h = getQueryStringRegExp("frameHeight");
						$(reportFrame).height(parseInt(h||"450") + 30);
					}
					var loading_status = doc.getElementById('loading_status');
					if(loading_status){
						$(loading_status).hide();
					}
					$(reportFrame).fadeIn("slow");
				}
				//当前页
				var cp = getQueryStringRegExp("curPage");
				//总页数
				var tp = getQueryStringRegExp("talPage");
		
				doc.getElementById('page_cpage').innerHTML = cp;
				doc.getElementById('page_tpage').innerHTML = tp;
				
				var first = doc.getElementById('page_first');
				var prev = doc.getElementById('page_prev');
				var next = doc.getElementById('page_next');
				var last = doc.getElementById('page_last');
				if(cp == 1){
					if(first){$(first).addClass('disabled');}
					if(prev){$(prev).addClass('disabled');}
				}else{
					if(first){$(first).removeClass('disabled');}
					if(prev){$(prev).removeClass('disabled');}
				}
				if(cp == tp){
					if(last){$(last).addClass('disabled');}
					if(next){$(next).addClass('disabled');}
				}else{
					if(last){$(last).removeClass('disabled');}
					if(next){$(next).removeClass('disabled');}
				}
				//判断是否显示分页按钮
				if(parseInt(tp||"0") <= 1 ){
					if(first){$(first).addClass('disabled');}
					if(prev){$(prev).addClass('disabled');}
					if(last){$(last).addClass('disabled');}
					if(next){$(next).addClass('disabled');}
				}
			}
		//}catch (e) {
		//}
	});
	</script>
</head>
<body>
</body>
</html>
