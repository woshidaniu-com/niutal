<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!-- tool bar-start  -->
	<div class="row sl_add_btn">
		<div class="col-sm-12">
			<!-- 加载当前菜单栏目下操作    : N0107  -->
	     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
		</div>
	</div>
	<!-- tool bar-end  -->
	<form class="form-horizontal sl_all_form">
		<div class="row">
			<div class="col-md-4 col-sm-4">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						功能模块
					</label>
					<div class="col-sm-8">
						<s:select cssClass="form-control chosen-select" name="gnmkdm" list="gnmkdmPairList"
							listKey="key" listValue="value" id="gnmkdm_query" headerKey=""
							headerValue="全部"></s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#gnmkdm_query').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-4">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						操作代码
					</label>
					<div class="col-sm-8">
						<s:select cssClass="form-control chosen-select" name="czdm" list="czdmPairList"
							listKey="key" listValue="value" id="czdm_query" headerKey=""
							headerValue="全部"></s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#czdm_query').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- btn-start  -->
	<div class="row sl_aff_btn" id="searchResult">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go">
				查 询
			</button>
		</div>
	</div>
	<!-- btn-end  -->
	<!-- table-start  -->
	<table id="tabGrid"></table>
	<div id="pager"></div>
	<!-- table-end  -->
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xtczms/xtczms.js?ver=<%=jsVersion%>"></script>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/kindeditor.ini"%>
</html>