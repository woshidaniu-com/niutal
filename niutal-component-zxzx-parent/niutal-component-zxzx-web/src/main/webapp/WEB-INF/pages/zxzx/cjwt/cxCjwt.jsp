<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zxzx/cjwt.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var cjwtGrid = new CjwtGrid();
				loadJqGrid("#tabGrid","#pager",cjwtGrid);
				bdan();
			});
		</script>
	</head>

	<body>
			<div class="toolbox">
				<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
	        </div> 
			<div class="searchtab">
				<s:form namespace="/zxzx" action="cjwt_cxCjwt" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>咨询板块</th>
							<td>
								<s:select list="bkdmList" id="bkdm" name="bkdm" listKey="bkdm" listValue="bkmc" headerKey="" headerValue="全部" theme="simple"></s:select>
							</td>
							<th>是否启用</th>
							<td>
								<s:select name="sffb" id="sffb" list="#{'':'全部','1':'启用','0':'关闭'}"  theme="simple"></s:select>
							</td>
							<th>咨询主题</th>
							<td>
								<s:textfield name="wtbt" id="wtbt" theme="simple"></s:textfield>
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
