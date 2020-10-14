<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="zf" uri="/niutal-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!-- tool bar-start  -->
	<div class="row sl_add_btn">
		<div class="col-sm-12">
			<!-- 加载当前菜单栏目下操作    : N010201  -->
	     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
		</div>
	</div>
	<!-- tool bar-end  -->
	<form class="form-horizontal sl_all_form">
		<div class="row">

			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						是否已分配用户
					</label>
					<div class="col-sm-8">
						<s:select cssClass="form-control chosen-select" name="sffpyh" list="sfList"
							listKey="key" listValue="value" id="sffpyh" headerKey=""
							headerValue="全部"></s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#sffpyh').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>

			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						角色名称
					</label>
					<div class="col-sm-8">
						<s:textfield cssClass="form-control" id="jsmc" name="jsmc"
							maxlength="20" placeholder="请输入角色名称进行模糊筛选"  ></s:textfield>
					</div>
				</div>
			</div>

		</div>
	</form>
	<!-- btn-start  -->
	<div class="row sl_aff_btn" id="searchResult">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go" >
				查 询
			</button>
		</div>
	</div>
	<!-- btn-end  -->
	<!-- table-start  -->
	<table id="tabGrid"></table>
	<div id="pager"></div>
	<!-- table-end  -->

	<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
	<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/jsgl/jsgl.js?ver=<%=jsVersion%>"></script>
</body>
</html>