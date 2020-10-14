<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/drdcsj/showruler.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function() {
				loadRuler();
			});
		</script>
	</head>
	<body>
		<br />
		<s:form name="importForm" id="importForm"
			action="niutal/dr/import_importData.html" method="post"
			theme="simple">
			<input type="hidden" id="drmkdm" name="drmkdm" value="${drmkdm}" />
			<div class="tab" style="overflow-x: hidden">
				<table width="100%" border="0" class=" formlist" cellpadding="0"
					cellspacing="0">
					<thead>
						<tr>
							<td align='right' style="width: 20%">
								列名称
							</td>
							<td align='center'>
								验证信息
							</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div>
					<table width="100%" border="0" class="formlist" id="below"
						style="position: fixed; _position: absolute; bottom: 0;">
						<tfoot>
							<tr>
								<td colspan="2">
									<div class="btn">
										<button type="button" onclick="iFClose();" id="buttonClose">
											关 闭
										</button>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>

		</s:form>
	</body>
</html>