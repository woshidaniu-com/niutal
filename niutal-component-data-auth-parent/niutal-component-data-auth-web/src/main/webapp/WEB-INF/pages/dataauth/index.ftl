<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/css/dataauth/index.css" />
	</head>
	<body>
		<div class="col-md-2">
			<h4>角色和用户</h4>
			<div class="panel panel-left">
				<div class="tree">
				    <ul>
				    [#list roles as r]
				    	<li>
		                    <span class="treeitem" item-type="role" item-data="${r.JSDM}"><i class="fa fa-plus-circle"></i>${r.JSMC}</span>
		                    <ul style="display: none;">
		                    	[#list users as u]
			                    	[#if r.JSDM==u.JSDM]
			                        <li><span class="treeitem" item-type="user" item-data="${u.YHM}">${u.YHM}</span></li>
			                        [/#if]
		                        [/#list]
		                    </ul>
		                </li>
				    [/#list]
				</div>
			</div>
		</div>
		<div class="col-md-10">
			<h4>数据权限<span style="color: red;font-weight:normal;font-size: 14px">（注意：角色的数据权限适用于所有关联该角色的用户,直接分配到用户的权限具有最优先级别)</span></h4>
			<div class="panel panel-right">
				<div id="data-rule-group" class="group">
					
				</div>
				<div id="data-rule-group-item" class="group">
					
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${base}/js/dataauth/tree.js"></script>
		<script type="text/javascript" src="${base}/js/dataauth/index.js"></script>
	</body>
</html>