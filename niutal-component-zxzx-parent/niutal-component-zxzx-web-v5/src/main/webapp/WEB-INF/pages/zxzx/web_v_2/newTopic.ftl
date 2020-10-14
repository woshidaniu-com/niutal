<!doctype html>
<html>
<head>
<script type="text/javascript">
	function refreshCode(){
		document.getElementById("yzmPic").src = _path + '/kaptcha?time=' + new Date().getTime();
	}
	
	jQuery(function(){
		jQuery("#askQuestionSubmitBtn").click(function(){
			var kzdt = jQuery("#kzdt"),zxnr = jQuery("#zxnr"), bkdm = jQuery("#bkdm"), yzm = jQuery("#yzm");
			var valid = true;
			var kzdtval = jQuery.trim(kzdt.val());
			var zxnrval = jQuery.trim(zxnr.val());
			var bkdmval = jQuery.trim(bkdm.val());
			var yzmval = jQuery.trim(yzm.val());
			if(kzdtval == ""){
				kzdt.css("background-color","#FBE7E7");
				valid = false;
			}
			if(zxnrval == ""){
				zxnr.css("background-color","#FBE7E7");
				valid = false;
			}
			if(bkdmval == ""){
				bkdm.css("background-color","#FBE7E7");
				valid = false;
			}
			if(yzmval == ""){
				yzm.css("background-color","#FBE7E7");
				valid = false;
			}
			return valid;
		});
	});
	
</script>
</head>
<body style="">
	<div class="mainbody-newtopic">
		<div class="mainframe mainframe_qz">
			<div class="establish_qz">
				<form data-toggle="validation" class="form-horizontal sl_all_form" action="newTopicSubmit.zf" id="ajaxForm">
					
					<div class="row">
						 <div class="col-md-12 col-sm-12">
					        <div class="form-group">
					          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>标题</label>
					          <div class="col-sm-9">
					            	<input type="text" id="kzdt" name="kzdt"
					           		class="form-control" validate="{required:true,stringMaxLength:200}" />
					          </div>
					        </div>
					      </div>
					</div>
			  
			  		<div class="row">
						 <div class="col-md-12 col-sm-12">
					        <div class="form-group">
					          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>版块类别</label>
					          <div class="col-sm-9">
					          	<select id="bkdm" name="bkdm" class="form-control" validate="{required:true}">
					          		<option value="">--请选择咨询板块--</option>
								 [#list kzdkList as item]
									<option value="${item.bkdm}">${item.bkmc}</option>
								 [/#list]
								 </select>
					          </div>
					        </div>
					      </div>
					</div>
					
					<div class="row">
						 <div class="col-md-12 col-sm-12">
					        <div class="form-group">
					          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>咨询内容<font color="red">(限1000字)</font></label>
					          <div class="col-sm-9" id="ke_control">
				                 <textarea validate="{required:true,stringMaxLength:1000}" style="width:100%;height:100px" name="zxnr" id="zxnr"  class="form-control"></textarea>
					          </div>
					        </div>
					      </div>
					</div>
					
					<div class="row">
						 <div class="col-md-12 col-sm-12">
						 
					        <div class="form-group">
					          <label for="" class="col-xs-2 col-sm-2 control-label"><span style="color:red;">*</span>验证码</label>
					          <div class="col-xs-2 col-sm-2">
					            	<input type="text" id="yzm" name="yzm"
					           		class="form-control" validate="{required:true}" />
					          </div>
					          <div class="input-group col-xs-4">
							      <img border="0" align="absmiddle" width="75px" height="25px" id="yzmPic" class="yzmPic" onclick="javascript:refreshCode();" name="yzmPic" src="${base}/kaptcha" >
						      </div>
					        </div>
					      </div>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</body>
</html>
