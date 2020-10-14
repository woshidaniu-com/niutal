<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/licenseManagement/cxLicense.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var licenseGrid = new LicenseGrid();
				loadJqGrid("#tabGrid","#pager",licenseGrid);
				bdan();
			});
		</script>
	</head>

	<body>
			<div class="toolbox">
				<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
	        </div> 
			<div class="searchtab">
				<s:form id="license-management-form" namespace="/license-management" action="authdata_cxLicense" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>机构代码</th>
							<td>
								<s:textfield name="authUserCode" id="authUserCode" theme="simple"></s:textfield>
							</td>
							<th>机构名称</th>
							<td>
								<s:textfield name="authUser" id="authUser" theme="simple"></s:textfield>
							</td>
							<td>
								<div class="btn">
									<button type="button" class="btn_cx" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
