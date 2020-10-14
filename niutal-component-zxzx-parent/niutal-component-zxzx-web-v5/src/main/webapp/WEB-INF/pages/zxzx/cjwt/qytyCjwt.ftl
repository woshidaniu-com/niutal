<!doctype html>
<html>
	<head>
	</head>
	<body>
	<form id="ajaxForm" id="ajaxForm" method="post" action="qytyBcCjwt.zf" data-toggle="validation" class="form-horizontal sl_all_form">
		<input type="hidden" name="wtid" value="${model.wtid}" />
		
			<!--
				<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">咨询主题</label>
			          <div class="col-sm-9">
			            	<input type="text" id="wtbt" name="wtbt"
			           		class="form-control" readonly value="${model.wtbt}"/>
			          </div>
			        </div>
			      </div> 
			</div>
 				-->
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>启用状态</label>
			          <div class="col-sm-8">
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

		</form>
	</body>
</html>