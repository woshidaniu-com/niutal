[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
		
	</head>
	<body>
    	<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(id_:用户名, first_:姓名)"/] 
		[/@q.panel]
		<!--查询条件  结束  -->
       <!--查询条件  结束  -->
		<div class="row formbox">
			<div class="col-md-6">
				<form class="form-inline">
				  <div class="checkbox">
				    <label>
				      <input type="checkbox" checked="checked" id="tabUnassignedUserGrid_chk"/> 设置为搜索列表
				    </label>
				  </div>
				</form>
				<table id = "tabUnassignedUserGrid"></table>
			</div>
			<div class="col-md-6">
				<form class="form-inline">
				  <div class="checkbox pull-left">
				    <label>
				      <input type="checkbox" id="tabAssignedUserGrid_chk"> 设置为搜索列表
				    </label>
				  </div>
				</form>
				<table id = "tabAssignedUserGrid"></table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			var tabUnassignedUserGrid = {
				 url: _path+'/processDefinition/assignment/0/list/userData.zf',
				 uniqueId: "id",
				 classes: "table table-condensed",
				 height: 500,
				 pageSize:	10,
				 pageList:	[10,30,50,100],
				 showColumns:		false,
		         showRefresh:		false,
		         showToggle:		false,
		         clickToSelect:         false,
				 columns: [
				 		{checkbox: true }, 
			            {field: 'id', title: ' 用 户 ', sortable:false, formatter:function(value,row,index){
			            	return "<span class='text-danger'>"+value+"["+row['firstName']+"]</span>";
			            }},
			            {field: 'id',
			            title: '<button name="batchAddAssignmentUserBtn" class="btn btn-success btn-xs" onclick="batchAddAssignmentUserBtn();">批量添加</button>',align:'right', formatter:function(value,row,index){
			            	return "<button name='addAssignmentUserBtn' class='btn btn-warning btn-xs' onclick='addAssignmentUser(\"" +value+ "\");'> 设置办理人 </span>";
			            }}
		          ],
		        searchParams:function(){
			   	   	var map = $.search.getSearchMap();
			   	   	map['procDefId'] = '${processDefinitonId}';
			   	   	map['taskDefId'] = '${taskDefinitionId}';
       				return map;
		       	}
			};
			
			var tabAssignedUserGrid = {
				 url: _path+'/processDefinition/assignment/1/list/userData.zf',
				 uniqueId: "assignmentId",
				 classes: "table table-condensed",
				 height: 500,
				 pageSize:	10,
				 pageList:	[10,30,50,100],
				 showColumns:		false,
		         showRefresh:		false,
		         showToggle:		false,
		         clickToSelect:         false,
				 columns: [
				 		{checkbox: true }, 
				 		{field:'assignmentId', title:'assignmentId', visible:false,hidden:true},
			            {field: 'id', title: ' 用 户 ', sortable:false, formatter:function(value,row,index){
			            	return "<span class='text-success'>"+value+"["+row['firstName']+"]</span>";
			            }},
			            {field: 'id',title: '<button name="batchDelAssignmentUserBtn" class="btn btn-danger btn-xs" onclick="batchDelAssignmentUserBtn();" >批量删除</button>',align:'right',sortable:false, formatter:function(value,row,index){
			            	return "<button class='btn btn-warning btn-sm' onclick='delAssignmentUser(\"" +row['assignmentId']+ "\");'> 删除办理人 </span>";
			            }}
		          ],
		        searchParams:function(){
			   	   	var map = $.search.getSearchMap();
			   	   	map['procDefId'] = '${processDefinitonId}';
			   	   	map['taskDefId'] = '${taskDefinitionId}';
       				return map;
		       	}
			};
			
			$('#tabUnassignedUserGrid').loadGrid(tabUnassignedUserGrid);
			$('#tabAssignedUserGrid').loadGrid(tabAssignedUserGrid);
			
			$(":button[name=search_button]").bind("click",function(){
				if($('#tabUnassignedUserGrid_chk').is(':checked')){
					$('#tabUnassignedUserGrid').refreshGrid();
				}
				if($('#tabAssignedUserGrid_chk').is(':checked')){
					$('#tabAssignedUserGrid').refreshGrid();
				}
			});
			
			
			});


		function delAssignmentUser(assignmentId){
			$.post(_path + '/processDefinition/assignment/' + assignmentId + '/delAssignment.zf',
				{},
				function(responseText){
					if(responseText["status"] == "success"){
						$('#tabUnassignedUserGrid').refreshGrid();
						$('#tabAssignedUserGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				},'json');
		}

		function addAssignmentUser(zgh){
				$.post(_path + '/processDefinition/assignment/' + zgh + '/addUser.zf',
				{"processDefintionId": '${processDefinitonId}',"taskDefintionId": '${taskDefinitionId}'},
				function(responseText){
					if(responseText["status"] == "success"){
						$('#tabUnassignedUserGrid').refreshGrid();
						$('#tabAssignedUserGrid').refreshGrid();
						
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				},'json');
			}
		
			
		function batchDelAssignmentUserBtn(){
			var ids = $("#tabAssignedUserGrid").getKeys();
			if (ids.length == 0) {
				$.alert('请选择您要删除的办理人！');
				return false;
			}
			$.post(_path + '/processDefinition/assignment/batchDelAssignment.zf', {
					"assignmentIds" : ids.join(",")
				}, function(responseText) {
					if(responseText["status"] == "success"){
						$('#tabUnassignedUserGrid').refreshGrid();
						$('#tabAssignedUserGrid').refreshGrid();
						$('[data-event="assignment-changed"]').trigger('assignment.changed.event', ['${taskDefinitionId}']);
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
								
		function batchAddAssignmentUserBtn(){
			var ids = $("#tabUnassignedUserGrid").getKeys();
			if (ids.length == 0) {
				$.alert('请选择您要增加的办理人！');
				return false;
			}
			$.post(_path + '/processDefinition/assignment/batchAddUser.zf', {
					"processDefintionId": '${processDefinitonId}',
					"taskDefintionId": '${taskDefinitionId}',
					"userNames" : ids.join(",")
				}, function(responseText) {
					if(responseText["status"] == "success"){
						$('#tabUnassignedUserGrid').refreshGrid();
						$('#tabAssignedUserGrid').refreshGrid();
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