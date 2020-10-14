<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
		.left-div{
			width:275px;
		}
    </style>
</head>
<body>
	<div style="display: none;"><%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%> </div>
	<div class="row">
		<div class="col-md-1 col-sm-1" style="width: 20%;">
			<div class="btn-group-vertical left-div" role="tablist" id="tab_btns">
				<s:if test=" topFuncList != null and topFuncList.size() > 0 ">
				<s:iterator value="topFuncList" status="stat">
					<button style="padding: 5px;text-align: left;" role="tab" data-toggle="tab" class="btn align-left btn-default <s:if test="#stat.index == 0">btn-primary</s:if>"
						type="button" data-func_code="<s:property value="key"/>" href="#<s:property value="key"/>_pane">
						<i class="bigger-100 glyphicon glyphicon-edit"></i>
						<s:property value="value" />
					</button>
				</s:iterator>
				</s:if>
			</div>
		</div>
		<div class="col-md-10 col-sm-10" style="width: 77%; padding-left: 0px;">
			<div class="panel panel-default" style="border: none;">
				<div class="panel-body" style="padding: 0px;">
					<!-- Tab panes -->
					<div class="tab-content" id="tab_content">
						<s:if test=" topFuncList != null and topFuncList.size() > 0 ">
						<s:iterator value="topFuncList" status="stat">
							<div data-func_code="<s:property value="key"/>" class="tab-pane  <s:if test="#stat.index == 0"> active </s:if> tree-responsive" id="<s:property value="key"/>_pane" style="height: 560px;">
								
							</div>
						</s:iterator>
						</s:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/bootstrapTree.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/design-funcs.js?ver=<%=jsVersion%>"></script>
</html>