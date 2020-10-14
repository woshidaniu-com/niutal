<!doctype html>
<html lang="zh-CN">
	<head>
		<script type="text/javascript">
			function save(){
				submitForm("systemConfigForm",function(responseText,statusText){
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"],function() {
						});
					} else{
						$.alert(responseText["message"],function() {
						});
					}
				});
			}
		</script>
	</head>  
	<body>  
		<div style="width: 100%; padding: 0px; margin: 0px;" id="bodyContainer">
			<div class="container container-func sl_all_bg">
		  		<!-- page content start -->  
				<form id="systemConfigForm"  class="form-horizontal sl_all_form" data-toggle="validation" action="${base}/xtgl/xtsz/saveXtsz.zf">
				<div class="row">
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label"><font color="red">*</font>学校代码</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="xxdm" name="xxdm" value="${xtszModel.xxdm}" readonly="true"/>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label"><font color="red">*</font> 学校名称</label>
							<div class="col-sm-8">
								 <input class="form-control" type="text" id="xxmc" name="xxmc" value="${xtszModel.xxmc}" readonly="true"/>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label"><font color="red">*</font>当前学年</label>
							<div class="col-sm-8">
								<select class="form-control input-sm chosen-select" name="dqxn" id="dqxn"  value="${xtszModel.dqxn}">
										[#list xnList as item]
			                    			<option value="${item.dm}"
			                    			[#if xtszModel.dqxn==item.dm]
			                    				selected="selected"
			                    			[/#if]
			                    			>${item.mc}</option>
			                    		[/#list]
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#dqxn').trigger("chosen");
			 			    	</SCRIPT> 
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label"><font color="red">*</font>当前学期</label>
							<div class="col-sm-8">
								<select class="form-control input-sm chosen-select" name="dqxq" id="dqxq"  value="${xtszModel.dqxq}">
										[#list xqList as item]
			                    			<option value="${item.dm}"
			                    			[#if xtszModel.dqxq==item.dm]
			                    				selected="selected"
			                    			[/#if]
			                    			>${item.mc}</option>
			                    		[/#list]
								</select>
								
								<SCRIPT type="text/javascript">
						    		jQuery('#dqxq').trigger("chosen");
			 			    	</SCRIPT>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label"><font color="red">*</font>当前年度</label>
							<div class="col-sm-8">
								<select class="form-control input-sm chosen-select" name="dqnd" id="dqnd"  value="${xtszModel.dqnd}">
									[#list ndList as item]
		                    			<option value="${item.value}"
		                    			[#if xtszModel.dqnd==item.value]
		                    				selected="selected"
		                    			[/#if]
		                    			>${item.text}</option>
		                    		[/#list]
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#dqnd').trigger("chosen");
			 			    	</SCRIPT>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="row sl_aff_btn">
				<div class="col-sm-12">
					<button class="btn btn-primary btn-sm" id="bnt" type="button" onclick="save()"> 保 存 </button>
				</div>
			</div>
	   		 <!-- page content end -->  
		   </div>
		</div>
	</body>  
</html>