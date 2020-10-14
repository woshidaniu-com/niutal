<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/js/plugins/bpm/process.css?ver=${messageUtil("niutal.cssVersion")}" />
	</head>
	<body>
	<div class="bpm-process panel panel-primary" id="bpm-task-process-form">
		<input type="hidden" name="decision.processInstanceId" value="${taskInfo.processInstanceId}"/>
		<input type="hidden" name="decision.executionId" value="${taskInfo.executionId}"/>
		<input type="hidden" name="decision.taskId" value="${taskInfo.id}"/>
		<input type="hidden" name="decision.historicActivityId" data-toggle="bpm-process-fallback-event" />
		
		<input type="hidden" name="taskAssignee" value="${taskInfo.assignee}"/>
	    <div class="panel-heading"> 审 批 处 理 </div>
	    <div class="panel-footer">
	    	 <div class="form-group" id="bpm-task-process-form-p-decision">
	    	 	
	    	 	<!-- 这块代码需要根据流程定义ID和任务定义ID去动态查询 -->
	            <div class="radio radio-success radio-inline text-success pDecision">
	                <input type="radio" class="styled" style-data="success" id="p_decision_PASS" value="PASS" name="decision.decision" checked>
	                <label for="p_decision_PASS">通 过</label>
	            </div>
	            <div class="radio radio-danger radio-inline text-danger pDecision">
	                <input type="radio" class="styled" style-data="danger" id="p_decision_NOPASS" value="NOPASS" name="decision.decision">
	                <label for="p_decision_NOPASS">不 通 过</label>
	            </div>
	            <div class="radio radio-warning radio-inline text-warning pDecision">
	                <input type="radio" class="styled" style-data="warning" id="p_decision_FALLBACK" value="FALLBACK" name="decision.decision">
	                <label for="p_decision_FALLBACK">退 回</label>
	            </div>
	            <!-- 这块代码需要根据流程定义ID和任务定义ID去动态查询 -->
	            
	          </div>
	          
	          <div class="form-group has-warning hidden" id="bpm-task-process-form-fallback">
	          	<blockquote>
				  <p class="text-warning">可退回节点</p>
				</blockquote>
				
			  </div>  
			  
	          <!-- 这块代码需要根据流程定义ID和任务定义ID去动态查询 -->
	          <div class="form-group" class="pDecisionMessage" id="bpm-task-process-form-p-decision-message">
	          		<div class="row">
	          			<div class="col-xs-10">
	          				<textarea id="p_decision_message" name="decision.decisionMessage" class="form-control" rows="3" maxlength="200"  placeholder="请填写意见"></textarea>
	          			</div>
	          			<div class="col-xs-2">
	          				<p class="text-info common-message-add hide" id="commonMessageAddEl">
	          					<a href='javascript:void(0);' class="label label-warning">设置为常用意见</a>
	          					
	          				</p>
	          			</div>
	          			<div class="col-xs-10">
	          				<div class="panel-group" id="bpm-task-process-common-messages-panel" role="tablist" aria-multiselectable="true">
					          <div class="panel panel-warning">
					            <div class="panel-heading" role="tab" id="bpm-task-process-common-messages">
					              <h4 class="panel-title">
					                <a class="collapsed" id="bpm-task-process-common-messages-link" data-toggle="collapse" data-parent="#bpm-task-process-common-messages-panel" href="#bpm-task-process-common-messages-list" aria-expanded="false" aria-controls="bpm-task-process-common-messages-list">
					                  	常用意见
					                  	<span class="caret"></span>
					                </a>
					              </h4>
					            </div>
					            <div id="bpm-task-process-common-messages-list" class="panel-collapse collapse" role="tabpanel" aria-labelledby="bpm-task-process-common-messages-list">
					                <table class="table table-condensed">
					                  <tbody>
										
					                  </tbody>
					                </table>
					            </div>
					          </div>
					        </div>
	          			</div>
	          		</div>
			  </div>
	          <!-- 这块代码需要根据流程定义ID和任务定义ID去动态查询 -->
	    </div>
	</div>
	</body>
</html>