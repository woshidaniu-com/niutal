<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	#input-group .chosen-single{
		border-radius: 4px 0 0 4px;
		border-right: 0px;
	}
	.widget_item{
		border: 2px dashed  #e6e6e6;
		border-radius: 6px;
		font-size: 18px;
		line-height: 1.33;
		padding: 5px 16px;
		text-align: center;
	}
	.btn_design{
		border: 2px dashed gray;
		padding: 5px 5px;
		line-height: 1;
	}	
	.btn_design:focus {
		outline:none;
	  	-moz-outline:none;
	 }
	</style>
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
					<label for="" class="col-sm-4 control-label">
						组件名称
					</label>
					<div class="col-sm-8">
						<s:textfield cssClass="form-control" id="widget_name_query"  name="widget_name" placeholder="功能组件名称（如：jqgrid列表）"></s:textfield>
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
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/base-widgets.js?ver=<%=jsVersion%>"></script>
</html>