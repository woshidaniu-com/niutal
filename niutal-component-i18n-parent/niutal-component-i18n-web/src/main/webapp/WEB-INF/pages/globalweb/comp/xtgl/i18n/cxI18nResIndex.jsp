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
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">文件类型</label>
					<div class="col-sm-8">
						<s:select list="#{'js':'javasript', 'properties': 'properties' }"
							id="res_type_cx" name="res_type_cx" listKey="key"
							listValue="value" headerKey="" headerValue="全部"
							cssClass="form-control chosen-select"></s:select>
						<SCRIPT type="text/javascript">
							jQuery('#res_type_cx').trigger("chosen");
						</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">文件代码</label>
					<div class="col-sm-8">
						<s:select list="codeList" id="res_code_cx" name="res_code_cx"
							listKey="res_code" listValue="res_desc" headerKey=""
							headerValue="全部" cssClass="form-control chosen-select"></s:select>
						<SCRIPT type="text/javascript">
							jQuery('#res_code_cx').trigger("chosen");
						</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">文件名称</label>
					<div class="col-sm-8">
						<s:textfield id="res_name_cx" name="res_name_cx"
							cssClass="form-control" placeholder="按文件代码或名称模糊查询"></s:textfield>
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
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/i18n/i18nResIndex.js?ver=<%=jsVersion%>"></script>
</html>