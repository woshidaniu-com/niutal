<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" theme="simple" class="form-horizontal sl_all_form">
			<div class="row">
				<div class="col-md-12 col-sm-12">
				<div class="panel panel-default">
					 <div class="panel-heading">表单预览</div>
					 <div class="panel-body">
					 	 <blockquote>
						  <p>
						  	<a href="javascript:void(0);" id="show_form_diagram_link" data-process-definition-id="${processDefinition.id}"  class="alert-link">点击预览表单</a>
						  </p>
						</blockquote>
				  	</div>
				  		<div class="panel-footer" style="height:300px;display:none" id="form_diagram_el" data-status="initial">
						
						</div>
			    </div>
			    </div>
			</div>
			
			<div class="panel panel-default">
			<div class="panel-heading">表单基本信息</div>
			<div class="panel-body">
			<input type="hidden" id="formDefinitionId" name="formDefinitionId" value="${formDefinition.id}"/>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">名称</label>
			          <div class="col-sm-9">
			            	<input type="text" id="formDefinitionName" name="formDefinitionName" value="${formDefinition.name}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			      
			      <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">版本</label>
			          <div class="col-sm-9">
			            	<input type="text" id="formDefinitionVersion" name="formDefinitionVersion" value="${formDefinition.version}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
			      <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">状态</label>
			          <div class="col-sm-9">
			            	<input type="text" id="formDefinitionSuspensionState" name="formDefinitionSuspensionState" 
			            	value="${(formDefinition.suspensionState==1)?string('启用','停用')}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">描述</label>
			          <div class="col-sm-9">
			            	<textarea rows="3" type="text" id="formDefinitionDescription" name="formDefinitionDescription"  style="height:initial;" class="form-control" readonly>${formDefinition.description}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
			</div>
			</div>
		</form>
		</div>		
		<script type="text/javascript" src="${base}/js/formManagement/preview.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
