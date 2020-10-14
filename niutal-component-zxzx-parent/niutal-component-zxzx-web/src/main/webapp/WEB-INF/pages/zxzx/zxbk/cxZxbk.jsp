<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zxzx/kzdk.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var kzdkGrid = new kzdkGrid();
				loadJqGrid("#tabGrid","#pager",kzdkGrid);
				bdan();
			});
		</script>
	</head>

	<body>
			<div class="toolbox">
				<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
	        </div> 
			<div class="searchtab">
				<s:form namespace="/zxzx" action="kzdk_cxkzdk" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>板块名称</th>
							<td>
								<s:textfield name="bkmc" id="bkmc" theme="simple"></s:textfield>
							</td>
							<th>是否启用</th>
							<td>
								<s:select name="sfqy" id="sfqy" list="#{'':'全部','1':'启用','0':'关闭'}"  theme="simple"></s:select>
							</td>
							<td>
								<div class="btn">
									<button type="button" class="btn_cx" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
									<button type="button" onclick="searchReset();">
										重 置
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
