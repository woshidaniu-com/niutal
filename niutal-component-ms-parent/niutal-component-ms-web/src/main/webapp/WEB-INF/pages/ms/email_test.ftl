<!doctype html>
<html lang="zh-CN">
	<head>
		[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
	</head>
	<body>
		<form action="mailClinetTestSend.zf" data-toggle="validation" role="form" class="form-horizontal sl_all_form" id="ajaxForm" method="post" theme="simple">
			<input type="hidden" name="skey" value="${skey}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>目的邮箱地址
						</label>
						<div class="col-sm-8">
							<input type="text" maxlength="20" name="sendTo" id="sendTo" value="" class="form-control input-sm span2" data-rules="{"required":true,"email2":true}">
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>邮件主题
						</label>
						<div class="col-sm-8">
							<input type="text" maxlength="20" name="subject" id="subject" class="form-control  input-sm span3" data-rules="{"required":true}">
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>邮件内容
						</label>
						<div class="col-sm-8">
							<textarea type="text" style="height: 500px;" name="content" id="content" data-rules="{"required":true}" class="form-control input-sm span4"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
		<button id="btn_send" style="position: relative;left: 50%;">测试发送</button>
		<script>
			$("#btn_send").click(function(){
				submitForm("ajaxForm",function(responseData,statusText){
					if(responseData["status"] == "success"){
						$.success(responseData["message"]);
					}else{
						$.error(responseData["message"]);
					}
				});
			});
		</script>
	</body>
</html>