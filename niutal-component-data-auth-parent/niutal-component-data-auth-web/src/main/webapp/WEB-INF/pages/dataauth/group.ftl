[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
<head>
<style>
.popover {
	word-break: break-all;
}
.tbcontent{
    width: 200px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
</head>
<body>
	<div id="page-content">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel  text-left" style="min-height:600px;">
					<div class="tab-base tab-drop-top">
						<ul class="nav nav-tabs">
							<li class="active">
								<a data-toggle="tab" href="#demo-drolf-tab-1" aria-expanded="true">权限定义</a>
							</li>
							<li class="">
								<a id="rule_tab" data-toggle="tab" href="#demo-drolf-tab-2" aria-expanded="false">权限设计</a>
							</li>
							<li class="">
								<a data-toggle="tab" href="#demo-drolf-tab-3" aria-expanded="false">规则检查</a>
							</li>
						</ul>
						<div class="tab-content" style="min-height:600px;">
							<div id="demo-drolf-tab-1" class="tab-pane fade active in">
								<div class="panel-body">
									<div class="form-inline col-md-8">
										<div class="form-group" style="padding-top:8px;">
											<label for="groupType" class="control-label" style="float:left;width:70px;">权限类型：</label>
											<div style="width:100px;float:left;">
												<select id="groupType" class="chosen-select">
													<option value="">全部</option>
													<option value="user">用户</option>
													<option value="role">角色</option>
												</select>
											</div>
										</div>
										&nbsp;
										<div class="form-group" style="padding-top:8px;">
											<label for="text_search" class="control-label" style="float:left;width:70px;">权限名称：</label>
											<input id="text_search" type="text" class="form-control" placeholder="请输入权限名称...">
										</div>
										&nbsp;
										<div id="btn_search" class="btn zf-btn btn-primary btn-light-primary">查询</div>
									</div>
									<div class="col-md-4 text-right">
					                	[@shiro.hasPermission name="dataauth:datarule:cx"]
										<button type="button" class="btn zf-btn btn-default btn-light-default" id="btn_add">
											<i class="fa fa-plus"></i> 新增
										</button>
										[/@shiro.hasPermission]
									</div>
								</div>
								<div class="col-md-12"><table id="ruleGroupGrid"></table></div>
							</div>
							<div id="demo-drolf-tab-2" class="tab-pane fade">
								<div class="panel-body">
									<div class="form-inline col-md-8">
										<div class="form-group" style="padding-top:8px;">
											<label for="c_text_search" class="control-label" style="float:left;width:70px;">拦截方法：</label>
											<input id="c_text_search" type="text" class="form-control" placeholder="请输入拦截方法关键字...">
										</div>
										&nbsp;
										<div id="c_btn_search" class="btn zf-btn btn-primary btn-light-primary">查询</div>
										&nbsp;&nbsp;
										<label id="l_groupName" style="color:red;"></label>
									</div>
									<div class="col-md-4 text-right">
					                	[@shiro.hasPermission name="dataauth:datarule:cx"]
										<button type="button" class="btn zf-btn btn-default btn-light-default" id="btn_c_add">
											<i class="fa fa-plus"></i> 新增
										</button>
										[/@shiro.hasPermission]
									</div>
								</div>
								<div class="col-md-12"><table id="ruleGrid"></table></div>
							</div>
							<div id="demo-drolf-tab-3" class="tab-pane fade">
								<div class="panel-body">
									<div class="form-inline col-md-12">
										<div class="form-group" style="padding-top:8px;">
											<input id="ck_text_search" type="text" class="form-control" style="width:650px;" 
												placeholder="请输入拦截方法完整名称，例:com.woshidaniu.boot.authz.dao.daointerface.IAuthzUserDao.getPagedList">
										</div>
										&nbsp;
										<div id="ck_btn_search" class="btn zf-btn btn-primary btn-light-primary">查询</div>
									</div>
								</div>
								<div class="col-md-12"><table id="ruleCheckGrid"></table></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	[#include "/globalweb/head/bsTable.ftl" /]
	<script type="text/javascript">
		var addBtn = false;var editBtn = false;var deleteBtn = false;
		[@shiro.hasPermission name="dataauth:datarule:zj"]
			addBtn = true;
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="dataauth:datarule:xg"]
			editBtn = true;
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="dataauth:datarule:sc"]
			deleteBtn = true;
		[/@shiro.hasPermission]
		var groupId = '';
	</script>
	<script type="text/javascript" src="${base}/js/dataauth/group.js"></script>
	<script type="text/javascript" src="${base}/js/dataauth/rule.js"></script>
	<script type="text/javascript" src="${base}/js/dataauth/ruleCheck.js"></script>
</body>
</html>