<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" theme="simple" class="form-horizontal sl_all_form">
			<input type="hidden" id="modelId" name="modelId" value="${model.id}"/>
			<input type="hidden" key="key" name="key" value="${model.key}" />
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-4 control-label">流程名称</label>
			          <div class="col-sm-8">
			            	<input type="text" id="name" name="name" maxlength="100" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			      <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-4 control-label">流程类别</label>
			          <div class="col-sm-8">
			            	<input type="text" id="category" name="category" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">流程描述</label>
			          <div class="col-sm-10">
			            	<textarea type="text" id="description" name="description" maxlength="200" rows="2" style="height:initial;" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row">
				<div class="col-md-12 col-sm-12">
				<div style="max-height:500px;overflow-x:hidden;">
				<table class="table" id="process_defination_task_tab">
			      <thead>
			        <tr>
			          <th width="15%"> 任 务 名 称</th>
			          <th width="23%"> 任 务 描 述</th>
			        </tr>
			      </thead>
			      <tbody>
			        <tr>
			          <td><input name="taskName" type="text" class="form-control input-sm" validate="{required:true}" readonly/></td>
			          <td><input name="taskDesc" type="text" class="form-control input-sm" readonly/></td>
			        </tr>
			      </tbody>
			    </table>
			    </div>
			    </div>
			</div>
		</form>
		</div>		
		<script type="text/javascript" src="${base}/js/processManagement/simple-moduler-view.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
