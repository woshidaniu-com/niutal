[#assign encoder=JspTaglibs["https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"] /]
<!doctype html>
<html>
	<head>
		
	</head>
	<body>
	
	<form id="ajaxForm" id="ajaxForm" method="post" action="hfBcZxwt.zf" data-toggle="validation" class="form-horizontal sl_all_form">
	 <input type="hidden" name="zxid" value="${model.zxid}" />
	 <div class="row">
		 <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label">咨询人:</label>
	          <div class="col-sm-7" style="margin-top: 8px">
	            	${model.zxrModel.xm}
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label">咨询时间:</label>
	          <div class="col-sm-7" style="margin-top: 8px">
	            	${model.zxsj}
	          </div>
	        </div>
	      </div>
	      
	       <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label">咨询版块:</label>
	          <div class="col-sm-7" style="margin-top: 8px">
	            	${model.kzdkModel.bkmc}
	          </div>
	        </div>
	      </div>
	      
<!-- 	       <div class="col-md-12 col-sm-12"> -->
<!-- 	        <div class="form-group"> -->
<!-- 	          <label for="" class="col-sm-3 control-label">咨询主题:</label> -->
<!-- 	          <div class="col-sm-7" style="margin-top: 8px"> -->
<!-- 	            	${model.kzdt} -->
<!-- 	          </div> -->
<!-- 	        </div> -->
<!-- 	      </div> -->
	      
	       <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label">咨询内容:</label>
	          <div class="col-sm-7" style="word-wrap: break-word;word-break: normal;margin-top: 8px">
	            	${model.zxnr}
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label"><span><span class="red">*</span>回复内容:</span></label>
	          <div class="col-sm-7" style="margin-top: 8px">
	            	<textarea validate="{required:true,stringMaxLength:1000}" 
	            	style="width:100%;height:100px" name="zxhfModel.hfnr" id="hfnr"  class="form-control">${model.zxhfModel.hfnr}</textarea>
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>回复后是否公开:</label>
	          <div class="col-sm-7" >
	          		<label class="radio-inline" for="sfgk_1">
						<input id="sfgk_1" validate="{required:true}" type="radio" name="sfgk" value="1" 
						[#if model.sfgk == '1']
						checked="checked"
						[/#if]
						><span class="label label-success">开启</span></input>
					</label>
					<label class="radio-inline" for="sfgk_0">
						<input id="sfgk_0" validate="{required:true}" type="radio" name="sfgk" value="0" 
						[#if model.sfgk == '0']
						checked="checked"
						[/#if]
							>
							<span class="label label-danger">关闭</span>
						</input>
					</label>
	          </div>
	        </div>
	      </div>
	      
	</div>
	 
	
</form>
</body>
</html>