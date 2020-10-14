[#assign encoder=JspTaglibs["https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"] /]
<!doctype html>
<html>
	<head>
	</head>
	<body>
		<table width="100%" border="0" style="margin-bottom: 0px" class=" table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
			<tbody>
				<tr>
					<td width="15%" align="right">
						角色名称
					</td>
					<td>
						${model.jsmc}
					</td>
					<td width="15%" align="right">
						启用状态
					</td>
					<td>
						[#if model.qyzt == 0]
							<span class="label label-danger"> 停  用 </span>
						[#else]
							<span class="label label-success"> 启  用 </span>
						[/#if]
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						角色说明
					</td>
					<td colspan="3">
						[@encoder.forHtmlContent value="${model.jssm}"][/@encoder.forHtmlContent]
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>