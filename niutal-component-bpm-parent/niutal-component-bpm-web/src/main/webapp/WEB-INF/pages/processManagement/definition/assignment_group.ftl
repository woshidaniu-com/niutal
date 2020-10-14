[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
		
	</head>
	<body>
    	<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(name_:用户组)"/] 
		[/@q.panel]
		<!--查询条件  结束  -->
       <!--查询条件  结束  -->
		<div class="row formbox">
			<div class="col-md-6">
				<form class="form-inline">
				  <div class="checkbox">
				    <label>
				      <input type="checkbox" checked="checked" id="tabUnassignedGroupGrid_chk"/> 设置为搜索列表
				    </label>
				  </div>
				</form>
				<table id = "tabUnassignedGroupGrid"></table>
			</div>
			<div class="col-md-6">
				<form class="form-inline">
				  <div class="checkbox pull-left">
				    <label>
				      <input type="checkbox" id="tabAssignedGroupGrid_chk"> 设置为搜索列表
				    </label>
				  </div>
				</form>
				<table id = "tabAssignedGroupGrid"></table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			var tabUnassignedGroupGrid = {
				 url: _path+'/processDefinition/assignment/0/list/groupData.zf',
				 uniqueId: "id",
				 classes: "table table-condensed",
				 pageSize:	10,
				 pageList:	[10,30,50,100],
				 showColumns:		false,
		         showRefresh:		false,
		         showToggle:		false,
				 columns: [
				 		{checkbox: true }, 
			            {field: 'name',title: ' 用 户 组 ',sortable:false,formatter:function(value,row,index){
			            	return "<span class='text-danger'>"+value+"</span>";
			            }},
			            {field: 'id',title: '<button name="batchAddAssignmentGroupBtn" class="btn btn-success btn-xs" onclick="batchAddAssignmentGroupBtn();">批量添加</button>',sortable:false, formatter:function(value,row,index){
			            	return "<button name='addAssignmentGroupBtn' class='btn btn-warning btn-xs' onclick='addAssignmentGroup(\"" +value+ "\");'> 设置办理组 </span>";
			            }}
		          ],
		        searchParams:function(){
			   	   	var map = $.search.getSearchMap();
			   	   	map['procDefId'] = '${processDefinitonId}';
			   	   	map['taskDefId'] = '${taskDefinitionId}';
       				return map;
		       	}
			};
			
			var tabAssignedGroupGrid = {
				 url: _path+'/processDefinition/assignment/1/list/groupData.zf',
				 uniqueId: "assignmentId",
				 classes: "table table-condensed",
				 pageSize:	10,
				 pageList:	[10,30,50,100],
				 showColumns:		false,
		         showRefresh:		false,
		         showToggle:		false,
				 columns: [
				 		{checkbox: true }, 
				 		{field:'assignmentId', title:'assignmentId', visible:false,hidden:true},
			            {field: 'name',title: ' 用 户 组 ',sortable:false, formatter:function(value,row,index){
			            	return "<span class='text-success'>"+value+"</span>";
			            }},
			            {field: 'id',title: '<button name="batchDelAssignmentGroupBtn" class="btn btn-danger btn-xs" onclick="batchDelAssignmentGroupBtn();" >批量删除</button>',sortable:false, formatter:function(value,row,index){
			            	return "<button class='btn btn-warning btn-sm' onclick='delAssignmentGroup(\"" +row['assignmentId']+ "\");'> 删除办理人 </span>";
			            }}
		          ],
		        searchParams:function(){
			   	   	var map = $.search.getSearchMap();
			   	   	map['procDefId'] = '${processDefinitonId}';
			   	   	map['taskDefId'] = '${taskDefinitionId}';
       				return map;
		       	}
			};
			
			$('#tabUnassignedGroupGrid').loadGrid(tabUnassignedGroupGrid);
			$('#tabAssignedGroupGrid').loadGrid(tabAssignedGroupGrid);
			
			$(":button[name=search_button]").bind("click",function(){
				if($('#tabUnassignedGroupGrid_chk').is(':checked')){
					$('#tabUnassignedGroupGrid').refreshGrid();
				}
				if($('#tabAssignedGroupGrid_chk').is(':checked')){
					$('#tabAssignedGroupGrid').refreshGrid();
				}
				
			});
			
		});
		
		
		function delAssignmentGroup(assignmentId){
			$.post(_path + '/processDefinition/assignment/' + assignmentId + '/delAssignment.zf',
				{},
				function(responseText){
					if(responseText["status"] == "success"){
						$('#tabUnassignedGroupGrid').refreshGrid();
						$('#tabAssignedGroupGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				},'json');
		}

		function addAssignmentGroup(groupid){
				$.post(_path + '/processDefinition/assignment/' + groupid + '/addGroup.zf',
				{"processDefintionId": '${processDefinitonId}',"taskDefintionId": '${taskDefinitionId}'},
				function(responseText){
					if(responseText["status"] == "success"){
						$('#tabUnassignedGroupGrid').refreshGrid();
						$('#tabAssignedGroupGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				},'json');
			}
		
			
		function batchDelAssignmentGroupBtn(){
			var ids = $("#tabAssignedGroupGrid").getKeys();
			if (ids.length == 0) {
				$.alert('请选择您要删除的办理组！');
				return false;
			}
			$.post(_path + '/processDefinition/assignment/batchDelAssignment.zf', {
					"assignmentIds" : ids.join(",")
				}, function(responseText) {
					if(responseText["status"] == "success"){
						$('#tabUnassignedGroupGrid').refreshGrid();
						$('#tabAssignedGroupGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
								
		function batchAddAssignmentGroupBtn(){
			var ids = $("#tabUnassignedGroupGrid").getKeys();
			if (ids.length == 0) {
				$.alert('请选择您要增加的办理组！');
				return false;
			}
			$.post(_path + '/processDefinition/assignment/batchAddGroup.zf', {
					"processDefintionId": '${processDefinitonId}',
					"taskDefintionId": '${taskDefinitionId}',
					"groups" : ids.join(",")
				}, function(responseText) {
					if(responseText["status"] == "success"){
						$('#tabUnassignedGroupGrid').refreshGrid();
						$('#tabAssignedGroupGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
	</script>
</html>