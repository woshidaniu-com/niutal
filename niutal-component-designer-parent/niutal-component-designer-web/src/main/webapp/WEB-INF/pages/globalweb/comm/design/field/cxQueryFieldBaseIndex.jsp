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
		<div class="row sl_add_btn" title="">
			<div class="col-sm-12">
				<input type="hidden" name="gnczStr" id="gnczStr" value="${gnczStr }" />
				<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			</div>
		</div>
		<!-- tool bar-end  -->
		<form class="form-horizontal sl_all_form">
			<div class="row">
				<div class="col-md-4 col-sm-4">
					<div class="form-group">
						<label for="" class="col-md-4 col-sm-4 control-label">
							字段使用范围
						</label>
						<div class="col-md-8 col-sm-8">
							<s:select cssClass="form-control chosen-select" name="field_scope" list="fieldScopeList" validate="{required:true}" listKey="key" listValue="value" id="field_scope_query" ></s:select>
							<SCRIPT type="text/javascript">
					    		jQuery('#field_scope_query').trigger("chosen");
					    	</SCRIPT>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-4">
					<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							字段标题
						</label>
						<div class="col-sm-8">
							<s:textfield cssClass="form-control" id="field_text_query"  name="field_text" placeholder="填写字段标题筛选"></s:textfield>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-4">
					<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							字段名称
						</label>
						<div class="col-sm-8">
							<s:textfield cssClass="form-control" id="field_name_query"  name="field_name" placeholder="填写字段名称筛选"></s:textfield>
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
	<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/base-fields.js?ver=<%=jsVersion%>"></script>

</html>