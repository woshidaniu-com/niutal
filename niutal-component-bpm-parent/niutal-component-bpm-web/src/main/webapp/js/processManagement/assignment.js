$(function() {

	var process_defination_tab = $('#process_defination_task_tab');
	initialProcessDefinationTable();
	var processDefinitionId = $('#processDefinitionId').val();
	
	$('[data-event="assignment-changed"]').on('assignment.changed.event', function(event, taskDefintionId){
	    if(taskDefintionId){
		var $this = $(this);
		if(taskDefintionId == $(this).attr('data-task-key')){
		   $.getJSON(_path + '/processDefinition/assignment/' + processDefinitionId + '/' + taskDefintionId + '/getTaskAssignmentCount.zf',function(data){
		       if(data && data['assignmentCount'] > 0){
			   $this.removeClass('btn-danger').addClass('btn-success');
		       }else{
			   $this.removeClass('btn-success').addClass('btn-danger');
		       }
		   });
		    
		}
	    }
	});
	
	if ($.founded(processDefinitionId)) {
		var url = _path + "/processDefinition/assignment/" + processDefinitionId + "/getAssignment.zf";
		$.getJSON(url,{},function(data) {
			if ($.founded(data['assignment'])) {
				var assignment = data['assignment'];
				$.each(assignment,function(i, n) {
					var taskRow = process_defination_tab.find('#'+n['taskDefinitionId']);
					var userId = n['userId'];
					var groupId = n['groupId'];
					var clazzId = n['clazzId'];
					if ($.founded(userId) || $.founded(groupId)　|| $.founded(clazzId)) {
					    var assignmentBtn =  taskRow.find('#candidate_assignments_btn');
					    //assignmentBtn.find('#candidate_assignments_btn').html('<i class="glyphicon glyphicon-cog" aria-hidden="true"></i>');
					    assignmentBtn.removeClass('btn-danger').addClass('btn-success');
					}
				});
			}
		});
	}

	$('#show_process_diagram_link').click(function(){
		if($('#process_diagram_el').attr('data-status') == 'initial'){
			var processDefinitionId = $(this).attr('data-process-definition-id');
			var diagram_url = _path + '/diagram-viewer/index.html?processDefinitionId=' + processDefinitionId + '&processInstanceId=';
			var diagram_iframe = $('<iframe allowtransparency="true" name="process-diagram-view-iframe" width="100%" height="100%" frameborder="0"></iframe');
			diagram_iframe.attr('src', diagram_url);
			$('#process_diagram_el').attr('data-status' , 'loaded');
			$('#process_diagram_el').css('display', 'block');
			$('#process_diagram_el').append(diagram_iframe);
		}else{
			$('#process_diagram_el').toggle();
		}
	});
	
	function createProcessDefinationTaskCandidateUserEl(id, name) {
		var el = $('<div>').addClass('btn-group').prop("title", name);
		var hidden = $('<input type="hidden" name="candidateUsers" value="' + id + '">');
		el.append('<button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown">'
			+ '<i class="fa fa-user" aria-hidden="true"></i>'
			+ name
			+ '['
			+ id
			+ ']<span class="caret"></span></button>');
		var ul = $('<ul class="dropdown-menu" role="menu"></ul>')
		var action = $('<a href="#" id="del-candidate-user"> 删 除 </a>').click(
			function() {
				el.remove();
			});
		$('<li>').append(action).appendTo(ul);
		el.append(hidden).append(ul);
		return el;
	}

	function createProcessDefinationTaskCandidateGroupEl(id, name) {
		var el = $('<div>').addClass('btn-group').prop("title", name);
		var hidden = $('<input type="hidden" name="candidateGroups" value="'+ id + '">');
		el.append('<button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown">'
			+ '<i class="fa fa-users" aria-hidden="true"></i>'
			+ name + '<span class="caret"></span></button>');
		var ul = $('<ul class="dropdown-menu" role="menu"></ul>')
		var action = $('<a href="#" id="del-candidate-group"> 删 除 </a>').click(
			function() {
				el.remove();
			});
		$('<li>').append(action).appendTo(ul);
		el.append(hidden).append(ul);
		return el;
	}

	var assignmentSetupConfig = {
		width : 1200,
		modalName : "assignmentSetupConfig",
		formName : "ajaxForm",
		offAtOnce : false
	};


	/**
	 * 初始化任务定义表格
	 * 
	 * @returns
	 */
	 function initialProcessDefinationTable() {
	 	var tbody = process_defination_tab.find('tbody');
	 	var rows = tbody.find('tr');
	 	rows.each(function(index, el) {
	 	    $('#candidate_assignments_btn', el).click(function() {
	 		$.showDialog(_path+ "/processDefinition/assignment/" + $(el).find(':input[name="processDefinitionId"]').val() + "/" + $(el).find(':input[name="taskDefinitionId"]').val() +"/list.zf",'流程任务办理人设置',
	 			$.extend({'candidate_users_container' : $('#candidate_users_container',el)},assignmentSetupConfig));
	 		});
	 	});
	 }

	});