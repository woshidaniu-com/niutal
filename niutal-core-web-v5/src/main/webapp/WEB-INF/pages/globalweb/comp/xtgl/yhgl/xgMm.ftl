<!doctype html>
<html>
<head>
	
</head>
<body>
	<form id="ajaxForm" method="post" action="${base}/xtgl/yhgl/savePassword.zf" data-toggle="validation"  role="form" class="form-horizontal sl_all_form" >
		
			
		<div class="row">
			 <div class="col-md-12 col-sm-12">
			 	
			 	[#if rules?size > 0]
			 			<div id="closeAlert" class="alert alert-dark">
			 			<a href="#" class="close" data-dismiss="alert">×</a>
			 			<strong>密码规则：</strong>
						[#list rules as item]
							<p>${item_index+1}. ${item}</p>
						[/#list]
						</div>
					[/#if]
					
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">
		          	<span style="color:red;">*</span>原密码
	          	</label>
		          <div class="col-sm-8">
		            	 <input type="password"  name="ymm" id="ymm" class="form-control input-sm"  validate="{required:true}" />
		          </div>
		        </div>
		      </div>
		      <div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">&nbsp;</label>
					<div class="col-sm-8" id="strengthID"></div>
				</div>
			 </div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">
						<span class="red">*</span>新密码
					</label>
					<div class="col-sm-8" >
						<input type="password" name="mm" id="mm" class="form-control input-sm" data-rules='{"required":true}'/>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">
						<span class="red">*</span>重复新密码
					</label>
					<div class="col-sm-8">
						<input type="password" name="nmm" id="nmm" class="form-control input-sm" data-rules='{"required":true,"equalTo":"#mm"}'/>
					</div>
				</div>
			</div>
		</div>
   </form>
 </body>
 <script type="text/javascript">
 	$(function(){
 		$("#mm").strength({"target":"#strengthID"});
 		$.extend($.validator.messages, {
			equalTo: "两次密码输入不一致"
		});
 	});
 </script>
</html>
