<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
	
	<div class="panel panel-default">
	      <div class="panel-heading">
	       <table id="processInfoList" class="table table-hover table-condensed">
				<thead>
					<tr>
						<th> 环 节 名 称 </th>
						<th> 参 与 人 </th>
						<th> 开 始 时 间 </th>
						<th> 结 束 时 间 </th>
						<th> 审 核 信 息 </th>
						<th> 详 细 信 息 </th>
					</tr>
				</thead>
				<tbody>
					[#list traceProcess as item]
				 		<tr>
				 			<td>${item['P_ACT_NAME']}</td>
				 			<td>${item.P_USER_ID}</td>
				 			<td>${item.P_START_TIME}</td>
				 			<td>${item.P_END_TIME}</td>
				 			<td><strong>${item.P_TASK_COMMENT}</strong></td>
				 			<td>${item.P_TASK_FULL_COMMENT}</td>
				 		</tr>
				 	[/#list]
				</tbody>
			</table>
	      </div>
	      
	      <div class="panel-body">
				<div style="height:200px;">
					<iframe allowtransparency="true" 
						src="${base}/diagram-viewer/index.html?processDefinitionId=${processDefinitionId}&processInstanceId=${processInstnceId}&colorValue=${colorValue}" name="process-diagram-view-iframe" width="100%" height="100%" frameborder="0"></iframe>
				</div>
	      </div>
	      
	  </div>

	</body>
</html>
