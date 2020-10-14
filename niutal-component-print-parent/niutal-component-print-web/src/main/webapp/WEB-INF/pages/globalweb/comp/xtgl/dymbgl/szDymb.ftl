<!doctype html>
<html>
	<head>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<form action="saveJcsj.zf" id="ajaxForm" data-toggle="validation" method="post" theme="simple" class="form-horizontal sl_all_form">
			<input type="hidden" name="nr" value="${nr}"/>
			<input type="hidden" name="sjyid" value="${sjyid}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-md-3 control-label">
							<span class="red">*</span>模板名称
						</label>
						<div class="col-md-9">
							<input type="text" maxlength="20" name="mc" id="mc" class="form-control input-sm span2"  validate="{required:true, mcUnique:true, stringMaxLength:20}"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-md-3 control-label">
							<span class="red">*</span>模板说明
						</label>
						<div class="col-md-9">
							<input type="text" maxlength="20" name="sm" id="sm" class="form-control input-sm span3" data-rules='{"required":true,"stringMaxLength":200}'/>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>