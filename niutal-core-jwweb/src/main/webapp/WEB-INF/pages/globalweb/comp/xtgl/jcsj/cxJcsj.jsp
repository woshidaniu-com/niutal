<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!-- tool bar-start  -->
	<div class="row sl_add_btn">
		<div class="col-sm-12">
			<!-- 加载当前菜单栏目下操作    : N010501  -->
	     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
		</div>
	</div>
	<!-- tool bar-end  -->
	<form class="form-horizontal sl_all_form" id="sl_all_form" name="form"
		method="post" action="/xtgl/jcsj_cxJcsj.html" theme="simple">
		<div class="row">
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						级别
					</label>
					<div class="col-sm-8">
						<s:select cssClass="form-control chosen-select" name="xtjb" list="#{'xt':'系统','yw':'业务'}"
							listKey="key" listValue="value" id="cx_xtjb" headerKey=""
							headerValue="全部 ">
						</s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#cx_xtjb').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						类型
					</label>
					<div class="col-sm-8">
						<s:select cssClass="form-control chosen-select" name="lxdm" list="lxdmList"
							listKey="lxdm" listValue="lxmc" id="cx_lxdm" headerKey=""
							headerValue="全部 ">
						</s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#cx_lxdm').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						代码
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="dm" id="cx_dm"
							placeholder="支持代码模糊查询" />
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						名称
					</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" name="mc" id="cx_mc"
							placeholder="支持名称模糊查询" />
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- btn-start  -->
	<div class="row sl_aff_btn">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">查 询</button>
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
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/jcsj/jcsj.js?ver=<%=jsVersion%>"></script>
</html>
