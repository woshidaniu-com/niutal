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
<head>
</head>
<body>
<div class="form-horizontal sl_all_form sl_all_bg timeSettingInfo" id="timeSettingInfo">
	<div class="row">
		<div class="col-md-12 col-sm-12 align-left">
    		<div class="error_v5" style="min-width: 400px !important; display: block !important;">
				<div class="error_ico"><i class="error2"></i></div>
				<div class="error_con">
					<p class="error_title">抱歉，</p>
					<s:if test="message != null ">
					<p class="error_text" style="font-size: 20px;"><s:property value="message"/></p>
					</s:if>
					<s:else>
					<p class="error_text" style="font-size: 20px;"><s:property value="model.tsxx"/></p>
					<p class="error_text red2" style="font-size: 14px;">开放时间【 <s:property value="model.kssj"/> 至 <s:property value="model.jssj"/>】 </p>
					</s:else>
				</div>
			</div>
	  	</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(e) {
		//根据文本框调整相应提示信息的css
		var bootbox = $("#timeSettingInfo").closest(".bootbox");
		if(bootbox.size() > 0 ){
			var footer = bootbox.find("div.modal-footer");
			footer.find("button.btn").filter("[data-bb-handler!=cancel]").button('loading')
		}else{
			$("#timeSettingInfo").css({
				"min-height": "460px"
			});

			var sizeReset = function(){
				//var body_height	=	$(window).height();
				var error_height	=	$(".error_v5").height() + 50;
				var margin_top	=	(460 - error_height)/2;
				var margin_top	= 	(margin_top>0) ? margin_top : 0;
				$(".error_v5").css("margin-top",margin_top);
			}
			
			$(window).resize(sizeReset);
			sizeReset();
		}
	});
	
</script>
</body>
</html>