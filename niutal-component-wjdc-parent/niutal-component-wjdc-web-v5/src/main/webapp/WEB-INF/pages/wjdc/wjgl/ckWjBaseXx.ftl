[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
		<form role="form" class="form-horizontal sl_all_form">
			<input type="hidden" name="wjid" value="${model.wjid}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							问卷名称
						</label>
						<div class="col-sm-9">
							<input type="text" name="wjmc" id="wjmc"  value="${model.wjmc}" class="form-control input-sm span2" readonly="true"/>
						</div>
					</div>
				</div>
			</div>
			[#if enableSjyxj = 'true']
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							问卷优先级
						</label>
						<div class="col-sm-9">
							<input type="text" name="wjyxj" id="wjyxj"  value="${model.wjyxj}" class="form-control input-sm span2"  readonly="true"/>
						</div>
					</div>
				</div>
			</div>
			[/#if]
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							过期时间
						</label>
						<div class="col-sm-9">
							<input type="text" maxlength="20" name="gqsj" id="gqsj" value="${model.gqsj}" readonly="true"  class="form-control input-sm span2"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							卷首语
						</label>
						<div class="col-sm-9">
							<textarea name="jsy" id="jsy" class="form-control " style="width:100%;height:120px" readonly="true">${model.jsy}</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							卷尾语
						</label>
						<div class="col-sm-9">
							<textarea name="jwy" id="jwy" class="form-control " style="width:100%;height:120px" readonly="true">${model.jwy}</textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>