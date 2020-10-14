<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<form id="bizDetailForm" theme="simple" class="form-horizontal sl_all_form">
			<input type="hidden" id="id" name="id" value="${model.id}"/>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-4 control-label">流程业务对象名称</label>
			          <div class="col-sm-8">
			            	<input type="text" id="name" name="name" maxlength="100" class="form-control" readonly value="${model.name}"/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">流程业务对象说明</label>
			          <div class="col-sm-10">
			            	<textarea type="text" id="description" name="description" maxlength="200" rows="2" style="height:initial;" class="form-control" readonly>${model.description}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row">
				<div class="col-md-12 col-sm-12">
				<div style="max-height:500px;overflow-x:hidden;">
				<table class="table" id="process_biz_field_tab">
			      <thead>
			        <tr>
			          <th width="15%"> 属 性 名 称</th>
			          <th width="23%"> 属 性 描 述</th>
			          <th width="23%"> 属 性 类 型</th>
			          <th width="23%"> 属 性 值</th>
			        </tr>
			      </thead>
			      <tbody>
			        <tr id="none-data-tr">
			          <td colspan="4">
			          	没有配置业务对象属性
			          </td>
			        </tr>
			      </tbody>
			    </table>
			    </div>
			    </div>
			</div>
		</form>	
		<script type="text/javascript" src="${base}/js/processManagement/biz-view.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>