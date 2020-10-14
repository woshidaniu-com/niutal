<!doctype html>
<html>
	<head>

	</head>

	<body>
	
		<div>
		  <ul class="nav nav-tabs" role="tablist" id="assignment_type_tab">
		    <li role="presentation">
		    	<a href="#UserSetting" aria-controls="UserSetting" role="tab" data-toggle="tab"><i class="glyphicon glyphicon-cog">用户</i></a>
		    </li>
		    <li role="presentation">
		    	<a href="#GroupSetting" aria-controls="GroupSetting" role="tab" data-toggle="tab"><i class="glyphicon glyphicon-cog">用户组</i></a>
		    </li>
		  </ul>
		  <div class="tab-content">
		    <div role="tabpanel" class="tab-pane" id="UserSetting"  url="${base}/processDefinition/assignment/${processDefinitonId}/${taskDefinitionId}/list/user.zf"></div>
		    <div role="tabpanel" class="tab-pane" id="GroupSetting" url="${base}/processDefinition/assignment/${processDefinitonId}/${taskDefinitionId}/list/group.zf"></div>
		  </div>
		
		</div>
		<script type="text/javascript">
			$(function(){
				
				$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
				  var new_tab = e.target;
				  var old_tab = e.relatedTarget;
				  var targetTabContent = $($(new_tab).attr('href'));
				  var oldTabContent = $($(old_tab).attr('href'));
				  oldTabContent.children().remove();
				  targetTabContent.append('<i class="fa fa-spinner fa-spin fa-2x fa-fw"></i>');
				  targetTabContent.load(targetTabContent.attr('url'), function(){
				  	
				  });
				  
				});
			
				$('#assignment_type_tab a[href="#UserSetting"]').tab('show');
				
			});
		</script>
	</body>
</html>
