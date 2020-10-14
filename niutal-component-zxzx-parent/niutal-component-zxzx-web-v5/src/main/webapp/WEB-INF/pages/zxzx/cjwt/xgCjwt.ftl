[#assign encoder=JspTaglibs["https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"] /]
<!doctype html>
<html>
	<head>
	
	</head>
	<body>
	<form id="ajaxForm" id="ajaxForm" method="post" action="xgBcCjwt.zf" data-toggle="validation" class="form-horizontal sl_all_form">
	 	<input type="hidden" name="wtid" value="${model.wtid}" />
		
<!-- 		<div class="row"> -->
<!-- 				 <div class="col-md-12 col-sm-12"> -->
<!-- 			        <div class="form-group"> -->
<!-- 			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>咨询主题</label> -->
<!-- 			          <div class="col-sm-9"> -->
<!-- 			            	<input type="text" id="wtbt" name="wtbt" -->
<!-- 			           		class="form-control" validate="{required:true,stringMaxLength:250}" value="${model.wtbt}"/> -->
<!-- 			          </div> -->
<!-- 			        </div> -->
<!-- 			      </div> -->
<!-- 			</div> -->
	  
	  		<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>咨询版块</label>
			          <div class="col-sm-9">
			          	<select id="bkdm" name="bkdm" class="form-control" validate="{required:true}">
			          		<option value="">--请选择咨询板块--</option>
						 	[#list bkdmList as item]
							<option value="${item.bkdm}"  [#if model.bkdm == item.bkdm] selected [/#if] >${item.bkmc}</option>
						 	[/#list]
						 </select>
			          </div>
			        </div>
			      </div>
			</div>
	  
	  		<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>启用状态</label>
			          <div class="col-sm-9">
			          		<label class="radio-inline" for="sffb_1">
								<input id="sffb_1" validate="{required:true}" type="radio" name="sffb" value="1" 
								[#if model.sffb == '1'] checked="checked"[/#if]
								><span class="label label-success">开启</span></input>
							</label>
							<label class="radio-inline" for="sffb_0">
								<input id="sffb_0" validate="{required:true}" type="radio" name="sffb" value="0"
								[#if model.sffb == '0'] checked="checked"[/#if]
								><span class="label label-danger">关闭</span></input>
							</label>
			          </div>
			        </div>
			      </div>
			</div>
	  		
	  		<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>咨询内容<font color="red">(限1000字)</font></label>
			          <div class="col-sm-9" id="ke_control">
		                 <textarea validate="{required:true,stringMaxLength:1000}" style="width:100%;height:100px" name="wtnr" id="wtnr"  class="form-control">${model.wtnr}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>回复内容<font color="red">(限1000字)</font></label>
			          <div class="col-sm-9" id="ke_control">
		                 <textarea validate="{required:true,stringMaxLength:1000}" style="width:100%;height:100px" name="wthf" id="wthf"  class="form-control">${model.wthf}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
		
	</form>
</body>
</html>