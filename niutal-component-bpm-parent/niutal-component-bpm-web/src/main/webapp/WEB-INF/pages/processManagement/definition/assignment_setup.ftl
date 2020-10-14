<!DOCTYPE html>
<html>
	<head>
		<style>
			.list-group .list-group-item-sm{
				position: relative;
				display: block;
				padding: 5px 10px;
				margin-bottom: -1px;
				background-color: #fff;
				border: 1px solid #ddd;
				font-size:12px
			}
			
			.process-candidates .btn-group{
				width:100%;
				margin-bottom:-1px;
			}
			
			.process-candidates button{
				width:100%;
				border-radius:0;
			}
			
			.process-candidates .dropdown-toggle{
				text-align:left;
				position: relative;
				text-overflow: ellipsis;
			    overflow: hidden;
			    white-space: nowrap;
			    padding-right:15px;
			}
			.process-candidates .dropdown-toggle .caret{
				    position: absolute;
				    top: 10px;
				    right: 10px;
			}
			
			#candidate_groups_container .btn-group:first-child{
				margin-left:-1px;
			}
			
		</style>
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" theme="simple" class="form-horizontal sl_all_form">
			<div class="row">
				<div class="col-md-12 col-sm-12">
				<div class="panel panel-default">
					 <div class="panel-heading">流程任务列表</div>
					 <div class="panel-body">
					 	 <blockquote>
						  <p>
						  	提示：以下任务列表没有先后顺序, <a href="javascript:void(0);" id="show_process_diagram_link" data-process-definition-id="${processDefinition.id}"  class="alert-link">请点击参考流程图</a>
						  	，设置保存后，会合并流程定义文件的相关人员设置，以该设置为准。
						  </p>
						</blockquote>
				  	</div>
				  		<div class="panel-footer" style="height:300px;display:none" id="process_diagram_el" data-status="initial">
						
						</div>
					<table class="table" id="process_defination_task_tab">
				      <thead>
				        <tr>
				          <th width="15%" > 任 务 名 称</th>
				          <th width="25%" > 任 务 描 述</th>
				          <th width="17%" > 任 务 办 理 人设 置  <span class="label label-success">(表示已设置)</span> <span class="label label-danger">(表示未设置)</span></th>
				        </tr>
				      </thead>
				      <tbody>
				      	[#list taskDefinitions.keySet() as key]
 						  <tr id="${taskDefinitions.get(key).key}">
				          <td>
				          	<input type="hidden" name="taskDefinitionId" value="${taskDefinitions.get(key).key}"/>
				          	<input type="hidden" name="processDefinitionId" value="${processDefinition.id}"/>
				          	<input name="taskName" type="text" value="${taskDefinitions.get(key).nameExpression}" class="form-control input-sm" readonly/>
				          </td>
				          <td><input name="taskDesc" type="text" value="${taskDefinitions.get(key).descriptionExpression}" class="form-control input-sm" readonly/></td>
				          <td>
			          		<div class="row">
							  <div class="col-xs-12">
							  	<button class="btn btn-danger btn-sm" type="button" id="candidate_assignments_btn" data-task-key="${taskDefinitions.get(key).key}" data-event="assignment-changed">
							  		<i class="glyphicon glyphicon-cog" aria-hidden="true">设置</i>
							  	</button>
							  </div>
							  </div>
							</div>
				          </td>
				        </tr>
						[/#list]
				      </tbody>
				    </table>
			    </div>
			    </div>
			</div>
			
			<div class="panel panel-default">
			<div class="panel-heading">流程基本信息</div>
			<div class="panel-body">
			<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinition.id}"/>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">名称</label>
			          <div class="col-sm-9">
			            	<input type="text" id="processDefinitionName" name="processDefinitionName" value="${processDefinition.name}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			      <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">类别</label>
			          <div class="col-sm-9">
			            	<input type="text" id="processDefinitionCategory" name="processDefinitionCategory" value="${processDefinition.category}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">版本</label>
			          <div class="col-sm-9">
			            	<input type="text" id="processDefinitionVersion" name="processDefinitionVersion" value="${processDefinition.version}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			      <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">状态</label>
			          <div class="col-sm-9">
			            	<input type="text" id="processDefinitionSuspensionState" name="processDefinitionSuspensionState" 
			            	value="${(processDefinition.suspensionState==1)?string('启用','停用')}" class="form-control" readonly/>
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row">
				 <div class="col-md-6 col-sm-6">
			        <div class="form-group">
			          <label for="" class="col-sm-3 control-label">描述</label>
			          <div class="col-sm-9">
			            	<textarea rows="3" type="text" id="processDefinitionDescription" name="processDefinitionDescription"  style="height:initial;" class="form-control" readonly>${processDefinition.description}</textarea>
			          </div>
			        </div>
			      </div>
			</div>
			</div>
			</div>
		</form>
		</div>		
		<script type="text/javascript" src="${base}/js/processManagement/assignment.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
