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
						操作用户&nbsp;
					</td>
					<td>
						<s:property value="yhm" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						操作类型&nbsp;
					</td>
					<td>
						<s:property value="czlx" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						操作日期&nbsp;
					</td>
					<td>
						<s:property value="czrq" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						操作模块&nbsp;
					</td>
					<td>
						<s:property value="czmk" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						主机名&nbsp;
					</td>
					<td>
						<s:property value="zjm" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						IP地址&nbsp;
					</td>
					<td>
						<s:property value="ipdz" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						业务名称&nbsp;
					</td>
					<td>
						<s:property value="ywmc" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						操作描述&nbsp;
					</td>
					<td>
						${czms}
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
</body>
</html>
