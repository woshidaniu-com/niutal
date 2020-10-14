<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" method="post" data-toggle="validation" action="update-metainfo-data.zf" theme="simple" class="form-horizontal sl_all_form">
			<input type="hidden" id="modelId" name="modelId" value="${modelId}"/>
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>表单名称</label>
			          <div class="col-sm-10">
			            	<input type="text" id="name" name="name" maxlength="100"
			           		class="form-control" validate="{required:true}" 
			           		value="${name}"
			           		placeholder="请输入表单名称"/>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">表单描述</label>
			          <div class="col-sm-10">
			            	<textarea type="text" id="description" name="description" maxlength="200" rows="2" style="height:initial;" class="form-control" placeholder="请输入表单描述">${description}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
		</form>
		</div>
	</body>
</html>
