[#assign encoder=JspTaglibs["https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"] /]
<!doctype html>
<html>
	<head>
		
	</head>
	<body>
	
	
	<form id="ajaxForm" id="ajaxForm" method="post" class="form-horizontal sl_all_form">
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">咨询人</label>
	          <div class="col-sm-9">
	            	<input type="text" value="${model.zxrModel.xm}"
	           		class="form-control" readonly/>
	          </div>
	        </div>
	     </div>
	     
	     <div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">咨询时间</label>
	          <div class="col-sm-9">
	            	<input type="text" value="${model.zxsj}"
	           		class="form-control" readonly/>
	          </div>
	        </div>
	     </div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">咨询版块</label>
	          <div class="col-sm-9">
	            	<input type="text" value="${model.kzdkModel.bkmc}"
	           		class="form-control" readonly/>
	          </div>
	        </div>
	     </div>
	</div>
	
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-12 col-sm-12"> -->
<!-- 	        <div class="form-group"> -->
<!-- 	          <label for="" class="col-sm-2 control-label">咨询主题</label> -->
<!-- 	          <div class="col-sm-9"> -->
<!-- 	            	<input type="text" value="${model.kzdt}" -->
<!-- 	           		class="form-control" readonly/> -->
<!-- 	          </div> -->
<!-- 	        </div> -->
<!-- 	     </div> -->
<!-- 	</div> -->
	
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">咨询内容</label>
	          <div class="col-sm-9">
	            	<textarea style="width:100%;height:100px" class="form-control" readonly>${model.zxnr}</textarea>
	          </div>
	        </div>
	     </div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">回复状态</label>
	          <div class="col-sm-9" style="padding-top: 7px;">
	            	[#if model.hfzt=="1"]
	    			<span class="label label-success">已回复</span>
		    		[#elseif model.hfzt == "0"]
		    		<span class="label label-danger">未回复</span>
		    		[#else]
		    		<span class="label label-info">未知</span>
		    		[/#if]
	          </div>
	        </div>
	     </div>
	</div>
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">咨询内容</label>
	          <div class="col-sm-9">
	            	<textarea  style="width:100%;height:100px" class="form-control" readonly>${model.zxhfModel.hfnr}</textarea>
	          </div>
	        </div>
	     </div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-sm-12">
	        <div class="form-group">
	          <label for="" class="col-sm-2 control-label">回复后是否公开</label>
	          <div class="col-sm-9" style="padding-top: 7px;">
	            	[#if model.sfgk=="1"]
	    			<span class="label label-success">公开</span>
		    		[#elseif model.sfgk == "0"]
		    		<span class="label label-danger">不公开</span>
		    		[#else]
		    		<span class="label label-info">未设置</span>
		    		[/#if]
	          </div>
	        </div>
	     </div>
	</div>
	</form>
</body>
</html>