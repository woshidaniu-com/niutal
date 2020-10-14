<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<body>
		<s:form id="ajaxForm" method="post" theme="simple" role="form" cssClass="form-horizontal sl_all_form">
			<table width="100%" border="0" style="margin-bottom: 0px" class=" table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
				<tbody>
					<tr>
						<td width="15%" align="right">
							用户名&nbsp;
						</td>
						<td>
							${model.yhm }
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
							Email&nbsp;
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
							${model.sjhm}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							所属机构&nbsp;
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
							${model.jsmc}
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							是否启用&nbsp;
						</td>
						<td>
							<s:if test="sfqy == 0">否</s:if>
							<s:elseif test="sfqy == 1">是</s:elseif>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</body>
</html>