<!doctype html>
<html>
	<body>
		<form id="ajaxForm" method="post" theme="simple" role="form" cssClass="form-horizontal sl_all_form">
			<table width="100%" border="0" style="margin-bottom: 0px" class=" table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
				<tbody>
					<tr>
						<td width="15%" align="right">
							用户名&nbsp;
						</td>
						<td>
							${model.zgh }
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							姓名&nbsp;
						</td>
						<td>
							${model.xm}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							邮箱&nbsp;
						</td>
						<td>
							${model.dzyx}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							联系电话&nbsp;
						</td>
						<td>
							${model.lxdh}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							部门&nbsp;
						</td>
						<td>
							${model.jgmc}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							所属角色&nbsp;
						</td>
						<td>
							${model.jsxx}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							启用状态&nbsp;
						</td>
						<td>
							[#if model.sfqy == 0]
								<span class="label label-danger"> 停  用 </span>
							[#else]
								<span class="label label-success"> 启  用 </span>
							[/#if]
						</td>
					</tr>
					[#if messageUtil("userManage.showUserType") == 'true']
					<tr>
						<td width="15%" align="right">
							用户类型&nbsp;
						</td>
						<td>
							[#if model.yhlx == 'admin']
								管理员
							[/#if]
							[#if model.yhlx == 'teacher']
								教师
							[/#if]
							[#if model.yhlx == 'student']
								学生
							[/#if]
							
						</td>
					</tr>
					[/#if]
				</tbody>
			</table>
		</form>
	</body>
</html>