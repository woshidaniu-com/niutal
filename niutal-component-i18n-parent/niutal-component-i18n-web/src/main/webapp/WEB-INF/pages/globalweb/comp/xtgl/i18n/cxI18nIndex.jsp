<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<div class="row sl_add_btn">
		<div class="col-sm-12">
			<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
		</div>
	</div>

	<s:form cssClass="form-horizontal sl_all_form" role="form" name="form"
		method="post" action="" theme="simple" onsubmit="return false;">
		<div class="row" id="autogrid">
			<div class="col-md-4 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">资源内容</label>
					<div class="col-sm-8">
						<s:textfield id="res_key_cx" name="res_key_cx" cssClass="form-control"
							placeholder="按内容中文或英文模糊查询"></s:textfield>
					</div>
				</div>
			</div>
		</div>
	</s:form>
	<!--查询按钮  开始-->
	<div class="row sl_aff_btn" id="searchBox">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go">查询</button>
		</div>
	</div>
	<!--查询按钮  结束-->
	<table id="tabGrid"></table>
	<div id="pager"></div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/autocomplete.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/i18n/i18nIndex.js?ver=<%=jsVersion%>"></script>
</html>