<!doctype html>
<html>
<head>
</head>
<body>
	<form id="ajaxForm" method="post" data-toggle="validation"  data-widget='{"onkeyup": false}' 
		action="save.zf" theme="simple" class="form-horizontal ">
    	<input type="hidden" name="groupId" value="${ruleGroup.groupId?default("")}"/>
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-12">
					<pre style="color:red;">
  *子选项SQL中itemKey和itemName是固定的别名，分别是选项值和显示名称.
  *子选项SQL中可以使用的占位信息：{yhm} - 选中的用户  {jsdm} - 当前选中的角色
  *权限类型表示当前权限只能分配给这个类型(用户或角色)
  *同一种权限代码的权限只能被设置一个(即为同一组单选项)
					</pre>
				</div>
		        <div class="col-sm-12">
		            <div class="form-group">
		                <label>权限代码</label>
		                <input type="text"name="groupCode" class="form-control"  value="${ruleGroup.groupCode?default("")}" placeholder="请输入权限代码" data-rules='{"required":true}'>
		            </div>
		        </div>
		        <div class="col-sm-12">
		            <div class="form-group">
		                <label>权限名称</label>
		                <input type="text" name="groupName" class="form-control"  value="${ruleGroup.groupName?default("")}" placeholder="请输入权限名称" data-rules='{"required":true}'>
		            </div>
		        </div>
		        <div class="col-sm-12">
		            <div class="form-group">
		                <label>权限类型</label>
		                <select id="f_groupType" name="groupType" class="chosen-select" data-rules='{"required":true}'>
							<option value="user" >用户</option>
							<option value="role" [#if ruleGroup.groupType == 'role']selected[/#if]>角色</option>
						</select>
						<script>
							$('#f_groupType').chosen({allow_single_deselect: true,no_results_text: "没有匹配结果",search_contains: false,disable_search:true})
						</script>
		            </div>
		        </div>
		        <div class="col-sm-12">
			    	<label>子选项SQL</label>
			        <textarea placeholder="子选项SQL,例:select xh as itemKey,xm as itemName from user" name="selectItem" rows="4" class="form-control">${ruleGroup.selectItem?default("")}</textarea>
			    </div>
		    </div>
		</div>
	</form>
</body>
</html>