<!doctype html>
<html>
	<head>
	</head>
	<body>
		<form id="ajaxForm" action="saveJsxx.zf" method="post" data-toggle="validation" theme="simple"  role="form" class="form-horizontal sl_all_form">
			<input type="hidden" name="jsdm" value="${jsdm}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>角色名称
						</label>
						<div class="col-sm-8">
							<input type="text" name="jsmc" id="jsmc" maxlength="20" class="form-control  input-sm span3" validate="{required:true,stringMinLength:2,stringMaxLength:20,unique:['V0101']}"/>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">
							启用状态
						</label>
						<div class="col-sm-8">
							<label class="radio-inline">
								<input type="radio" style="cursor: pointer;" checked="checked" name="qyzt" value="1" /><span> 启 用 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" name="qyzt" value="0" /><span> 停 用 </span>
							</label>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group ">
						<label class="col-sm-2 control-label" >
							角色说明
						</label>
						<div class="col-sm-8">
							<textarea name="jssm" id="jssm"  class="form-control" rows="2" style="height: 50px;"  validate="{stringMaxLength:1000}"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>