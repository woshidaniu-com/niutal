<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<div class="form-horizontal sl_all_form" style="padding-bottom: 15px;">
		<div class="row clearfix"  style="height: 500px;padding-bottom: 30px;overflow-y: auto;">
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
					$(document).data("offDetails","1");
					//加载功能主页：且添加不再进入提示信息页面的标记字段
					onClickMenu.call(this,'${model.dyym}','${model.gnmkdm}',{"offDetails":"1"});
				});
			}else{
				count -= 1;
			}
			$("#badge_text").text(count);
		}, 1000);
		
	});
	</script>
</body>
</html>
