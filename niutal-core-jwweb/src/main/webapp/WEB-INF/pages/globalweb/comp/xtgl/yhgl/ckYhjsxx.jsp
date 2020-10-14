<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<body>
		<s:form  method="post" theme="simple" role="form" cssClass="form-horizontal sl_all_form">
			<table width="100%" border="0" style="margin-bottom: 0px;min-height: 200px;" class=" table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
				<tbody>
					<s:if test="jsList!=null and jsList.size() > 0 ">
						<tr>
							<td width="15%" align="right" valign="middle" style="vertical-align:middle;" rowspan="<s:property value="jsList.size()"/>">
								<label>已有角色<s:property value="jsList.size()"/>个&nbsp;</label>
							</td>
							<td>
								<s:property value="jsList[0].jsmc"/>
							</td>
						</tr>
						<s:if test="jsList.size() > 1">
							<s:iterator value="jsList" begin="1">
							<tr>
								<td>
									<s:property value="jsmc"/>
								</td>
							</tr>
							</s:iterator>
						</s:if>
					</s:if>
					<s:else>
						<tr>
							<td style="min-height: 200px;">
								该用户尚无角色!
							</td>
						</tr>
					</s:else>
				</tbody>
			</table>
		</s:form>
	</body>
</html>