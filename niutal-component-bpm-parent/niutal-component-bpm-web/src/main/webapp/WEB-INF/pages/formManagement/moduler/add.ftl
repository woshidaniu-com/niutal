<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" method="post" data-toggle="validation" action="addData.zf" theme="simple" class="form-horizontal sl_all_form">
			<div class="row">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>表单名称</label>
		          <div class="col-sm-9">
		            	<input type="text" id="name" name="name" maxlength="100"
		           		class="form-control" validate="{required:true}" placeholder="请输入表单名称"/>
		          </div>
		        </div>
			</div>
			
			<div class="row">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">表单描述</label>
		          <div class="col-sm-9">
		            	<textarea type="text" id="description" name="description" placeholder="请输入表单描述" maxlength="200" rows="2" style="height:initial;" class="form-control"/>
		          </div>
		        </div>
			</div>
		</form>
		</div>
	</body>
</html>
