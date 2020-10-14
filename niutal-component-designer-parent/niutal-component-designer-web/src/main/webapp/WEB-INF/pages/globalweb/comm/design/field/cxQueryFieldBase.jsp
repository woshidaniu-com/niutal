<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<s:form id="ajaxForm" method="post" theme="simple" role="form" cssClass="form-horizontal sl_all_form">
		<table width="100%" border="0" style="margin-bottom: 0px" class=" table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
			<tbody>
				<tr>
					<td width="15%" align="right">
						<label>功能模块</label>&nbsp;
					</td>
					<td>
						<s:property value="model.gnmkmc" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						<label>操作名称</label>&nbsp;
					</td>
					<td>
						<s:property value="model.czmc" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						<label>备	注</label>&nbsp;
					</td>
					<td>
						<s:property value="model.bz" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						<label>操作描述</label>&nbsp;
					</td>
					<td>
						${model.czms}
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
</body>
</html>
