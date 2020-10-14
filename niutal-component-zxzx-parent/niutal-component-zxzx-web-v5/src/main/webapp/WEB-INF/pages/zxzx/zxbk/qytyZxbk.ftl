<!doctype html>
<html>
<head>
</head>
	<body>
		<form id="ajaxForm" id="ajaxForm" method="post" action="qytyBckzdk.zf" data-toggle="validation" class="form-horizontal sl_all_form">
		<input type="hidden" name="bkdm" value="${model.bkdm}" />
		
		<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>启用状态</label>
			          <div class="col-sm-8">
			          		<label class="radio-inline" for="sfqy_1">
								<input id="sfqy_1" validate="{required:true}" type="radio" name="sfqy" value="1" [#if model.sfqy == '1'] checked="checked" [/#if]><span class="label label-success">启用</span></input>
							</label>
							<label class="radio-inline" for="sfqy_0">
								<input id="sfqy_0" validate="{required:true}" type="radio" name="sfqy" value="0" [#if model.sfqy == '0'] checked="checked" [/#if]><span class="label label-danger">停用</span></input>
							</label>
			          </div>
			        </div>
			      </div>
			</div>
		
		</form>
	</body>
</html>