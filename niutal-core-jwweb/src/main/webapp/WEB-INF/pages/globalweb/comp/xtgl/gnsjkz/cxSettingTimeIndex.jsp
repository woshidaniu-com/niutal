<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	.radio, .checkbox {
	    margin-bottom: 0px !important;
	    margin-top: 0px !important;
	}
	.radio input[type="radio"], 
	.radio-inline input[type="radio"], 
	.checkbox input[type="checkbox"], 
	.checkbox-inline input[type="checkbox"] {
	  	margin-top: 2px !important;
	}
	</style>
</head>
<body>
	<!--操作按钮 开始 -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12"  id="btn_box">
	      	<!-- 加载当前菜单栏目下操作     -->
      		<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!--操作按钮 结束  -->
	<!-- search start -->
	<form class="form-horizontal sl_all_form">
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						功能模块名称
					</label>
					<div class="col-sm-8">
						<input class="form-control" id="gnmkmc" name="gnmkmc"
							placeholder="功能模块名称模糊查询" maxlength="20"/>
					</div>
				</div>
			</div>

		</div>
	</form>
	<div class="row sl_aff_btn" id="searchResult">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">
				查 询
			</button>
		</div>
	</div>
	<!-- search-end  -->
	<s:form   action="/xtgl/timeSetting_bcSettingTime.html"   id="ajaxForm"  role="form" name="form" method="post" onsubmit="return false;">
   	<!-- table-start  -->
		<table id="tabGrid"></table>
	<!-- table-end  -->
	</s:form>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/gnsjkz/settingTime.js?ver=<%=jsVersion%>"></script>	
</html>